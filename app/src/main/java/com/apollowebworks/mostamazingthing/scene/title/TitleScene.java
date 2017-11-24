package com.apollowebworks.mostamazingthing.scene.title;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.DrawUtil;
import com.apollowebworks.mostamazingthing.R;
import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.elevator.ElevatorScene;

import static com.apollowebworks.mostamazingthing.DrawUtil.RELATIVE_HEIGHT;
import static com.apollowebworks.mostamazingthing.DrawUtil.adjustToScreen;

public class TitleScene extends Scene {

	private static final float MARGIN = 2.f;

	public TitleScene(InSearchController inSearchController) {
		super(inSearchController);
	}

	@Override
	public void draw(Canvas canvas, Resources resources, Context context) {
		drawBlackBackground(canvas);

		// load image
		// Instead of passing in context, consider moving a resource manager in here?
		// And set the image the first time we draw?
		Drawable titleImage = resources.getDrawable(R.drawable.isotmat, context.getTheme());
		Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.isotmat);

		Rect imgRect = centeredOnScreen(canvas, (float) bitmap.getWidth() / bitmap.getHeight());

		titleImage.setBounds(imgRect);
		titleImage.draw(canvas);
	}

	public boolean onTouch(MotionEvent motionEvent, Rect clipBounds) {
		inSearchController.activateScene(ElevatorScene.class);
		return true;
	}

	Rect centeredOnScreen(Canvas canvas, float ratio) {
		float newHeight = RELATIVE_HEIGHT - MARGIN * 2;
		float newWidth = newHeight * ratio;
		RectF relativeSize = new RectF(-newWidth/2.f,
				newHeight/2.f,
				newWidth/2.f,
				-newHeight/2.f);
		return adjustToScreen(relativeSize, canvas);
	}
}
