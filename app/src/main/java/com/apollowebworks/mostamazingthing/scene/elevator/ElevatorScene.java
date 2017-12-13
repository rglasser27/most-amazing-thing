package com.apollowebworks.mostamazingthing.scene.elevator;

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
import com.apollowebworks.mostamazingthing.world.model.Elevator;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.getVirtualPoint;

public class ElevatorScene extends Scene {

	private static final String TAG = ElevatorScene.class.getName();

	private static final float SHAFT_CENTER = 147;
	private static final int ELEVATOR_START_Y = 60;
	private static final float ELEVATOR_SPEED = .5f;

	private boolean moving;
	private PointF lastTouched;

	private Elevator elevator;

	public ElevatorScene(SceneController sceneController) {
		super(sceneController);
		this.elevator = new Elevator(new PointF(SHAFT_CENTER, ELEVATOR_START_Y));
		this.setBackgroundImage(sceneController.getImageManager().getBitmap("elevpic"));
		moving = false;
	}

	@Override
	public SceneId getId() {
		return SceneId.ELEVATOR;
	}

	@Override
	public void drawToBuffer(Canvas canvas) {
		elevator.draw(canvas);
		drawText(10, 11, "Store");
		drawText(8, 22, "Smoke");
		drawText(18, 21, "Auction");
	}

	@Override
	public boolean onTouch(MotionEvent event, Rect clipBounds) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				moving = true;
				lastTouched = getVirtualPoint(event.getX(), event.getY(), clipBounds);
				Log.d(TAG, "Touched a point on the screen (" +
						event.getX() + ", " + event.getY() + ")");
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}

	@Override
	public boolean tick(Long msElapsed) {
		boolean wasMoving = moving;
		if (moving) {
			moving = !elevator.moveToward(new PointF(SHAFT_CENTER, lastTouched.y), ELEVATOR_SPEED, msElapsed);
		}
		return wasMoving;
	}
}
