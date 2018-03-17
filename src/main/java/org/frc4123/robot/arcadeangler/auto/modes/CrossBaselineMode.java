package org.frc4123.robot.arcadeangler.auto.modes;

import edu.wpi.first.wpilibj.DriverStation;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import org.frc4123.robot.arcadeangler.auto.actions.DriveMPAction;
import org.frc4123.robot.arcadeangler.control.SmarterDashboard;

import java.io.File;

public class CrossBaselineMode extends AutoModeBase {

    // @Override

    SmarterDashboard mSmartDashboard = SmarterDashboard.getInstance();

    Trajectory left;
    Trajectory right;

    protected void routine() { //TODO: Ensure files are named correctly and properly generated

        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        //Possible Values: R - right, L - left, C - center.
        String robotPosition = mSmartDashboard.getSelectedRobotStartingPosition();


        if (gameData.length() > 0) {
            if (gameData.charAt(0) == 'L') {
                if (robotPosition == "C") {
                    System.out.println("Driving to left from center");
                    //Put left auto code here
                    left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/center_leftswitchleft_detailed.csv"));
                    right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/center_leftswitchright_detailed.csv"));
                } else {
                    System.out.println("Driving straight to baseline");
                    left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/crossbaseline_left_detailed.csv"));
                    right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/crossbaseline_right_detailed.csv"));

                }
            }
            if (gameData.charAt(0) == 'R') {
                if (robotPosition == "C") {
                    System.out.println("Driving to right from center");
                    //Put right auto code here
                    left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/center_rightswitchleft_detailed.csv"));
                    right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/center_rightswitchright_detailed.csv"));
                } else {
                    System.out.println("Driving straight to baseline");
                    left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/crossbaseline_left_detailed.csv"));
                    right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/crossbaseline_right_detailed.csv"));

                }
            }
        }

        if (left == null || right == null) {
            System.out.println("No Motion Profile loaded. Doing nothing to avoid crash.");
            //TODO: Test the below for validity
            stop();
        }

//        left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/crossbaseline_left_detailed.csv"));
//        right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/crossbaseline_right_detailed.csv"));

        runAction(new DriveMPAction(left, right));
        System.out.println("Drove from " + robotPosition + " to Baseline.");
    }
}
