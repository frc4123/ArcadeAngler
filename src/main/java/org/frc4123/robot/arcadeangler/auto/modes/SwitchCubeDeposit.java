package org.frc4123.robot.arcadeangler.auto.modes;

import edu.wpi.first.wpilibj.DriverStation;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import org.frc4123.robot.arcadeangler.auto.actions.DriveMPAction;

import java.io.File;

public class SwitchCubeDeposit extends AutoModeBase {
    //@Override

    Trajectory left;
    Trajectory right;

    protected void routine() {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();


        
        if(gameData.length() > 0)
        {
            if(gameData.charAt(0) == 'L')
            {
                //Put left auto code here
                left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/left_detailed.csv"));
                right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/right_detailed.csv"));
            } else {
                //Put right auto code here
                left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/left_detailed.csv"));
                right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/left_detailed.csv"));
            }
        }

        runAction(new DriveMPAction(left, right));
    }
}
