package org.frc4123.robot.arcadeangler.auto.modes;

import edu.wpi.first.wpilibj.DriverStation;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import org.frc4123.robot.arcadeangler.auto.actions.*;
import org.frc4123.robot.arcadeangler.control.SmarterDashboard;

import java.io.File;


public class SwitchCubeDepositMode extends AutoModeBase {
    //@Override

    SmarterDashboard mSmartDashboard = SmarterDashboard.getInstance();
    String robotPosition = mSmartDashboard.getSelectedRobotStartingPosition();

    Trajectory left;
    Trajectory right;
//    Trajectory reverse_left;
//    Trajectory reverse_right;

    protected void routine() {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        //Possible Values: R - right, L - left, C - center.
        //TODO: Test this to ensure it replaces like I thought it would

        //Why doesn't this work?
//        Trajectory reverse_left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/back_up_2ft_left_detailed.csv"));
//        Trajectory reverse_right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/back_up_2ft_right_detailed.csv"));

        //TODO: Ensure there are actually paths for all these OR ELSE EVERYTHING WILL CRASH
        if (gameData.length() > 0) {
            if (gameData.charAt(0) == 'L') {
                if (robotPosition == "L") {
                    //Put left auto code here
                    left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/left_leftsw_left_detailed.csv"));
                    right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/left_leftsw_right_detailed.csv"));
                } else if (robotPosition == "R") {
                    //Put left auto code here
                    left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/right_leftsw_left_detailed.csv"));
                    right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/right_leftsw_right_detailed.csv"));
                } else if (robotPosition == "C") {
                    //Put left auto code here
                    left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/center_leftsw_left_detailed.csv"));
                    right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/center_leftsw_right_detailed.csv"));
                }
            }
            if (gameData.charAt(0) == 'R') {
                if (robotPosition == "L") {
                    //Put left auto code here
                    left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/left_rightsw_left_detailed.csv"));
                    right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/left_rightsw_right_detailed.csv"));
                } else if (robotPosition == "R") {
                    //Put left auto code here
                    left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/right_rightsw_left_detailed.csv"));
                    right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/right_rightsw_right_detailed.csv"));
                } else if (robotPosition == "C") {
                    //Put left auto code here
                    left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/center_rightsw_left_detailed.csv"));
                    right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/center_rightsw_right_detailed.csv"));
                }
            }
        } else {
            //TESTPLZ
            System.out.println("Field piece position not recieved. Driving to baseline.");
            left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/crossbaseline_left_detailed.csv"));
            right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/crossbaseline_right_detailed.csv"));
            runAction(new DriveMPAction(left, right));
            stop();
        }

        System.out.println("Flipping grabber up.");
        runAction(new FlipperPneumaticAction(FlipperPneumaticAction.State.UP));
        System.out.println("Driving to target.");
        runAction(new DriveMPAction(left, right));
        System.out.println("Elevating to switch.");
        runAction(new ElevateAction(ElevateAction.Position.SWITCH));

        //TODO: determine necessity of the following line
        //runAction(new FlipperPneumaticAction(FlipperPneumaticAction.State.DOWN));

        System.out.println("Opening arms.");
        runAction(new ArmsPneumaticAction(ArmsPneumaticAction.State.OPEN));

        //The below is just icing on the cake. Remove if breaking things
        //TODO: Determine if reverse motion profile even works - doesn't work rn
//        runAction(new ElevateAction(ElevateAction.Position.DOWN));
//        runAction(new DriveMPAction(reverse_left, reverse_right));
//        runAction(new FlipperPneumaticAction(FlipperPneumaticAction.State.DOWN));
//        done();
    }
}
