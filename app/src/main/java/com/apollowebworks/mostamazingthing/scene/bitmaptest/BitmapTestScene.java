package com.apollowebworks.mostamazingthing.scene.bitmaptest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.graphics.model.FullScreenImage;
import com.apollowebworks.mostamazingthing.scene.Scene;

import static com.apollowebworks.mostamazingthing.math.DrawUtil.RELATIVE_HEIGHT;
import static com.apollowebworks.mostamazingthing.math.DrawUtil.adjustToScreen;

public class BitmapTestScene extends Scene {

	private static final float MARGIN = 2.f;

	public BitmapTestScene(InSearchController inSearchController) {
		super(inSearchController);
	}

	@Override
	public void draw(Canvas canvas, Resources resources, Context context) {
		FullScreenImage image = imageManager.getImage("auction");
		image.draw(canvas);
	}

	public boolean onTouch(MotionEvent motionEvent, Rect clipBounds) {
		return false;
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
