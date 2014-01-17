package com.dmitrynikol.spaceshooter.client.math;

/**
 * 2D dimensions of an object
 *
 * @author Dmitry Nikolaenko
 *
 */
public class Size2D {
	public int width;
	public int height;

	public Size2D(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "Size: (" + width + ", " + height + ")";
	}
}

