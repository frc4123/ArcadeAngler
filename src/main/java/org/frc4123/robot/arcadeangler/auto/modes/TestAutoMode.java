package org.frc4123.robot.arcadeangler.auto.modes;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import org.frc4123.robot.arcadeangler.auto.actions.DriveMPAction;

import java.io.File;

public class TestAutoMode extends AutoModeBase {


    @Override
    protected void routine() {


//
//        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7*0.7, 0.050*2, 60.0);
//        Waypoint[] points = new Waypoint[] {
//                new Waypoint(0,0,0),
//                new Waypoint(2,0,0)
//
//
////                new Waypoint(0 , 0, 0),
////                new Waypoint(1*2, 1*2, Pathfinder.d2r(90)),
////                new Waypoint(0, 2*2, Pathfinder.d2r(180)),
////                new Waypoint(-1*2, 1*2, Pathfinder.d2r(270)),
////                new Waypoint(0, 0, Pathfinder.d2r(0)),
//
//                //Figure8
////                new Waypoint(1, 1, Pathfinder.d2r(90)),
////                new Waypoint(0, 2, Pathfinder.d2r(180)),
////                new Waypoint(-1, 1, Pathfinder.d2r(270)),
////                new Waypoint(1, -1, Pathfinder.d2r(270)),
////                new Waypoint(0, -2, Pathfinder.d2r(180)),
////                new Waypoint(-1, -1, Pathfinder.d2r(90)),
////                new Waypoint(0, 0, Pathfinder.d2r(0))
//
//
////                new Waypoint(10, 20, Pathfinder.d2r(180))
////                new Waypoint(5, 15, Pathfinder.d2r(270)),
////                new Waypoint(15, 5, Pathfinder.d2r(270)),
////                new Waypoint(10, 0, Pathfinder.d2r(180)),
////                new Waypoint(5, 5, Pathfinder.d2r(90)),
////                new Waypoint(10, 10, 0),
////                new Waypoint(0, 0, 0)
//        };
//        Trajectory trajectory = Pathfinder.generate(points, config);


        Trajectory left = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/left_detailed.csv"));
        Trajectory right = Pathfinder.readFromCSV(new File("/home/lvuser/trajectories/right_detailed.csv"));

        runAction(new DriveMPAction(left, right));
    }
}
