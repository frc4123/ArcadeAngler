package org.frc4123.robot.arcadeangler.auto.actions;

import edu.wpi.first.wpilibj.Timer;

public class WaitAction implements Action {

    /**
     * Action to wait for a given amount of time To use this Action, call
     * runAction(new WaitAction(your_time))
     */

    private double mTimeToWait;
    private double mStartTime;

    /**
     * @param timeToWait Time in seconds to delay before continuing on to next command.
     */
    public WaitAction(double timeToWait) {
        mTimeToWait = timeToWait;
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - mStartTime >= mTimeToWait;
    }

    @Override
    public void update() {

    }

    @Override
    public void done() {

    }

    @Override
    public void start() {
        mStartTime = Timer.getFPGATimestamp();
    }
}

