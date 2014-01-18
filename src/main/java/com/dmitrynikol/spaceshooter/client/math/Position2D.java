package com.dmitrynikol.spaceshooter.client.math;

/**
 * A basic 2D position components
 *
 * @author Dmitry Nikolaenko
 */
public class Position2D
{
  private int x;
  private int y;

  public Position2D( int x, int y )
  {
    this.x = x;
    this.y = y;
  }

  public Position2D( Position2D position )
  {
    this.x = position.x;
    this.y = position.y;
  }

  public int getX()
  {
    return x;
  }

  public void setX( int x )
  {
    this.x = x;
  }

  public int getY()
  {
    return y;
  }

  public void setY( int y )
  {
    this.y = y;
  }

  @Override
  public String toString()
  {
    return "Position: (" + x + ", " + y + ")";
  }
}

