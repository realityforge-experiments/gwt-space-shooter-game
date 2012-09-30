package com.dmitrynikol.spaceshooter.client.sound;

import java.util.HashMap;
import java.util.Map;

import com.dmitrynikol.spaceshooter.client.sound.SoundManagerFactory.SoundResource;

/**
 * SoundManager takes care of the sound you want to play.
 * 
 * @author Dmitry Nikolaenko
 *
 */
public class SoundManager {
	
	private Map<SoundResource, SoundPlayer> soundMap;
	private boolean mute = false;
	
	public SoundManager() {
		soundMap = new HashMap<SoundResource, SoundPlayer>();
	}
	
	/**
	 * Register sound in sound system
	 * 
	 * @param resource the enum of sound which will be registered
	 */
	public void registerSound(SoundResource resource) {
		if (!soundMap.containsKey(resource)) {
			soundMap.put(resource, new SoundPlayer(resource.getUri()));
		}
	}
	
	/**
	 * Register sound in sound system with specific MIME type
	 * 
	 * @param resource the enum of sound which will be registered
	 * @param mimeType MIME type of the new Sound object
	 */
	public void registerSound(SoundResource resource, String mimeType) {
		if (!soundMap.containsKey(resource)) {
			soundMap.put(resource, new SoundPlayer(resource.getUri(), mimeType));
		}
	}
	
	/**
	 * Unregisters the sound
	 * 
	 * @param resource in sound system
	 */
	public void unregisteredSound(SoundResource resource) {
		soundMap.remove(resource).stop();
	}
	
	/**
	 * Whether the sound with this key is registered.
	 * 
	 * @param sound in sound system
	 * @return whether a sound is registered 
	 */
	public boolean isSoundRegistered(SoundResource sound) {
		return soundMap.containsKey(sound);
	}
	
	/**
	 * Start playing the sound.
	 * 
	 * @param sound in sound system
	 */
	public void playSound(SoundResource sound) {
		if (soundMap.containsKey(sound) && mute == false) {
			soundMap.get(sound).play();
		}
	}
	
	/**
	 * Stops the sound.
	 * 
	 * @param sound in sound system
	 */
	public void stopSound(SoundResource sound) {
		if (soundMap.containsKey(sound)) {
			soundMap.get(sound).stop();
		}
	}
	
	/**
	 * Sets a loop on current sound and play it immediately.
	 * 
	 * @param loop responsible for loop mode
	 */
	public void setLoop(SoundResource sound, boolean loop) {
		if (soundMap.containsKey(sound)) {
			soundMap.get(sound).setLoop(loop);
		}
	}
	
	/**
	 * Sets the muting state on current sound.
	 * 
	 * @param mute true for muting, false otherwise
	 */
	public void setMute(final boolean mute) {
		this.mute = mute;
		
		if (mute) {
			for (SoundResource sound : soundMap.keySet()) {
				stopSound(sound);
			}
		}
	}
}
