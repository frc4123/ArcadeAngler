package org.frc4123.robot.arcadeangler;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.frc4123.robot.arcadeangler.control.Joysticks;
import org.frc4123.robot.arcadeangler.Constants;
import edu.wpi.first.wpilibj.Spark;
import org.frc4123.robot.arcadeangler.subsystems.PowerCubeManipulator;

public class Robot extends IterativeRobot {

    //Controllers of doom
    Joysticks mJoysticks = new Joysticks();

    //Robot Drive

    SpeedControllerGroup m_left = new SpeedControllerGroup(_talon_L_master, _talon_L_slave);


    SpeedControllerGroup m_right = new SpeedControllerGroup(_talon_R_master, _talon_R_slave);

    DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

    //Subsystems
    PowerCubeManipulator mPCM = new PowerCubeManipulator();

    @Override
    public void robotInit() { }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() { }

    @Override
    public void teleopInit() { }

    @Override
    public void testInit() { }


    @Override
    public void disabledPeriodic() { }
    
    @Override
    public void autonomousPeriodic() { }

    @Override
    public void teleopPeriodic() {

        //PowerCube Manipulator Commands
        if (mJoysticks.getGrabberStatus() == Joysticks.Grabber.INTAKE) {
            mPCM.intakeCube();
        }else if (mJoysticks.getGrabberStatus() == Joysticks.Grabber.EJECT){
            mPCM.ejectCube();
        }else {
            mPCM.stop();
        }

    }

    @Override
    public void testPeriodic() { }
}