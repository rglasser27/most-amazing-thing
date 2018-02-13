package com.apollowebworks.mostamazingthing.scene.rooms;

import android.graphics.Canvas;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;

public class StoreScene extends RoomScene {
	public StoreScene(SceneController sceneController) {
		super(sceneController);
		this.setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.STORE));
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	@Override
	public SceneId getId() {
		return SceneId.STORE;
	}

	@Override
	protected void drawToBuffer(Canvas canvas) {

	}
}
