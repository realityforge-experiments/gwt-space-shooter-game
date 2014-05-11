package com.dmitrynikol.spaceshooter.client.core;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * Defines the requirements for an object responsible for rendering.
 *
 * @author Dmitry Nikolaenko
 */
public interface Renderer
{
  void render( Context2d context );
}
