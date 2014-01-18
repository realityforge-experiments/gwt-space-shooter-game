package com.dmitrynikol.spaceshooter.client.util;

import com.dmitrynikol.spaceshooter.client.main.PowerupElement;
import java.util.Random;

/**
 * Class is used to generate a powerup.
 *
 * @author Dmitry Nikolaenko
 */
public final class PowerupsGenerator
{

  private static final Random generator = new Random();

  /**
   * Method allowing to get a random powerup element.
   *
   * @return random asteroid element
   */
  public static PowerupElement getRandomPowerupElement()
  {
    return new PowerupElement( getRandomPowerup() );
  }

  /**
   * Method allowing to get a random powerup.
   *
   * @return random powerup
   */
  public static Powerup getRandomPowerup()
  {
    Powerup[] powerups = Powerup.values();
    Powerup powerup = powerups[ generator.nextInt( powerups.length ) ];

    return powerup;
  }
}

