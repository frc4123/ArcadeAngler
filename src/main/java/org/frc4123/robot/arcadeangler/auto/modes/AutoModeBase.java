package org.frc4123.robot.arcadeangler.auto.modes;

import org.frc4123.robot.arcadeangler.Constants;
import org.frc4123.robot.arcadeangler.auto.actions.Action;

public abstract class AutoModeBase {
    /**
     * An abstract class that is the basis of the robot's autonomous routines. This
     * is implemented in auto.modes (which are routines that do actions).
     */
    double m_update_rate = Constants.kAutoUpdateRate;
    protected boolean m_active = false;

    protected abstract void routine();

    public void run() {
        m_active = true;

        //TODO Determine if necessary / how will be executed
        routine();
//	            System.out.println("Auto mode done, ended early");
        //done();
        System.out.println("Auto mode done");
    }

    public void done() {
    }

    public void stop() {
        m_active = false;
    }

    public boolean isActive() {
        return m_active;
    }

    public boolean isActiveWithThrow(){
        if (!isActive()) {
            //TODO This is a placeholder. Should we do some sort of error reporting?
            System.out.println("Something's Wrong....");
        }
        return isActive();
    }

    public void runAction(Action action){
        isActiveWithThrow();
        action.start();
        while (isActiveWithThrow() && !action.isFinished()) {
            action.update();
            long waitTime = (long) (m_update_rate * 1000.0);
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        action.done();
    }
}
