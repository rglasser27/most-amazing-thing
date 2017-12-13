package com.apollowebworks.mostamazingthing.scene.intro;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.TextButton;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.getVirtualPoint;

public class IntroScene extends Scene {
	private static final String TAG = "IntroScene";

	private TextButton demonstrationButton;
	private TextButton playButton;

	public IntroScene(SceneController sceneController) {
		super(sceneController);
		demonstrationButton = new TextButton(10, 4, "Demonstration", sceneController.getTextPaint());
		playButton = new TextButton(10, 20, "Play game", sceneController.getTextPaint());
	}

	@Override
	public SceneId getId() {
		return SceneId.INTRO;
	}

	@Override
	protected void drawToBuffer(Canvas canvas) {
		drawText(6, 1, "Do you want to see a demonstration or");
		drawText(7, 1, "play a real game?");
		demonstrationButton.draw(canvas);
		playButton.draw(canvas);
	}

	@Override
	public boolean onTouch(MotionEvent event, Rect clipBounds) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				demonstrationButton.setPressing(false);
				playButton.setPressing(false);
				PointF point = getVirtualPoint(event.getX(), event.getY(), clipBounds);
				demonstrationButton.checkPressing((int) point.x, (int) point.y);
				playButton.checkPressing((int) point.x, (int) point.y);
				break;
			case MotionEvent.ACTION_UP:
				point = getVirtualPoint(event.getX(), event.getY(), clipBounds);
				if (demonstrationButton.checkPressing((int) point.x, (int) point.y)) {
					Log.d(TAG, "Clicked demonstration button");
				} else if (playButton.checkPressing((int) point.x, (int) point.y)) {
					Log.d(TAG, "Clicked play button");
					sceneController.activateScene(SceneId.CAREXT);
				}
				demonstrationButton.setPressing(false);
				playButton.setPressing(false);
				break;
		}
		return true;
	}
}
