package com.apollowebworks.mostamazingthing.scene.title;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.R;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.*;

/**
 * This scene overrides draw, because it doesn't use a smaller bitmap
 */
public class TitleScene extends Scene {

	private static final float MARGIN = 2.f;
	private final Bitmap bitmap;
	private Drawable titleImage;

	public TitleScene(SceneController sceneController) {
		super(sceneController);
		// load image
		// Instead of passing in context, consider moving a resource manager in here?
		// And set the image the first time we draw?
		bitmap = BitmapFactory.decodeResource(sceneController.getResources(), R.drawable.isotmat);
		titleImage = sceneController.getResources().getDrawable(R.drawable.isotmat, sceneController.getResources().newTheme());
	}

	@Override
	public void draw(Canvas canvas) {
		drawBackground(canvas);
		Rect imgRect = centeredOnScreen(canvas, (float) bitmap.getWidth() / bitmap.getHeight());
		titleImage.setBounds(imgRect);
		titleImage.draw(canvas);
	}

	public boolean onTouch(MotionEvent motionEvent, Rect clipBounds, int action) {
		controller.activateScene(SceneId.ELEVATOR);
		return true;
	}

	@Override
	public SceneId getId() {
		return SceneId.TITLE;
	}

	@Override
	protected void drawToBuffer(Canvas canvas) {
	}

	Rect centeredOnScreen(Canvas canvas, float ratio) {
		float newHeight = RELATIVE_HEIGHT - MARGIN * 2;
		float newWidth = newHeight * ratio;
		RectF relativeSize = new RectF((RELATIVE_WIDTH-newWidth)/2.f,
				MARGIN,
				(RELATIVE_WIDTH+newWidth)/2.f,
				MARGIN+newHeight);
		return adjustToScreen(relativeSize, canvas);
	}
}
