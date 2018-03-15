package org.frc4123.robot.arcadeangler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import com.ctre.phoenix.motion.*;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;


public class MotionProfile {

    /**
     * Example logic for firing and managing motion profiles.
     * This example sends MPs, waits for them to finish
     * Although this code uses a CANTalon, nowhere in this module do we changeMode() or call set() to change the output.
     * This is done in Robot.java to demonstrate how to change control modes on the fly.
     *
     * The only routines we call on Talon are....
     *
     * changeMotionControlFramePeriod
     *
     * getMotionProfileStatus
     * clearMotionProfileHasUnderrun     to get status and potentially clear the error flag.
     *
     * pushMotionProfileTrajectory
     * clearMotionProfileTrajectories
     * processMotionProfileBuffer,   to push/clear, and process the trajectory points.
     *
     * getControlMode, to check if we are in Motion Profile Control mode.
     *
     * Example of advanced features not demonstrated here...
     * [1] Calling pushMotionProfileTrajectory() continuously while the Talon executes the motion profile, thereby keeping it going indefinitely.
     * [2] Instead of setting the sensor position to zero at the start of each MP, the program could offset the MP's position based on current position.
     */


    /**
     * The status of the motion profile executer and buffer inside the Talon.
     * Instead of creating a new one every time we call getMotionProfileStatus,
     * keep one copy.
     */
    private MotionProfileStatus _status = new MotionProfileStatus();

    /**
     * additional cache for holding the active trajectory point
     */
    double _pos = 0, _vel = 0, _heading = 0;

    /**
     * reference to the talon we plan on manipulating. We will not changeMode()
     * or call set(), just get motion profile status and make decisions based on
     * motion profile.
     */
    private TalonSRX l_talon;
    private TalonSRX r_talon;

    // Motion profile to follow
    Trajectory _trajectory = null;
    Trajectory _l_trajectory = null;
    Trajectory _r_trajectory = null;

    /**
     * State machine to make sure we let enough of the motion profile stream to
     * talon before we fire it.
     */
    private int _state = 0;
    private boolean _isFinished;
    /**
     * Any time you have a state machine that waits for external events, its a
     * good idea to add a timeout. Set to -1 to disable. Set to nonzero to count
     * down to '0' which will print an error message. Counting loops is not a
     * very accurate method of tracking timeout, but this is just conservative
     * timeout. Getting time-stamps would certainly work too, this is just
     * simple (no need to worry about timer overflows).
     */
    private int _loopTimeout = -1;
    /**
     * If start() gets called, this flag is set and in the control() we will
     * service it.
     */
    private boolean _bStart = false;

    /**
     * Since the CANTalon.set() routine is mode specific, deduce what we want
     * the set value to be and let the calling module apply it whenever we
     * decide to switch to MP mode.
     */
    private SetValueMotionProfile _setValue = SetValueMotionProfile.Disable;
    /**
     * How many trajectory points do we wait for before firing the motion
     * profile.
     */
    private static final int kMinPointsInTalon = 5;
    /**
     * Just a state timeout to make sure we don't get stuck anywhere. Each loop
     * is about 20ms.
     */
    private static final int kNumLoopsTimeout = 10;


    /**
     * Lets create a periodic task to funnel our trajectory points into our talon.
     * It doesn't need to be very accurate, just needs to keep pace with the motion
     * profiler executer.  Now if you're trajectory points are slow, there is no need
     * to do this, just call l_talon.processMotionProfileBuffer() in your teleop loop.
     * Generally speaking you want to call it at least twice as fast as the duration
     * of your trajectory points.  So if they are firing every 20ms, you should call
     * every 10ms.
     */
    class PeriodicRunnable implements java.lang.Runnable {
        public void run() {
            // check for overflow
            MotionProfileStatus lstatus = new MotionProfileStatus();
            MotionProfileStatus rstatus = new MotionProfileStatus();
            l_talon.getMotionProfileStatus(lstatus);
            r_talon.getMotionProfileStatus(rstatus);
            if (lstatus.btmBufferCnt<64) {
                l_talon.processMotionProfileBuffer();
            }
            if (rstatus.btmBufferCnt<64) {
                r_talon.processMotionProfileBuffer();
            }
        }
    }

    Notifier _notifer = new Notifier(new PeriodicRunnable());


