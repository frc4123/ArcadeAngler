package org.frc4123.robot.arcadeangler.control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc4123.robot.arcadeangler.auto.modes.AutoModeBase;
import org.frc4123.robot.arcadeangler.auto.modes.CrossBaselineMode;
import org.frc4123.robot.arcadeangler.auto.modes.SwitchCubeDepositMode;
import org.frc4123.robot.arcadeangler.auto.modes.TestAutoMode;

public class SmarterDashboard extends edu.wpi.first.wpilibj.smartdashboard.SmartDashboard {

    private static SmarterDashboard mInstance = null;

    public static SmarterDashboard getInstance() {
        if (mInstance == null) {
            mInstance = new SmarterDashboard();
        }
        return mInstance;
    }

    String robotPosition;
    AutoModeBase selectedAutoMode;
    SendableChooser robotPositionChooser = new SendableChooser();

    SendableChooser autoModeChooser = new SendableChooser();


    public void sendAutoInfo() {
        robotPositionChooser.addDefault("Center", robotPosition = "C");
        robotPositionChooser.addObject("Left", robotPosition = "L");
        robotPositionChooser.addObject("Right", robotPosition = "R");
        SmartDashboard.putData("Robot Position", robotPositionChooser);

        autoModeChooser.addDefault("Cross Baseline", new CrossBaselineMode());
        autoModeChooser.addObject("Deposit Cube in Switch", new SwitchCubeDepositMode());
//        autoModeChooser.addObject("Deposit Cube in Scale", new ScaleCubeDepositMode());
        autoModeChooser.addObject("TestAutoMode", new TestAutoMode());
        SmartDashboard.putData("Auto Mode", autoModeChooser);

    }

    //TODO: Test this
    public AutoModeBase getSelectedAutoMode() {
        selectedAutoMode = (AutoModeBase) autoModeChooser.getSelected();
        return selectedAutoMode;
        //TODO: place auto info into sd
    }

    public String getSelectedRobotStartingPosition() {
        robotPosition = (String) robotPositionChooser.getSelected();
        return robotPosition;
        //TODO: place auto info into sd
    }
}
