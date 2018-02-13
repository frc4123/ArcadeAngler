package org.frc4123.robot.arcadeangler.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import org.frc4123.robot.arcadeangler.Constants;

public class PowerCubeManipulator {

    DoubleSolenoid armOpener = new DoubleSolenoid(2, 3);
    DoubleSolenoid grabFlipper = new DoubleSolenoid(0, 1);

    public void ejectCube(){
        armOpener.set(DoubleSolenoid.Value.kForward);
    }

    public void intakeCube(){
        armOpener.set(DoubleSolenoid.Value.kReverse);
    }

    public void stopGrabbing(){
        armOpener.set(DoubleSolenoid.Value.kOff);
    }

    public void foldArmsDown(){
        grabFlipper.set(DoubleSolenoid.Value.kReverse);
    }

    public void foldArmsUp(){
        grabFlipper.set(DoubleSolenoid.Value.kForward);
    }

    public void stopFolding(){
        grabFlipper.set(DoubleSolenoid.Value.kOff);
    }
}
