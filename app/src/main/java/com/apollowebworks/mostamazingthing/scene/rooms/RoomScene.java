package com.apollowebworks.mostamazingthing.scene.rooms;

import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.components.TextButton;

abstract class RoomScene extends Scene {

	RoomScene(SceneController sceneController, int leaveX, int leaveY) {
		super(sceneController);
		TextButton leaveButton = new TextButton(this, leaveX, leaveY, "Leave", sceneController.getTextPaint());
		addButton(leaveButton);
	}

	RoomScene(SceneController sceneController) {
		this(sceneController, 3, 4);
	}

	@Override
	public void buttonEvent(TextButton button) {
		controller.activateScene(SceneId.ELEVATOR);
	}
}
