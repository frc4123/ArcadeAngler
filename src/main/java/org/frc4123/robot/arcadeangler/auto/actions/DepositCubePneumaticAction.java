package org.frc4123.robot.arcadeangler.auto.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc4123.robot.arcadeangler.subsystems.PneumaticGrabber;

public class DepositCubePneumaticAction implements Action {

    private final PneumaticGrabber mGrabber2 = new PneumaticGrabber();

    private boolean isCubeDeposited = false;

    @Override
    public boolean isFinished() {
        if (!mGrabber2.isArmsDown() && !mGrabber2.isArmsClosed()){
            isCubeDeposited = true;
            return isCubeDeposited;
        } else {
            isCubeDeposited = false;
            return isCubeDeposited;
        }
    }

    @Override
    public void update() {
        //TODO: Lowkey not sure what these do
    }

    @Override
    public void done() {
        SmartDashboard.putBoolean("isDepositCubePneum Complete?", isCubeDeposited);
    }

    @Override
    public void start() {
        mGrabber2.foldArmsUp();
        //TODO: This while might come back to bite us
        while (!mGrabber2.isArmsDown()) {
            mGrabber2.open();
        }
    }
}
