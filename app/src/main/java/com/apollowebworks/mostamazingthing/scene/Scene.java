package com.apollowebworks.mostamazingthing.scene;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.graphics.manager.ImageManager;
import com.apollowebworks.mostamazingthing.graphics.model.FullScreenImage;

public abstract class Scene {

	protected InSearchController inSearchController;
	private Paint backgroundPaint;
	protected ImageManager imageManager;
	private FullScreenImage backgroundImage;

	public Scene(InSearchController inSearchController) {
		this.inSearchController = inSearchController;
		backgroundPaint = new Paint();
		backgroundPaint.setARGB(255, 0, 0, 0);
	}

	/**
	 * Make time elapse in the scene
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
	 * @param motionEvent the event
	 * @param clipBounds last known screen size
	 * @return true if anything changed and needs to be redrawn
	 */
	public boolean onTouch(MotionEvent motionEvent, Rect clipBounds) {
		// do nothing by default
		return false;
	}


	protected void drawBackground(Canvas canvas) {
		drawBlackBackground(canvas);
		if (backgroundImage != null) {
			backgroundImage.draw(canvas);
		}
	}

	private void drawBlackBackground(Canvas canvas) {
		// Fill with black
		canvas.drawRect(canvas.getClipBounds(), backgroundPaint);
	}

	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	public void setBackgroundImage(FullScreenImage image) {
		this.backgroundImage = image;
	}
}
