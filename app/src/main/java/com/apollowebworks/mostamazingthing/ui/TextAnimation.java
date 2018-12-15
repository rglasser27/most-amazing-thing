package com.apollowebworks.mostamazingthing.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.apollowebworks.mostamazingthing.scene.Scene;

public class TextAnimation extends BaseAnimation {
	private static final long TIME_BETWEEN_LETTERS = 100;

	private String text;
	private int x;
	private int y;
	private int lettersDisplayed = 0;
	private long lastTimeDisplayed;
	private Paint paint;

	public TextAnimation(Scene scene, String text, int y, int x, Paint paint) {
		super(scene);
		this.text = text;
		this.y = y;
		this.x = x;
		lettersDisplayed = 0;

		this.paint = paint;

		lastTimeDisplayed = System.currentTimeMillis();
	}

	public void draw(Canvas canvas) {
		String substr = text.substring(0, lettersDisplayed);
		String[] strings = substr.split(";");
		for (int i = 0; i < strings.length; i ++) {
			canvas.drawText(strings[i], x * 8 - 7, (y+i) * 8, paint);
		}
	}

	public boolean tick() {
		long now = System.currentTimeMillis();
		if (lettersDisplayed < text.length()) {
			int newLetters = (int) ((now - lastTimeDisplayed) / TIME_BETWEEN_LETTERS) + 1;
			if (newLetters > lettersDisplayed) {
				lettersDisplayed = newLetters > text.length() ? text.length() : newLetters;
				return true;
			}
		}
		return false;
	}
}
