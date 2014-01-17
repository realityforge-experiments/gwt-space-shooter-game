package com.dmitrynikol.spaceshooter.client.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author Dmitry Nikolaenko
 */
public interface SpaceShooterClientBundle
  extends ClientBundle
{

  public interface StyleResources
    extends CssResource
  {
    @ClassName( "start" )
    String start();
  }

  static final SpaceShooterClientBundle INSTANCE = GWT.create( SpaceShooterClientBundle.class );

  @Source( "css/space-shooter-game.css" )
  StyleResources css();

  @Source( "images/spaceship.png" )
  ImageResource spaceship();

  @Source( "images/asteroid1.png" )
  ImageResource asteroid1();

  @Source( "images/asteroid2.png" )
  ImageResource asteroid2();

  @Source( "images/asteroid3.png" )
  ImageResource asteroid3();

  @Source( "images/galaxy.jpg" )
  ImageResource galaxy();

  @Source( "images/spacesky.jpg" )
  ImageResource spacesky();

  @Source( "images/freeze.png" )
  ImageResource freeze();

  @Source( "images/reflex_boost.png" )
  ImageResource reflex_boost();

  @Source( "images/shield.png" )
  ImageResource shield();

  @Source( "images/x2_exp.png" )
  ImageResource x2_exp();

  @Source( "images/medikit.png" )
  ImageResource medikit();

  @Source( "sound/bump.wav" )
  public DataResource bumpSound();

  @Source( "sound/shield.wav" )
  public DataResource shieldSound();

  @Source( "sound/frezee.wav" )
  public DataResource frezeeSound();

  @Source( "sound/the_end.ogg" )
  public DataResource theEndSound();

  @Source( "sound/game_theme.ogg" )
  public DataResource gameThemeSound();
}
