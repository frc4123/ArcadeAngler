package org.frc4123.robot.arcadeangler.auto.modes;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import org.frc4123.robot.arcadeangler.auto.actions.DriveMPAction;

import java.io.File;

public class CrossBaselineMode extends AutoModeBase {

    @Override
    protected void routine() { //TODO: Ensure files are named correctly and properly generated
        Trajectory left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/crossbaseline_left_detailed.csv"));
        Trajectory right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/crossbaseline_right_detailed.csv"));

        runAction(new DriveMPAction(left, right));
        System.out.println("Drove to Baseline.");
    }
}
