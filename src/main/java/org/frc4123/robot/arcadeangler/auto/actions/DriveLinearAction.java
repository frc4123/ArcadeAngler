package org.frc4123.robot.arcadeangler.auto.actions;

public class DriveLinearAction implements Action{


    private double mTargetDistance;

    public DriveLinearAction(double encunits){
        mTargetDistance = encunits;
    }

    @Override
    public boolean isFinished() {
        System.out.println("DriveLinearAction onTarget: " + robotDrive.onTarget());
        return robotDrive.onTarget();
    }

    @Override
    public void update() {
    }

    @Override
    public void done() {
        //robotDrive.setRelativeSetpoint(0);
        //driveModeController.setDriveMode(DriveMode.NONE);
        System.out.println("DriveLinearAction done");
    }

    @Override
    public void start() {
        robotDrive.setSafetyEnabled(false);
        driveModeController.setDriveMode(DriveMode.LINEAR);
        robotDrive.setRelativeSetpoint(mTargetDistance);
        System.out.println("DriveLinearAction start");
    }

}
