package com.dmitrynikol.spaceshooter.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.voices.client.Sound;
import com.dmitrynikol.spaceshooter.client.handlers.GameKeyHandler;
import com.dmitrynikol.spaceshooter.client.handlers.GameMouseHandler;
import com.dmitrynikol.spaceshooter.client.main.Asteroid;
import com.dmitrynikol.spaceshooter.client.main.Bullet;
import com.dmitrynikol.spaceshooter.client.main.PowerupElement;
import com.dmitrynikol.spaceshooter.client.main.Spaceship;
import com.dmitrynikol.spaceshooter.client.sound.SoundManagerFactory;
import com.dmitrynikol.spaceshooter.client.util.Accuracy;
import com.dmitrynikol.spaceshooter.client.util.ApplicationContext;
import com.dmitrynikol.spaceshooter.client.util.ApplicationUtils;
import com.dmitrynikol.spaceshooter.client.util.AsteroidGenerator;
import com.dmitrynikol.spaceshooter.client.util.FrameRate;
import com.dmitrynikol.spaceshooter.client.util.Powerup;
import com.dmitrynikol.spaceshooter.client.util.PowerupsGenerator;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Dmitry Nikolaenko
 * 
 */
public class SpaceShooter implements EntryPoint {
	
	private Canvas canvas;
	private Context2d context;
	
	private Timer timer;
	private FrameRate frameRate;
	
	/**
	 * start point of rendering object
	 */
	private int currentElapsedPoint;
	
	/**
	 * last elapsed point of rendering object
	 */
	private int lastElapsedPoint;
	
	private Duration duration;
	
	private Anchor start;
	private Element span;
	
	private Spaceship spaceship;
	private List<PowerupElement> powerups;
	private List<Asteroid> asteroids;
	
	private int score;
	private int oldScore;
	
	private Powerup lastPowerup;
	private float timerPoint;
	
	private GameKeyHandler keyHandler;
	private GameMouseHandler mouseHandler;
	
	public void onModuleLoad() {
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			RootPanel.get().add(new Label(ApplicationUtils.CANVAS_NOT_SUPPORTED));
			return;
		}
		
		canvas.setWidth(ApplicationUtils.CANVAS_WIDTH + Unit.PX.getType());
		canvas.setCoordinateSpaceWidth(ApplicationUtils.CANVAS_WIDTH);
		canvas.setHeight(ApplicationUtils.CANVAS_HEIGHT + Unit.PX.getType());
		canvas.setCoordinateSpaceHeight(ApplicationUtils.CANVAS_HEIGHT);
		
		RootPanel.get("instructions").setVisible(true);
		RootPanel.get("loading").setVisible(false); 
		
		RootPanel.get().add(canvas);
		
		context = canvas.getContext2d();
		frameRate = new FrameRate();
		
