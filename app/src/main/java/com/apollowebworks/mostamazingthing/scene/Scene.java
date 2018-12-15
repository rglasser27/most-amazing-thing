package com.apollowebworks.mostamazingthing.scene;

import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.ui.TextAnimation;
import com.apollowebworks.mostamazingthing.ui.TextButton;
import com.apollowebworks.mostamazingthing.ui.Turtle;

import java.util.ArrayList;
import java.util.List;

import static com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap.SCREEN_HEIGHT;
import static com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap.SCREEN_WIDTH;
import static com.apollowebworks.mostamazingthing.util.DrawUtil.getVirtualPoint;

public abstract class Scene {

	private static final boolean DEBUG_TOUCH = true;
	private static final String TAG = "Scene";

	protected SceneController sceneController;
	private Bitmap backgroundImage;
	private Canvas tempCanvas;
	private Paint textPaint;
	private List<PointF> moarDots;
	private List<TextButton> buttons;
	private TextAnimation textAnimation;

	public Scene(SceneController sceneController) {
		this.sceneController = sceneController;
		backgroundImage = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);
		drawBlackBackground(new Canvas(backgroundImage));
		textPaint = sceneController.getTextPaint();
		moarDots = new ArrayList<>();
		buttons = new ArrayList<>();
	}

	public void init() {
		// Do nothing by default
	}

	protected void addButton(TextButton button) {
		buttons.add(button);
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
		if (DEBUG_TOUCH) {
			for (PointF moarDot : moarDots) {
				Paint paint = new Paint();
				paint.setColor(Color.WHITE);
				tempCanvas.drawCircle(moarDot.x, moarDot.y, 1, paint);
			}
		}
		if (textAnimation != null) {
			textAnimation.draw(tempCanvas);
		}
		for (TextButton b: buttons) {
			b.draw(tempCanvas);
		}
		canvas.drawBitmap(tempBitmap, new Rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT), canvas.getClipBounds(), null);
	}

	/**
	 * Handle a touch event from the app
	 *
	 * @param motionEvent the event
	 * @param clipBounds  last known screen size
	 * @return true if anything changed and needs to be redrawn
	 */
	public boolean onTouch(MotionEvent motionEvent, Rect clipBounds) {
//		for (TextButton button : buttons) {
		for (TextButton button : buttons) {
			if (button.onTouch(motionEvent, clipBounds)) {
				return true;
			}
		}
		return false;
	}

	protected void addDot(MotionEvent event, Rect clipBounds) {
		if (DEBUG_TOUCH && event.getAction() == MotionEvent.ACTION_DOWN) {
			PointF virtualPoint = getVirtualPoint(event.getX(), event.getY(), clipBounds);
			Log.d(TAG, "Touched a point on the screen (" + virtualPoint.x + ", " + virtualPoint.y + ")");

			moarDots.add(new PointF(virtualPoint.x, virtualPoint.y));
			if (moarDots.size() > 10) {
				moarDots.remove(0);
			}
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

}
