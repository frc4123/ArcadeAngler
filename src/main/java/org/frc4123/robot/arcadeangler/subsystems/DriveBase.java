package org.frc4123.robot.arcadeangler.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.frc4123.robot.arcadeangler.Constants;

public class DriveBase {

    private static DriveBase mInstance = null;
    public static DriveBase getInstance() {
        if (mInstance == null) {
            mInstance = new DriveBase();
        }
        return mInstance;
    }

    public WPI_TalonSRX l_master = new WPI_TalonSRX(Constants.id_driveLeftMaster);
    WPI_TalonSRX l_slave = new WPI_TalonSRX(Constants.id_driveLeftSlave);
    public WPI_TalonSRX r_master = new WPI_TalonSRX(Constants.id_driveRightMaster);
    WPI_TalonSRX r_slave = new WPI_TalonSRX(Constants.id_driveRightSlave);

    SpeedControllerGroup left = new SpeedControllerGroup(l_master);
    SpeedControllerGroup right = new SpeedControllerGroup(r_master);

    public DifferentialDrive mDrive = new DifferentialDrive(left, right);


    public DriveBase() {
        l_master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
        r_master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

        l_master.set(ControlMode.PercentOutput, 0);
        l_slave.set(ControlMode.Follower, Constants.id_driveLeftMaster);
        r_master.set(ControlMode.PercentOutput, 0);
        r_slave.follow(r_master);
        System.out.println("Robot.robotInit");

        //TODO: Test what happens when you have this enabled but there's no pneumatics commands
        //2018 Pneumatics Additions
//        squishyBoi.setClosedLoopControl(true);


        l_master.setSensorPhase(true);
        r_master.setSensorPhase(true);

        r_master.setInverted(true);
        r_slave.setInverted(true);
    }

    public void resetDriveEncoders(){
        l_master.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
        r_master.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }
}
