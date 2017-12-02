package com.apollowebworks.mostamazingthing.scene;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.graphics.Turtle;
import com.apollowebworks.mostamazingthing.graphics.manager.ImageManager;

import static com.apollowebworks.mostamazingthing.graphics.model.FullScreenImage.SCREEN_HEIGHT;
import static com.apollowebworks.mostamazingthing.graphics.model.FullScreenImage.SCREEN_WIDTH;

public abstract class Scene {

	protected InSearchController inSearchController;
	protected ImageManager imageManager;
	private Bitmap backgroundImage;
	protected Canvas tempCanvas;
	private Bitmap tempBitmap;
	private Paint textPaint;

	public Scene(InSearchController inSearchController) {
		this.inSearchController = inSearchController;
		backgroundImage = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);
		drawBlackBackground(new Canvas(backgroundImage));
		textPaint = inSearchController.getTextPaint();
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

	public abstract void draw(Canvas canvas, Resources resources, Context context);

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
			canvas.drawBitmap(backgroundImage, new Rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT), canvas.getClipBounds(), null);
		}
	}

	protected void newFrame() {
		tempBitmap = backgroundImage.copy(backgroundImage.getConfig(), true);
		tempCanvas = new Canvas(tempBitmap);
	}

	private void drawBlackBackground(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
	}

	protected void setBackgroundImage(Bitmap image) {
		this.backgroundImage = image;
		tempCanvas = new Canvas(image);
	}

	protected void drawFinalFrame(Canvas canvas) {
		canvas.drawBitmap(tempBitmap, new Rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT), canvas.getClipBounds(), null);
	}

	protected void drawText(int y, int x, String text) {
		tempCanvas.drawText(text, x * 8 - 5, y * 8 - 1, textPaint);
	}

	protected void turtleDraw(String str) {
		new Turtle(tempCanvas).draw(str);
	}
}
