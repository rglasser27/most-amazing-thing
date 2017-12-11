package com.apollowebworks.mostamazingthing.scene;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.world.model.map.Landmark;
import com.apollowebworks.mostamazingthing.world.model.map.LandmarkType;

public class MapScene extends Scene {

	public MapScene(SceneController sceneController) {
		super(sceneController);
	}

	@Override
	public SceneId getId() {
		return SceneId.MAP;
	}

	@Override
	public void drawToBuffer(Canvas canvas) {
		new Landmark(new Point(100, 100), new Point(100, 100), LandmarkType.TREE).draw(canvas);
	}
}
