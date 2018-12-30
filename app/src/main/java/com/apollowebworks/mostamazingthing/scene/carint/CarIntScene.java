package com.apollowebworks.mostamazingthing.scene.carint;

import android.graphics.*;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;
import com.apollowebworks.mostamazingthing.util.DrawUtil;

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
	public boolean onDownTouch(PointF point) {
		if (doorArea.contains(point.x, point.y)) {
			controller.activateScene(SceneId.CAREXT);
		}
		return true;
	}

	@Override
	public boolean tick(Long msElapsed) {
		return false;
	}
}
