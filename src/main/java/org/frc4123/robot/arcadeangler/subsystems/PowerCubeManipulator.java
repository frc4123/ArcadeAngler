package org.frc4123.robot.arcadeangler.subsystems;

import org.frc4123.robot.arcadeangler.Constants;

public class PowerCubeManipulator {

    EjectIntakeSpeedController lManipulatorArm = new EjectIntakeSpeedController(Constants.id_grabber_wheel_left, Constants.kIntakeCubeSpeed, Constants.id_intake_limit, Constants.kEjectCubeSpeed, Constants.kTimeCubeEject);
    EjectIntakeSpeedController rManipulatorArm = new EjectIntakeSpeedController(Constants.id_grabber_wheel_right, Constants.kIntakeCubeSpeed, Constants.id_intake_limit, Constants.kEjectCubeSpeed, Constants.kTimeCubeEject);

    public void ejectCube(){
        lManipulatorArm.eject();
        rManipulatorArm.eject();
    }

    public void intakeCube(){
        lManipulatorArm.intake();
        rManipulatorArm.intake();
    }

    public void stop(){
        lManipulatorArm.stop();
        rManipulatorArm.stop();
    }
}
