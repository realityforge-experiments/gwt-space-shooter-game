package com.dmitrynikol.spaceshooter.client.util;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration that represents an objects that instantly benefit or
 * add extra abilities to the game character as a game mechanic.
 *
 * @author Dmitry Nikolaenko
 */
public enum Powerup
{
  /**
   * The asteroids are frozen for some time.
   */
  FREEZE( 0 ),
  /**
   * The reflex boost slows asteroids in the game down except for your movement.
   */
  REFLEX_BOOST( 1 ),
  /**
   * With the shield powerup you are invulnerable for a short period of time.
   */
  SHIELD( 2 ),
  /**
   * When this powerup is active, you get double experience from destroyed asteroids.
   */
  DOUBLE_EXP( 3 ),
  /**
   * Pick this up and you get one more health back instantly
   */
  MEDIKIT( 4 );

  private static final Map<Integer, Powerup> c_typeMap = new HashMap<Integer, Powerup>();
  static
  {
    for ( final Powerup power : EnumSet.allOf( Powerup.class ) )
    {
      c_typeMap.put( power.getType(), power );
    }
  }

  private int type;

  private Powerup( final int type )
  {
    this.type = type;
  }

  public int getType()
  {
    return type;
  }

  public static Powerup get( final int code )
  {
    return c_typeMap.get( code );
  }
}

