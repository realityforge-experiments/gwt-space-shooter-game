package com.dmitrynikol.spaceshooter.client.util;

import java.util.Random;

/**
 * Class is used to generate a powerup.
 *
 * @author Dmitry Nikolaenko
 */
public final class PowerupsGenerator
{
  private static final Random c_generator = new Random();

  /**
   * Method allowing to get a random powerup.
   *
   * @return random powerup
   */
  public static Powerup getRandomPowerup()
  {
    final Powerup[] powerups = Powerup.values();
    return powerups[ c_generator.nextInt( powerups.length ) ];
  }
}