    /**
     * C'tor
     *
     * @param l_master reference to left Talon object to fetch motion profile status from.
     * @param r_master reference to right Talon object to fetch motion profile status from.
     */
    public MotionProfile(TalonSRX l_master, TalonSRX r_master) {
        l_talon = l_master;
        r_talon = r_master;
        /*
         * since our MP is 10ms per point, set the control frame rate and the
         * notifer to half that
         */
        l_talon.changeMotionControlFramePeriod(25);
        r_talon.changeMotionControlFramePeriod(25);
        _notifer.startPeriodic(0.025);
    }

    public void setTrajectory(Trajectory trajectory){
        this._trajectory = trajectory;
    }
    public void setTrajectory(Trajectory l_trajectory, Trajectory r_trajectory){
        this._l_trajectory = l_trajectory;
        this._r_trajectory = r_trajectory;
    }

    /**
     * Called to clear Motion profile buffer and reset state info during
     * disabled and when Talon is not in MP control mode.
     */
    public void reset() {
        /*
         * Let's clear the buffer just in case user decided to disable in the
         * middle of an MP, and now we have the second half of a profile just
         * sitting in memory.
         */
        l_talon.clearMotionProfileTrajectories();
        r_talon.clearMotionProfileTrajectories();
        /* When we do re-enter motionProfile control mode, stay disabled. */
        _setValue = SetValueMotionProfile.Disable;
        /* When we do start running our state machine start at the beginning. */
        _state = 0;
        _loopTimeout = -1;
        /*
         * If application wanted to start an MP before, ignore and wait for next
         * button press
         */
        _bStart = false;
    }

    /**
     * Called every loop.
     */
    public void control() {
        /* Get the motion profile status every loop */
        l_talon.getMotionProfileStatus(_status);

        /*
         * track time, this is rudimentary but that's okay, we just want to make
         * sure things never get stuck.
         */
        if (_loopTimeout < 0) {
            /* do nothing, timeout is disabled */
        } else {
            /* our timeout is nonzero */
            if (_loopTimeout == 0) {
                /*
                 * something is wrong. Talon is not present, unplugged, breaker
                 * tripped
                 */
//                    Instrumentation.OnNoProgress();
            } else {
                --_loopTimeout;
            }
        }

        /* first check if we are in MP mode */
        if (l_talon.getControlMode() != ControlMode.MotionProfile || r_talon.getControlMode() != ControlMode.MotionProfile) {
            /*
             * we are not in MP mode. We are probably driving the robot around
             * using gamepads or some other mode.
             */
            _state = 0;
            _loopTimeout = -1;
        } else {
            /*
             * we are in MP control mode. That means: starting Mps, checking Mp
             * progress, and possibly interrupting MPs if thats what you want to
             * do.
             */
            switch (_state) {
                case 0: /* wait for application to tell us to start an MP */
                    if (_bStart) {
                        _bStart = false;

                        _setValue = SetValueMotionProfile.Disable;
                        startFilling();
                        /*
                         * MP is being sent to CAN bus, wait a small amount of time
                         */
                        _state = 1;
                        _loopTimeout = kNumLoopsTimeout;
                        _isFinished = false;
                    }
                    break;
                case 1: /*
                 * wait for MP to stream to Talon, really just the first few
                 * points
                 */
                    /* do we have a minimum numberof points in Talon */
                    if (_status.btmBufferCnt > kMinPointsInTalon) {
                        /* start (once) the motion profile */
                        _setValue = SetValueMotionProfile.Enable;
                        /* MP will start once the control frame gets scheduled */
                        _state = 2;
                        _loopTimeout = kNumLoopsTimeout;
                    }
                    break;
                case 2: /* check the status of the MP */
                    /*
                     * if talon is reporting things are good, keep adding to our
                     * timeout. Really this is so that you can unplug your talon in
                     * the middle of an MP and react to it.
                     */
                    if (!_status.isUnderrun) {
                        _loopTimeout = kNumLoopsTimeout;
                    }
                    /*
                     * If we are executing an MP and the MP finished, start loading
                     * another. We will go into hold state so robot servo's
                     * position.
                     */
                    if (_status.activePointValid && _status.isLast) {
                        /*
                         * because we set the last point's isLast to true, we will
                         * get here when the MP is done
                         */
                        _setValue = SetValueMotionProfile.Hold;
                        _state = 0;
                        _loopTimeout = -1;
                        _isFinished = true;
                    }
                    break;
            }

            /* Get the motion profile status every loop */
            l_talon.getMotionProfileStatus(_status);
            _heading = l_talon.getActiveTrajectoryHeading();
            _pos = l_talon.getActiveTrajectoryPosition();
            _vel = l_talon.getActiveTrajectoryVelocity();

            System.out.println("_status.topBufferCnt = " + _status.topBufferCnt);
            System.out.println("_status.btmBufferCnt = " + _status.btmBufferCnt);

            /* printfs and/or logging */
//                Instrumentation.process(_status, _pos, _vel, _heading);
        }
    }

