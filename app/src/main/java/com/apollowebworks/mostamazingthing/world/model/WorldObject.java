package com.apollowebworks.mostamazingthing.world.model;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PointF;

abstract class WorldObject {
	PointF position;

	WorldObject(PointF position) {
		this.position = position;
	}

	public abstract void draw(Canvas canvas, Resources resources);

	public void moveTo(PointF position) {
		this.position = position;
	}

	/**
	 * @param targetPosition where to move the object
	 * @param speed          how fast it can go, in units per second
	 * @param msElapsed      how many milliseconds have elapsed since the last movement update
	 * @return true if the object reached its destination
	 */
	public boolean moveToward(PointF targetPosition, float speed, Long msElapsed) {
		// If more than one second has elapsed,
		float boundedMs = msElapsed > 1000 ? 1000 : msElapsed;
		float unitsMoved = speed * 1000.f / boundedMs;
		float distance = findDistanceToTarget(targetPosition);
		if (distance <= speed) {
			position = targetPosition;
			return true;
		}
		float xMove = (targetPosition.x - position.x) * unitsMoved / distance;
		float newX = position.x + xMove;
		float yMove = (targetPosition.y - position.y) * speed / distance;
		float newY = position.y + yMove;
		position = new PointF(newX, newY);
		return false;
	}

	private float findDistanceToTarget(PointF targetPosition) {
		float xDist = targetPosition.x - position.x;
		float yDist = targetPosition.y - position.y;
		return (float) Math.sqrt(xDist * xDist + yDist * yDist);
	}
}
