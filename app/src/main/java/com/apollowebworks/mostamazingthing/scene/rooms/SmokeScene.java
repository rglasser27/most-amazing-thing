package com.apollowebworks.mostamazingthing.scene.rooms;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.TextButton;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;

import java.util.Random;

public class SmokeScene extends RoomScene {
	private static final String TAG = "SmokeScene";
	private static final String[] SMOKE_LINES = {
			"I have a;little somethin';for you ...",
			"You'll need;plenty of chips;at the store.",
			"You're gonna;need more chips;to buy things;for the B-liner."
	};
	private final TextButton wakeButton;
	private boolean awake;
	private Random random;

	public SmokeScene(SceneController sceneController) {
		super(sceneController);
		Log.d(TAG, "Entering smoke's room");
		wakeButton = new TextButton(this, 18, 4, "Hey, Smoke!", new Paint());
		addButton(wakeButton);
		this.setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.SMOKE));
		awake = false;
		random = new Random();
	}

	@Override
	public void drawToBuffer(Canvas canvas) {
		if (awake) {
			// Draw Smoke's eyes?
		}
	}

	@Override
	public SceneId getId() {
		return SceneId.SMOKE;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	@Override
	public boolean onTouch(MotionEvent motionEvent, Rect clipBounds) {
		return super.onTouch(motionEvent, clipBounds);
	}

	@Override
	public void buttonEvent(TextButton button) {
		if (button == wakeButton) {
			wakeSmoke();
		} else {
			super.buttonEvent(button);
		}
	}

	private void wakeSmoke() {
		awake = true;
		int line = random.nextInt(SMOKE_LINES.length);
		addTextAnimation(SMOKE_LINES[line], 2, 11);
	}
}
