package org.frc4123.robot.arcadeangler.control;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import org.frc4123.robot.arcadeangler.Constants;
import org.frc4123.robot.arcadeangler.Utils;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

public class TribeRobotDrive extends DifferentialDrive {

    private static WPI_TalonSRX l_master = new WPI_TalonSRX(Constants.id_driveLeftMaster);
    private static WPI_TalonSRX l_slave = new WPI_TalonSRX(Constants.id_driveLeftSlave);
    private static WPI_TalonSRX r_master = new WPI_TalonSRX(Constants.id_driveRightMaster);
    private static WPI_TalonSRX r_slave = new WPI_TalonSRX(Constants.id_driveRightSlave);
    private static SpeedControllerGroup m_left = new SpeedControllerGroup(l_master, l_slave);
    private static SpeedControllerGroup m_right = new SpeedControllerGroup(r_master, r_slave);

    private double linearClosedLoopTolerance = Constants.kLinearClosedLoop_Tolerance_Default;

    private ControlMode driveMode = ControlMode.RAW;

    static TribeRobotDrive mInstance = new TribeRobotDrive();

    public static TribeRobotDrive getInstance() {
        return mInstance;
    }

    private TribeRobotDrive() {
        super(m_left, m_right);

        //Set up TalonSRXs in open loop mode
        //Set Masters to PercentOutput mode
        l_master.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
        r_master.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);

        //Set Slaves to follow
        l_slave.set(com.ctre.phoenix.motorcontrol.ControlMode.Follower, Constants.id_driveLeftMaster);
        r_slave.set(com.ctre.phoenix.motorcontrol.ControlMode.Follower, Constants.id_driveRightMaster);

        //TODO: Determine necessity on this new VersaChassis
//        r_master.setInverted(true);
//        l_master.setSensorPhase(true);

