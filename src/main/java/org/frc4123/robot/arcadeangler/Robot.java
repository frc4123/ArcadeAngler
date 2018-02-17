package org.frc4123.robot.arcadeangler;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.frc4123.robot.arcadeangler.control.Joysticks;
import org.frc4123.robot.arcadeangler.subsystems.Elevator;

public class Robot extends IterativeRobot {

    //Controllers of doom
    Joysticks mJoysticks = new Joysticks();

    //Subsystems
    //PowerCubeManipulator mPCM = new PowerCubeManipulator();
    Elevator elevator = new Elevator();

    @Override
    public void robotInit() {
        System.out.println("Robot.robotInit");
    }

    @Override
    public void disabledInit() {
        System.out.println("Robot.disabledInit");


    }

    @Override
    public void autonomousInit() { }

    @Override
    public void teleopInit() { }

    @Override
    public void testInit() { }


    @Override
    public void disabledPeriodic() {
        elevator.stop();
    }
    
    @Override
    public void autonomousPeriodic() { }

    @Override
    public void teleopPeriodic() {
        //PowerCube Manipulator Commands
//        if (mJoysticks.getGrabberStatus()== Joysticks.Grabber.INTAKE) {
//            mPCM.intakeCube();
//        }else if (mJoysticks.getGrabberStatus() == Joysticks.Grabber.EJECT){
//            mPCM.ejectCube();
//        }else {
//            mPCM.stopWheels();
//        }

        //Elevator
        elevator.setMode(mJoysticks.getElevatorMode());
        elevator.set(mJoysticks.getElevatorThrottle());

        if (elevator.getDescendLimitSW()){
            elevator.resetEncoder();
        }

    }

    @Override
    public void testPeriodic() { }
}