package com.apollowebworks.mostamazingthing.scene.rooms;

import android.graphics.Canvas;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;

public class SmokeScene extends RoomScene {
	private static final String TAG = "SmokeScene";

	public SmokeScene(SceneController sceneController) {
		super(sceneController);
		this.setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.SMOKE));
	}

	@Override
	public void drawToBuffer(Canvas canvas) {

	}

	@Override
	public SceneId getId() {
		return SceneId.SMOKE;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
}
