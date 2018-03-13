package org.frc4123.robot.arcadeangler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import org.frc4123.robot.arcadeangler.auto.AutoModeExecuter;
import org.frc4123.robot.arcadeangler.auto.modes.CrossBaselineMode;
import org.frc4123.robot.arcadeangler.auto.modes.TestAutoMode;
import org.frc4123.robot.arcadeangler.control.Joysticks;
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

    //Autonomous
    public String robotPosition;// = "N";

    @Override
    public void robotInit() {
        System.out.println("Robot.robotInit");

        //TODO: Test what happens when you have this enabled but there's no pneumatics commands
        //Compressor Enable Closed Loop Control (PCM does all the work)
        mGrabberTwo.squishyBoi.setClosedLoopControl(true);
    }

    @Override
    public void disabledInit() {
        mDriveBase.mDrive.setSafetyEnabled(true);
        mDriveBase.mDrive.stopMotor();
        System.out.println("Robot.disabledInit");
        autoModeExecuter.stop();
    }


    public String getRobotPosition() {
        SmartDashboard.getString("Robot Position: ", robotPosition);
        return robotPosition;
    }

    AutoModeExecuter autoModeExecuter = new AutoModeExecuter();

    @Override
    public void autonomousInit() {
        SmartDashboard.getString("Robot Position: ", robotPosition);
        mDriveBase.mDrive.setSafetyEnabled(false);
        autoModeExecuter.setAutoMode(new TestAutoMode());
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

        //Grabbers - Switch between from SmartDashboard, GrabberTwo is default
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