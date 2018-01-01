package com.apollowebworks.mostamazingthing.controller;

import android.content.res.Resources;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneFactory;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.exception.DecrunchException;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;

public class SceneController {

	public static final int FPS = 30;
	private static final SceneId STARTING_SCENE = SceneId.CAREXT;
	private final Paint textPaint;

	private Scene activeScene;
	private Resources resources;
	private View view;
	private Rect clipBounds;

	private Long lastTickTime;
	private final ImageManager imageManager;

	public SceneController(Resources resources, View view) {
		this.resources = resources;
		this.view = view;
		activeScene = null;

		imageManager = new ImageManager();
		try {
			imageManager.readImages(resources);
		} catch (DecrunchException e) {
			e.printStackTrace();
		}

		Typeface typeFace = Typeface.createFromAsset(resources.getAssets(), "fonts/Px437_IBM_BIOS.ttf");
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setTypeface(typeFace);
		textPaint.setTextSize(8);
		textPaint.setAntiAlias(false);

		activateScene(STARTING_SCENE);
	}

	public ImageManager getImageManager() {
		return imageManager;
	}

	public void drawScene(Canvas canvas) {
		if (canvas.getClipBounds().right > 0) {
			clipBounds = canvas.getClipBounds();
		}
		if (activeScene != null) {
			activeScene.draw(canvas);
		}
	}

	public boolean onTouch(MotionEvent motionEvent) {
		if (clipBounds != null && activeScene.onTouch(motionEvent, clipBounds)) {
			redraw();
			return true;
		}
		return false;
	}

	public void activateScene(SceneId sceneId) {
		lastTickTime = System.currentTimeMillis();
		activeScene = SceneFactory.create(sceneId, this);
		redraw();
	}

	private void redraw() {
		view.invalidate();
	}

	public void tick() {
		Long currentTime = System.currentTimeMillis();
		Long elapsed = currentTime - lastTickTime;
		lastTickTime = currentTime;
		if (activeScene != null) {
			if (activeScene.tick(elapsed)) {
				redraw();
			}
		}
	}

	public Paint getTextPaint() {
		return textPaint;
	}

	public Resources getResources() {
		return resources;
	}
}
