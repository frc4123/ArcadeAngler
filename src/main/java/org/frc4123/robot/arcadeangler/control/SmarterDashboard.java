package org.frc4123.robot.arcadeangler.control;

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
    public void SmartDashboard(){
        //TODO
    }

    SendableChooser robotPositionChooser = new SendableChooser();
    SendableChooser autoModeChooser = new SendableChooser();
    public String robotPosition = "C";

    public void setAutoInfo(){
        robotPositionChooser.addDefault("Center", robotPosition = "C");
        robotPositionChooser.addObject("Left", robotPosition = "L");
        robotPositionChooser.addObject("Right", robotPosition = "R");
        SmartDashboard.putData("Robot Position", robotPositionChooser);

        autoModeChooser.addDefault("Cross Baseline", robotPosition = "C");
        autoModeChooser.addObject("Deposit Cube in Switch", robotPosition = "L");
        autoModeChooser.addObject("Deposit Cube on Scale", robotPosition = "L");

    }
    public void getAutoInfo(){
        SmartDashboard.getData("Robot Position");
        //TODO: place auto info into sd
    }
}
