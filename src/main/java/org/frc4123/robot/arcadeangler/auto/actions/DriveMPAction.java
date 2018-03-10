package org.frc4123.robot.arcadeangler.auto.actions;

import com.ctre.phoenix.motorcontrol.ControlMode;
import jaci.pathfinder.Trajectory;
import org.frc4123.robot.arcadeangler.MotionProfile;
import org.frc4123.robot.arcadeangler.subsystems.DriveBase;

public class DriveMPAction implements Action {

    private MotionProfile motionProfile;
    private DriveBase mDriveBase = DriveBase.getInstance();

    public DriveMPAction(Trajectory trajectory) {
        motionProfile = new MotionProfile(mDriveBase.l_master, mDriveBase.r_master);
        motionProfile.setTrajectory(trajectory);
    }

    public DriveMPAction(Trajectory l_trajectory, Trajectory r_trajectory) {
        motionProfile = new MotionProfile(mDriveBase.l_master, mDriveBase.r_master);
        motionProfile.setTrajectory(l_trajectory, r_trajectory);
    }

    @Override
    public boolean isFinished() {
        return motionProfile.isFinished();
    }

    @Override
    public void update() {
        motionProfile.control();
        mDriveBase.l_master.set(ControlMode.MotionProfile, motionProfile.getSetValue().value);
        mDriveBase.r_master.set(ControlMode.MotionProfile, motionProfile.getSetValue().value);
        //mDriveBase.mDrive. //TODO feed the watchdog here?
    }

    @Override
    public void done() {

    }

    @Override
    public void start() {
        // Todo print some helpful debug messages here

        motionProfile.startMotionProfile();
    }
}
