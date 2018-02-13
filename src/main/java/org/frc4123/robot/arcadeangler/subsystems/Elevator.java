package org.frc4123.robot.arcadeangler.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator {

    public enum CurrentState {ELEVATING, DESCENDING, STOPPED}
    private CurrentState currentState = CurrentState.STOPPED;

    private float speedElevate;
    private float speedDescend;

    /**
    * @param speedElevate
    *             TODO: define
    * @param speedDescend
     *             TODO: Define
    * */
    public Elevator(float speedElevate, float speedDescend){
        master.set(ControlMode.PercentOutput, 0);
        slave.follow(master);

        //Store speeds
        this.speedElevate = speedElevate;
        this.speedDescend = speedDescend;
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
        new
        currentState = CurrentState.STOPPED;
    }

    private void descend(){
        currentState = CurrentState.STOPPED;
    }

    private void stop(){
        currentState = CurrentState.STOPPED;
    }
}
