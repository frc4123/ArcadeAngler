package org.frc4123.robot.arcadeangler.control;

import edu.wpi.first.wpilibj.Joystick;

public class Joysticks {

    private final Joystick driveStick;
    private final Joystick auxStick;

    //A Button - Logitech
    private static final int kControllerIntakeButton = 1;
    //B Button - Logitech
    private static final int kControllerEjectButton = 2;
    //Left Joystick - Logitech
    private static final int kControllerElevateAxis = 2;


    //Constructor
    public Joysticks() {
        //driveStick = XBOX 360 Controller
        driveStick = new Joystick(0);
        //auxStick = Logitech Gamepad F310 (switch on X)
        auxStick = new Joystick(1);
    }

    //Driver Joystick controls
    public double getThrottle() {
        return -driveStick.getRawAxis(1);
    }

    public double getTurn() {
        return -driveStick.getRawAxis(2);
    }

    public double getSpeedModifier() {
        return ((-1 * driveStick.getRawAxis(3) + 1) / 2);
    }

    public boolean getAimAssit() {
        return driveStick.getRawButton(5) || driveStick.getRawButton(3);
    }

    public boolean getDriveModeEnc() {
        return driveStick.getRawButton(8);
    }

    public boolean getDriveModeRaw() {
        return driveStick.getRawButton(11);
    }

    //Aux Joystick controls

    public enum Grabber{
        EJECT, STOPPED, INTAKE
    }

    public Grabber getGrabberStatus() {
        if (auxStick.getRawButton(kControllerIntakeButton)){
            return Grabber.INTAKE;
        }
        else if (auxStick.getRawButton(kControllerEjectButton)){
            return Grabber.EJECT;
        }
        else{
            return Grabber.STOPPED;
        }
    }

    public enum Elevator{
        UP, DOWN, STOPPED
    }

    public Elevator getElevatorStatus() {
        if (auxStick.getRawAxis(kControllerElevateAxis) > 0){
            return Elevator.DOWN;
        }
        else if (auxStick.getRawAxis(kControllerElevateAxis) < 0){
            return Elevator.UP;
        }
        else{
            return Elevator.STOPPED;
        }
    }

    public boolean setHeadingPIDFromSmartDashboard() {
        return driveStick.getRawButton(7);
    }

}


