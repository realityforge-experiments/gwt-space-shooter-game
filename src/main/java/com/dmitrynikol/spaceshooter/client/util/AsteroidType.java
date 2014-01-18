package com.dmitrynikol.spaceshooter.client.util;

/**
 * Enumeration that represents the type of asteroid
 *
 * @author Dmitry Nikolaenko
 */
public enum AsteroidType
{
  COALY( 0, AsteroidAcceleration.DAYDREAM ),
  FLINTY( 1, AsteroidAcceleration.NIGHTMARE ),
  METAL( 2, AsteroidAcceleration.MADNESS );

  private int type;
  private AsteroidAcceleration acceleration;

  private AsteroidType( int type, AsteroidAcceleration acceleration )
  {
    this.type = type;
    this.acceleration = acceleration;
  }

  public int getAsteroidType()
  {
    return type;
  }

  public int getAsteroidAcceleration()
  {
    return acceleration.getAcceleration();
  }
}