		start = new Anchor();
		span = DOM.createSpan();
		span.setInnerText("Play Game");
		start.getElement().appendChild(span);
		start.getElement().setId("start");
		start.setStyleName("button button-blue");
		start.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// switch on mute mode and stop all sound
				SoundManagerFactory.getSoundManager().setMute(true);
				canvas.setFocus(true);
				start.setVisible(false);
				reset();
				// switch off mute mode
				SoundManagerFactory.getSoundManager().setMute(false);
				// start background sound
				SoundManagerFactory.getSoundManager().setLoop(SoundManagerFactory.SoundResource.GAME_THEME, true);
				timer.scheduleRepeating(ApplicationUtils.REFRESH_RATE);
				ApplicationUtils.setVisibleCursor(false);
				RootPanel.get("instructions").setVisible(false);
			}
		});
		
		ApplicationUtils.drawStartPointBackground(context);
		RootPanel.get().add(start);
		
		spaceship = new Spaceship(ApplicationUtils.CANVAS_WIDTH / 2, ApplicationUtils.CANVAS_HEIGHT / 5 * 4);
		
		duration = new Duration();
		lastElapsedPoint = duration.elapsedMillis();
		
		timer = new Timer() {
			@Override
			public void run() {
				update();
			}
		};
		
		initializeCanvasHandlers();
		
		// register sound 
		SoundManagerFactory.getSoundManager().registerSound(SoundManagerFactory.SoundResource.FREZEE);
		SoundManagerFactory.getSoundManager().registerSound(SoundManagerFactory.SoundResource.SHIELD);
		SoundManagerFactory.getSoundManager().registerSound(SoundManagerFactory.SoundResource.BUMP);
		SoundManagerFactory.getSoundManager().registerSound(SoundManagerFactory.SoundResource.GAME_THEME, 
				Sound.MIME_TYPE_AUDIO_OGG_VORBIS);
		SoundManagerFactory.getSoundManager().registerSound(SoundManagerFactory.SoundResource.THE_END, 
				Sound.MIME_TYPE_AUDIO_OGG_VORBIS);
		
		// and start background sound
		SoundManagerFactory.getSoundManager().setLoop(SoundManagerFactory.SoundResource.GAME_THEME, true);
	}
	
	/**
	 * add key and mouse handler to the canvas
	 */
	private void initializeCanvasHandlers() {
		keyHandler = new GameKeyHandler(spaceship);
		mouseHandler = new GameMouseHandler(spaceship);
		
		canvas.addKeyDownHandler(keyHandler);
		canvas.addMouseDownHandler(mouseHandler);
		canvas.addMouseMoveHandler(mouseHandler);
		
		keyHandler.setTimer(timer);
	}
	
	/**
	 * reset all parameters in application
	 */
	private void reset() {
		score = 0;
		oldScore = 0;
		timerPoint = 0;
		spaceship.setHealth(5);
		spaceship.setScore(score);
		frameRate = new FrameRate();
		asteroids = new ArrayList<Asteroid>();
		asteroids.addAll(AsteroidGenerator.getAllAsteroidList());
		powerups = new ArrayList<PowerupElement>();
		
		for (Asteroid asteroid : asteroids) {
			asteroid.destroy();
		}
		
		keyHandler.setTimer(timer);
	}
	
	/**
	 * update current state of components and game info on canvas
	 */
	private void update() {
		currentElapsedPoint  = duration.elapsedMillis();
		float delta = (currentElapsedPoint  - lastElapsedPoint) / 1000.0f;
		lastElapsedPoint = currentElapsedPoint;
		
		frameRate.addFrame(delta);
		
		// draw permanent background color 
		//context.setFillStyle(ApplicationUtils.CANVAS_COLOR);
		//context.fillRect(0, 0, ApplicationUtils.CANVAS_WIDTH, ApplicationUtils.CANVAS_HEIGHT);
		
		ApplicationUtils.drawDynamicBackground(context);
		
		context.setFillStyle(ApplicationUtils.TEXT_COLOR);
		context.fillText("Score: ".concat(String.valueOf(score)), 16, 28);
		context.fillText("Best score: ".concat(String.valueOf(spaceship.getBestScore())), 16, 38);
		context.fillText("Health: ".concat(String.valueOf(spaceship.getHealth())), 16, 48);
		context.fillText("Total time: ".concat(String.valueOf((int) frameRate.getTotalElapsedTime())).concat(" sec"), 16, 58);
		context.fillText("FPS: ".concat(String.valueOf(frameRate.getCurrentFrameRate())), 16, 68);
			
		int indexCoordinate = 0;
		for (Map.Entry<Powerup, Float> entry : ApplicationContext.getTempPowerups().entrySet()) {
			Powerup pow = entry.getKey();
			timerPoint = entry.getValue();
			indexCoordinate++;
			float stopwatchTimer = timerPoint - frameRate.getTotalElapsedTime();
			if (timerPoint > 0 && stopwatchTimer > 0) {
				context.fillText(pow.name().concat(": ").concat(String.valueOf(stopwatchTimer)), 6, 108 + indexCoordinate * 20);
			}
		}
		
		ApplicationUtils.drawInfoWrapper(context);
		
		spaceship.render(context);
		
		if (spaceship.getHealth() <= 0) {
			// switch on mute mode and stop all sound
			SoundManagerFactory.getSoundManager().setMute(true);
			// stopped game process
			timer.cancel();
			// disable the PAUSE key 
			keyHandler.setTimer(null);
			spaceship.destroy();
			span.setInnerText("Play Again");
			start.setVisible(true);
			ApplicationUtils.setVisibleCursor(true);
			
			// switch off mute mode
			SoundManagerFactory.getSoundManager().setMute(false);
			SoundManagerFactory.getSoundManager().playSound(SoundManagerFactory.SoundResource.THE_END);
		}
		
		oldScore = score;
		
		// "rendering" (displaying) an asteroids and bullet elements
		for (Asteroid asteroid : asteroids) {
			asteroid.update(delta);
			asteroid.render(context);
			
			Bullet bullet = spaceship.getBullet();
			
			// check collision between bullet and asteroids
			if (bullet != null) {
				if (ApplicationUtils.collision(bullet.getPosition(), asteroid.getPosition(), Accuracy.LEVEL1)) {
					SoundManagerFactory.getSoundManager().playSound(SoundManagerFactory.SoundResource.FREZEE);
					bullet.destroy();
					asteroid.destroy();
					score += ApplicationContext.getScoreIncrease();
					spaceship.setScore(score);
				}
			}
			
			// check collision between asteroids and spaceship
			if (!ApplicationContext.isShieldEnabled() && 
					ApplicationUtils.collision(spaceship.getPosition(), asteroid.getPosition(), Accuracy.LEVEL3)) {
				asteroid.destroy();
				spaceship.setHealth(spaceship.getHealth() - 1);
				SoundManagerFactory.getSoundManager().playSound(SoundManagerFactory.SoundResource.BUMP);
			}
		}
		
		// "rendering" (displaying) a powerup elements
		for (PowerupElement powerup : new ArrayList<PowerupElement>(powerups)) {
			powerup.update(delta);
			powerup.render(context);
		
			// check collision between powerup element and spaceship
			if (ApplicationUtils.collision(spaceship.getPosition(), powerup.getPosition(), Accuracy.LEVEL3)) {
				powerup.destroy();
				powerups.remove(powerup);
				lastPowerup = powerup.getPowerupType();
				
				if (!lastPowerup.equals(Powerup.MEDIKIT)) {
					timerPoint = frameRate.getTotalElapsedTime() + 5;
					if (!ApplicationContext.getTempPowerups().containsKey(lastPowerup)) {
						ApplicationContext.getTempPowerups().put(lastPowerup, timerPoint);
						ApplicationContext.runPowerups(spaceship, lastPowerup);
					}
				} else {
					spaceship.setHealth(spaceship.getHealth() + 1);
				}
				
				SoundManagerFactory.getSoundManager().playSound(SoundManagerFactory.SoundResource.SHIELD);
			}
			
			// if the object is out of canvas scope then just remove it
			if (powerup.getPosition().getY() > ApplicationUtils.CANVAS_HEIGHT) {
				powerups.remove(powerup);
			}
		}
		
		// increase the number of asteroids and add new powerup element
		if (score > 0 && oldScore != score) {
			if (score % 3 == 0) {
				// add random powerups
				powerups.add(new PowerupElement(PowerupsGenerator.getRandomPowerup()));
				// add specific powerups
				//powerups.add(new PowerupElement(Powerup.FREEZE));
			}
			if (score % 5 == 0) {
				asteroids.add(AsteroidGenerator.getRandomAsteroid());
			}
		}
	}
}
