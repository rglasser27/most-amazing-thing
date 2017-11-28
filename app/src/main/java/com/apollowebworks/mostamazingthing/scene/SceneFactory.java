package com.apollowebworks.mostamazingthing.scene;

import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.scene.elevator.ElevatorScene;
import com.apollowebworks.mostamazingthing.scene.smoke.SmokeScene;
import com.apollowebworks.mostamazingthing.scene.title.TitleScene;

public class SceneFactory {

	public static Scene create(SceneId sceneId, InSearchController inSearchController) {
		switch (sceneId) {
			case TITLE:
				return new TitleScene(inSearchController);
			case ELEVATOR:
				return new ElevatorScene(inSearchController);
			case SMOKE:
				return new SmokeScene(inSearchController);
			default:
				throw (new UnsupportedOperationException());
		}
	}
}
