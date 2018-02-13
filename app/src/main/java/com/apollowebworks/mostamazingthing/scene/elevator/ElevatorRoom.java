package com.apollowebworks.mostamazingthing.scene.elevator;

import com.apollowebworks.mostamazingthing.scene.SceneId;

public class ElevatorRoom {
	private int top;
	private int bottom;
	private SceneId scene;

	ElevatorRoom(int top, int bottom, SceneId scene) {
		this.top = top;
		this.bottom = bottom;
		this.scene = scene;
	}

	public int getTop() {
		return top;
	}

	public int getBottom() {
		return bottom;
	}

	public SceneId getScene() {
		return scene;
	}
}
