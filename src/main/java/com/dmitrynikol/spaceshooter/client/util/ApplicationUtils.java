package com.dmitrynikol.spaceshooter.client.util;

import com.dmitrynikol.spaceshooter.client.bundle.ClientBundleInjector;
import com.dmitrynikol.spaceshooter.client.math.Position2D;
import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.CanvasPattern;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.Repetition;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import java.util.Date;

/**
 * Miscellaneous utility functions.
 *
 * @author Dmitry Nikolaenko
 */
public final class ApplicationUtils
{

  public static final int MAXIMUM_WIDTH_BOUNDARY = 600;

  public static final int CANVAS_HEIGHT = 300;
  public static final int CANVAS_WIDTH = 600;

  public static final int DEFAULT_X_COORD = 600;
  public static final int DEFAULT_Y_COORD = 300;

  public static final int REFRESH_RATE = 20;
  public static final int DELTA = 10;
  public static final int MAXIMUM_ASTEROID_NUMBER = 3;
  public static final int UPDATE_ASTEROID_TIME = 10;

  public static final int EXPIRE_DATE = 30;
  public static final long MILLISECS_PER_DAY = 1000L * 60L * 60L * 24L;

  public static final String CANVAS_NOT_SUPPORTED = "Sorry, your browser doesn't support the HTML5 Canvas element";

  public static final CssColor CANVAS_COLOR = CssColor.make( "rgba(1,1,0,1.0)" );
  public static final CssColor TEXT_COLOR = CssColor.make( "rgba(255,255,0,1.0)" );
  public static final CssColor BULLET_COLOR = CssColor.make( "rgba(255,0,255,1.0)" );
  public static final CssColor INFO_SHAPE_WRAPPER_COLOR = CssColor.make( "rgba(255,0,255,0.3)" );
  public static final String SHIELD_COLOR = "#8ED6FF";

  private static final ClientBundleInjector injector = GWT.create( ClientBundleInjector.class );
  private static Context2d context2D;

  /**
   * Class contains only static methods.
   */
  private ApplicationUtils()
  {
  }

  /**
   * Generates a random integer number between lower and upper bounds.
   *
   * @param lower bound for generated integer number
   * @param upper bound for generated integer number
   * @return a random integer greater than or equal to lower and less than or equal to upper
   */
  public static int getRandomIntegerNumberBetween( int lower, int upper )
  {
    if ( lower >= upper )
    {
      throw new IllegalArgumentException( "lower number must be lower than upper number" );
    }
    return new java.util.Random().nextInt( upper - lower + 1 ) + lower;
  }

  /**
   * Generates a random double number between lower and upper bounds.
   *
   * @param lower bound for generated double number
   * @param upper bound for generated double number
   * @return random double greater than lower and less than upper
   */
  private static double getRandomDoubleNumberBeatween( double lower, double upper )
  {
    if ( lower > upper )
    {
      throw new IllegalArgumentException( "lower number must be lower than upper number" );
    }
    return lower + ( upper - lower ) * new java.util.Random().nextDouble();
  }

  /**
   * Sets the best result of the game to the cookie.
   *
   * @param name  the name of the cookie to be added
   * @param value the cookie's value
   */
  public static void setCookie( String name, String value )
  {
    Date date = new Date();
    date.setTime( date.getTime() + MILLISECS_PER_DAY * EXPIRE_DATE );
    Cookies.setCookie( name, value, date );
  }

  /**
   * Checks collision of objects.
   *
   * @param first interacting element
   * @param first interacting element
   * @return return true if collision is happened, otherwise false
   */
  public static boolean collision( Position2D first, Position2D second, Accuracy accuracy )
  {
    int xAxisFirst = first.getX();
    int xAxisSecond = second.getX();

    // bullet moves from the left side
    int intersectAxisX1 = Math.abs( first.getX() - second.getX() );
    // bullet moves from the right side
    int intersectAxisX2 = first.getX() - second.getX();
    int intersectAxisY = Math.abs( first.getY() - second.getY() );

    if ( xAxisFirst < xAxisSecond && intersectAxisX1 < accuracy.getShootingAccuracy() && intersectAxisY < 50 )
    {
      return true;
    }

    if ( xAxisFirst > xAxisSecond && intersectAxisX2 < 50 && intersectAxisY < 50 )
    {
      return true;
    }

    return false;
  }

