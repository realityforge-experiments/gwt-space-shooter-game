package com.dmitrynikol.spaceshooter.client.main;

import com.dmitrynikol.spaceshooter.client.bundle.ClientBundleInjector;
import com.dmitrynikol.spaceshooter.client.bundle.SpaceShooterClientBundle;
import com.dmitrynikol.spaceshooter.client.core.GameComponent;
import com.dmitrynikol.spaceshooter.client.math.Position2D;
import com.dmitrynikol.spaceshooter.client.math.Size2D;
import com.dmitrynikol.spaceshooter.client.util.ApplicationContext;
import com.dmitrynikol.spaceshooter.client.util.ApplicationUtils;
import com.dmitrynikol.spaceshooter.client.util.AsteroidGenerator;
import com.dmitrynikol.spaceshooter.client.util.AsteroidType;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.Image;

/**
 * This is the Asteroid game element.
 * 
 * @author Dmitry Nikolaenko
 * 
 */
public class Asteroid extends GameComponent {
	
	private boolean isAlive;
	private int acceleration;
	private ImageElement image;
	private Position2D position;
	private Size2D size;
	private AsteroidType type;
	
	private final ClientBundleInjector injector = GWT.create(ClientBundleInjector.class);
	private final SpaceShooterClientBundle gameResourceBundle = injector.spaceShooterBundle();
	
	public Asteroid() {
		this(AsteroidType.COALY);
	}
	
	public Asteroid(AsteroidType type) {
		this.type = type;
		initComponent();
	}

	@Override
	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public void destroy() {
		type = AsteroidGenerator.getRandomAsteroidType();
		initComponent();
	}

	@Override
	public void render(Context2d context) {
		context.drawImage(image, position.getX(), position.getY());
	
		if (ApplicationContext.isPositionEnabled()) {
			ApplicationUtils.drawElementPosition(context, position, type.name().concat(": "), 
					position.getX() - 10, position.getY() + image.getWidth() + 10);
		}
	}

	@Override
	public void update(float delta) {
		if (!ApplicationContext.isFreezeEnabled()) {
			if (position.getY() > ApplicationUtils.CANVAS_HEIGHT) {
				type = AsteroidGenerator.getRandomAsteroidType();
				initComponent();
			}
			position.setY(position.getY() + (ApplicationContext.isReflexBoostEnabled() ? 1 : acceleration));
		}
	}

	@Override
	protected void initComponent() {
		isAlive = true;
		updateAsteroidSurface();
		//image = ImageElement.as(new Image(injector.spaceShooterBundle().asteroid1().getSafeUri()).getElement());
		size = new Size2D(image.getWidth(), image.getHeight());
		setSize(size);
		updateAsteroidPosition();
		updateAsteroidAcceleration();
	}

	@Override
	public void injectImage(ImageElement image) {
		this.image = image;
	}
	
	/**
	 * method update position of asteroid
	 */
	private void updateAsteroidPosition() {
		int randomNumber = 
				ApplicationUtils.getRandomIntegerNumberBetween(0, ApplicationUtils.MAXIMUM_WIDTH_BOUNDARY - 
						getSize().getWidth());
		this.position = new Position2D(randomNumber, 0);
		setPosition(position);
	}
	
	/**
	 * update behavior of asteroid
	 */
	private void updateAsteroidAcceleration() {
		acceleration = type.getAsteroidAcceleration();
	}
	
	/**
	 * update surface of asteroid
	 */
	private void updateAsteroidSurface() {
		SafeUri uri = null;
		switch(type.getAsteroidType()) {
			case 0: 
				uri = gameResourceBundle.asteroid1().getSafeUri(); 
				break;
			case 1: 
				uri = gameResourceBundle.asteroid2().getSafeUri(); 
				break;
			case 2: 
				uri = gameResourceBundle.asteroid3().getSafeUri();
				break;
			default: 
				uri = gameResourceBundle.asteroid1().getSafeUri();
				break;
		}
		image = ImageElement.as(new Image(uri).getElement());
	}
	
	public AsteroidType getType() {
		return type;
	}
}         