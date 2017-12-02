package com.apollowebworks.mostamazingthing.scene;

import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.scene.carext.CarExtScene;
import com.apollowebworks.mostamazingthing.scene.elevator.ElevatorScene;
import com.apollowebworks.mostamazingthing.scene.misc.MarqueeScene;
import com.apollowebworks.mostamazingthing.scene.smoke.SmokeScene;
import com.apollowebworks.mostamazingthing.scene.title.TitleScene;

public class SceneFactory {

	public static Scene create(SceneId sceneId, InSearchController controller) {
		switch (sceneId) {
			case TITLE:
				return new TitleScene(controller);
			case ELEVATOR:
				return new ElevatorScene(controller);
			case SMOKE:
				return new SmokeScene(controller);
			case MARQUEE:
				return new MarqueeScene(controller);
			case CAREXT:
				return new CarExtScene(controller);
			default:
				throw (new UnsupportedOperationException());
		}
	}
}