  /**
   * Method generate random color.
   */
  public static CssColor getRandomRGBColor()
  {
    int redColor = Random.nextInt( 255 );
    int greenColor = Random.nextInt( 255 );
    int blueColor = Random.nextInt( 255 );

    return CssColor.make( redColor, greenColor, blueColor );
  }

  /**
   * Method generate random color with alpha transparency
   */
  public static CssColor getRandomRGBAColor()
  {
    int redColor = Random.nextInt( 255 );
    int greenColor = Random.nextInt( 255 );
    int blueColor = Random.nextInt( 255 );

    return CssColor.make( "rgba(" + redColor + ", " + greenColor + "," +
                          blueColor + ", " + Random.nextDouble() + ")" );
  }

  /**
   * Sets whether cursor is visible.
   *
   * @param visible true to show the default cursor, false to hide it
   */
  public static void setVisibleCursor( boolean visible )
  {
    DOM.setStyleAttribute( RootPanel.get().getElement(), "cursor", visible ? "default" : "none" );
  }

  /**
   * draw pause label
   */
  public static void drawPauseLabel()
  {
    if ( context2D != null )
    {
      drawEllipse( 565, 14, 80, 20 );
      context2D.setFillStyle( ApplicationUtils.TEXT_COLOR );
      context2D.fillText( "PAUSE...", 545, 18 );
    }
  }

  /**
   * Draw ellipse.
   *
   * @param centerX of the figure
   * @param centerY of the figure
   * @param width   the width of the rectangle
   * @param height  the height of the rectangle
   */
  public static void drawEllipse( double centerX, double centerY, double width, double height )
  {
    if ( context2D != null )
    {
      context2D.setLineWidth( 2 );
      context2D.beginPath();
      // move to the top center of the ellipse
      context2D.moveTo( centerX, centerY - height / 2 );

      context2D.bezierCurveTo(
        centerX + width / 2, centerY - height / 2,
        centerX + width / 2, centerY + height / 2,
        centerX, centerY + height / 2
      );
      context2D.bezierCurveTo(
        centerX - width / 2, centerY + height / 2,
        centerX - width / 2, centerY - height / 2,
        centerX, centerY - height / 2
      );

      context2D.setFillStyle( INFO_SHAPE_WRAPPER_COLOR );
      context2D.fill();
      context2D.closePath();
      context2D.stroke();
    }
  }

  /**
   * Draw shapes info wrapper.
   *
   * @param context used to draw on a canvas
   */
  public static void drawInfoWrapper( Context2d context )
  {
    context.setFillStyle( INFO_SHAPE_WRAPPER_COLOR );
    context.setStrokeStyle( "white" );
    context.setLineWidth( 2 );
    context.beginPath();
    context.moveTo( 55, 5 );
    context.quadraticCurveTo( 5, 5, 5, 40 );
    context.quadraticCurveTo( 5, 80, 25, 80 );
    context.quadraticCurveTo( 20, 90, 10, 95 );
    context.quadraticCurveTo( 30, 90, 35, 80 );
    context.quadraticCurveTo( 105, 80, 105, 42 );
    context.quadraticCurveTo( 105, 5, 55, 5 );
    context.fill();
    context.stroke();
  }

