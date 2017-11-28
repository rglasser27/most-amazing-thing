package com.apollowebworks.mostamazingthing.scene.smoke;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;

public class SmokeScene extends Scene {
	public SmokeScene(InSearchController inSearchController) {
		super(inSearchController);
	}

	@Override
	public void draw(Canvas canvas, Resources resources, Context context) {

	}

	@Override
	public SceneId getId() {
		return SceneId.SMOKE;
	}
}
