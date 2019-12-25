package com.apollowebworks.mostamazingthing.scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.animation.Animation;
import com.apollowebworks.mostamazingthing.animation.AnimationChain;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.animation.TextAnimation;
import com.apollowebworks.mostamazingthing.ui.TextButton;
import com.apollowebworks.mostamazingthing.ui.Turtle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap.SCREEN_HEIGHT;
import static com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap.SCREEN_WIDTH;

public abstract class Scene {

	private static final String TAG = "Scene";

	protected SceneController controller;
	private Bitmap backgroundImage;
	private Canvas tempCanvas;
	private Paint textPaint;
	private List<PointF> moarDots;
	private List<TextButton> buttons;
	private TextAnimation textAnimation;
	private AnimationChain animations;
	private boolean debugTouch;

	public Scene(SceneController controller) {
		this.controller = controller;
		backgroundImage = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);
		drawBlackBackground(new Canvas(backgroundImage));
		textPaint = controller.getTextPaint();
		moarDots = new ArrayList<>();
		buttons = new ArrayList<>();
		debugTouch = false;
	}

	public void init() {
		// Do nothing by default
	}

	protected void addButton(TextButton button) {
		buttons.add(button);
	}

	protected void addButtons(TextButton... button) {
		Arrays.stream(button).forEach(this::addButton);
	}

	protected Bitmap createBlankBackground() {
		Bitmap bitmap = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);
		drawBlackBackground(new Canvas(backgroundImage));
		return bitmap;
	}

	abstract public SceneId getId();

	/**
	 * Make time elapse in the scene
	 *
	 * @param msElapsed The number of milliseconds that have elapsed since the last tick
	 * @return true if anything changed
	 */
	public boolean tick(Long msElapsed) {
		// do nothing by default
		boolean updated = false;
		if (textAnimation != null) {
			updated = textAnimation.tick();
		}
		return updated;
	}

	protected abstract void drawToBuffer(Canvas canvas);

	public void draw(Canvas canvas) {
		Bitmap tempBitmap = backgroundImage.copy(backgroundImage.getConfig(), true);
		tempCanvas = new Canvas(tempBitmap);
		drawToBuffer(tempCanvas);
		if (debugTouch) {
			for (PointF moarDot : moarDots) {
				Paint paint = new Paint();
				paint.setColor(Color.WHITE);
				tempCanvas.drawCircle(moarDot.x, moarDot.y, 1, paint);
			}
		}
		if (textAnimation != null) {
			textAnimation.draw(tempCanvas);
		}
		for (TextButton b : buttons) {
			b.draw(tempCanvas);
		}
		canvas.drawBitmap(tempBitmap, new Rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT), canvas.getClipBounds(), null);
	}

	/**
	 * Handle a touch event from the app
	 *
	 * @param action the action from a {@link MotionEvent}
	 * @param point  where did the event occur
	 * @return true if anything changed and needs to be redrawn
	 */
	public final boolean onTouch(int action, PointF point) {
		debugTouch(action, point);
		if (handleButtons(action, point)) {
			return true;
		}

		switch (action) {
			case MotionEvent.ACTION_DOWN:
				return onDownTouch(point) || debugTouch;
			case MotionEvent.ACTION_UP:
				return onUpTouch(point);
			case MotionEvent.ACTION_MOVE:
				return onMoveTouch(point);
			default:
				return false;
		}
	}

	/**
	 * Override this method to make this do something when a touch is started in the scene
	 */
	protected boolean onDownTouch(PointF point) {
		return false;
	}

	/**
	 * Override this method to make this do something when a touch is released in the scene
	 */
	protected boolean onUpTouch(PointF point) {
		return false;
	}

	/**
	 * Override this method to make this do something when something is dragged
	 */
	protected boolean onMoveTouch(PointF point) {
		return false;
	}

	private boolean handleButtons(int action, PointF point) {
		for (TextButton button : buttons) {
			if (button.onTouch(action, point)) {
				return true;
			}
		}
		return false;
	}

	private void debugTouch(int action, PointF point) {
		if (action == MotionEvent.ACTION_DOWN) {
			Log.d(TAG, "Touched a point on the screen (" + point.x + ", " + point.y + ")");
			debugTouchDown(point);
		}
	}

	protected void addDot(PointF location) {
		moarDots.add(location);
		if (moarDots.size() > 10) {
			moarDots.remove(0);
		}
	}

	protected void debugTouchDown(PointF point) {
		moarDots.add(new PointF(point.x, point.y));
		if (moarDots.size() > 10) {
			moarDots.remove(0);
		}
	}

	protected void drawBackground(Canvas canvas) {
		drawBlackBackground(canvas);
		if (backgroundImage != null) {
			Paint backgroundPaint = new Paint();
			backgroundPaint.setAntiAlias(false);
			canvas.drawBitmap(backgroundImage, new Rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT), canvas.getClipBounds(), backgroundPaint);
		}
	}

	public void buttonEvent(TextButton button) {
	}

	private void drawBlackBackground(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
	}

	protected void setBackgroundImage(Bitmap image) {
		this.backgroundImage = image;
		tempCanvas = new Canvas(image);
	}

	protected void drawText(int y, int x, String text) {
		tempCanvas.drawText(text, x * 8 - 7, y * 8, textPaint);
	}

	protected void turtleDraw(String str) {
		new Turtle(tempCanvas).draw(str);
	}

	protected void addTextAnimation(String text, int x, int y) {
		this.textAnimation = new TextAnimation(this, text, y, x, textPaint);
	}

	protected void addAnimation(Animation animation) {
	}

	public void setDebugTouch(boolean debugTouch) {
		this.debugTouch = debugTouch;
	}
}
