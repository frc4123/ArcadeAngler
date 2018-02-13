package org.frc4123.robot.arcadeangler.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.frc4123.robot.arcadeangler.Constants;

public class Elevator {

    WPI_TalonSRX master = new WPI_TalonSRX(Constants.id_elevateMaster);
    WPI_TalonSRX slave = new WPI_TalonSRX(Constants.id_elevateSlave);

    public enum CurrentState {ELEVATING, DESCENDING, ELEVATINGPID, DESCENDINGPID, STOPPED}
    private CurrentState currentState = CurrentState.STOPPED;

    private float speedElevate;
    private float speedDescend;

    /**
    * //@param speedElevate
    *             TODO: define
    * //@param speedDescend
     *             TODO: Define
    * */
    public Elevator(){
        master.set(ControlMode.PercentOutput, 0);
        slave.follow(master);

        //Store speeds
        //this.speedElevate = speedElevate;
       // this.speedDescend = speedDescend;
    }

    public void SetPID(int P, int I, int D){

    }

    public void setCurrentState(CurrentState desiredState) {
        if (desiredState != this.currentState){
            this.currentState = desiredState;
            switch(desiredState){
                case ELEVATING:
                    elevate();
                    break;

                case DESCENDING:
                    descend();
                    break;

                case ELEVATINGPID:
                    elevateWithPID();
                    break;

                case DESCENDINGPID:
                    descendWithPID();
                    break;

                case STOPPED:
                    stop();
                    break;

                default:
                    stop();
                    break;

            }
        }
    }

    private void elevate(){
        currentState = CurrentState.ELEVATING;
        new Thread(Runnable)
        currentState = CurrentState.STOPPED;
    }

    private void descend(){
        currentState = CurrentState.STOPPED;
    }

    private void elevateWithPID(){

    }

    private void descendWithPID(){

    }

    private void stop(){
        master.set(0);
        slave.set(0);
        currentState = CurrentState.STOPPED;
    }
}
