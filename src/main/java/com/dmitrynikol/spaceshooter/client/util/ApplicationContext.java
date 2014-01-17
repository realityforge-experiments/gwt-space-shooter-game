package com.dmitrynikol.spaceshooter.client.util;

import java.util.HashMap;
import java.util.Map;

import com.dmitrynikol.spaceshooter.client.main.Spaceship;
import com.google.gwt.user.client.Timer;

/**
 * Application context class
 *
 * @author Dmitry Nikolaenko
 *
 */
public class ApplicationContext {

	private static final int POWERUP_DELAY = 5000;

	private static boolean positionEnabled = true;
	private static boolean freezeEnabled = false;
	private static boolean shieldEnabled = false;
	private static boolean reflexBoostEnabled = false;
	private static int scoreIncrease = 1;
	private static Timer timer;
	private static Map<Powerup, Float> temporaryPowerups = new HashMap<Powerup, Float>();

	/**
     * Class contains only static methods.
     */
	private ApplicationContext() {}

	/**
	 * Gets whether game elements is enabled drawing position.
	 *
	 * @return <code>true</code> if the drawing position is enabled
	 */
	public static boolean isPositionEnabled() {
		return positionEnabled;
	}

	/**
	 * Sets whether game elements is enabled drawing position.
	 *
	 * @param enabled <code>true</code> to enable the drawing position, <code>false</code> to disable it
	 */
	public static void setPositionEnabled(boolean enabled) {
		positionEnabled = enabled;
	}

	/**
	 * Whether enemies are frozen.
	 *
	 * @return <code>true</code> if the freeze is enabled, otherwise false
	 */
	public static boolean isFreezeEnabled() {
		return freezeEnabled;
	}

	/**
	 * Sets whether freezing is enabled.
	 *
	 * @param freezeEnabled <code>true</code> to enable freeze powerup, otherwise disable
	 */
	public static void setFreezeEnabled(boolean freezeEnabled) {
		ApplicationContext.freezeEnabled = freezeEnabled;
	}

	/**
	 * Whether spaceship shield is enabled.
	 *
	 * @return <code>true</code> if the shield is enabled, otherwise false
	 */
	public static boolean isShieldEnabled() {
		return shieldEnabled;
	}

	/**
	 * Sets whether shield for spaceship is enabled.
	 *
	 * @param enabled <code>true</code> to enable shield powerup, otherwise disable
	 */
	public static void setShieldEnabled(boolean enabled) {
		shieldEnabled = enabled;
	}

	/**
	 * Whether reflex boost is enabled.
	 *
	 * @return <code>true</code> if the reflex boost is enabled, otherwise false
	 */
	public static boolean isReflexBoostEnabled() {
		return reflexBoostEnabled;
	}

	/**
	 *  Sets whether reflex boost is enabled.
	 *
	 * @param reflexBoostEnabled <code>true</code> to enable reflex boost powerup, otherwise disable
	 */
	public static void setReflexBoostEnabled(boolean reflexBoostEnabled) {
		ApplicationContext.reflexBoostEnabled = reflexBoostEnabled;
	}

	/**
	 * Get game score.
	 *
	 * @return score
	 */
	public static int getScoreIncrease() {
		return scoreIncrease;
	}

	/**
	 * Set game score.
	 *
	 * @param scoreIncrease
	 */
	public static void setScoreIncrease(int scoreIncrease) {
		ApplicationContext.scoreIncrease = scoreIncrease;
	}

	/**
	 * Get map of temporary powerups
	 *
	 * @return temporaryPowerups map that contains temporary powerups
	 */
	public static Map<Powerup, Float> getTempPowerups() {
		return temporaryPowerups;
	}

	/**
	 * Method run powerups for spaceship for a certain period.
	 *
	 * @param spaceship
	 * @param powerup object that add extra abilities
	 */
	public static void runPowerups(Spaceship spaceship, final Powerup powerup) {
		switch(powerup) {
			case FREEZE:        ApplicationContext.setFreezeEnabled(true);       break;
			case REFLEX_BOOST:  ApplicationContext.setReflexBoostEnabled(true);  break;
			case SHIELD:        ApplicationContext.setShieldEnabled(true);       break;
			case DOUBLE_EXP:    ApplicationContext.setScoreIncrease(2);          break;
			case MEDIKIT:       spaceship.setHealth(spaceship.getHealth() + 1);  break;
			default:
				break;
		}


		timer = new Timer() {
			@Override
			public void run() {
				switch(powerup) {
					case FREEZE:         ApplicationContext.setFreezeEnabled(false);       break;
					case REFLEX_BOOST:   ApplicationContext.setReflexBoostEnabled(false);  break;
					case SHIELD: 	     ApplicationContext.setShieldEnabled(false);       break;
					case DOUBLE_EXP:     ApplicationContext.setScoreIncrease(1);           break;
					default:
						break;
				}
				temporaryPowerups.remove(powerup);
			}
		};
		timer.schedule(POWERUP_DELAY);
	}
}

