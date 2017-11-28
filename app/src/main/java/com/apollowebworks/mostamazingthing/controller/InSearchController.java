package com.apollowebworks.mostamazingthing.controller;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.apollowebworks.mostamazingthing.graphics.exception.DecrunchException;
import com.apollowebworks.mostamazingthing.graphics.manager.ImageManager;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneFactory;
import com.apollowebworks.mostamazingthing.scene.SceneId;

public class InSearchController {

	public static final int FPS = 30;

	private Scene activeScene;
	private Resources resources;
	private View view;
	private Rect clipBounds;

	private Long msSinceLastTick;
	private final ImageManager imageManager;

	public InSearchController(Resources resources, View view) {
		this.resources = resources;
		this.view = view;
		activeScene = null;

		imageManager = new ImageManager();
		try {
			imageManager.readImages(resources);
		} catch (DecrunchException e) {
			e.printStackTrace();
		}

		activateScene(SceneId.TITLE);
	}

	public ImageManager getImageManager() {
		return imageManager;
	}

	public void drawScene(Canvas canvas) {
		if (canvas.getClipBounds().right > 0) {
			clipBounds = canvas.getClipBounds();
		}
		if (activeScene != null) {
			activeScene.draw(canvas, resources, view.getContext());
		}
	}

	public boolean onTouch(MotionEvent motionEvent) {
		return clipBounds != null && activeScene.onTouch(motionEvent, clipBounds);
	}

	public void activateScene(SceneId sceneId) {
		msSinceLastTick = System.currentTimeMillis();
		activeScene = SceneFactory.create(sceneId, this);
		redraw();
	}

	private void redraw() {
		view.invalidate();
	}

	public void tick() {
		if (activeScene != null) {
			if (activeScene.tick(msSinceLastTick)) {
				redraw();
			}
		}
	}
}
