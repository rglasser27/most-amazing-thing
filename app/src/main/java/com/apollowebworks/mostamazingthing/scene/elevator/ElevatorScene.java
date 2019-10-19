package com.apollowebworks.mostamazingthing.scene.elevator;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;
import com.apollowebworks.mostamazingthing.world.model.Elevator;

import java.util.Arrays;
import java.util.Optional;

import static com.apollowebworks.mostamazingthing.world.model.JetpackGuy.FACING_RIGHT;

/**
 * Elevator positions from elev.bas.txt lines 1150-1180
 * 1150 IF OY=29 THEN CLS:LOCATE 10,12:PRINT"Stepping outside...":BRANCH=2:GOSUB 25550:RUN"ground.bas
 * 1160 IF OY>47 AND OY<51 THEN GOTO 5000
 * 1170 IF OY>67 AND OY<71 THEN GOTO 4000
 * 1180 IF OY>132 AND OY<136 THEN GOTO 6000
 */
public class ElevatorScene extends Scene {

	private static final String TAG = ElevatorScene.class.getName();

	private static final float SHAFT_CENTER = 146;
	private static final int ELEVATOR_START_Y = 43;
	private static final float ELEVATOR_SPEED = .5f;
	private static final float HIGHEST = 36;
	private static final float LOWEST = 160;

	private static final int TOP_Y = 29;
	private static final int SMOKE_Y = 56;
	private static final int STORE_Y = 76;
	private static final int AUCTION_Y = 134;

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
		rooms[0] = new ElevatorRoom(SMOKE_Y, SceneId.SMOKE);
		rooms[1] = new ElevatorRoom(STORE_Y, SceneId.STORE);
		rooms[2] = new ElevatorRoom(AUCTION_Y, SceneId.AUCTION);

		addDot(new PointF(SHAFT_CENTER, SMOKE_Y));

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
	protected boolean onDownTouch(PointF point) {
		moving = true;
		lastTouched = point;
		if (lastTouched.y > LOWEST) {
			lastTouched.y = LOWEST;
		}
		if (lastTouched.y < HIGHEST) {
			lastTouched.y = HIGHEST;
		}
		Log.d(TAG, "The elevator is now moving to height " + lastTouched.y);
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

				Optional<ElevatorRoom> room = reachedRoom();
				room.ifPresent(r -> controller.activateScene(r.getScene()));
				return room.isPresent();
			}
		}
		return wasMoving;
	}

	/**
	 * @return true if the elevator is currently touched a room
	 */
	private Optional<ElevatorRoom> reachedRoom() {
		return Arrays.stream(rooms)
				.filter(room -> lastTouched.y > room.getTop() && lastTouched.y < room.getBottom())
				.findFirst();
	}
}
