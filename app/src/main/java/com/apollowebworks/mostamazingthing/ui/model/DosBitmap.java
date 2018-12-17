package com.apollowebworks.mostamazingthing.ui.model;

import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import com.apollowebworks.mostamazingthing.ui.exception.FullScreenImageException;
import com.apollowebworks.mostamazingthing.util.DrawUtil;

import java.util.List;

/**
 * there are 320x200=23040 pixels in a screen
 * Each pixel takes two bits
 * Four pixels in a line = 1 byte
 */
public class DosBitmap {
	private final int bmWidth;
	private final int bmHeight;
	private RgbColor[] pixels;
	private int height;

	public DosBitmap(List<List<RgbColor>> pixelRows) {
		bmHeight = pixelRows.size();
		bmWidth = pixelRows.get(0).size();
		pixels = new RgbColor[bmWidth * bmHeight];
		for (int y = 0; y < bmHeight; y ++) {
			for (int x = 0; x < bmWidth; x ++) {
				pixels[y*bmWidth+x] = pixelRows.get(y).get(x);
			}
		}
	}

	public DosBitmap(int bmWidth, int bmHeight) {
		this.bmWidth = bmWidth;
		this.bmHeight = bmHeight;
		pixels = new RgbColor[bmWidth * bmHeight];
		setRect(0, 0, bmWidth, bmHeight, RgbColor.BLACK);
	}

	public int getBmWidth() {
		return bmWidth;
	}

	public int getBmHeight() {
		return bmHeight;
	}

	public void setPixel(int x, int y, RgbColor color) {
		if (x < 0 || x >= bmWidth || y < 0 || y >= bmHeight) {
			throw new FullScreenImageException("Out of bounds");
		}
		pixels[(y * bmWidth) + (x % bmWidth)] = color;
	}

	public RgbColor getPixel(int x, int y) {
		if (x < 0 || x >= bmWidth || y < 0 || y >= bmHeight) {
			throw new FullScreenImageException("Out of bounds");
		}
		return pixels[(y * bmWidth) + (x % bmWidth)];
	}

	public void setRect(int left, int top, int width, int height, RgbColor color) {
		if (left < 0 || left >= bmWidth ||
				width < 0 || left + width > bmWidth ||
				top < 0 || top >= bmHeight ||
				height < 0 || top + height > bmHeight) {
			throw new FullScreenImageException("Out of bounds");
		}

		for (int y = top; y < top + height; y++) {
			for (int x = left; x < left + width; x++) {
				pixels[(y * bmWidth) + (x % bmWidth)] = color;
			}
		}
	}

	public void draw(Canvas canvas) {
		for (int y = 0; y < bmHeight; y++) {
			for (int x = 0; x < bmWidth; x++) {
				drawPixel(canvas, x, y);
			}
		}
	}

	private void drawPixel(Canvas canvas, int x, int y) {
		float left = x * canvas.getWidth() / bmWidth;
		float top = y * canvas.getHeight() / bmHeight;
		float right = (x + 1) * canvas.getWidth() / bmWidth;
		float bottom = (y + 1) * canvas.getHeight() / bmHeight;
		Paint paint = DrawUtil.PAINT_MAP.get(pixels[(y * bmWidth) + (x % bmWidth)]);
		canvas.drawRect(left, top, right, bottom, paint);
	}
}
