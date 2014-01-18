package com.dmitrynikol.spaceshooter.client.handlers;

import com.dmitrynikol.spaceshooter.client.main.Spaceship;
import com.dmitrynikol.spaceshooter.client.math.Position2D;
import com.dmitrynikol.spaceshooter.client.math.Size2D;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;

/**
 * Game mouse handler.
 *
 * @author Dmitry Nikolaenko
 */
public class GameMouseHandler
  implements MouseDownHandler, MouseMoveHandler
{

  private Spaceship spaceship;
  private Position2D currentPosition;
  private Size2D elementSize;

  public GameMouseHandler( Spaceship element )
  {
    this.spaceship = element;
  }

  @Override
  public void onMouseMove( MouseMoveEvent event )
  {
    currentPosition = spaceship.getPosition();
    elementSize = spaceship.getSize();

    currentPosition.setX( event.getX() );
    currentPosition.setY( event.getY() );
    spaceship.setPosition( currentPosition );
  }

  @Override
  public void onMouseDown( MouseDownEvent event )
  {
    Position2D bulletPosition =
      new Position2D( currentPosition.getX() + ( elementSize.width == 0 ? 24 : elementSize.width / 2 ),
                      currentPosition.getY() - 15 );
    spaceship.shot( bulletPosition );
  }
}

