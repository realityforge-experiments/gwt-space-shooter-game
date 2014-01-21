package com.dmitrynikol.spaceshooter.client.sound;

import com.dmitrynikol.spaceshooter.client.sound.SoundManagerFactory.SoundResource;
import java.util.HashMap;
import java.util.Map;

/**
 * SoundManager takes care of the sound you want to play.
 *
 * @author Dmitry Nikolaenko
 */
public class SoundManager
{
  private Map<SoundResource, SoundPlayer> soundMap = new HashMap<SoundResource, SoundPlayer>();
  private boolean mute = false;

  /**
   * Register sound in sound system
   *
   * @param resource the enum of sound which will be registered
   */
  public void registerSound( final SoundResource resource )
  {
    if ( !soundMap.containsKey( resource ) )
    {
      soundMap.put( resource, new SoundPlayer( resource.getUri() ) );
    }
  }

  /**
   * Register sound in sound system with specific MIME type
   *
   * @param resource the enum of sound which will be registered
   * @param mimeType MIME type of the new Sound object
   */
  public void registerSound( final SoundResource resource, final String mimeType )
  {
    if ( !soundMap.containsKey( resource ) )
    {
      soundMap.put( resource, new SoundPlayer( resource.getUri(), mimeType ) );
    }
  }

  /**
   * Start playing the sound.
   *
   * @param sound in sound system
   */
  public void playSound( final SoundResource sound )
  {
    if ( soundMap.containsKey( sound ) && !mute )
    {
      soundMap.get( sound ).play();
    }
  }

  /**
   * Stops the sound.
   *
   * @param sound in sound system
   */
  public void stopSound( final SoundResource sound )
  {
    if ( soundMap.containsKey( sound ) )
    {
      soundMap.get( sound ).stop();
    }
  }

  /**
   * Sets a loop on current sound and play it immediately.
   *
   * @param loop responsible for loop mode
   */
  public void setLoop( final SoundResource sound, final boolean loop )
  {
    if ( soundMap.containsKey( sound ) )
    {
      soundMap.get( sound ).setLoop( loop );
    }
  }

  /**
   * Sets the muting state on current sound.
   *
   * @param mute true for muting, false otherwise
   */
  public void setMute( final boolean mute )
  {
    this.mute = mute;

    if ( mute )
    {
      for ( SoundResource sound : soundMap.keySet() )
      {
        stopSound( sound );
      }
    }
  }
}

