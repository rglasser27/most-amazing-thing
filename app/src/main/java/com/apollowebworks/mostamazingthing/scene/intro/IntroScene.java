package com.apollowebworks.mostamazingthing.scene.intro;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.TextButton;

public class IntroScene extends Scene {
	private static final String TAG = "IntroScene";

	private TextButton demonstrationButton;
	private TextButton playButton;

	public IntroScene(SceneController sceneController) {
		super(sceneController);
		demonstrationButton = new TextButton(this, 10, 4, "Demonstration", sceneController.getTextPaint());
		playButton = new TextButton(this, 10, 20, "Play game", sceneController.getTextPaint());
	}

	@Override
	public SceneId getId() {
		return SceneId.INTRO;
	}

	@Override
	protected void drawToBuffer(Canvas canvas) {
		drawText(6, 1, "Do you want to see a demonstration or");
		drawText(7, 1, "play a real game?");
		super.draw(canvas);
	}

	@Override
	public void buttonEvent(TextButton button) {
		if (demonstrationButton == button) {
			if (playButton == demonstrationButton) {
				Log.d(TAG, "The demo isn't working yet.");
			} else {
				controller.activateScene(SceneId.CAREXT);
			}
		}
	}

	@Override
	protected boolean onMoveTouch(PointF point) {
		return super.onMoveTouch(point) || onDownTouch(point);
	}
}
