package com.apollowebworks.mostamazingthing.ui.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class TextButton {
	private static final int MARGIN = 4;
	private static final String TAG = "TextButton";

	private final int id;
	private String text;
	/**
	 * This is the x/y of the button in text terms (80x25)
	 */
	private Point position;
	private Rect border;
	private Paint textPaint;
	private Paint borderPaint;
	private Paint fillPaint;

	private Scene scene;

	private boolean pressing;

	private List<ButtonHandler> handlers;

	public TextButton(int id, Scene scene, int y, int x, String text, Paint textPaint) {
		this.id = id;
		pressing = false;

		this.scene = scene;
		this.text = text;
		this.position = new Point(x, y);
		this.border = new Rect((x - 1) * 8 - MARGIN,
							   (y - 1) * 8 - MARGIN,
							   (x + text.length() - 1) * 8 + MARGIN,
							   y * 8 + MARGIN);
		this.textPaint = new Paint(textPaint);
		this.textPaint.setColor(Color.CYAN);

		borderPaint = new Paint();
		borderPaint.setColor(Color.WHITE);
		borderPaint.setStyle(Paint.Style.STROKE);

		fillPaint = new Paint();
		fillPaint.setColor(Color.CYAN);
		fillPaint.setStyle(Paint.Style.FILL);

		handlers = new ArrayList<>();
	}

	public TextButton(Scene scene, int y, int x, String text, Paint textPaint) {
		this(0, scene, y, x, text, textPaint);
	}

	public int getId() {
		return id;
	}

	private boolean checkPressing(PointF point) {
		if (border.contains((int) point.x, (int) point.y)) {
			pressing = true;
			return true;
		}
		return false;
	}

	public void draw(Canvas canvas) {
		if (pressing) {
			textPaint.setColor(Color.BLACK);
			fillPaint.setColor(Color.CYAN);
		} else {
			textPaint.setColor(Color.WHITE);
			fillPaint.setColor(Color.BLACK);
		}
		canvas.drawRect(border, fillPaint);
		canvas.drawText(text, position.x * 8 - 7, position.y * 8, textPaint);
		canvas.drawRect(border, borderPaint);
	}

	public boolean onTouch(int action, PointF virtualPoint) {
		switch (action) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				pressing = false;
				return checkPressing(virtualPoint);
			case MotionEvent.ACTION_UP:
				if (checkPressing(virtualPoint)) {
					Log.d(TAG, "Clicked button \"" + text + "\"");
					scene.buttonEvent(this);
					pressing = false;
					return true;
				}
		}
		return false;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void addHandler(ButtonHandler handler) {
		handlers.add(handler);
	}

	public boolean handle(int action, PointF point) {
		if (this.onTouch(action, point)) {
			for (ButtonHandler handler : handlers) {
				handler.handleButton(action, point);
			}
			return true;
		}
		return false;
	}
}
