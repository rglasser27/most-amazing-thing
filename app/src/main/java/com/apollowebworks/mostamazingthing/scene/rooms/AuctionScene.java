package com.apollowebworks.mostamazingthing.scene.rooms;

import android.graphics.Canvas;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;

public class AuctionScene extends RoomScene {
	public AuctionScene(SceneController sceneController) {
		super(sceneController);
		this.setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.AUCTION));
	}

	@Override
	public void drawToBuffer(Canvas canvas) {

	}

	@Override
	public SceneId getId() {
		return SceneId.AUCTION;
	}
}
