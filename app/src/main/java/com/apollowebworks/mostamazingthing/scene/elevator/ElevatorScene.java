package com.apollowebworks.mostamazingthing.scene.elevator;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;
import com.apollowebworks.mostamazingthing.world.model.Elevator;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.getVirtualPoint;
import static com.apollowebworks.mostamazingthing.world.model.JetpackGuy.FACING_RIGHT;

public class ElevatorScene extends Scene {

	private static final String TAG = ElevatorScene.class.getName();

	private static final float SHAFT_CENTER = 146;
	private static final int ELEVATOR_START_Y = 43;
	private static final float ELEVATOR_SPEED = .5f;
	private static final float HIGHEST = 36;
	private static final float LOWEST = 160;

	private boolean moving;
	private PointF lastTouched;

	private Elevator elevator;

	private ElevatorRoom[] rooms;

	public ElevatorScene(SceneController sceneController) {
		super(sceneController);
		this.elevator = new Elevator(new PointF(SHAFT_CENTER, ELEVATOR_START_Y));
		this.setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.ELEVPIC));
		rooms = new ElevatorRoom[3];
		// TODO: Define these as constants; make the user click the rooms instead of anywhere on X
		rooms[0] = new ElevatorRoom(45, 65, SceneId.SMOKE);
		rooms[1] = new ElevatorRoom(70, 90, SceneId.STORE);
		rooms[2] = new ElevatorRoom(120, 140, SceneId.AUCTION);
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

		// TODO: Draw the small b-liner on the surface
	}

	@Override
	public boolean onTouch(MotionEvent event, Rect clipBounds) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				moving = true;
				lastTouched = getVirtualPoint(event.getX(), event.getY(), clipBounds);
				if (lastTouched.y > LOWEST) {
					lastTouched.y = LOWEST;
				}
				if (lastTouched.y < HIGHEST) {
					lastTouched.y = HIGHEST;
				}
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

			// If we just stopped moving, decide whether we are cutting to another scene
			if (wasMoving && !moving) {
				// Exit to ground level?
				if (lastTouched.y <= HIGHEST) {
					controller.prepareJetpackGuy(70, 150, FACING_RIGHT);
					controller.activateScene(SceneId.CAREXT);
				}

				// Landed on a room?
				for (ElevatorRoom room : rooms) {
					if (lastTouched.y > room.getTop() && lastTouched.y < room.getBottom()) {
						controller.activateScene(room.getScene());
						return false;
					}
				}
			}
		}
		return wasMoving;
	}
}
