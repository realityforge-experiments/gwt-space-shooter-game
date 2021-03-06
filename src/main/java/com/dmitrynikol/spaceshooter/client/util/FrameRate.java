package com.dmitrynikol.spaceshooter.client.util;

/**
 * Class for calculating frame rate of the game
 *
 * @author Dmitry Nikolaenko
 */
public class FrameRate
{
  /**
   * total elapsed time of the game
   */
  private float totalElapsedTime;

  /**
   * counter for determine the reset state
   */
  private int iterationFrames;

  /**
   * total time for a certain number of iterations
   */
  private float timeIteration;

  /**
   * number of iterations to update frame rate
   */
  private int resetIteration;

  /**
   * current frame rate of the game
   */
  private float currentFrameRate;

  public FrameRate()
  {
    totalElapsedTime = 0;
    resetIteration = 15;
    iterationFrames = 0;
    timeIteration = 0;
  }

  public void addFrame( final float time )
  {
    iterationFrames++;
    this.totalElapsedTime += time;
    timeIteration += time;

    if ( iterationFrames > resetIteration )
    {
      currentFrameRate = iterationFrames / timeIteration;
      timeIteration = 0;
      iterationFrames = 0;
    }
  }

  public float getCurrentFrameRate()
  {
    return currentFrameRate;
  }

  public float getTotalElapsedTime()
  {
    return totalElapsedTime;
  }
}
