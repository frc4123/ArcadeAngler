package org.frc4123.robot.arcadeangler.auto.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc4123.robot.arcadeangler.Constants;
import org.frc4123.robot.arcadeangler.subsystems.WheelsGrabber;

public class ArmsWheelsAction implements Action {

    private final WheelsGrabber mGrabber1 = new WheelsGrabber();

    public enum State {INTAKE, EJECT, STOP}

    private double wheelSpeed = 0;
    private boolean isCubeGrabbed = false;

    public ArmsWheelsAction(State desiredState) {

        switch (desiredState) {
            case INTAKE:
                wheelSpeed = Constants.kIntakeCubeSpeed;
                break;
            case EJECT:
                wheelSpeed = Constants.kEjectCubeSpeed;
                break;
            case STOP:
            default:
                wheelSpeed = Constants.kStopCubeSpeed;
                break;
        }
    }

    @Override
    public boolean isFinished() {
        if (mGrabber1.isCubeGrabbed()) {
            isCubeGrabbed = true;
            return isCubeGrabbed;
        } else {
            isCubeGrabbed = false;
            return isCubeGrabbed;
        }
    }

    @Override
    public void update() {
        //TODO: Lowkey not sure what these do
    }

    @Override
    public void done() {
        SmartDashboard.putBoolean("isGrabCubeWheelsAction Complete?", isCubeGrabbed);
    }

    @Override
    public void start() {
        mGrabber1.setIntakeSpeed(wheelSpeed);
    }
}
