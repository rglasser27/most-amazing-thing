package com.apollowebworks.mostamazingthing.util;

import android.graphics.*;
import com.apollowebworks.mostamazingthing.ui.model.RgbColor;

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

	public static PointF getVirtualPoint(float x, float y, Rect clipBounds) {
		return new PointF(getRelativeX((int) x, clipBounds.right), getRelativeY((int) y, clipBounds.bottom));
	}

	public static Rect adjustToScreen(RectF rect, Canvas canvas) {
		int left = getScreenX(rect.left, canvas.getWidth());
		int top = getScreenY(rect.top, canvas.getHeight());
		int right = getScreenX(rect.right, canvas.getWidth());
		int bottom = getScreenY(rect.bottom, canvas.getHeight());
		return new Rect(left, top, right, bottom);
	}

	public static float findDistanceToTarget(PointF position, PointF targetPosition) {
		float xDist = targetPosition.x - position.x;
		float yDist = targetPosition.y - position.y;
		return (float) Math.sqrt(xDist * xDist + yDist * yDist);
	}

	public static float magnitude(PointF p) {
		return (float) Math.sqrt(p.x * p.x + p.y * p.y);
	}

	public static PointF normalize(PointF p) {
		return multiply(p, 1 / magnitude(p));
	}

	public static PointF multiply(PointF vector, float speed) {
		return new PointF(vector.x * speed, vector.y * speed);
	}

	public static PointF add(PointF p1, PointF p2) {
		return new PointF(p1.x + p2.x, p1.y + p2.y);
	}

	public static PointF subtract(PointF p1, PointF p2) {
		return new PointF(p1.x - p2.x, p1.y - p2.y);
	}

	/**
	 * @param x           virtual screen position
	 * @param screenWidth screen pixel width
	 * @return actual x on screen
	 */
	private static int getScreenX(float x, int screenWidth) {
		// The point starts out centered, so move it left
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
		paint.setAntiAlias(false);
		return paint;
	}
}
