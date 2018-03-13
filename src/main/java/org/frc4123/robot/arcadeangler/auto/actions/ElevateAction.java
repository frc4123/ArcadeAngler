package org.frc4123.robot.arcadeangler.auto.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc4123.robot.arcadeangler.subsystems.Elevator;

public class ElevateAction implements Action {

    private Elevator mElevator = Elevator.getInstance();

    public enum Position {SWITCH, SCALE, DOWN};
    private Elevator.Mode elevateMode;
    private boolean isElevationFinished = false;

    public ElevateAction(Position position) {

        switch (position) {
            case SWITCH:
                elevateMode = Elevator.Mode.HIGH;
                break;
            case SCALE:
                elevateMode = Elevator.Mode.MEDIUM;
                break;
            case DOWN:
                elevateMode = Elevator.Mode.LOW;
                break;
        }

    }

    @Override
    public boolean isFinished() {
        isElevationFinished = true;
        return mElevator.hasReachedSetpoint();
    }

    @Override
    public void update() {
    }

    @Override
    public void done() {
        //TODO: Consider necessity of this
        SmartDashboard.putBoolean("isElevateAction Complete?", isElevationFinished);
    }

    @Override
    public void start() {
        mElevator.setMode(elevateMode);
    }
}
