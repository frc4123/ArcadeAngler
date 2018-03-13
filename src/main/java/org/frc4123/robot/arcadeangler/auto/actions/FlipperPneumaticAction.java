package org.frc4123.robot.arcadeangler.auto.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc4123.robot.arcadeangler.subsystems.PneumaticGrabber;

public class FlipperPneumaticAction implements Action {

    private PneumaticGrabber mGrabber2 = PneumaticGrabber.getInstance();

    public enum State {DOWN, UP}

    private PneumaticGrabber.FlipperUpperState flipState;
    private boolean isFlippedDwn = false;

    public FlipperPneumaticAction(State desiredState) {

        switch (desiredState) {
            case DOWN:
                flipState = PneumaticGrabber.FlipperUpperState.DOWN;
                break;
            case UP:
                flipState = PneumaticGrabber.FlipperUpperState.UP;
                break;
            default:
                flipState = PneumaticGrabber.FlipperUpperState.NEUTRAL;
                break;
        }
    }

    @Override
    public boolean isFinished() {
        if (mGrabber2.isArmsDown()) {
            isFlippedDwn = true;
            return isFlippedDwn;
        } else {
            isFlippedDwn = false;
            return isFlippedDwn;
        }
    }

    @Override
    public void update() {
        //TODO: Lowkey not sure what these do
    }

    @Override
    public void done() {
        SmartDashboard.putBoolean("isGrabCubePneumAction Complete?", isFlippedDwn);
    }

    @Override
    public void start() {
        mGrabber2.setFlipperUpperState(flipState);
    }
}
