package com.dmitrynikol.spaceshooter.client.util;

/**
 * Enumeration that represents the degrees of shooting accuracy
 *
 * @author Dmitry Nikolaenko
 */
public enum Accuracy
{
  LEVEL1( 10 ),
  LEVEL2( 20 ),
  LEVEL3( 30 ),
  LEVEL4( 40 ),
  LEVEL5( 50 );

  private int accuracy;

  private Accuracy( final int accuracy )
  {
    this.accuracy = accuracy;
  }

  public int getShootingAccuracy()
  {
    return accuracy;
  }
}

