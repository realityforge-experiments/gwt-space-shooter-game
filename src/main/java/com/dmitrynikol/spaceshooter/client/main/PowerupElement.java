package com.dmitrynikol.spaceshooter.client.main;

import com.dmitrynikol.spaceshooter.client.bundle.ClientBundleInjector;
import com.dmitrynikol.spaceshooter.client.bundle.SpaceShooterClientBundle;
import com.dmitrynikol.spaceshooter.client.core.GameComponent;
import com.dmitrynikol.spaceshooter.client.math.Position2D;
import com.dmitrynikol.spaceshooter.client.math.Size2D;
import com.dmitrynikol.spaceshooter.client.util.ApplicationContext;
import com.dmitrynikol.spaceshooter.client.util.ApplicationUtils;
import com.dmitrynikol.spaceshooter.client.util.Powerup;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.Image;

/**
 * This is the powerup game element.
 *
 * @author Dmitry Nikolaenko
 */
public class PowerupElement
  extends GameComponent
{
  private boolean isAlive;

  private ImageElement image;
  private Position2D position;
  private Size2D size;

  private Powerup powerup;

  private final ClientBundleInjector injector = GWT.create( ClientBundleInjector.class );
  private final SpaceShooterClientBundle gameResourceBundle = injector.spaceShooterBundle();

  public PowerupElement()
  {
    this( Powerup.SHIELD );
  }

  public PowerupElement( Powerup powerup )
  {
    this.powerup = powerup;
    initComponent();
  }

  @Override
  public boolean isAlive()
  {
    return isAlive;
  }

  @Override
  public void destroy()
  {
    isAlive = false;
  }

  @Override
  public void render( Context2d context )
  {
    if ( isAlive() )
    {
      context.drawImage( image, position.getX(), position.getY() );

      if ( ApplicationContext.isPositionEnabled() )
      {
        ApplicationUtils.drawElementPosition( context, position, powerup.name().concat( ": " ),
                                              position.getX() - 15, position.getY() + 40 );
      }
    }
  }

  @Override
  public void update( float delta )
  {
    position.setY( position.getY() + 1 );
  }

  @Override
  protected void initComponent()
  {
    isAlive = true;
    updatePowerup();
    size = new Size2D( image.getWidth(), image.getHeight() );
    setSize( size );
    updatePowerupElementPosition();
  }

  /**
   * update the position of powerup element
   */
  private void updatePowerupElementPosition()
  {
    int randomNumber =
      ApplicationUtils.getRandomIntegerNumberBetween( 0, ApplicationUtils.MAXIMUM_WIDTH_BOUNDARY -
                                                         getSize().getWidth() );
    this.position = new Position2D( randomNumber, 0 );
    setPosition( position );
  }

  /**
   * update powerup for element
   */
  private void updatePowerup()
  {
    SafeUri uri = null;
    switch ( powerup.getType() )
    {
      case 0:
        uri = gameResourceBundle.freeze().getSafeUri();
        break;
      case 1:
        uri = gameResourceBundle.reflex_boost().getSafeUri();
        break;
      case 2:
        uri = gameResourceBundle.shield().getSafeUri();
        break;
      case 3:
        uri = gameResourceBundle.x2_exp().getSafeUri();
        break;
      case 4:
        uri = gameResourceBundle.medikit().getSafeUri();
        break;
      default:
        uri = gameResourceBundle.shield().getSafeUri();
        break;
    }
    image = ImageElement.as( new Image( uri ).getElement() );
  }

  /**
   * Method return powerups type
   *
   * @return powerups type
   */
  public Powerup getPowerupType()
  {
    return powerup;
  }
}

