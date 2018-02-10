package org.frc4123.robot.arcadeangler;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.frc4123.robot.arcadeangler.control.Joysticks;
import org.frc4123.robot.arcadeangler.Constants;
import edu.wpi.first.wpilibj.Spark;
import org.frc4123.robot.arcadeangler.subsystems.PowerCubeManipulator;

public class Robot extends IterativeRobot {

    //Controllers of doom
    Joysticks mJoysticks = new Joysticks();

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
        //PowerCube Manipulator Commands TODO: Maybe Replace with enum
        if (mJoysticks.getGrabberStatus() == Joysticks.Grabber.INTAKE) {
            mPCM.intakeCube();
        } else if (mJoysticks.getGrabberStatus() == Joysticks.Grabber.EJECT) {
            mPCM.ejectCube();
        } else if (mJoysticks.getGrabberStatus() == Joysticks.Grabber.UP) {
            mPCM.foldArmsUp();
        } else if (mJoysticks.getGrabberStatus() == Joysticks.Grabber.DOWN){
            mPCM.foldArmsDown();
        }else {
            mPCM.stopWheels();
            mPCM.stopFolding();
        }
    }

    @Override
    public void testPeriodic() { }
}