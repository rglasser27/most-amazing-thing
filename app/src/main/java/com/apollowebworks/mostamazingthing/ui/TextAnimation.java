package com.apollowebworks.mostamazingthing.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import com.apollowebworks.mostamazingthing.scene.Scene;

public class TextAnimation extends BaseAnimation {
	private static final long TIME_BETWEEN_LETTERS = 100;
	private static final String TAG = "base animation";

	private String text;
	private int x;
	private int y;
	private int lettersDisplayed = 0;
	private long lastTimeDisplayed;
	private Paint paint;

	public TextAnimation(Scene scene, String text, int y, int x, Paint paint) {
		super(scene);
		this.text = text.replaceAll(";", "    ;");
		this.y = y;
		this.x = x;
		lettersDisplayed = 0;

		this.paint = paint;

		lastTimeDisplayed = System.currentTimeMillis();
	}

	public void draw(Canvas canvas) {
		String substr = text.substring(0, lettersDisplayed);
//		for (int i = 0; i < lettersDisplayed; i ++) {
//			if lettersDisplayed
//		}
		String[] strings = substr.split(";");
		canvas.drawText(strings[strings.length - 1], x * 8 - 7, y * 8, paint);
//		for (int i = 0; i < strings.length; i ++) {
//			canvas.drawText(strings[i], x * 8 - 7, (y+i) * 8, paint);
//		}
	}

	public boolean tick() {
		long now = System.currentTimeMillis();
		if (lettersDisplayed < text.length()) {
			int newLetters = (int) ((now - lastTimeDisplayed) / TIME_BETWEEN_LETTERS) + 1;
			if (newLetters > lettersDisplayed) {
				if (newLetters == text.length() || newLetters % 10 == 0) {
					Log.d(TAG, "Text animation updated; displaying " + newLetters + " letters of " + text.length());
				}
				lettersDisplayed = newLetters > text.length() ? text.length() : newLetters;
				return true;
			}
		}
		return false;
	}
}
