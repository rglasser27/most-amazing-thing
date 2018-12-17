package com.apollowebworks.mostamazingthing.world.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;

import static com.apollowebworks.mostamazingthing.util.DrawUtil.RELATIVE_WIDTH;
import static com.apollowebworks.mostamazingthing.util.DrawUtil.subtract;

/**
 * Displaying the jetpack guy on the screen
 */
public class JetpackGuy extends MoveableObject {

	/**
	 * Acceleration, in pixels / s^2
	 */
	private static final int MIN_DISTANCE_FOR_TOUCH = 20;

	private static final float MAX_VELOCITY_X = 70;
	private static final float ACCELERATION_X = MAX_VELOCITY_X / 3;
	private static final float DECELERATION_X = ACCELERATION_X / 4;

	private static final float SPEED_CHANGE_Y = 10;
	private static final float MAX_VELOCITY_Y = 2 * SPEED_CHANGE_Y;

	private static final int MOVING_LEFT = 0;
	private static final int FACING_LEFT = 1;
	public  static final int FACING_RIGHT = 2;
	private static final int MOVING_RIGHT = 3;

	private Bitmap[] frames;
	private int frame;
	private PointF direction;
	private PointF velocity;
	private static final float BOTTOM = 180;

	public JetpackGuy(PointF position, Bitmap[] frames) {
		super(position);
		this.frames = frames;
		direction = new PointF(0, 1);
		velocity = new PointF(0, 0);
		frame = FACING_LEFT;
	}

	@Override
	public void draw(Canvas canvas) {
		Bitmap f = frames[frame];
		canvas.drawBitmap(f,
						  new Rect(0, 0, f.getWidth(), f.getHeight()),
						  new Rect((int) position.x - f.getWidth() / 2, (int) position.y - f.getHeight() / 2,
								   (int) position.x + f.getWidth() / 2, (int) position.y + f.getHeight() / 2),
						  null);
	}

	public boolean move(Long msElapsed) {
		// If more than one second has elapsed, limit the movement
		float boundedMs = msElapsed > 1000 ? 1000 : msElapsed;
		updateXVelocity(boundedMs);

		// Update position
		if (velocity.x == 0 && velocity.y == 0) {
			return false;
		}

		position.x += velocity.x * boundedMs / 1000;
		position.y += velocity.y * boundedMs / 1000;
		checkTopBound();
		checkBottomBound();
		return true;
	}

	private void updateXVelocity(float boundedMs) {
		if (frame == MOVING_LEFT) {
			velocity.x -= ACCELERATION_X * boundedMs / 1000;
		} else if (frame == MOVING_RIGHT) {
			velocity.x += ACCELERATION_X * boundedMs / 1000;
		} else if (velocity.x < 0) {
			velocity.x += DECELERATION_X * boundedMs / 1000;
			if (velocity.x > 0) {
				velocity.x = 0;
			}
		} else {
			velocity.x -= DECELERATION_X * boundedMs / 1000;
			if (velocity.x < 0) {
				velocity.x = 0;
			}
		}
		if (velocity.x > MAX_VELOCITY_X) {
			velocity.x = MAX_VELOCITY_X;
		}
		if (velocity.x < -MAX_VELOCITY_X) {
			velocity.x = -MAX_VELOCITY_X;
		}
	}

	/**
	 * update frame image, depending on speed
	 */
	private void updateFrame(int dir) {
		if (dir > 0) {
			frame++;
			if (frame > 3) {
				frame = 3;
			}
		} else {
			frame--;
			if (frame < 0) {
				frame = 0;
			}
		}
	}

	private void checkTopBound() {
		if (position.y < 0) {
			position.y = 0;
			if (direction.x != 0) {
				direction.y = 0;
				direction.x = direction.x > 0 ? 1 : -1;
			}
		}
	}

	private void checkBottomBound() {
		if (position.y >= BOTTOM) {
			position.y = BOTTOM;
			stop();
		}
	}

	public int checkSides() {
		if (position.x < 0) {
			position.x = RELATIVE_WIDTH;
			return -1;
		} else if (position.x > RELATIVE_WIDTH) {
			position.x = 0;
			return 1;
		}
		return 0;
	}

	public boolean handleTouch(PointF virtualPoint) {
		PointF distanceFromGuy = subtract(virtualPoint, position);
		if (distanceFromGuy.x > 10) {
			updateFrame(1);
		} else if (distanceFromGuy.x < -10) {
			updateFrame(-1);
		}

		if (distanceFromGuy.y > MIN_DISTANCE_FOR_TOUCH) {
			velocity.y += SPEED_CHANGE_Y;
		} else if (distanceFromGuy.y < -MIN_DISTANCE_FOR_TOUCH) {
			velocity.y -= SPEED_CHANGE_Y;
		}
		if (velocity.y > MAX_VELOCITY_Y) {
			velocity.y = MAX_VELOCITY_Y;
		} else if (velocity.y < -MAX_VELOCITY_Y) {
			velocity.y = -MAX_VELOCITY_Y;
		}

		return true;
	}

	public void stop() {
		velocity.x = 0;
		velocity.y = 0;
		if (frame <= FACING_LEFT) {
			frame = FACING_LEFT;
		} else {
			frame = FACING_RIGHT;
		}
	}
}
