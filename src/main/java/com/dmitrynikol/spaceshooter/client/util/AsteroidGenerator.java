package com.dmitrynikol.spaceshooter.client.util;

import com.dmitrynikol.spaceshooter.client.main.Asteroid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class is used to generate an asteroid.
 *
 * @author Dmitry Nikolaenko
 */
public final class AsteroidGenerator
{
  private static final Random generator = new Random();

  /**
   * Method allowing to get a random asteroid element
   *
   * @return random asteroid element
   */
  public static Asteroid getRandomAsteroid()
  {
    return new Asteroid( getRandomAsteroidType() );
  }

  /**
   * Method allowing to get a random type of asteroids
   *
   * @return random type of asteroids
   */
  public static AsteroidType getRandomAsteroidType()
  {
    AsteroidType[] asteroids = AsteroidType.values();
    AsteroidType asteroidType = asteroids[ generator.nextInt( asteroids.length ) ];

    return asteroidType;
  }

  /**
   * Method allowing to get a list of asteroids
   *
   * @return list of asteroids
   */
  public static List<Asteroid> getAllAsteroidList()
  {
    List<Asteroid> asteroids = new ArrayList<Asteroid>();
    for ( AsteroidType type : AsteroidType.values() )
    {
      asteroids.add( new Asteroid( type ) );
    }
    return asteroids;
  }
}

