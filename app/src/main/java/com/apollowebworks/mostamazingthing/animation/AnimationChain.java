package com.apollowebworks.mostamazingthing.animation;

import android.graphics.Canvas;
import com.apollowebworks.mostamazingthing.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimationChain implements Animation {
	private List<Animation> chain;

	public AnimationChain() {
		chain = new ArrayList<>();
	}

	@Override
	public Scene getScene() {
		return null;
	}

	@Override
	public void draw(Canvas canvas) {
		currentAnimation().ifPresent(a -> a.draw(canvas));
	}

	@Override
	public boolean tick() {
		return currentAnimation().map(Animation::tick).orElse(false);
	}

	@Override
	public boolean isComplete() {
		return !currentAnimation().isPresent();
	}

	public void add(Animation a) {
		chain.add(a);
	}

	private Optional<Animation> currentAnimation() {
		return chain.stream()
				.filter(a -> !a.isComplete())
				.findFirst();
	}
}
