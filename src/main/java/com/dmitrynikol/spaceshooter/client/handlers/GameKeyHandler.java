package com.dmitrynikol.spaceshooter.client.handlers;

import com.dmitrynikol.spaceshooter.client.main.Spaceship;
import com.dmitrynikol.spaceshooter.client.math.Position2D;
import com.dmitrynikol.spaceshooter.client.math.Size2D;
import com.dmitrynikol.spaceshooter.client.util.ApplicationContext;
import com.dmitrynikol.spaceshooter.client.util.ApplicationUtils;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;

/**
 * Game key handler.
 *
 * @author Dmitry Nikolaenko
 */
public class GameKeyHandler
  implements KeyDownHandler
{
  private Spaceship spaceship;
  private Timer timer;
  private boolean pause;
  private boolean drawingPosition;

  public GameKeyHandler( final Spaceship spaceship )
  {
    this.spaceship = spaceship;
    pause = false;
    drawingPosition = true;
  }

  @Override
  public void onKeyDown( final KeyDownEvent event )
  {
    final int key = event.getNativeKeyCode();
    final Position2D currentPosition = spaceship.getPosition();
    final Size2D elementSize = spaceship.getSize();

    switch ( key )
    {
      case KeyCodes.KEY_UP:
        currentPosition.setY( currentPosition.getY() - ApplicationUtils.DELTA );
        break;
      case KeyCodes.KEY_DOWN:
        currentPosition.setY( currentPosition.getY() + ApplicationUtils.DELTA );
        break;
      case KeyCodes.KEY_LEFT:
        currentPosition.setX( currentPosition.getX() - ApplicationUtils.DELTA );
        break;
      case KeyCodes.KEY_RIGHT:
        currentPosition.setX( currentPosition.getX() + ApplicationUtils.DELTA );
        break;
      case KeyCodes.KEY_SHIFT:
        Position2D bulletPosition =
          new Position2D( currentPosition.getX() + elementSize.width / 2, currentPosition.getY() - 15 );
        spaceship.shot( bulletPosition );
        break;
      case KeyCodes.KEY_CTRL:
        drawingPosition = !drawingPosition;
        ApplicationContext.setPositionEnabled( drawingPosition );
        break;
      case KeyCodes.KEY_ESCAPE:
        if ( timer != null )
        {
          if ( pause )
          {
            timer.scheduleRepeating( ApplicationUtils.REFRESH_RATE );
            ApplicationUtils.setVisibleCursor( false );
          }
          else
          {
            timer.cancel();
            ApplicationUtils.setVisibleCursor( true );
            ApplicationUtils.drawPauseLabel();
          }
          pause = !pause;
          //DOM.getElementById("start").getStyle().setDisplay(Display.INLINE);
        }
      default:
        break;
    }
  }

  public void setTimer( Timer timer )
  {
    this.timer = timer;
  }
}

