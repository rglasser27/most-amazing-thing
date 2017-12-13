package com.apollowebworks.mostamazingthing.scene.bitmaptest;

import android.graphics.*;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;
import com.apollowebworks.mostamazingthing.ui.model.DosBitmap;
import com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;

import java.util.ArrayList;
import java.util.List;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.RELATIVE_HEIGHT;
import static com.apollowebworks.mostamazingthing.util.DrawUtil.adjustToScreen;
import static com.apollowebworks.mostamazingthing.util.DrawUtil.getVirtualPoint;

public class BitmapTestScene extends Scene {

	private static final float MARGIN = 2.f;

	public BitmapTestScene(SceneController sceneController) {
		super(sceneController);
//		setBackgroundImage(imageManager.getBitmap(ImageManager.AUCTION));
	}

	@Override
	public void drawToBuffer(Canvas canvas) {
//		DosBitmap image = imageManager.getImage("auction");
//		image.draw(canvas);
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

	@Override
	public boolean onTouch(MotionEvent event, Rect clipBounds) {
		return true;
	}
}
