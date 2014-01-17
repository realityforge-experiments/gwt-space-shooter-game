package com.dmitrynikol.spaceshooter.client.sound;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;

/**
 * The SoundPlayer class enables to easily include sounds in applications.
 * It can play sound files in the mp3, mp4, wav, ogg, au, mid and others formats.
 *
 * You shouldn't work with this class directly, instead you should use SoundManager,
 * it will fit your needs.
 *
 * @author Dmitry Nikolaenko
 *
 */
public class SoundPlayer {

	/**
	 * MIME type of the Sound object
	 */
	private String mimeType;

	/**
	 * Current audio file
	 */
	private Sound currentSound;

	/**
	 * Controller class which allow to create Sound objects
	 */
	private SoundController soundController;

	public SoundPlayer(final String uri) {
		this(uri, Sound.MIME_TYPE_AUDIO_WAV_PCM);

	}

	public SoundPlayer(final String uri, final String mimeType) {
		this.mimeType = mimeType;

		soundController = new SoundController();

		currentSound = soundController.createSound(this.mimeType, uri);

		 // with the sound handler we know when the sound has loaded
		currentSound.addEventHandler(new SoundHandler() {
			@Override
			public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
				// See detailed documentation in SoundResource.LoadState
			    // in order to understand these possible values:
			    // LOAD_STATE_SUPPORTED_AND_READY
			    // LOAD_STATE_SUPPORTED_NOT_READY
			    // LOAD_STATE_SUPPORTED_MAYBE_READY
			    // LOAD_STATE_NOT_SUPPORTED
			    // LOAD_STATE_SUPPORT_NOT_KNOWN
			    // LOAD_STATE_UNINITIALIZED
				System.out.println("Load state: " + event.getLoadState().name());
			}

			@Override
			public void onPlaybackComplete(PlaybackCompleteEvent event) {
				// WARNING: this method may in fact never be called; see SoundResource.LoadState
			}
		});
	}

	/**
	 * Starts playing the sound.
	 */
	public void play() {
		currentSound.play();
	}

	/**
	 * Stop play back of this sound.
	 */
	public void stop() {
		currentSound.stop();
	}

	/**
	 * Set whether this sound should be played in a loop.
	 *
	 * @param loop true for looping, false otherwise
	 */
	public void setLoop(boolean loop) {
		currentSound.setLooping(loop);
		currentSound.play();
	}

	/**
	 * Sets the muting state of the audio.
	 *
	 * @param mute true for muting, false otherwise
	 */
	public void setMute(boolean mute) {
		currentSound.setVolume(mute ? 0 : 100);
	}

	/**
	 * Set this sound's volume (range 0..100).
	 *
	 * @param volume new value for sound volume
	 */
	public void setVolume(int volume) {
		currentSound.setVolume(volume);
	}

	/**
	 * Set the left/right speaker balance (range -100..100).
	 *
	 * @param balance new balance (range -100..100)
	 */
	public void setBalance(int balance) {
		currentSound.setBalance(balance);
	}
}

