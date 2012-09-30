package com.dmitrynikol.spaceshooter.client.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * 
 * @author Dmitry Nikolaenko
 *
 */
public interface SpaceShooterClientBundle extends ClientBundle {
	
	public interface StyleResources extends CssResource {
		@ClassName("start")
		String start();
	}
	
	static final SpaceShooterClientBundle INSTANCE = GWT.create(SpaceShooterClientBundle.class);
	
	@Source("resources/css/space-shooter-game.css")
	StyleResources css();
	
	@Source("resources/images/spaceship.png")
	ImageResource spaceship();
	
	@Source("resources/images/asteroid1.png")
	ImageResource asteroid1();
	
	@Source("resources/images/asteroid2.png")
	ImageResource asteroid2();
	
	@Source("resources/images/asteroid3.png")
	ImageResource asteroid3();
	
	@Source("resources/images/galaxy.jpg")
	ImageResource galaxy();
	
	@Source("resources/images/spacesky.jpg")
	ImageResource spacesky();
	
	@Source("resources/images/freeze.png")
	ImageResource freeze();
	
	@Source("resources/images/reflex_boost.png")
	ImageResource reflex_boost();
	
	@Source("resources/images/shield.png")
	ImageResource shield();
	
	@Source("resources/images/x2_exp.png")
	ImageResource x2_exp();
	
	@Source("resources/images/medikit.png")
	ImageResource medikit();

	@Source("resources/sound/bump.wav")
	public DataResource bumpSound();
	
	@Source("resources/sound/shield.wav")
	public DataResource shieldSound();
	
	@Source("resources/sound/frezee.wav") 
	public DataResource frezeeSound();
	
	@Source("resources/sound/the_end.ogg")
	public DataResource theEndSound();
	
	@Source("resources/sound/game_theme.ogg")
	public DataResource gameThemeSound();
}
