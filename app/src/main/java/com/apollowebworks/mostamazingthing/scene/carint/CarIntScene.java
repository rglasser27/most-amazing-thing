package com.apollowebworks.mostamazingthing.scene.carint;

import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.getVirtualPoint;

public class CarIntScene extends Scene {

	private static final String TAG = CarIntScene.class.getName();
	private final RectF doorArea;

	public CarIntScene(SceneController sceneController) {
		super(sceneController);
		this.setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.CARINT));
		doorArea = new RectF(252, 119, 270, 155);

	}

	@Override
	public SceneId getId() {
		return SceneId.CAREXT;
	}

	@Override
	public void drawToBuffer(Canvas canvas) {
	}

	@Override
	public boolean onTouch(MotionEvent event, Rect clipBounds) {
		addDot(event, clipBounds);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				PointF virtualPoint = getVirtualPoint(event.getX(), event.getY(), clipBounds);
				if (doorArea.contains(virtualPoint.x, virtualPoint.y)) {
					sceneController.activateScene(SceneId.CAREXT);
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}

	@Override
	public boolean tick(Long msElapsed) {
		return false;
	}
}
