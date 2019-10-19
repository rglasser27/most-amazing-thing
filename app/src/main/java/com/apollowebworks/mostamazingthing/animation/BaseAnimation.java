package com.apollowebworks.mostamazingthing.animation;

import android.graphics.Canvas;
import com.apollowebworks.mostamazingthing.scene.Scene;

import java.time.Clock;
import java.time.Instant;

public abstract class BaseAnimation implements Animation {
	protected Scene scene;
	private Clock clock;
	private final Instant startTime;

	BaseAnimation(Scene scene) {
		this.scene = scene;
		clock = Clock.systemDefaultZone();
		startTime = clock.instant();
	}

	@Override
	public Scene getScene() {
		return scene;
	}

	@Override
	public void draw(Canvas canvas) {
	}

	long getElapsedTimeMs() {
		return clock.instant().toEpochMilli() - startTime.toEpochMilli();
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public boolean isComplete() {
		return false;
	}
}
