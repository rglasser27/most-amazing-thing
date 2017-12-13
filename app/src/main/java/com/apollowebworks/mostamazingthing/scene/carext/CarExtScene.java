package com.apollowebworks.mostamazingthing.scene.carext;

import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;
import com.apollowebworks.mostamazingthing.world.model.JetpackGuy;
import com.apollowebworks.mostamazingthing.world.model.NightRock;
import com.apollowebworks.mostamazingthing.world.model.PopberryTree;
import com.apollowebworks.mostamazingthing.world.model.WorldObject;

import java.util.Arrays;
import java.util.List;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.getVirtualPoint;

public class CarExtScene extends Scene {

	private static final String TAG = CarExtScene.class.getName();

	private final RectF doorArea;
	private List<WorldObject> objectsInScene;
	private final JetpackGuy jetpackGuy;

	public CarExtScene(SceneController sceneController) {
		super(sceneController);
		this.setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.CAREXT));
		PopberryTree tree = new PopberryTree();
		NightRock rock = new NightRock();
		Bitmap[] jetpackFrames = new Bitmap[]{
				sceneController.getImageManager().getBitmap(ImageManager.JPLEFT_1),
				sceneController.getImageManager().getBitmap(ImageManager.JPLEFT_2),
				sceneController.getImageManager().getBitmap(ImageManager.JPRIGHT_1),
				sceneController.getImageManager().getBitmap(ImageManager.JPRIGHT_2),
		};
		jetpackGuy = new JetpackGuy(new PointF(250, 150), jetpackFrames);
		objectsInScene = Arrays.asList(tree, jetpackGuy);
		doorArea = new RectF(237, 145, 254, 161);
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
//		Paint paint = new Paint();
//		paint.setColor(Color.WHITE);
//		paint.setAlpha(0x88);
//		canvas.drawCircle((doorArea.left+doorArea.right)/2, (doorArea.top+doorArea.bottom)/2, 5, paint);
//		canvas.drawRect(doorArea, paint);
	}

	@Override
	public boolean onTouch(MotionEvent event, Rect clipBounds) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				PointF virtualPoint = getVirtualPoint(event.getX(), event.getY(), clipBounds);
				Log.d(TAG, "Touched a point on the screen (" + virtualPoint.x + ", " + virtualPoint.y + ")");
				if (doorArea.contains(virtualPoint.x, virtualPoint.y)
						&& doorArea.contains(jetpackGuy.getPosition().x, jetpackGuy.getPosition().y)) {
					jetpackGuy.stop();
					sceneController.activateScene(SceneId.CARINT);
				}
				return jetpackGuy.handleTouch(virtualPoint);
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}

	@Override
	public boolean tick(Long msElapsed) {
		return jetpackGuy.move(msElapsed);
	}
}
