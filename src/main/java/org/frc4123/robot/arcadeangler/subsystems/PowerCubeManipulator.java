package org.frc4123.robot.arcadeangler.subsystems;

import edu.wpi.first.wpilibj.Spark;
import org.frc4123.robot.arcadeangler.Constants;

public class PowerCubeManipulator {

    EjectIntakeSpeedController lManipulatorArm = new EjectIntakeSpeedController(Constants.id_grabber_wheel_left, Constants.kIntakeCubeSpeed, Constants.id_intake_limit, Constants.kEjectCubeSpeed, Constants.kTimeCubeEject);
    EjectIntakeSpeedController rManipulatorArm = new EjectIntakeSpeedController(Constants.id_grabber_wheel_right, Constants.kIntakeCubeSpeed, Constants.id_intake_limit, Constants.kEjectCubeSpeed, Constants.kTimeCubeEject);

    Spark armFlipperMotor = new Spark(Constants.id_grabber_flipper_upper);

    public void ejectCube(){
        lManipulatorArm.eject();
        rManipulatorArm.eject();
    }

    public void intakeCube(){
        lManipulatorArm.intake();
        rManipulatorArm.intake();
    }

    public void stopWheels(){
        lManipulatorArm.stop();
        rManipulatorArm.stop();
    }

    public void foldArmsDown(){
        armFlipperMotor.set(Constants.kFoldArmsDwn);
    }

    public void foldArmsUp(){
        armFlipperMotor.set(Constants.kFoldArmsUp);
    }

    public void stopFolding(){
        armFlipperMotor.set(0);
    }
}
