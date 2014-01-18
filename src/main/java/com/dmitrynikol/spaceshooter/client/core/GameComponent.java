package com.dmitrynikol.spaceshooter.client.core;

import com.dmitrynikol.spaceshooter.client.math.Position2D;
import com.dmitrynikol.spaceshooter.client.math.Size2D;
import com.google.gwt.dom.client.ImageElement;

/**
 * This is the basic GameComponent class
 * <p/>
 * Every game element will extends this class
 *
 * @author Dmitry Nikolaenko
 */
public abstract class GameComponent
  implements GameElement, Renderer, Updater
{
  private Position2D position;
  private Size2D size;

  protected abstract void initComponent();

  public abstract void injectImage( ImageElement image );

  public Position2D getPosition()
  {
    return position;
  }

  public void setPosition( Position2D position )
  {
    this.position = position;
  }

  public Size2D getSize()
  {
    return size;
  }

  public void setSize( Size2D size )
  {
    this.size = size;
  }
}
