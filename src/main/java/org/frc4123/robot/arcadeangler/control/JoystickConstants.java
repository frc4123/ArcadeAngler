package org.frc4123.robot.arcadeangler.control;

public class JoystickConstants {

    /*Currently sourced from
    * http://www.team358.org/files/programming/ControlSystem2015-2019/images/XBoxControlMapping.jpg
    * and
    * http://team358.org/files/programming/ControlSystem2009-/components.php
    * and
    * http://eidetec.com/frc-controller
    * TODO: Double Check these values before merging with master
    * */

    //**XBOX Controller**//
    //Button Constants
    public static final int kXBOX_A = 0;
    public static final int kXBOX_B = 1;
    public static final int kXBOX_X = 2;
    public static final int kXBOX_Y = 3;
    public static final int kXBOX_LBump = 4;
    public static final int kXBOX_RBump = 5;
    public static final int kXBOX_Back = 6;
    public static final int kXBOX_Start = 7;
    public static final int kXBOX_LJoyPress = 8;
    public static final int kXBOX_RJoyPress = 9;
    //Axis Constants
    public static final int kXBOX_LJoyX = 0; //Left is negative
    public static final int kXBOX_LJoyY = 1; //Up is negative
    public static final int kXBOX_LTrigger = 2; //Only 0 to 1
    public static final int kXBOX_RTrigger = 3; //Only 0 to 1
    public static final int kXBOX_RJoyX = 4; //Left is negative
    public static final int kXBOX_RJoyY = 5; //Up is negative

    //**Logitech F310 Game Controller**//
    //Button Constants
    public static final int kF310_A = 1;
    public static final int kF310_B = 2;
    public static final int kF310_X = 3;
    public static final int kF310_Y = 4;
    public static final int kF310_LBump = 5;
    public static final int kF310_RBump = 6;
    public static final int kF310_Back = 7;
    public static final int kF310_Start = 8;
    public static final int kF310_LJoyPress = 9;
    public static final int kF310_RJoyPress = 10;
    //Axis Constants
    public static final int kF310_LJoyX = 1; //Left is negative
    public static final int kF310_LJoyY = 2; //Up is negative
    public static final int kF310_LTrigger = 3; //Only 0 to 1
    public static final int kF310_RTrigger = 4; //Only 0 to 1
    public static final int kF310_RJoyX = 5; //Left is negative
    public static final int kF310_RJoyY = 6; //Up is negative

    //**Logitech Extreme 3D (Flight Sim Joystick)**//
    //Button Constants - Note: Buttons with mere numbers are numbered on the controller
    public static final int kX3D_Trigger = 1;
    public static final int kX3D_TriggerThumb = 2;
    public static final int kX3D_3 = 3;
    public static final int kX3D_4 = 4;
    public static final int kX3D_5 = 5;
    public static final int kX3D_6 = 6;
    public static final int kX3D_7 = 7;
    public static final int kX3D_8 = 8;
    public static final int kX3D_9 = 9;
    public static final int kX3D_10 = 10;
    public static final int kX3D_11 = 11;
    public static final int kX3D_12 = 12;
    //Axis Constants
    public static final int kX3D_X = 1; //Forward is negative
    public static final int kX3D_Y = 1; //Left is negative
    public static final int kX3D_Twist = 1; //Left is negative
    public static final int kX3D_Potentiometer = 1; //Note that indicated positive on the controller is negative is code. Up is -1
}
