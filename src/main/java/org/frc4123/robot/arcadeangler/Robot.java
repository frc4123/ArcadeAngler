package org.frc4123.robot.arcadeangler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.frc4123.robot.arcadeangler.control.Joysticks;
import org.frc4123.robot.arcadeangler.subsystems.PneumaticGrabber;
import org.frc4123.robot.arcadeangler.subsystems.PowerCubeManipulator;
import org.frc4123.robot.arcadeangler.subsystems.Elevator;

public class Robot extends IterativeRobot {

    //Controllers of doom
    Joysticks mJoysticks = new Joysticks();

    //Subsystems
    PneumaticGrabber mPWRCubeMan = new PneumaticGrabber();
    PowerCubeManipulator mPWRCubeMan = new PowerCubeManipulator();
    Elevator mElevator = new Elevator();

    //Drive
    WPI_TalonSRX l_master = new WPI_TalonSRX(Constants.id_driveLeftMaster);
    WPI_TalonSRX l_slave = new WPI_TalonSRX(Constants.id_driveLeftSlave);
    WPI_TalonSRX r_master = new WPI_TalonSRX(Constants.id_driveRightMaster);
    WPI_TalonSRX r_slave = new WPI_TalonSRX(Constants.id_driveRightSlave);

    SpeedControllerGroup left = new SpeedControllerGroup(l_master, l_slave);
    SpeedControllerGroup right = new SpeedControllerGroup(r_master, r_slave);

    DifferentialDrive mDrive = new DifferentialDrive(left, right);

    //2018 Pneumatics Controller Test
    Compressor squishyBoi = new Compressor(0);

    @Override
    public void robotInit() {
        l_master.set(ControlMode.PercentOutput, 0);
        l_slave.follow(l_master);
        r_master.set(ControlMode.PercentOutput, 0);
        r_slave.follow(r_master);
        System.out.println("Robot.robotInit");

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

        mDrive.arcadeDrive(mJoysticks.getThrottle(), mJoysticks.getTurn());

        //PowerCube Manipulator Commands
        switch (mJoysticks.getFlipperUpperState()) {

            case UP:
                mPWRCubeMan.foldArmsUp();
                break;
            case DOWN:
                mPWRCubeMan.foldArmsDown();
                break;
            case NEUTRAL:
                break;

        }
        switch (mJoysticks.getGrabberState()) {
            case OPEN:
                mPWRCubeMan.open();
                break;
            case CLOSE:

                mPWRCubeMan.close();
                break;
            case NEUTRAL:
                break;
        }
        mPWRCubeMan.setIntakeSpeed(mJoysticks.getIntakeSpeed());
        mPWRCubeMan.setFlipperUpperSpeed(mJoysticks.getFlipperUpperSpeed());

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