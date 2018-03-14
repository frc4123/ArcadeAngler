package org.frc4123.robot.arcadeangler.control;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmarterDashboard extends edu.wpi.first.wpilibj.smartdashboard.SmartDashboard {

    SendableChooser robotPositionChooser = new SendableChooser();
    public String robotPosition = "C";

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

    public void setAutoInfo(){
        robotPositionChooser.addDefault("Center", robotPosition = "C");
        robotPositionChooser.addObject("Left", robotPosition = "L");
        robotPositionChooser.addObject("Right", robotPosition = "R");
        SmartDashboard.putData("Robot Position", robotPositionChooser);
        
    }
    public void getAutoInfo(){
        SmartDashboard.getData("Robot Position");
        //TODO: place auto info into sd
    }
}
