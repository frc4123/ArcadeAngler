package org.frc4123.robot.arcadeangler.subsystems;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import org.frc4123.robot.arcadeangler.Constants;

public class Elevator {

    WPI_TalonSRX master = new WPI_TalonSRX(Constants.id_elevateMaster);
    WPI_TalonSRX slave = new WPI_TalonSRX(Constants.id_elevateSlave);

    DigitalInput descendLimit = new DigitalInput(Constants.id_descend_limit);
    boolean isDescended = false;

    public enum Mode {
        MANUAL(0), HIGH(Constants.kElevatorHigh), MEDIUM(Constants.kElevatorMedium), LOW(Constants.kElevatorLow);

        private double position;

        Mode(double position) {
            this.position = position;
        }


        public double getPosition() {
            return position;
        }
    }

    private Mode mode = Mode.MANUAL;


    public Elevator() {
        master.set(ControlMode.PercentOutput, 0);
        master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

        //master.config_IntegralZone(Constants.kPIDLoopIdx, Constants.kIntegralZone, Constants.kTimeoutMs);
        //master.config_kI(Constants.kPIDLoopIdx, 0.0, 10);
        //master.configForwardSoftLimitThreshold(Constants.kElevateMaxPos, Constants.kTimeoutMs);
        //master.configForwardSoftLimitEnable(true, Constants.kTimeoutMs);
       // master.configMaxIntegralAccumulator(0, 0, 10);
        master.configAllowableClosedloopError(Constants.kPIDLoopIdx, 100, 10);
        //master.setIntegralAccumulator(0, Constants.kPIDLoopIdx, 10);

        master.configOpenloopRamp(0, 0);
        slave.follow(master);
        
    }

    public void setMode(Mode desiredState) {
        this.mode = desiredState;

        switch (mode) {
            case MANUAL:
                break;
            case HIGH: case MEDIUM: case LOW:

                master.set(ControlMode.Position, desiredState.getPosition());
                break;
            default:
                stop();
                break;
        }
    }

    /**
     * Sets the speed controler's mode to {@link ControlMode#PercentOutput} only if in MANUAL mode and speed to speed
     * @param speed between -1.0 and 1.0, with 0.0 as stopped.
     */
    public void set(double speed) {
        System.out.println("passedspeed = " + speed);
        System.out.println("currentspeed = " + master.getClosedLoopError(0));
        System.out.println("mode = " + mode);
        System.out.println("master.getClosedLoopError() = " + master.getClosedLoopError(Constants.kPIDLoopIdx));
        System.out.println("master.getIntegralAccumulator() = " + master.getIntegralAccumulator(Constants.kPIDLoopIdx));
        System.out.println("master.configGetParameter(ParamEnum.eProfileParamSlot_I, 0,10) = " + master.configGetParameter(ParamEnum.eProfileParamSlot_I, 0,10));
//        System.out.println("i term = " + master.getT);
        if (mode == Mode.MANUAL) {
            System.out.println("manual mode");
            master.set(ControlMode.PercentOutput, speed);
        }
    }

    public void stop() {
        setMode(Mode.MANUAL);
        master.set(ControlMode.PercentOutput,0); //TODO maybe ControlMode.Disabled?
    }

    public boolean getDescendLimitSW() {
        if (descendLimit.get()){
            isDescended = true;
        }else {
            isDescended = false;
        }
        return isDescended;
    }

    public void resetEncoder() {
        master.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }

    public Mode getMode() {
        return mode;
    }

    public boolean hasReachedSetpoint() {
        return master.getClosedLoopError(Constants.kPIDLoopIdx) < 10; //TODO probs a better way of getting if isOnTarget
    }
}
