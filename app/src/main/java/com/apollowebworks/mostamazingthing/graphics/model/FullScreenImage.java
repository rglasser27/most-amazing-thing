package com.apollowebworks.mostamazingthing.graphics.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.apollowebworks.mostamazingthing.graphics.exception.FullScreenImageException;
import com.apollowebworks.mostamazingthing.math.DrawUtil;

/**
 * there are 320x200=23040 pixels in a screen
 * Each pixel takes two bits
 * Four pixels in a line = 1 byte
 */
public class FullScreenImage {
	public static final int SCREEN_WIDTH = 320;
	public static final int SCREEN_HEIGHT = 200;
	private static final int PIXELS = SCREEN_WIDTH * SCREEN_HEIGHT;

	private RgbColor[] pixels;

	public FullScreenImage() {
		pixels = new RgbColor[PIXELS];
		setRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, RgbColor.BLACK);
	}

	public void setPixel(int x, int y, RgbColor color) {
		if (x < 0 || x >= SCREEN_WIDTH || y < 0 || y >= SCREEN_HEIGHT) {
			throw new FullScreenImageException("Out of bounds");
		}
		pixels[(y * SCREEN_WIDTH) + (x % SCREEN_WIDTH)] = color;
	}

	public RgbColor getPixel(int x, int y) {
		if (x < 0 || x >= SCREEN_WIDTH || y < 0 || y >= SCREEN_HEIGHT) {
			throw new FullScreenImageException("Out of bounds");
		}
		return pixels[(y * SCREEN_WIDTH) + (x % SCREEN_WIDTH)];
	}

	public void setRect(int left, int top, int width, int height, RgbColor color) {
		if (left < 0 || left >= SCREEN_WIDTH ||
				width < 0 || left + width > SCREEN_WIDTH ||
				top < 0 || top >= SCREEN_HEIGHT ||
				height < 0 || top + height > SCREEN_HEIGHT) {
			throw new FullScreenImageException("Out of bounds");
		}

		for (int y = top; y < top + height; y++) {
			for (int x = left; x < left + width; x++) {
				pixels[(y * SCREEN_WIDTH) + (x % SCREEN_WIDTH)] = color;
			}
		}
	}

	public void draw(Canvas canvas) {
		for (int y = 0; y < SCREEN_HEIGHT; y++) {
			for (int x = 0; x < SCREEN_WIDTH; x++) {
				drawPixel(canvas, x, y);
			}
		}
	}

	private void drawPixel(Canvas canvas, int x, int y) {
		float left = x * canvas.getWidth() / SCREEN_WIDTH;
		float top = y * canvas.getHeight() / SCREEN_HEIGHT;
		float right = (x + 1) * canvas.getWidth() / SCREEN_WIDTH;
		float bottom = (y + 1) * canvas.getHeight() / SCREEN_HEIGHT;
		Paint paint = DrawUtil.PAINT_MAP.get(pixels[(y * SCREEN_WIDTH) + (x % SCREEN_WIDTH)]);
		canvas.drawRect(left, top, right, bottom, paint);
	}
}
