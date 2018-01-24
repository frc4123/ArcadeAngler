package org.frc4123.robot.arcadeangler.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;

/**
 * Behaves like a Spark, but extends and retracts with timers
 * 
 * @author Tribe Robotics, FRC Team 4123
 */
public class EjectIntakeSpeedController extends Spark {
	public enum CurrentState {EJECTING, INTAKING, STOPPED}
	private CurrentState currentState = CurrentState.STOPPED;
	private boolean timeEjectExpired = false;

	private float speedEject;
	private float speedIntake;
	private float timeEject;
	private DigitalInput intakeLimit;
	
	
	/**
	 * Constructor
	 * 
	 * @param channel
	 *            The PWM channel that the SPARK is attached to. 0-9 are
	 *            on-board, 10-19 are on the MXP port
	 * @param speedEject
	 *            The speed at which the device should eject.
	 * @param speedIntake
	 *            The speed at which the device should intake.
	 */
	public EjectIntakeSpeedController(final int channel, float speedEject, float speedIntake) {
		// Call Spark's constructor
		super(channel);

		// Store our speeds and times
		this.speedEject = speedEject;
		this.speedIntake = speedIntake;
	}

	/**
	 * Constructor
	 * 
	 * @param channel
	 *            The PWM channel that the SPARK is attached to. 0-9 are
	 *            on-board, 10-19 are on the MXP port
	 * @param speedEject
	 *            The speed at which the device should eject.
	 * @param speedIntake
	 *            The speed at which the device should intake.
	 * @param timeEject
	 *            The duration that the device should eject.
	 * @param intake_limit_id
	 * 			  ID of reverse limit switch input.           
	 */
	public EjectIntakeSpeedController(final int channel, float speedIntake, final int intake_limit_id, float speedEject, float timeEject) {
		// Call Spark's constructor
		super(channel);

		// Store our speeds and times
		this.speedEject = speedEject;
		this.speedIntake = speedIntake;
		this.timeEject = timeEject;
		this.intakeLimit = new DigitalInput(intake_limit_id);
	}


	/**
	 * Extends or retracts device
	 *
	 * @param desiredState
	 *            EJECTING to eject, INTAKING to intake, STOPPED to stop
	 */
	public void setCurrentState(CurrentState desiredState) {
		if (desiredState != this.currentState){
			this.currentState = desiredState;
			switch(desiredState){
			case EJECTING:
				eject();
				break;

			case INTAKING:
				intake();
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

	/**
	 * Gives Eject command until timer runs out and sets state based on input of
	 */
	public void eject() {
		currentState = CurrentState.EJECTING;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!isEjected() && currentState == CurrentState.EJECTING){
					set(speedEject);
					Timer ejectTimer = new Timer();
					ejectTimer.reset();
					ejectTimer.start();
					if (ejectTimer.hasPeriodPassed(timeEject) == true){
						timeEjectExpired = true;
						set(0);
					} else{
						timeEjectExpired = false;
						set(speedEject);
					}

				}
				set(0);
				currentState = CurrentState.STOPPED;
			}
		}).start();
	}

	/**
	 * Retracts device until limit switch
	 */
	public void intake() {
		currentState = CurrentState.INTAKING;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!isIntaked() && currentState == CurrentState.INTAKING){
					set(speedIntake);
				}
				set(0);
				currentState = CurrentState.STOPPED;
			}
		}).start();
	}

	public void stop(){
		currentState = CurrentState.STOPPED;
		set(0);
	}

	public boolean isEjected() {
		//True when object has been ejected
		return timeEjectExpired;
	}


	public boolean isIntaked() {
		return !intakeLimit.get();
		//We're using normally closed switches
	}
}
