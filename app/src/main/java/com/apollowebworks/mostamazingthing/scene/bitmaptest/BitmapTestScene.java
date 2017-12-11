package com.apollowebworks.mostamazingthing.scene.bitmaptest;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.ui.model.DosBitmap;
import com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.RELATIVE_HEIGHT;
import static com.apollowebworks.mostamazingthing.util.DrawUtil.adjustToScreen;

public class BitmapTestScene extends Scene {

	private static final float MARGIN = 2.f;

	public BitmapTestScene(SceneController sceneController) {
		super(sceneController);
	}

	@Override
	public void drawToBuffer(Canvas canvas) {
		DosBitmap image = imageManager.getImage("auction");
		image.draw(canvas);
	}

	public boolean onTouch(MotionEvent motionEvent, Rect clipBounds) {
		return false;
	}

	@Override
	public SceneId getId() {
		return SceneId.BITMAP_TEST;
	}

	Rect centeredOnScreen(Canvas canvas, float ratio) {
		float newHeight = RELATIVE_HEIGHT - MARGIN * 2;
		float newWidth = newHeight * ratio;
		RectF relativeSize = new RectF(-newWidth / 2.f,
				newHeight / 2.f,
				newWidth / 2.f,
				-newHeight / 2.f);
		return adjustToScreen(relativeSize, canvas);
	}
}
