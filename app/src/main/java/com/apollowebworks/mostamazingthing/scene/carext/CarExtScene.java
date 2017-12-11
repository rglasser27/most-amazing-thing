package com.apollowebworks.mostamazingthing.scene.carext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;
import com.apollowebworks.mostamazingthing.world.model.*;

import java.util.Arrays;
import java.util.List;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.getVirtualPoint;

public class CarExtScene extends Scene {

	private static final String TAG = CarExtScene.class.getName();

	private boolean moving;
	private PointF lastTouched;
	private List<WorldObject> objectsInScene;
	private final JetpackGuy jetpackGuy;

	public CarExtScene(SceneController sceneController) {
		super(sceneController);
		this.setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.CAREXT));
		moving = false;
		PopberryTree tree = new PopberryTree();
		NightRock rock = new NightRock();
//		jetpackGuy = new JetpackGuy(new PointF(235, 160),
		Bitmap[] jetpackFrames = new Bitmap[]{
				sceneController.getImageManager().getBitmap(ImageManager.JPLEFT_1),
				sceneController.getImageManager().getBitmap(ImageManager.JPLEFT_2),
				sceneController.getImageManager().getBitmap(ImageManager.JPRIGHT_1),
				sceneController.getImageManager().getBitmap(ImageManager.JPRIGHT_2),
		};
		jetpackGuy = new JetpackGuy(new PointF(150, 50), jetpackFrames);
		objectsInScene = Arrays.asList(tree, jetpackGuy);
	}

	@Override
	public SceneId getId() {
		return SceneId.CAREXT;
	}


	@Override
	public void drawToBuffer(Canvas canvas) {
		drawText(19, 21, ">>B-Liner");
		drawText(19, 33, ">>>");
		for (WorldObject o : objectsInScene) {
			o.draw(canvas);
		}
	}

	@Override
	public boolean onTouch(MotionEvent event, Rect clipBounds) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				PointF virtualPoint = getVirtualPoint((int) event.getX(), (int) event.getY(), clipBounds);
				Log.d(TAG, "Touched a point on the screen (" + virtualPoint.x + ", " + virtualPoint.x + ")");
				return jetpackGuy.handleTouch(virtualPoint);
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}

	@Override
	public boolean tick(Long msElapsed) {
		return jetpackGuy.move(msElapsed);
//		if (speed > 0) {
//			moving = !jetpackGuy.move();
//		}
//		return wasMoving;

	}
}
