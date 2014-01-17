package com.dmitrynikol.spaceshooter.client.util;

/**
 * Enumeration that represents the levels of difficulty
 *
 * @author Dmitry Nikolaenko
 *
 */
public enum AsteroidAcceleration {
	DAYDREAM(1),
	NIGHTMARE(2),
	MADNESS(3);

	private int acceleration;

	private AsteroidAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}

	public int getAcceleration() {
		return acceleration;
	}
}