        setTalonControlMode(driveMode);

    }

    public void arcadeDrive(double moveValue, double rotateValue, double speedMod) {
        super.arcadeDrive(speedMod * moveValue, speedMod * rotateValue);
    }

    public enum ControlMode {
        RAW, POSITION, MOTIONPROFILE
    }

    public void setTalonControlMode(ControlMode mode) {
        switch (mode) {
            case RAW:
                r_master.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
                l_master.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
                break;

            case POSITION:
                r_master.set(com.ctre.phoenix.motorcontrol.ControlMode.Position, r_master.getSelectedSensorPosition(Constants.kPIDLoopIdx));
                l_master.set(com.ctre.phoenix.motorcontrol.ControlMode.Position, l_master.getSelectedSensorPosition(Constants.kPIDLoopIdx));
                break;

            case MOTIONPROFILE:
                r_master.set(com.ctre.phoenix.motorcontrol.ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
                l_master.set(com.ctre.phoenix.motorcontrol.ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
                startMotionProfile();
                break;
        }
    }

    public void initMotionProfile(String leftTrajFilePath, String rightTrajFilePath){
        resetSensors();

        Trajectory left = Pathfinder.readFromCSV(new File(leftTrajFilePath));
        Trajectory right = Pathfinder.readFromCSV(new File(rightTrajFilePath));

        l_master.changeMotionControlFramePeriod(10);
        r_master.changeMotionControlFramePeriod(10);

        l_master.clearMotionProfileTrajectories();
        r_master.clearMotionProfileTrajectories();
        l_master.configMotionProfileTrajectoryPeriod(0, Constants.kTimeoutMs);
        r_master.configMotionProfileTrajectoryPeriod(0, Constants.kTimeoutMs);

        for (int i = 0; i < left.length(); i++) {
            Trajectory.Segment seg = left.get(i);
            TrajectoryPoint point = new TrajectoryPoint();
            point.position = seg.position * Constants.kTicksPerFoot;
            point.velocity = seg.velocity * 0.001 * Constants.kTicksPerFoot; //
            //point.headingDeg = seg.heading;

            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.timeDur = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_20ms;

            point.zeroPos = false;
            if (i == 0) {
                point.zeroPos = true;
            }

            point.isLastPoint = false;
            if ((i + 1) == left.length()) {
                point.isLastPoint = true;
            }

            l_master.pushMotionProfileTrajectory(point);

        }

        for (int i = 0; i < right.length(); i++) {
            Trajectory.Segment seg = right.get(i);
            TrajectoryPoint point = new TrajectoryPoint();
            point.position = seg.position * Constants.kTicksPerFoot;
            point.velocity = seg.velocity * 0.001 * Constants.kTicksPerFoot; //ft/s to units/100ms
            //point.headingDeg = seg.heading;

            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.timeDur = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_20ms;

            point.zeroPos = false;
            if (i == 0) {
                point.zeroPos = true;
            }

            point.isLastPoint = false;
            if ((i + 1) == right.length()) {
                point.isLastPoint = true;
            }

//            System.out.println("point.isLastPoint || point.zeroPos = " + (point.isLastPoint || point.zeroPos));
            //System.out.println("pointvel = " + point.velocity);
            r_master.pushMotionProfileTrajectory(point);
        }

    }

    MotionProfileStatus l_motionProfileStatus = new MotionProfileStatus();
    MotionProfileStatus r_motionProfileStatus = new MotionProfileStatus();
    public void startMotionProfile(){
        l_master.getMotionProfileStatus(l_motionProfileStatus);
        r_master.getMotionProfileStatus(r_motionProfileStatus);
        System.out.println("l_motionProfileStatus.isUnderrun = " + l_motionProfileStatus.isUnderrun);
        System.out.println("l_motionProfileStatus.topBufferCnt = " + l_motionProfileStatus.topBufferCnt);
        System.out.println("l_motionProfileStatus.btmBufferCnt = " + l_motionProfileStatus.btmBufferCnt);
        System.out.println("l_motionProfileStatus.isLast = " + l_motionProfileStatus.isLast);

        if (l_motionProfileStatus.btmBufferCnt < 64) {
            l_master.processMotionProfileBuffer();
        }
        if (r_motionProfileStatus.btmBufferCnt < 64) {
            r_master.processMotionProfileBuffer();
        }

        if (l_motionProfileStatus.btmBufferCnt > 5 && r_motionProfileStatus.btmBufferCnt > 5) {
            l_master.set(com.ctre.phoenix.motorcontrol.ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
            r_master.set(com.ctre.phoenix.motorcontrol.ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
        }
    }

    private double rSetPnt = 0;
    public void setSetpointRight(double setpoint) {
        if (driveMode == ControlMode.POSITION){
            r_master.set(setpoint);
            rSetPnt = setpoint;
        }else {
            System.out.println("Right talon not in POSITION mode. Setpoint not set.");
        }
    }

    private double lSetPnt = 0;
    public void setSetpointLeft(double setpoint) {
        if (driveMode == ControlMode.POSITION){
            l_master.set(setpoint);
            lSetPnt = setpoint;
        }else {
            System.out.println("Left talon not in POSITION mode. Setpoint not set.");
        }
    }

    public double getSetpointRight() {
        return rSetPnt;
    }

    public double getSetpointLeft() {
        return lSetPnt;
    }

    public void setLinearClosedLoopTolerance(double linearClosedLoopTolerance) {
        this.linearClosedLoopTolerance = linearClosedLoopTolerance;
    }

    public double getLinearClosedLoopTolerance() {
        return this.linearClosedLoopTolerance;
    }

//    public double getLeftDistanceInches() {
//        return Utils.encUnitsToInches(l_master.getEncPosition());
//    }
//
//    public double getRightDistanceInches() {
//        return Utils.encUnitsToInches(r_master.getEncPosition());
//    }

    public double getLeftVelocityInchesPerSecond() {
        return Utils.encVelToInchesPerSecond(l_master.getSelectedSensorVelocity(0));
    }

    public double getRightVelocityInchesPerSecond() {
        return Utils.encVelToInchesPerSecond(r_master.getSelectedSensorVelocity(0));
    }

    /**
     * Prints status to SmartDashboard
     */
    public void outputToSmartDashboard() {
//        SmartDashboard.putNumber("left_distance", getLeftDistanceInches());
//        SmartDashboard.putNumber("right_distance", getRightDistanceInches());
        SmartDashboard.putNumber("left_velocity", getLeftVelocityInchesPerSecond());
        SmartDashboard.putNumber("right_velocity", getRightVelocityInchesPerSecond());
        SmartDashboard.putNumber("left_error", l_master.getClosedLoopError(0));
        SmartDashboard.putNumber("right_error", r_master.getClosedLoopError(0));

//        SmartDashboard.putNumber("robotdrive_setpoint_error", getSetpointError());
    }


    /**
     * Resets and zeros all sensors
     */
    public void resetSensors() {
        System.out.println("Resetting RobotDrive sensors");
        l_master.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
        r_master.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }

}
