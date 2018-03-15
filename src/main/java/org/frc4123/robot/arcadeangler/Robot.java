package org.frc4123.robot.arcadeangler;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc4123.robot.arcadeangler.auto.AutoModeExecuter;
import org.frc4123.robot.arcadeangler.auto.modes.AutoModeBase;
import org.frc4123.robot.arcadeangler.auto.modes.TestAutoMode;
import org.frc4123.robot.arcadeangler.control.Joysticks;
import org.frc4123.robot.arcadeangler.control.SmarterDashboard;
import org.frc4123.robot.arcadeangler.subsystems.DriveBase;
import org.frc4123.robot.arcadeangler.subsystems.PneumaticGrabber;
import org.frc4123.robot.arcadeangler.subsystems.WheelsGrabber;
import org.frc4123.robot.arcadeangler.subsystems.Elevator;

public class Robot extends IterativeRobot {

    //Controllers of doom
    Joysticks mJoysticks = new Joysticks();

    //Subsystems
    DriveBase mDriveBase = DriveBase.getInstance();
    PneumaticGrabber mGrabberTwo = PneumaticGrabber.getInstance();
    WheelsGrabber mGrabberOne = WheelsGrabber.getInstance();
    Elevator mElevator = Elevator.getInstance();
    Boolean isGrabberTwoSelected = true;

    //SmartDashboard
    SmarterDashboard mSmartDashboard = SmarterDashboard.getInstance();


    @Override
    public void robotInit() {
        System.out.println("Robot.robotInit");

        //TODO: Test what happens when you have this enabled but there's no pneumatics commands
        //Compressor Enable Closed Loop Control (PCM does all the work)
        mGrabberTwo.squishyBoi.setClosedLoopControl(true);

        //Smart Dashboard
        mSmartDashboard.setAutoInfo();
        SmartDashboard.updateValues();
    }

    @Override
    public void disabledInit() {
        mDriveBase.mDrive.setSafetyEnabled(true);
        mDriveBase.mDrive.stopMotor();
        autoModeExecuter.stop();
        System.out.println("Robot.disabledInit");
        SmartDashboard.updateValues();
    }


    AutoModeExecuter autoModeExecuter = new AutoModeExecuter();

    @Override
    public void autonomousInit() {
        //mSmartDashboard.getAutoInfo();
        mDriveBase.mDrive.setSafetyEnabled(false);
        autoModeExecuter.setAutoMode(mSmartDashboard.getSelectedAutoMode());
        autoModeExecuter.start();
    }

    @Override
    public void teleopInit() {
        System.out.println("Robot.teleopInit");
        autoModeExecuter.stop();
    }

    @Override
    public void testInit() {
    }

    @Override
    public void disabledPeriodic() {
        mElevator.stop();
        mJoysticks.disabledPeriodic();
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopPeriodic() {

        SmartDashboard.getBoolean("Is Pneumatic Grabber Used?", isGrabberTwoSelected);

        mDriveBase.mDrive.arcadeDrive(mJoysticks.getTurn(), mJoysticks.getThrottle());

        //Grabbers - Switch between from SmarterDashboard, GrabberTwo is default
        if (isGrabberTwoSelected) {
            //GrabberTwo Commands
            mGrabberTwo.setGrabberState(mJoysticks.getGrabberState());
            mGrabberTwo.setFlipperUpperState(mJoysticks.getFlipperUpperState());
            } else {
            //GrabberOne Control
            mGrabberOne.setIntakeSpeed(mJoysticks.getIntakeSpeed());
            mGrabberOne.setFlipperUpperSpeed(mJoysticks.getFlipperUpperSpeed());
        }

        //Elevator
        mElevator.setMode(mJoysticks.getElevatorMode());
        mElevator.set(mJoysticks.getElevatorThrottle());
        System.out.println("mElevator.getDescendLimitSW() = " + mElevator.getDescendLimitSW());
        if (mElevator.getDescendLimitSW()) {
            mElevator.resetEncoder();
        }

    }

    @Override
    public void testPeriodic() {
    }
}