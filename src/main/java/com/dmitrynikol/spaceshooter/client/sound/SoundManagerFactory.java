package com.dmitrynikol.spaceshooter.client.sound;

import com.dmitrynikol.spaceshooter.client.bundle.ClientBundleInjector;
import com.dmitrynikol.spaceshooter.client.bundle.SpaceShooterClientBundle;
import com.google.gwt.core.client.GWT;

/**
 * Factory that allow to get access to the sound manager.
 *
 * @author Dmitry Nikolaenko
 */
public class SoundManagerFactory
{
  private static final ClientBundleInjector injector = GWT.create( ClientBundleInjector.class );
  private static final SpaceShooterClientBundle gameResourceBundle = injector.spaceShooterBundle();

  public enum SoundResource
  {
    GAME_THEME( gameResourceBundle.gameThemeSound().getSafeUri().asString() ),
    THE_END( gameResourceBundle.theEndSound().getSafeUri().asString() ),
    FREZEE( gameResourceBundle.frezeeSound().getSafeUri().asString() ),
    SHIELD( gameResourceBundle.shieldSound().getSafeUri().asString() ),
    BUMP( gameResourceBundle.bumpSound().getSafeUri().asString() );

    private String name;

    private SoundResource( String name )
    {
      this.name = name;
    }

    public String getUri()
    {
      return name;
    }
  }

  private static final SoundManager c_soundManager =  new SoundManager();

  public static SoundManager getSoundManager()
  {
    return c_soundManager;
  }
}

