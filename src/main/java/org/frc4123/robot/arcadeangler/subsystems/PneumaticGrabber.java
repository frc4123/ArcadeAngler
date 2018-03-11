package org.frc4123.robot.arcadeangler.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class PneumaticGrabber {

    private static PneumaticGrabber mInstance = null;
    public static PneumaticGrabber getInstance() {
        if (mInstance == null) {
            mInstance = new PneumaticGrabber();
        }
        return mInstance;
    }

    DoubleSolenoid armOpener = new DoubleSolenoid(2, 3);
    DoubleSolenoid grabFlipper = new DoubleSolenoid(0, 1);
    public Compressor squishyBoi = new Compressor(0);

    public enum GrabberState {OPEN, CLOSE, NEUTRAL;}

    private GrabberState grabState = GrabberState.NEUTRAL;

    public enum FlipperUpperState {UP, DOWN, NEUTRAL}

    private FlipperUpperState flipState = FlipperUpperState.NEUTRAL;

    public void setGrabberState(GrabberState grabState) {
        this.grabState = grabState;

        switch (grabState) {
            case OPEN:
                open();
                break;
            case CLOSE:
                close();
                break;
            case NEUTRAL:
            default:
                stopGrabbingCube();
                break;
        }
    }

    public void setFlipperUpperState(FlipperUpperState flipState) {
        this.flipState = flipState;

        switch (flipState) {
            case UP:
                foldArmsUp();
                break;
            case DOWN:
                foldArmsDown();
                break;
            case NEUTRAL:
            default:
                stopFolding();
                break;
        }
    }

    public void foldArmsDown() {
        grabFlipper.set(DoubleSolenoid.Value.kReverse);
    }

    public void foldArmsUp() {
        grabFlipper.set(DoubleSolenoid.Value.kForward);
    }

    public void open() {
        armOpener.set(DoubleSolenoid.Value.kForward);
    }

    public void close() {
        armOpener.set(DoubleSolenoid.Value.kReverse);
    }

    public void stopFolding() {
        grabFlipper.set(DoubleSolenoid.Value.kOff);
    }

    public void stopGrabbingCube() {
        armOpener.set(DoubleSolenoid.Value.kOff);
    }

    public boolean isArmsClosed() {
        if (armOpener.get() == DoubleSolenoid.Value.kReverse) {
            return true;
        } else if (armOpener.get() == DoubleSolenoid.Value.kForward) {
            return false;
        } else {
            return false;
        }
    }

    public boolean isArmsDown() {
        if (grabFlipper.get() == DoubleSolenoid.Value.kReverse) {
            return true;
        } else if (grabFlipper.get() == DoubleSolenoid.Value.kForward) {
            return false;
        } else {
            return false;
        }
    }

}
