package com.apollowebworks.mostamazingthing.scene;

import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.bitmaptest.BitmapTestScene;
import com.apollowebworks.mostamazingthing.scene.carext.CarExtScene;
import com.apollowebworks.mostamazingthing.scene.carint.CarIntScene;
import com.apollowebworks.mostamazingthing.scene.elevator.ElevatorScene;
import com.apollowebworks.mostamazingthing.scene.intro.IntroScene;
import com.apollowebworks.mostamazingthing.scene.misc.MarqueeScene;
import com.apollowebworks.mostamazingthing.scene.rooms.AuctionScene;
import com.apollowebworks.mostamazingthing.scene.rooms.SmokeScene;
import com.apollowebworks.mostamazingthing.scene.rooms.StoreScene;
import com.apollowebworks.mostamazingthing.scene.title.TitleScene;

public class SceneFactory {

	public static Scene create(SceneId sceneId, SceneController controller) {
		switch (sceneId) {
			case TITLE:
				return new TitleScene(controller);
			case INTRO:
				return new IntroScene(controller);
			case ELEVATOR:
				return new ElevatorScene(controller);
			case SMOKE:
				return new SmokeScene(controller);
			case STORE:
				return new StoreScene(controller);
			case AUCTION:
				return new AuctionScene(controller);
			case MARQUEE:
				return new MarqueeScene(controller);
			case CAREXT:
				return new CarExtScene(controller);
			case CARINT:
				return new CarIntScene(controller);
			case MAP:
				return new MapScene(controller);
			case BITMAP_TEST:
				return new BitmapTestScene(controller);
			default:
				throw (new UnsupportedOperationException());
		}
	}
}
