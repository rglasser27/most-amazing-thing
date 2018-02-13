package com.apollowebworks.mostamazingthing.scene.rooms;

import android.graphics.Paint;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.TextButton;

abstract class RoomScene extends Scene {

	RoomScene(SceneController sceneController) {
		super(sceneController);
		TextButton leaveButton = new TextButton(this, 3, 4, "Leave", new Paint());
		addButton(leaveButton);
	}

	@Override
	public void buttonEvent(TextButton button) {
		sceneController.activateScene(SceneId.ELEVATOR);
	}
}
