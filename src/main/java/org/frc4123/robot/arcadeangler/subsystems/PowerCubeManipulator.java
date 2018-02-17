package org.frc4123.robot.arcadeangler.subsystems;

import edu.wpi.first.wpilibj.Spark;
import org.frc4123.robot.arcadeangler.Constants;

public class PowerCubeManipulator {

//    EjectIntakeSpeedController lManipulatorArm = new EjectIntakeSpeedController(Constants.id_grabber_wheel_left, Constants.kIntakeCubeSpeed, Constants.id_intake_limit, Constants.kEjectCubeSpeedMod, Constants.kTimeCubeEject);
//    EjectIntakeSpeedController rManipulatorArm = new EjectIntakeSpeedController(Constants.id_grabber_wheel_right, Constants.kIntakeCubeSpeed, Constants.id_intake_limit, Constants.kEjectCubeSpeedMod, Constants.kTimeCubeEject);

    Spark armFlipperMotor = new Spark(Constants.id_grabber_flipper_upper);
    Spark armWheels = new Spark(Constants.id_grabber_wheels);

    public void setFlipperUpperSpeed(double speed) {
        armFlipperMotor.set(speed);
    }

    public void setIntakeSpeed(double speed){
        armWheels.set(speed*Constants.kEjectCubeSpeedMod);
    }

    public void stopWheels(){
        armWheels.set(0);
    }

    public void stopFolding(){
        armFlipperMotor.set(0);
    }

    public void stopAll(){
        stopWheels();
        stopFolding();
    }
}
