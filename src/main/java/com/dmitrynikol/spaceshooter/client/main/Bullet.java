package com.dmitrynikol.spaceshooter.client.main;

import com.dmitrynikol.spaceshooter.client.core.GameComponent;
import com.dmitrynikol.spaceshooter.client.math.Position2D;
import com.dmitrynikol.spaceshooter.client.math.Size2D;
import com.dmitrynikol.spaceshooter.client.util.ApplicationContext;
import com.dmitrynikol.spaceshooter.client.util.ApplicationUtils;
import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;

/**
 * This is the Bullet game element.
 *
 * @author Dmitry Nikolaenko
 */
public class Bullet
  extends GameComponent
{

  public enum Type
  {
    YELLOW_STAR( 0.8, "#F4F201", "#E4C700", "rgba(228,199,0,0)" ),
    GREEN_STAR( 0.9, "#A7D30C", "#019F62", "rgba(1,159,98,0)" ),
    BLUE_STAR( 0.8, "#00C9FF", "#00B5E2", "rgba(0,201,255,0)" ),
    HOTPINK_STAR( 0.75, "#FF5F98", "#FF0188", "rgba(255,1,136,0)" );

    private double middleStopColor;
    private String color1;
    private String color2;
    private String color3;

    private Type( double middleStopColor, String color1, String color2, String color3 )
    {
      this.middleStopColor = middleStopColor;
      this.color1 = color1;
      this.color2 = color2;
      this.color3 = color3;
    }

    private Type( double middleStopColor, CssColor color1, CssColor color2, CssColor color3 )
    {
      this( middleStopColor, color1.value(), color2.value(), color3.value() );
    }

    public void updateGradientColors( CanvasGradient gradient )
    {
      gradient.addColorStop( 0, color1 );
      gradient.addColorStop( middleStopColor, color2 );
      gradient.addColorStop( 1, color3 );
    }
  }

  private boolean isAlive;
  private int acceleration;
  private ImageElement image;
  private Position2D position;
  private Size2D size;
  private Type type;

  public Bullet()
  {
    this( Type.YELLOW_STAR );
  }

  public Bullet( Type type )
  {
    this.type = type;
    initComponent();
  }

  @Override
  public boolean isAlive()
  {
    return isAlive;
  }

  @Override
  public void update( float delta )
  {
    position.setY( position.getY() - acceleration );
  }

  @Override
  public void destroy()
  {
    isAlive = false;
  }

  @Override
  public void render( Context2d context )
  {
    position = getPosition();
    drawBullet( context );
  }

  @Override
  protected void initComponent()
  {
    acceleration = 5;
    isAlive = true;
  }

  @Override
  public void injectImage( ImageElement image )
  {
  }

  private void drawBullet( Context2d context )
  {
    CanvasGradient gradient =
      context.createRadialGradient( position.getX(), position.getY(), 3,
                                    position.getX(), position.getY(), 15 );

    type.updateGradientColors( gradient );

    context.setFillStyle( gradient );
    context.fillRect( 0, 0, ApplicationUtils.CANVAS_WIDTH, ApplicationUtils.CANVAS_HEIGHT );

    if ( ApplicationContext.isPositionEnabled() )
    {
      ApplicationUtils.drawElementPosition( context, position, "Bullet: ",
                                            position.getX() - 30, position.getY() + 30 );
    }
  }

  public Type getBulletType( int score )
  {
    switch ( score % 16 )
    {
      case 0:
      case 1:
      case 2:
      case 3:
        return Type.YELLOW_STAR;
      case 4:
      case 5:
      case 6:
      case 7:
        return Type.GREEN_STAR;
      case 8:
      case 9:
      case 10:
      case 11:
        return Type.BLUE_STAR;
      case 12:
      case 13:
      case 14:
      case 15:
        return Type.HOTPINK_STAR;
      default:
        break;
    }

    return type;
  }

  public void updateBullet( Type type )
  {
    this.type = type;
  }
}

