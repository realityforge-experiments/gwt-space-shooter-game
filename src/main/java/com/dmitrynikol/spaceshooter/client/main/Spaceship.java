package com.dmitrynikol.spaceshooter.client.main;

import com.dmitrynikol.spaceshooter.client.bundle.ClientBundleInjector;
import com.dmitrynikol.spaceshooter.client.core.GameComponent;
import com.dmitrynikol.spaceshooter.client.math.Position2D;
import com.dmitrynikol.spaceshooter.client.math.Size2D;
import com.dmitrynikol.spaceshooter.client.util.ApplicationContext;
import com.dmitrynikol.spaceshooter.client.util.ApplicationUtils;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Image;

/**
 * This is the Spaceship game element.
 *
 * @author Dmitry Nikolaenko
 *
 */
public class Spaceship extends GameComponent {

	private final ClientBundleInjector injector = GWT.create(ClientBundleInjector.class);

	private int health;
	private int score;
	private int bestScore;

	private ImageElement image;
	private Position2D position;
	private Size2D size;
	private Bullet bullet = null;

	public Spaceship() {
		this(ApplicationUtils.DEFAULT_X_COORD, ApplicationUtils.DEFAULT_Y_COORD);
	}


	public Spaceship(int xCoord, int yCoord) {
		position = new Position2D(xCoord, yCoord);
		initComponent();
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Context2d context) {
		if (isAlive()) {
			context.drawImage(image, position.getX(), position.getY());
			if (bullet != null && bullet.isAlive()) {
				bullet.render(context);
				bullet.update(0.5f);
			}

			if (ApplicationContext.isShieldEnabled()) {
				ApplicationUtils.drawShield(context, position);
			}

			if (ApplicationContext.isPositionEnabled()) {
				ApplicationUtils.drawElementPosition(context, position, "Spaceship: ",
						position.getX() - 20, position.getY() + image.getWidth() + 10);
			}
		}
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	protected void initComponent() {
		health = 5;
		String best = Cookies.getCookie("score");
		if (best != null) {
			bestScore = Integer.valueOf(best);
		}
		setPosition(position);
		image = ImageElement.as(new Image(injector.spaceShooterBundle().spaceship().getSafeUri()).getElement());
		size = new Size2D(image.getWidth(), image.getHeight());
		setSize(size);
	}

	@Override
	public void injectImage(ImageElement image) {
		this.image = image;
	}

	@Override
	public void destroy() {
		bestScore = Math.max(score, bestScore);
		ApplicationUtils.setCookie("score", String.valueOf(bestScore));
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}

	public int getBestScore() {
		return bestScore;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void shot(Position2D position) {
		bullet = new Bullet();
		bullet.updateBullet(bullet.getBulletType(score));
		bullet.setPosition(position);
	}

	public Bullet getBullet() {
		return bullet;
	}
}

