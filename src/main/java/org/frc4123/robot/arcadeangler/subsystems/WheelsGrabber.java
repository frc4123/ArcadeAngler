package org.frc4123.robot.arcadeangler.subsystems;


import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import org.frc4123.robot.arcadeangler.Constants;

public class WheelsGrabber {

    Spark armFlipper = new Spark(Constants.id_grabber_flipper_upper);
    Spark armWheels = new Spark(Constants.id_grabber_wheels);

    Timer cubeIntakeTimer = new Timer();

    public void setFlipperUpperSpeed(double speed) {
        armFlipper.set(speed);
    }

    public void setIntakeSpeed(double speed){
        armWheels.set(speed*Constants.kEjectCubeSpeedMod);
    }

    public void stopWheels(){
        armWheels.set(0);
    }

    public void stopFolding(){
        armFlipper.set(0);
    }

    public void stopAll(){
        stopWheels();
        stopFolding();

    }

    public boolean isCubeGrabbed() {
        //TODO: Need way to return this for real so we don't burn out the motors
        return false;
    }

    public boolean isArmsDown() {
        //TODO: Find a better way to get this
        if (armFlipper.get() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
