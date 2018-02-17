package org.frc4123.robot.arcadeangler.control;

import edu.wpi.first.wpilibj.Joystick;
import org.frc4123.robot.arcadeangler.Constants;
import org.frc4123.robot.arcadeangler.subsystems.PowerCubeManipulator;

public class Joysticks {

    private final Joystick driveStick;
    private final Joystick auxStick;

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

    public double getFlipperUpperSpeed(){
        return auxStick.getRawAxis(JoystickConstants.kF310_RJoyY);
    }

    public double getIntakeSpeed(){
        return -(auxStick.getRawButton(JoystickConstants.kF310_LBump) ? 1 : 0) + (auxStick.getRawButton(JoystickConstants.kF310_RBump) ? 1 : 0);
    }

    public boolean setHeadingPIDFromSmartDashboard() {
        return driveStick.getRawButton(7);
    }

}


