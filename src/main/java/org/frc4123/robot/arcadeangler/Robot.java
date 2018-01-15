package org.frc4123.robot.arcadeangler;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.frc4123.robot.arcadeangler.control.Joysticks;
import org.frc4123.robot.arcadeangler.Constants;
import edu.wpi.first.wpilibj.Spark;

public class Robot extends IterativeRobot {

    //Controllers of doom
    Joysticks mJoysticks = new Joysticks();

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
        if (mJoysticks.getGrabberStatus()== Joysticks.Grabber.INTAKE) {
            //TODO: Set spark mc to intake
        }else if (mJoysticks.getGrabberStatus() == Joysticks.Grabber.EJECT){
            //TODO: Set spark mc to eject
        }else {
            //TODO: Set spark mc to stop
        }
    }

    @Override
    public void testPeriodic() { }
}