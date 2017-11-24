package com.apollowebworks.mostamazingthing.controller;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneFactory;
import com.apollowebworks.mostamazingthing.scene.title.TitleScene;

import java.util.HashSet;
import java.util.Set;

public class InSearchController {

	public static final int FPS = 30;

	private Scene activeScene;
	private Resources resources;
	private View view;
	private Rect clipBounds;

	private Set<Scene> scenes;
	private Long msSinceLastTick;

	public InSearchController(Resources resources, View view) {
		this.resources = resources;
		this.view = view;
		activeScene = null;
		scenes = new HashSet<>();
		activateScene(TitleScene.class);
		msSinceLastTick = System.currentTimeMillis();
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

	public <S extends Scene> Scene activateScene(Class<S> sceneClass) {
		S Scene;
		if (activeScene == null || !activeScene.getClass().isAssignableFrom(sceneClass)) {
			boolean found = false;
			for (Scene scene : scenes) {
				if (scene.getClass().isAssignableFrom(sceneClass)) {
					activeScene = scene;
					found = true;
					break;
				}
			}
			if (!found) {
				activeScene = SceneFactory.create(sceneClass, this);
			}
			redraw();
		}
		return activeScene;
	}

	public void redraw() {
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
