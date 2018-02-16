package org.frc4123.robot.arcadeangler;

public class Constants {

    //TODO: Check all these values. Will be a progressive task

    public static final int kTimeCubeEject = 2; //In seconds Todo: Hopefully...

    /***********AUTO***********/

    /**********TELEOP**********/

    //PowerCube Manipulator Constants
    public static final float kEjectCubeSpeed = 1;
    public static final float kIntakeCubeSpeed = -1;
    public static final float kFoldArmsUp = 1;
    public static final float kFoldArmsDwn = -1;
    public static final double kJoyNeutralZone = 0.5;

    //Climber Constants
    public static final float kClimberUpSpeed = -1;

    //Drive Chassis and wheel and motor constants
    public static final double kChassisWheelDiameterInch = 6;
    public static final double kChassisWheelCircumferenceInch = kChassisWheelDiameterInch*Math.PI;

    //Elevator PID parameters
    //TODO Tune these, determine if relevant
    public static final double kElevateP = 0;
    public static final double kElevateI = 0;
    public static final double kElevateD = 0;

    //Drive Chassis PID parameters
    //TODO Tune these
    public static final double kDriveLeftP = 0;
    public static final double kDriveLeftI = 0;
    public static final double kDriveLeftD = 0;
    public static final double kDriveRightP = 0;
    public static final double kDriveRightI = 0;
    public static final double kDriveRightD = 0;
    public static final double kLinearClosedLoop_Tolerance_Default = 1024;

    public static final double kHeadingClosedLoop_P = 0;
    public static final double kHeadingClosedLoop_I = 0;
    public static final double kHeadingClosedLoop_D = 0;
    public static final double kHeadingClosedLoop_Tolerance_Default = 1;

    //Elevator
    public static final int kElevatorDefaultElevateSpeed = 1;


    //Robot Ports - These should match up to TODO: create Google doc to outline motor controller ports

    //Drive CANTalons
    public static final int id_driveLeftMaster = 1;
    public static final int id_driveLeftSlave = 0;
    public static final int id_driveRightMaster = 2;
    public static final int id_driveRightSlave = 3;

    //TODO: Set these to reality once the electronics is laid out. Sparks?
    //PowerCube Manipulator Speed Controllers
    public static final int id_grabber_wheel_left = 1;
    public static final int id_grabber_wheel_right = 0;
    public static final int id_grabber_flipper_upper = 2;
    //PowerCube Grabber Limit Switch
    public static final int id_intake_limit = 1; //TODO: Find real input ID

    //Elevator Speed Controllers
    public static final int id_elevate_talon_right = 4;
    public static final int id_eleavte_talon_left = 5;


}
