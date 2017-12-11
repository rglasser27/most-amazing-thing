package com.apollowebworks.mostamazingthing.scene.smoke;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;

public class SmokeScene extends Scene {
	public SmokeScene(SceneController sceneController) {
		super(sceneController);
	}

	@Override
	public void drawToBuffer(Canvas canvas) {

	}

	@Override
	public SceneId getId() {
		return SceneId.SMOKE;
	}
}
