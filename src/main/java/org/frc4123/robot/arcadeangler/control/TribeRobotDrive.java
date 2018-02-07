package org.frc4123.robot.arcadeangler.control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc4123.robot.arcadeangler.Constants;
import org.frc4123.robot.arcadeangler.Utils;

public class TribeRobotDrive extends DifferentialDrive{
    private static WPI_TalonSRX leftMaster = new WPI_TalonSRX(Constants.id_driveLeftMaster);
    private static WPI_TalonSRX leftSlave = new WPI_TalonSRX(Constants.id_driveLeftSlave);
    private static WPI_TalonSRX rightMaster = new WPI_TalonSRX(Constants.id_driveRightMaster);
    private static WPI_TalonSRX rightSlave = new WPI_TalonSRX(Constants.id_driveRightSlave);

    private final ADXRS450_Gyro mGyro;

    public enum AssistMode {NONE, HEADING, LINEAR}
    AssistMode mAssistMode = AssistMode.NONE;
    private PIDController headingPIDController;

    private double linearClosedLoopTolerance = Constants.kLinearClosedLoop_Tolerance_Default;

    private ControlMode driveMode = ControlMode.RAW;

    static TribeRobotDrive mInstance = new TribeRobotDrive();
    public static TribeRobotDrive getInstance(){
        return mInstance;
    }

    private TribeRobotDrive() {
        super(leftMaster, rightMaster);

        //Set up TalonSRXs in open loop mode
        //Set Masters to PercentVbus mode
        leftMaster.set(com.ctre.phoenix.motorcontrol.ControlMode.Velocity, 0);
        rightMaster.set(com.ctre.phoenix.motorcontrol.ControlMode.Velocity, 0);

        //Set Slaves to follow
        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);

        rightMaster.setInverted(true);
        leftMaster.setSensorPhase(true);

        setTalonControlMode(driveMode);

        mGyro = new ADXRS450_Gyro();

        //Set up a PIDController for controlling robot's heading using mGyro as a feedback sensor
        headingPIDController = new PIDController(
                Constants.kHeadingClosedLoop_P, Constants.kHeadingClosedLoop_I, Constants.kHeadingClosedLoop_D,
                getGyro(),
                new PIDOutput() {
                    @Override
                    public void pidWrite(double output) {
                        //Turn robot however much the PID controller tells us to
                        //RobotDrive and Gyro have different directions set to +
                        arcadeDrive(0, -output);
                    }
                });
        //Robot can turn either way to reach setpoint; do whichever is shortest
        headingPIDController.setContinuous(true);
        //TODO What's our min and max? setContinuous is useless without one.

