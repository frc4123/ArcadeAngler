package org.frc4123.robot.arcadeangler.auto.actions;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc4123.robot.arcadeangler.subsystems.PneumaticGrabber;

public class GrabCubePneumaticAction implements Action {

    private final PneumaticGrabber mGrabber2 = new PneumaticGrabber();

    private boolean isCubeGrabbed = false;

    @Override
    public boolean isFinished() {
        if (mGrabber2.isArmsDown() && mGrabber2.isArmsClosed()){
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
        SmartDashboard.putBoolean("isGrabCubePneumAction Complete?", isCubeGrabbed);
    }

    @Override
    public void start() {
        mGrabber2.foldArmsDown();
        while (mGrabber2.isArmsDown()) {
            mGrabber2.close();
        }
    }
}
