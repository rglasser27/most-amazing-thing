package com.apollowebworks.mostamazingthing.animation;

import android.graphics.Canvas;
import com.apollowebworks.mostamazingthing.scene.Scene;

public class PauseAnimation extends BaseAnimation {

	private long durationMs;

	PauseAnimation(Scene scene, long durationMs, Animation next) {
		super(scene);
		this.durationMs = durationMs;
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public boolean isComplete() {
		return getElapsedTimeMs() >= durationMs;
	}
}
