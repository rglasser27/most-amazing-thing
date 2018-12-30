package com.apollowebworks.mostamazingthing.controller;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneFactory;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.exception.DecrunchException;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;
import com.apollowebworks.mostamazingthing.util.DrawUtil;
import com.apollowebworks.mostamazingthing.world.state.GameState;

public class SceneController {

	public static final int FPS = 30;
//	private static final SceneId STARTING_SCENE = SceneId.SMOKE;
	private static final SceneId STARTING_SCENE = SceneId.AUCTION;
	private final Paint textPaint;

	private Scene activeScene;
	private Resources resources;
	private View view;
	private Rect clipBounds;

	private Long lastTickTime;
	private final ImageManager imageManager;

	private GameState gameState;

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

		gameState = new GameState();
		gameState.setItem(1);
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
		if (clipBounds != null) {
			PointF virtualPoint = DrawUtil.getVirtualPoint(motionEvent.getX(), motionEvent.getY(), clipBounds);
			if (activeScene.onTouch(motionEvent.getAction(), virtualPoint)) {
				redraw();
				return true;
			}
		}
		return false;
	}

	public void activateScene(SceneId sceneId) {
		lastTickTime = System.currentTimeMillis();
		activeScene = SceneFactory.create(sceneId, this);
		activeScene.init();
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

	public String getString(int id) {
		return resources.getString(id);
	}

	public String[] getStrings(int id) {
		return resources.getStringArray(id);
	}

	public GameState getGameState() {
		return gameState;
	}

	public void prepareJetpackGuy(int x, int y, int facing) {
		gameState.setxCoord(x);
		gameState.setyCoord(y);
	}
}
