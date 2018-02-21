package org.frc4123.robot.arcadeangler;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.frc4123.robot.arcadeangler.control.Joysticks;
import org.frc4123.robot.arcadeangler.Constants;
import edu.wpi.first.wpilibj.Spark;
import org.frc4123.robot.arcadeangler.control.TribeRobotDrive;
import org.frc4123.robot.arcadeangler.subsystems.PowerCubeManipulator;

import java.net.URI;

public class Robot extends IterativeRobot {

    //Controllers of doom
    Joysticks mJoysticks = new Joysticks();

    //Robot Drive
    TribeRobotDrive mDrive = TribeRobotDrive.getInstance();


//    DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

    //Subsystems
    //PowerCubeManipulator mPCM = new PowerCubeManipulator();

    @Override
    public void robotInit() { }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() { }

    @Override
    public void teleopInit() {
        mDrive.initMotionProfile("/home/lvuser/trajectories/left_detailed.csv", "/home/lvuser/trajectories/right_detailed.csv");
    }

    @Override
    public void testInit() { }


    @Override
    public void disabledPeriodic() {

    }
    
    @Override
    public void autonomousPeriodic() { }

    @Override
    public void teleopPeriodic() {

//        //PowerCube Manipulator Commands
//        if (mJoysticks.getGrabberStatus() == Joysticks.Grabber.INTAKE) {
//            mPCM.intakeCube();
//        }else if (mJoysticks.getGrabberStatus() == Joysticks.Grabber.EJECT){
//            mPCM.ejectCube();
//        }else {
//            mPCM.stop();
//        }

        mDrive.arcadeDrive(mJoysticks.getThrottle(), mJoysticks.getTurn(), mJoysticks.getSpeedModifier());
        if (mJoysticks.getAimAssit()){
            mDrive.setTalonControlMode(TribeRobotDrive.ControlMode.MOTIONPROFILE);
            //mDrive.startMotionProfile();
        }
    }

    @Override
    public void testPeriodic() { }
}