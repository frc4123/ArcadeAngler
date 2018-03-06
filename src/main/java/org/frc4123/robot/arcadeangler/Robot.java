package org.frc4123.robot.arcadeangler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc4123.robot.arcadeangler.control.Joysticks;
import org.frc4123.robot.arcadeangler.subsystems.PneumaticGrabber;
import org.frc4123.robot.arcadeangler.subsystems.WheelsGrabber;
import org.frc4123.robot.arcadeangler.subsystems.Elevator;

public class Robot extends IterativeRobot {

    //Controllers of doom
    Joysticks mJoysticks = new Joysticks();

    //Subsystems
    PneumaticGrabber mGrabberTwo = new PneumaticGrabber();
    Compressor squishyBoi = new Compressor(0);
    WheelsGrabber mGrabberOne = new WheelsGrabber();
    Elevator mElevator = new Elevator();
    Boolean isGrabberTwoSelected = true;

    //Drive
    WPI_TalonSRX l_master = new WPI_TalonSRX(Constants.id_driveLeftMaster);
    WPI_TalonSRX l_slave = new WPI_TalonSRX(Constants.id_driveLeftSlave);
    WPI_TalonSRX r_master = new WPI_TalonSRX(Constants.id_driveRightMaster);
    WPI_TalonSRX r_slave = new WPI_TalonSRX(Constants.id_driveRightSlave);

    SpeedControllerGroup left = new SpeedControllerGroup(l_master, l_slave);
    SpeedControllerGroup right = new SpeedControllerGroup(r_master, r_slave);

    DifferentialDrive mDrive = new DifferentialDrive(left, right);

    @Override
    public void robotInit() {
        l_master.set(ControlMode.PercentOutput, 0);
        l_slave.follow(l_master);
        r_master.set(ControlMode.PercentOutput, 0);
        r_slave.follow(r_master);
        System.out.println("Robot.robotInit");

        //TODO: Test what happens when you have this enabled but there's no pneumatics commands
        //2018 Pneumatics Additions
        squishyBoi.setClosedLoopControl(true);
    }

    @Override
    public void disabledInit() {
        mDrive.stopMotor();
        System.out.println("Robot.disabledInit");
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void teleopInit() {
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

        mDrive.arcadeDrive(mJoysticks.getThrottle(), mJoysticks.getTurn());

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