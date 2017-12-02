package com.apollowebworks.mostamazingthing.scene.carext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.graphics.Turtle;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.world.model.Elevator;
import com.apollowebworks.mostamazingthing.world.model.PopberryTree;

import static com.apollowebworks.mostamazingthing.math.DrawUtil.getVirtualPoint;

public class CarExtScene extends Scene {

	private static final String TAG = CarExtScene.class.getName();

	private static final float SHAFT_CENTER = 147;
	private static final int ELEVATOR_START_Y = 60;
	private static final float ELEVATOR_SPEED = .5f;

	private boolean moving;
	private PointF lastTouched;
	private PopberryTree tree;

	public CarExtScene(InSearchController inSearchController) {
		super(inSearchController);
		this.setBackgroundImage(inSearchController.getImageManager().getBitmap("carext"));
		moving = false;
		tree = new PopberryTree();
	}

	@Override
	public SceneId getId() {
		return SceneId.CAREXT;
	}


	@Override
	public void draw(Canvas canvas, Resources resources, Context context) {
		newFrame();
		tree.draw(tempCanvas);
		drawFinalFrame(canvas);
	}

	@Override
	public boolean onTouch(MotionEvent event, Rect clipBounds) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				moving = true;
				lastTouched = getVirtualPoint((int) event.getX(), (int) event.getY(), clipBounds);
				Log.d(TAG, "Touched a point on the screen (" +
						event.getX() + ", " + event.getY() + ")");
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}

}