        //Set up P I and D parameters in SmartDashboard
        SmartDashboard.putNumber("headingPID_P", Constants.kHeadingClosedLoop_P);
        SmartDashboard.putNumber("headingPID_I", Constants.kHeadingClosedLoop_I);
        SmartDashboard.putNumber("headingPID_D", Constants.kHeadingClosedLoop_D);


    }

    public void arcadeDrive(double moveValue, double rotateValue, double speedMod){
        super.arcadeDrive(speedMod*moveValue, speedMod*rotateValue);
    }

    public enum ControlMode{
        RAW, ENCODER_VEL, ENCODER_POS
    }

    public void setTalonControlMode(ControlMode mode){
        switch (mode){
            case RAW:
                rightMaster.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);
                leftMaster.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, 0);

               // rightMaster.set(0);
               // leftMaster.set(0);
                break;

            case ENCODER_VEL:
                rightMaster.set(com.ctre.phoenix.motorcontrol.ControlMode.Velocity, 0);
                leftMaster.set(com.ctre.phoenix.motorcontrol.ControlMode.Velocity, 0);

                //rightMaster.set(0);
                //leftMaster.set(0);
                break;

            case ENCODER_POS:
                rightMaster.set(com.ctre.phoenix.motorcontrol.ControlMode.Position, rightMaster.getActiveTrajectoryPosition());
                leftMaster.set(com.ctre.phoenix.motorcontrol.ControlMode.Position, leftMaster.getActiveTrajectoryPosition());

                //rightMaster.set(;
                //leftMaster.set(leftMaster.getPosition());
                break;
        }
    }


    /**
     * Sets TalonControlMode. Use this to switch between normal drive mode, heading, and linear setpoints.
     * @param assistMode NONE, HEADING, LINEAR
     */
    public void setAssistMode(AssistMode assistMode){
        this.mAssistMode = assistMode;
        switch (assistMode){
            case NONE:
                setTalonControlMode(ControlMode.RAW);
                headingPIDController.disable();
                break;
            case HEADING:
                setTalonControlMode(ControlMode.RAW);
                headingPIDController.enable();
                break;
            case LINEAR:
                setTalonControlMode(ControlMode.ENCODER_POS);
                headingPIDController.disable();
                break;
        }
    }
    /**
     * Set HEADING or LINEAR setpoint, depending on TribeRobotDrive's current assistMode
     */
    public void setSetpoint(double setpoint){
        switch (mAssistMode){
            case NONE:
                //TODO Shouldn't be called like this. Should we throw an error?
                break;
            case HEADING:
                headingPIDController.setSetpoint(setpoint);
                break;
            case LINEAR:
                setSetpointRight(setpoint);
                setSetpointLeft(setpoint);
                break;
        }
    }

    /**
     * Calls setSetpoint(deltaSetpoint + currentPosition)
     */
    public void setRelativeSetpoint(double deltaSetpoint){
        System.out.println("setRelativeSetipoint");
        switch (mAssistMode){
            case NONE:
                //TODO Shouldn't be called like this. Should we throw an error?
                break;
            case HEADING:
                headingPIDController.setSetpoint(mGyro.getAngle() + deltaSetpoint);
                break;
            case LINEAR:
                setSetpointRight(rightMaster.getActiveTrajectoryPosition() + deltaSetpoint);
                System.out.println("Right Position = " + rightMaster.getActiveTrajectoryPosition());
                setSetpointLeft(leftMaster.getActiveTrajectoryPosition() + deltaSetpoint);
                break;
        }
    }

    /**
     *
     * @return current setpoint of either the heading or linear, depending on assistMode
     */
    public double getSetpoint(){
        switch (mAssistMode){
            case NONE:
                //TODO Shouldn't be called like this. Should we throw an error?
                return 0;
            case HEADING:
                return headingPIDController.getSetpoint();
            case LINEAR:
                return (getSetpointRight() + getSetpointLeft()) / 2;
            default:
                return 0;
        }

    }

    /**
     *
     * @return current error of either the heading or linear, depending on assistMode
     */
    public double getSetpointError(){
        switch (mAssistMode){
            case NONE:
                return 0;
            case HEADING:
                return headingPIDController.getError();
            case LINEAR:
                return (rightMaster.getLastError() + leftMaster.getLastError()) / 2;
            default:
                return 0;
        }
    }

    /**
     *
     * @return If TribeRobotDrive's error is less than the tolerance
     */
    public boolean onTarget(){
        switch (mAssistMode){
            case NONE:
                return false;
            case HEADING:
                return headingPIDController.getError() < Constants.kHeadingClosedLoop_Tolerance_Default;
            case LINEAR:
                System.out.println(rightMaster.getClosedLoopError());
                return Math.abs(((rightMaster.getError() + leftMaster.getError()) / 2)) <= linearClosedLoopTolerance;
            default:
                return false;
        }
    }

    public void setSetpointRight(double setpoint){
        rightMaster.set(setpoint);
    }
    public void setSetpointLeft(double setpoint){
        leftMaster.set(setpoint);
    }
    public double getSetpointRight(){
        return rightMaster.getSetpoint();
    }
    public double getSetpointLeft(){
        return leftMaster.getSetpoint();
    }

    public void setLinearClosedLoopTolerance(double linearClosedLoopTolerance){
        this.linearClosedLoopTolerance = linearClosedLoopTolerance;
    }

    public double getLinearClosedLoopTolerance(){
        return this.linearClosedLoopTolerance;
    }


    public ADXRS450_Gyro getGyro(){
        return mGyro;
    }


    public double getLeftDistanceInches(){
        return Utils.encUnitsToInches(leftMaster.getEncPosition());
    }

    public double getRightDistanceInches(){
        return Utils.encUnitsToInches(rightMaster.getEncPosition());
    }

    public double getLeftVelocityInchesPerSecond(){
        return Utils.encVelToInchesPerSecond(leftMaster.getSelectedSensorVelocity(0));
    }

    public double getRightVelocityInchesPerSecond(){
        return Utils.encVelToInchesPerSecond(rightMaster.getSelectedSensorVelocity(0));
    }

    /**
     * Prints status to SmartDashboard
     */
    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("left_distance", getLeftDistanceInches());
        SmartDashboard.putNumber("right_distance", getRightDistanceInches());
        SmartDashboard.putNumber("left_velocity", getLeftVelocityInchesPerSecond());
        SmartDashboard.putNumber("right_velocity", getRightVelocityInchesPerSecond());
        SmartDashboard.putNumber("left_error", leftMaster.getClosedLoopError(0));
        SmartDashboard.putNumber("right_error", rightMaster.getClosedLoopError(0));
        SmartDashboard.putNumber("gyro_angle", mGyro.getAngle());

        SmartDashboard.putNumber("robotdrive_setpoint_error", getSetpointError());
    }

    /**
     * Grabs PID heading data from SmartDashboard. Used for debugging robot.
     */
    public void setPIDFromSmartDashboard(){
        headingPIDController.setPID(
                SmartDashboard.getNumber("headingPID_P", Constants.kHeadingClosedLoop_P),
                SmartDashboard.getNumber("headingPID_I", Constants.kHeadingClosedLoop_I),
                SmartDashboard.getNumber("headingPID_D", Constants.kHeadingClosedLoop_D));
    }

    /**
     * Resets and zeros all sensors
     */
    public void resetSensors() {
        System.out.println("Resetting RobotDrive sensors");
        leftMaster.setSelectedSensorPosition(leftMaster.);
        rightMaster.setPosition(0);

        mGyro.reset();
    }

}
