package com.apollowebworks.mostamazingthing.scene.elevator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.model.Elevator;
import com.apollowebworks.mostamazingthing.scene.Scene;

import static com.apollowebworks.mostamazingthing.DrawUtil.getVirtualPoint;

public class ElevatorScene extends Scene {

	private static final String TAG = ElevatorScene.class.getName();

	private static final float SHAFT_CENTER = 0;
	private static final int ELEVATOR_START_Y = 60;
	private static final float ELEVATOR_SPEED = 1f;

	private boolean moving;
	private PointF lastTouched;

	private Elevator elevator;

	public ElevatorScene(InSearchController inSearchController) {
		super(inSearchController);
		this.elevator = new Elevator(new PointF(SHAFT_CENTER, ELEVATOR_START_Y));
		moving = false;
	}

	@Override
	public void draw(Canvas canvas, Resources resources, Context context) {
		drawBlackBackground(canvas);
		elevator.draw(canvas, resources);
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
		inSearchController.redraw();
		return true;
	}

	@Override
	public boolean tick(Long msElapsed) {
		boolean wasMoving = moving;
		if (moving) {
			moving = !elevator.moveToward(new PointF(SHAFT_CENTER, lastTouched.y), 1f, msElapsed);
		}
		return wasMoving;
	}
}
