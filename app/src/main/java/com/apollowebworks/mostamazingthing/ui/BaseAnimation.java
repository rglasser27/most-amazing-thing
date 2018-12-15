package com.apollowebworks.mostamazingthing.ui;

import com.apollowebworks.mostamazingthing.scene.Scene;

public abstract class BaseAnimation {
	protected Scene scene;

	BaseAnimation(Scene scene) {
		this.scene = scene;
	}
}
