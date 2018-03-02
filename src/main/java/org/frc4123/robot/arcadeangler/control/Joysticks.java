package org.frc4123.robot.arcadeangler.control;

import edu.wpi.first.wpilibj.Joystick;
import org.frc4123.robot.arcadeangler.Constants;
import org.frc4123.robot.arcadeangler.subsystems.Elevator;


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
        return -driveStick.getRawAxis(JoystickConstants.kXBOX_LJoyY);
    }

    public double getTurn() {
        return driveStick.getRawAxis(JoystickConstants.kF310_RJoyX);
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

    //GrabberOne
    public double getFlipperUpperSpeed() {
        return -auxStick.getRawAxis(JoystickConstants.kF310_RJoyY);
    }

    public double getIntakeSpeed() {
        return -(auxStick.getRawButton(JoystickConstants.kF310_LBump) ? 1 : 0) + (auxStick.getRawButton(JoystickConstants.kF310_RBump) ? 1 : 0);
    }

    //GrabberTwo
    public enum FlipperUpperState {
        UP, DOWN, NEUTRAL
    }

    public FlipperUpperState getFlipperUpperState() {
        if (auxStick.getRawAxis(JoystickConstants.kF310_RJoyY) <= -Constants.kJoyNeutralZone) {
            return FlipperUpperState.UP;
        } else if (auxStick.getRawAxis(JoystickConstants.kF310_RJoyY) >= Constants.kJoyNeutralZone) {
            return FlipperUpperState.DOWN;
        } else {
            return FlipperUpperState.NEUTRAL;
        }
    }

    public enum GrabberState {OPEN, CLOSE, NEUTRAL}

    public GrabberState getGrabberState() {
        if (auxStick.getRawButton(JoystickConstants.kF310_LBump)) {
            return GrabberState.OPEN;
        } else if (auxStick.getRawButton(JoystickConstants.kF310_RBump)) {
            return GrabberState.CLOSE;
        } else {
            return GrabberState.NEUTRAL;
        }
    }

    //Elevator
    private Elevator.Mode currentMode = Elevator.Mode.MANUAL;

    public Elevator.Mode getElevatorMode() {

        if (auxStick.getPOV() != -1) {
            currentMode = Elevator.Mode.MANUAL;
        } else if (auxStick.getRawButton(JoystickConstants.kF310_Y)) {
            currentMode = Elevator.Mode.HIGH;
        } else if (auxStick.getRawButton(JoystickConstants.kF310_B)) {
            currentMode = Elevator.Mode.MEDIUM;
        } else if (auxStick.getRawButton(JoystickConstants.kF310_A)) {
            currentMode = Elevator.Mode.LOW;
        }

        return currentMode;
    }

    /**
     * A double to give elevator.set a speed in MANUAL mode
     *
     * @return If POV "up" selected, return 1 * the position of the left Joystick Trigger
     * If POV "down" selected, return -1 * the position of the left Joystick Trigger
     * Default return is 0.
     */
    public double getElevatorThrottle() {
        switch (auxStick.getPOV()) {
            case 0:
                return 1 * auxStick.getRawAxis(JoystickConstants.kF310_LTrigger);
            case 180:
                return -1 * auxStick.getRawAxis(JoystickConstants.kF310_LTrigger);
            default:
                return 0;
        }
    }

    public boolean setHeadingPIDFromSmartDashboard() {
        return driveStick.getRawButton(7);
    }

    public void disabledPeriodic() {
        currentMode = Elevator.Mode.MANUAL;
    }
}