    /**
     * Find enum value if supported.
     *
     * @param durationMs
     * @return enum equivalent of durationMs
     */
    private TrajectoryDuration GetTrajectoryDuration(int durationMs) {
        /* create return value */
        TrajectoryDuration retval = TrajectoryDuration.Trajectory_Duration_0ms;
        /* convert duration to supported type */
        retval = retval.valueOf(durationMs);
        /* check that it is valid */
        if (retval.value != durationMs) {
            DriverStation.reportError("Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);
        }
        /* pass to caller */
        return retval;
    }

    /**
     * Start filling the MPs to all of the involved Talons.
     */
    private void startFilling() {
        if (_l_trajectory == null || _r_trajectory == null) {
            TankModifier modifier = new TankModifier(_trajectory);
//        modifier.modify(0.6096);
            modifier.modify(Constants.kWheelBaseWidth);

            _l_trajectory = modifier.getLeftTrajectory();
            _r_trajectory = modifier.getRightTrajectory();
        }
        startFilling(_l_trajectory, l_talon);
        startFilling(_r_trajectory, r_talon);
    }

    private void startFilling(Trajectory trajectory, TalonSRX talon) {

        /* create an empty point */
        TrajectoryPoint point = new TrajectoryPoint();

        /* did we get an underrun condition since last time we checked ? */
        if (_status.hasUnderrun) {
            /* better log it so we know about it */
//            Instrumentation.OnUnderrun();
            System.out.println("Has Underrun");
            /*
             * clear the error. This flag does not auto clear, this way
             * we never miss logging it.
             */
            talon.clearMotionProfileHasUnderrun(0);
        }
        /*
         * just in case we are interrupting another MP and there is still buffer
         * points in memory, clear it.
         */
        talon.clearMotionProfileTrajectories();

        /* set the base trajectory period to zero, use the individual trajectory period below */
        talon.configMotionProfileTrajectoryPeriod(Constants.kBaseTrajPeriodMs, Constants.kTimeoutMs);

        /* This is fast since it's just into our TOP buffer */
        for (int i = 0; i < trajectory.length(); i++) {
            Trajectory.Segment seg = trajectory.get(i);
            point = new TrajectoryPoint(); // TODO is this wasteful? We re-set everything we need to.. Why create a new object?
            point.position = seg.position * Constants.kTicksPerMeter * Constants.kMetersPerFoot;
            point.velocity = seg.velocity * 0.1 * Constants.kTicksPerMeter * Constants.kMetersPerFoot; // Native units per 100ms
            //point.headingDeg = 0; /* future feature - not used in this example*/
//            point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */ //TODO Use this?

            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.timeDur = TrajectoryDuration.Trajectory_Duration_0ms;//GetTrajectoryDuration((int) (seg.dt*1000));



            point.zeroPos = false;
            if (i == 0) {
                point.zeroPos = true;
            }

            point.isLastPoint = false;
            if ((i + 1) == trajectory.length()) {
                point.isLastPoint = true;
            }

            talon.pushMotionProfileTrajectory(point);
        }
    }

    /**
     * Called by application to signal Talon to start the buffered MP (when it's
     * able to).
     */
    public void startMotionProfile() {
        _bStart = true;
    }

    /**
     * @return the output value to pass to Talon's set() routine. 0 for disable
     * motion-profile output, 1 for enable motion-profile, 2 for hold
     * current motion profile trajectory point.
     */
    public SetValueMotionProfile getSetValue() {
        return _setValue;
    }

    public boolean isFinished() {
        return _isFinished;
    }
}


