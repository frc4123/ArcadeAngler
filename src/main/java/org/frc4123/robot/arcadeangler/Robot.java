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

        //PowerCube Manipulator Commands
        switch (mJoysticks.getGrabberStatus()){
            case INTAKE:
                mPCM.intakeCube();
                break;
            case EJECT:
                mPCM.ejectCube();
                break;
            case UP:
                mPCM.foldArmsUp();
                break;
            case DOWN:
                mPCM.foldArmsDown();
                break;
            case STOPPED:
                default:
                    mPCM.stopWheels();
                    mPCM.stopFolding();
                break;
        }
    }

    @Override
    public void testPeriodic() { }
}