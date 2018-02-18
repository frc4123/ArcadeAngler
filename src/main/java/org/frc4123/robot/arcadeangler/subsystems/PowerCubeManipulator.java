package org.frc4123.robot.arcadeangler.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import org.frc4123.robot.arcadeangler.Constants;

public class PowerCubeManipulator {

    DoubleSolenoid armOpener = new DoubleSolenoid(2, 3);
    DoubleSolenoid grabFlipper = new DoubleSolenoid(0, 1);

    public void foldArmsDown(){
        grabFlipper.set(DoubleSolenoid.Value.kReverse);
    }

    public void foldArmsUp(){
        grabFlipper.set(DoubleSolenoid.Value.kForward);
    }

    public void open(){
        armOpener.set(DoubleSolenoid.Value.kForward);
    }

    public void close(){
        armOpener.set(DoubleSolenoid.Value.kReverse);
    }

    public void stopFolding(){
        grabFlipper.set(DoubleSolenoid.Value.kOff);
    }

    public void stopGrabbingCube(){
        armOpener.set(DoubleSolenoid.Value.kOff);
    }
}
