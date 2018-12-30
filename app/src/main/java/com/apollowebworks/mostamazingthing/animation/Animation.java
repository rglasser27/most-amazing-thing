package com.apollowebworks.mostamazingthing.animation;

import android.graphics.Canvas;
import com.apollowebworks.mostamazingthing.scene.Scene;

interface Animation {
	Scene getScene();

	/**
	 * Draw the current animation frame
	 */
	void draw(Canvas canvas);

	/**
	 * Perform between frame activity.
	 * @return true if anything has changed since the last time draw was invoked
	 */
	boolean tick();

	/**
	 * @return true if the animation has finished
	 */
	boolean isComplete();
}
