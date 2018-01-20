package org.frc4123.robot.arcadeangler.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import org.frc4123.robot.arcadeangler.subsystems.EjectIntakeSpeedController;

public class PowerCubeManipulator {

    EjectIntakeSpeedController lManipulatorArm = new EjectIntakeSpeedController();
    EjectIntakeSpeedController rManipulatorArm = new EjectIntakeSpeedController();

    public void ejectCube(){
        lManipulatorArm.eject();
        lManipulatorArm.eject();
    }
}
