package com.apollowebworks.mostamazingthing.math;

import android.graphics.*;
import com.apollowebworks.mostamazingthing.graphics.model.RgbColor;

import java.util.HashMap;
import java.util.Map;

public class DrawUtil {
	/**
	 * Define relative width and height for easy pinpointing positions on map.
	 * 0., 0. is the center of the screen.
	 * -150., 100. is the top left.
	 * 150., -100. is the bottom right.
	 */
	public static final float RELATIVE_WIDTH = 320.f;
	public static final float RELATIVE_HEIGHT = 200.f;

	public static Map<RgbColor, Paint> PAINT_MAP = createPaintMap();


	public static PointF getVirtualPoint(int x, int y, Rect clipBounds) {
		return new PointF(getRelativeX(x, clipBounds.right), getRelativeY(y, clipBounds.bottom));
	}

	public static Rect adjustToScreen(RectF rect, Canvas canvas) {
		int left = getScreenX(rect.left, canvas.getWidth());
		int top = getScreenY(rect.top, canvas.getHeight());
		int right = getScreenX(rect.right, canvas.getWidth());
		int bottom = getScreenY(rect.bottom, canvas.getHeight());
		return new Rect(left, top, right, bottom);
	}

	/**
	 * @param x           virtual screen position
	 * @param screenWidth screen pixel width
	 * @return actual x on screen
	 */
	private static int getScreenX(float x, int screenWidth) {
		// The point starts out centered, so move it left
//		float newX = x + (RELATIVE_WIDTH);
		// Change from relative width to actual screen width
		return (int) (x * screenWidth / RELATIVE_WIDTH);
	}

	/**
	 * @param y            virtual screen position
	 * @param screenHeight screen pixel height
	 * @return actual y on screen
	 */
	private static int getScreenY(float y, int screenHeight) {
		// Positive y goes up instead of down in the relative notation, so invert it.
		// Also the point starts out centered, so move it up
//		float newY = RELATIVE_HEIGHT - y;
		// Change from relative width to actual screen width
		return (int) (y * screenHeight / RELATIVE_HEIGHT);
	}

	/**
	 * @param x           actual x on screen
	 * @param screenWidth screen pixel width
	 * @return virtual screen position
	 */
	private static float getRelativeX(int x, int screenWidth) {
		return x * RELATIVE_WIDTH / screenWidth;
	}

	/**
	 * @param y            actual y on screen
	 * @param screenHeight screen pixel height
	 * @return virtual screen position
	 */
	private static float getRelativeY(int y, int screenHeight) {
		return y * RELATIVE_HEIGHT / screenHeight;
	}

	private static Map<RgbColor, Paint> createPaintMap() {
		HashMap<RgbColor, Paint> map = new HashMap<>();
		map.put(RgbColor.BLACK, createPaint(Color.BLACK));
		map.put(RgbColor.WHITE, createPaint(Color.WHITE));
		map.put(RgbColor.MAGENTA, createPaint(Color.MAGENTA));
		map.put(RgbColor.CYAN, createPaint(Color.CYAN));
		return map;
	}

	private static Paint createPaint(int color) {
		Paint paint = new Paint(color);
		paint.setColor(color);
		paint.setStyle(Paint.Style.FILL);
//		paint.setStrokeWidth(10);
//		paint.setStyle(Paint.Style.STROKE);
		return paint;
	}

}
