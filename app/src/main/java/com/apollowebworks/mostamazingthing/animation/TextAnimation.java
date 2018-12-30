package com.apollowebworks.mostamazingthing.animation;

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
	private int lettersDisplayed;
	private Paint paint;

	public TextAnimation(Scene scene, String text, int y, int x, Paint paint) {
		super(scene);
		this.text = text.replaceAll(";", "    ;");
		this.y = y;
		this.x = x;
		lettersDisplayed = 0;
		this.paint = paint;
	}

	@Override
	public void draw(Canvas canvas) {
		String substr = text.substring(0, lettersDisplayed);
		String[] strings = substr.split(";");
		canvas.drawText(strings[strings.length - 1], x * 8 - 7, y * 8, paint);
	}

	private int lettersDisplayed() {
		long elapsed = getElapsedTimeMs();
		int letters = (int) (elapsed / TIME_BETWEEN_LETTERS) + 1;
		return letters > text.length() ? text.length() : letters;
	}

	public boolean tick() {
		int newLetters = lettersDisplayed();
		if (newLetters <= lettersDisplayed) {
			return false;
		}
		if (newLetters == text.length() || newLetters % 10 == 0) {
			Log.d(TAG, "Text animation updated; displaying " + newLetters + " letters of " + text.length());
		}
		lettersDisplayed = newLetters;
		return true;
	}
}
