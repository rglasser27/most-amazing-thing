package com.apollowebworks.mostamazingthing.world.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import com.apollowebworks.mostamazingthing.util.DrawUtil;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.*;

/**
 * Displaying the jetpack guy on the screen
 * TODO: Need to find the original logic for how he moves and update the behavior
 * Also screen boundaries
 */
public class JetpackGuy extends MoveableObject {

	private Bitmap[] frames;
	private Bitmap frame;
	private PointF direction;
	private float speed;
	private static final float SPEED_UPDATE = .2f;
	private static final float MAX_RESTING_SPEED = .5f;
	private boolean facingLeft;

	public JetpackGuy(PointF position, Bitmap[] frames) {
		super(position);
		this.frames = frames;
		direction = new PointF(0, 1);
		speed = 0;
		facingLeft = true;
		frame = frames[0];
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(frame,
						  new Rect(0, 0, frame.getWidth(), frame.getHeight()),
						  new Rect((int) position.x - frame.getWidth() / 2, (int) position.y - frame.getHeight() / 2,
								   (int) position.x + frame.getWidth() / 2, (int) position.y + frame.getHeight() / 2),
						  null);
	}

	public boolean move(Long msElapsed) {
		// If more than one second has elapsed, limit the movement
		float boundedMs = msElapsed > 1000 ? 1000 : msElapsed;

		// update frame image, depending on speed
		float xMagnitude = direction.x * speed;
		if (xMagnitude <= -SPEED_UPDATE / 5) {
			facingLeft = true;
		} else if (xMagnitude >= SPEED_UPDATE / 5) {
			facingLeft = false;
		}
		if (xMagnitude < -SPEED_UPDATE * 2) {
			frame = frames[1];
		} else if (xMagnitude < -SPEED_UPDATE / 5) {
			frame = frames[0];
		} else if (xMagnitude < SPEED_UPDATE * 2) {
			frame = facingLeft ? frames[0] : frames[2];
		} else if (xMagnitude > SPEED_UPDATE / 5) {
			frame = frames[3];
		} else {
			frame = frames[2];
		}

		float unitsMoved = speed * 1000.f / boundedMs;
		position = add(position, multiply(direction, unitsMoved));
		return true;
	}

	public boolean handleTouch(PointF virtualPoint) {
		PointF newTarget = subtract(virtualPoint, position);
		PointF oldVel = multiply(direction, speed);
		PointF newVel = multiply(normalize(newTarget), SPEED_UPDATE);
		PointF finalVel = DrawUtil.add(oldVel, newVel);
		speed = magnitude(finalVel);
		direction = multiply(finalVel, 1 / speed);
		return true;
	}

	public void stop() {
		speed = 0;
	}
}
