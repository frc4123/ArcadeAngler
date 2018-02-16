package org.frc4123.robot.arcadeangler.subsystems;

import edu.wpi.first.wpilibj.Spark;
import org.frc4123.robot.arcadeangler.Constants;

public class PowerCubeManipulator {

    EjectIntakeSpeedController lManipulatorArm = new EjectIntakeSpeedController(Constants.id_grabber_wheel_left, Constants.kIntakeCubeSpeed, Constants.id_intake_limit, Constants.kEjectCubeSpeed, Constants.kTimeCubeEject);
    EjectIntakeSpeedController rManipulatorArm = new EjectIntakeSpeedController(Constants.id_grabber_wheel_right, Constants.kIntakeCubeSpeed, Constants.id_intake_limit, Constants.kEjectCubeSpeed, Constants.kTimeCubeEject);

    Spark armFlipperMotor = new Spark(Constants.id_grabber_flipper_upper);

    public enum Mode {
        EJECT, INTAKE, FOLDUP, FOLDDOWN, STOPINTAKE, STOPFOLD, STOPALL
    }

    private Mode mode = Mode.STOPALL;

    public void setMode(Mode desiredState) {
        this.mode = desiredState;

        switch (mode) {
            case EJECT:
                ejectCube();
                break;
            case INTAKE:
                intakeCube();
                break;
            case FOLDUP:
                foldArmsUp();
                break;
            case FOLDDOWN:
                foldArmsDown();
                break;
            case STOPINTAKE:
                stopWheels();
                break;
            case STOPFOLD:
                stopFolding();
                break;
            default:
                case STOPALL:
                stopWheels();
                stopFolding();
                break;
        }
    }

    public void ejectCube(){
        lManipulatorArm.setCurrentState(EjectIntakeSpeedController.CurrentState.EJECTING);
        rManipulatorArm.setCurrentState(EjectIntakeSpeedController.CurrentState.EJECTING);
    }

    public void intakeCube(){
        lManipulatorArm.setCurrentState(EjectIntakeSpeedController.CurrentState.INTAKING);
        rManipulatorArm.setCurrentState(EjectIntakeSpeedController.CurrentState.INTAKING);
    }

    public void stopWheels(){
        lManipulatorArm.setCurrentState(EjectIntakeSpeedController.CurrentState.STOPPED);
        rManipulatorArm.setCurrentState(EjectIntakeSpeedController.CurrentState.STOPPED);
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