  /**
   * Draw initial background for the game.
   *
   * @param context used to draw on a canvas
   */
  public static void drawStartPointBackground( Context2d context )
  {
    // draw image from the bundle
        /*ImageElement background =
				ImageElement.as(new Image(injector.spaceShooterBundle().galaxy().getSafeUri()).getElement());
		context.drawImage(background, 0, 0); */

    context2D = context;

    // create gradients
    CanvasGradient gradient = context.createLinearGradient( 0, 0, 0, CANVAS_HEIGHT );

    gradient.addColorStop( getRandomDoubleNumberBeatween( 0.1, 0.3 ), getRandomRGBColor().value() );
    gradient.addColorStop( 1, getRandomRGBAColor().value() );

    // assign gradients to fill
    context.setFillStyle( gradient );

    // draw filled rectangle on a canvas
    context.fillRect( 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT );

    drawStellarSky( context );
  }

  /**
   * Draw stellar sky on canvas.
   *
   * @param context used to draw on a canvas
   */
  public static void drawStellarSky( Context2d context )
  {
    context.setFillStyle( "white" );

    // draw stars
    for ( int i = 1; i < 300; i++ )
    {
      context.save();
      context.translate( Math.floor( Math.random() * CANVAS_WIDTH ), Math.floor( Math.random() * CANVAS_HEIGHT ) );
      drawStar( context, Math.floor( Math.random() * 5 ) + 2 );
      context.restore();
    }
  }

  /**
   * Draw a star.
   *
   * @param context used to draw on a canvas
   * @param radius  coordinate for new position
   */
  private static void drawStar( Context2d context, double radius )
  {
    context.save();
    context.beginPath();
    context.moveTo( radius, 0 );
    for ( int i = 0; i < 9; i++ )
    {
      context.rotate( Math.PI / 5 );
      context.lineTo( i % 2 == 0 ? ( radius / 0.525731 ) * 0.200811 : radius, 0 );
    }
    context.closePath();
    context.fill();
    context.restore();
  }

  /**
   * Draw game background image.
   *
   * @param context used to draw on a canvas
   */
  public static void drawDynamicBackground( Context2d context )
  {
    try
    {
      ImageElement background =
        ImageElement.as( new Image( injector.spaceShooterBundle().spacesky().getSafeUri() ).getElement() );
      CanvasPattern pattern = context.createPattern( background, Repetition.REPEAT );
      context.setFillStyle( pattern );
      context.fillRect( 0, 0, ApplicationUtils.CANVAS_WIDTH, ApplicationUtils.CANVAS_HEIGHT );
    }
    catch ( JavaScriptException exception )
    {
      // TODO uncommented if we need exception information, only for firefox browser
      //exception.printStackTrace();
    }
  }

  /**
   * Draws filled text.
   *
   * @param context  used to draw on a canvas
   * @param position of the element
   * @param text     the text as a String
   * @param x        the x coordinate of the text position
   * @param y        the y coordinate of the text position
   */
  public static void drawElementPosition( Context2d context, Position2D position, String text, double x, double y )
  {
    if ( position != null && position.getY() >= 0 )
    {
      context.setFillStyle( ApplicationUtils.TEXT_COLOR );
      StringBuilder bulletPosition = new StringBuilder();
      bulletPosition.append( "[" );
      bulletPosition.append( position.getX() );
      bulletPosition.append( ", " );
      bulletPosition.append( position.getY() );
      bulletPosition.append( "]" );
      context.fillText( text.concat( bulletPosition.toString() ), x, y );
    }
  }

  /**
   * Draw shield for spaceship.
   *
   * @param context  used to draw on a canvas
   * @param position of the element
   */
  public static void drawShield( Context2d context, Position2D position )
  {
    context.beginPath();
    context.setGlobalAlpha( 0.3 );
    context.arc( position.getX() + 23, position.getY() + 20, 35, 0, 2 * Math.PI, false );
    context.setFillStyle( "#8ED6FF" );
    context.fill();
    context.setLineWidth( 2 );
    context.setStrokeStyle( "white" );
    context.stroke();
    context.setGlobalAlpha( 1 );
  }
}

