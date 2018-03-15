package org.frc4123.robot.arcadeangler.control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmarterDashboard extends edu.wpi.first.wpilibj.smartdashboard.SmartDashboard {

    private static SmarterDashboard mInstance = null;
    public static SmarterDashboard getInstance() {
        if (mInstance == null) {
            mInstance = new SmarterDashboard();
        }
        return mInstance;
    }

    public enum robotStartingPosition {CENTER, RIGHT, LEFT}
    private robotStartingPosition startPos = robotStartingPosition.CENTER;
    public String robotPosition;
    SendableChooser robotPositionChooser = new SendableChooser();

    SendableChooser autoModeChooser = new SendableChooser();

    public void setRobotStartingPosition(robotStartingPosition startingPos) {
        this.startPos = startingPos;

        switch (startPos) {
            case CENTER:
            default:
                robotPosition = "C";
                break;
            case RIGHT:
                robotPosition = "R";
                break;
            case LEFT:
                robotPosition = "L";
                break;
        }
    }

    public String getRobotStartingPosition(){
        return robotPosition;
    }

    public void setAutoInfo(){
        robotPositionChooser.addDefault("Center", robotStartingPosition.CENTER);
        robotPositionChooser.addObject("Left", robotStartingPosition.LEFT);
        robotPositionChooser.addObject("Right", robotStartingPosition.RIGHT);
        SmartDashboard.putData("Robot Position", robotPositionChooser);

//        autoModeChooser.addDefault("Cross Baseline", new );
//        autoModeChooser.addObject("Deposit Cube in Switch", robotPosition = "L");
//        autoModeChooser.addObject("Deposit Cube on Scale", robotPosition = "L");

    }
    public void getAutoInfo(){
        SmartDashboard.getData("Robot Position");
        //TODO: place auto info into sd
    }
}