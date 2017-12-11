package com.apollowebworks.mostamazingthing.ui;

import android.graphics.*;

public class TextButton {
	private static final int MARGIN = 4;

	private String text;
	/**
	 * This is the x/y of the button in text terms (80x25)
	 */
	private Point position;
	private Rect border;
	private Paint textPaint;
	private Paint borderPaint;
	private Paint fillPaint;

	private boolean pressing;

	public TextButton(int y, int x, String text, Paint textPaint) {
		pressing = false;

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
	}

	public boolean checkPressing(int x, int y) {
		if (border.contains(x, y)) {
			pressing = true;
			return true;
		}
		return false;
	}

	public void setPressing(boolean pressing) {
		this.pressing = pressing;
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

}
