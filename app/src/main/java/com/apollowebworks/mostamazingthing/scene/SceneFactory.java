package com.apollowebworks.mostamazingthing.scene;

import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.scene.elevator.ElevatorScene;
import com.apollowebworks.mostamazingthing.scene.title.TitleScene;

public class SceneFactory {
	public static <S extends Scene> Scene create(Class<S> clazz, InSearchController inSearchController) {
		if (clazz.isAssignableFrom(TitleScene.class)) {
			return new TitleScene(inSearchController);
		}
		if (clazz.isAssignableFrom(ElevatorScene.class)) {
			return new ElevatorScene(inSearchController);
		}
		throw (new UnsupportedOperationException());
	}
}
