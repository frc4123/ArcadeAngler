package org.frc4123.robot.arcadeangler.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ElevatorClimber {

    /**
    * @param master the master TalonSRX will have the encoder wired into it
    * @param slave  slave TalonSRX will simply be set to follower
    * */
    public void manualElevate(TalonSRX master, TalonSRX slave){
        slave.follow(master);
    }

    public void manualClimb(){

    }
}
