package com.apollowebworks.mostamazingthing.scene;

import android.graphics.*;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.ui.Turtle;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;

import static com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap.SCREEN_HEIGHT;
import static com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap.SCREEN_WIDTH;

public abstract class Scene {

	protected SceneController sceneController;
	protected ImageManager imageManager;
	private Bitmap backgroundImage;
	private Canvas tempCanvas;
	private Bitmap tempBitmap;
	private Paint textPaint;

	public Scene(SceneController sceneController) {
		this.sceneController = sceneController;
		backgroundImage = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);
		drawBlackBackground(new Canvas(backgroundImage));
		textPaint = sceneController.getTextPaint();
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
		return false;
	}

	protected abstract void drawToBuffer(Canvas canvas);

	public void draw(Canvas canvas) {
		tempBitmap = backgroundImage.copy(backgroundImage.getConfig(), true);
		tempCanvas = new Canvas(tempBitmap);
		drawToBuffer(tempCanvas);
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
		// do nothing by default
		return false;
	}

	protected void drawBackground(Canvas canvas) {
		drawBlackBackground(canvas);
		if (backgroundImage != null) {
			Paint backgroundPaint = new Paint();
			backgroundPaint.setAntiAlias(false);
			canvas.drawBitmap(backgroundImage, new Rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT), canvas.getClipBounds(), backgroundPaint);
		}
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
}
