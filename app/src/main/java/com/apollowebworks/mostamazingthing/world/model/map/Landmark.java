package com.apollowebworks.mostamazingthing.world.model.map;

import android.graphics.Canvas;
import android.graphics.Point;
import com.apollowebworks.mostamazingthing.ui.Turtle;
import com.apollowebworks.mostamazingthing.world.model.WorldObject;

/**
 * TODO:
 * Maybe this should not be an object, but just a draw instruction on MapScene.
 */
public class Landmark implements WorldObject {
	private final Point coordinates;
	private final Point position;
	private final LandmarkType type;

	public Landmark(Point coordinates, Point position, LandmarkType type) {
		this.coordinates = coordinates;
		this.position = position;
		this.type = type;
	}

	@Override
	public void draw(Canvas canvas) {
		Turtle turtle = new Turtle(canvas);
		turtle.penUp();
		turtle.moveTo(position.x, position.y);
		switch(type) {
			case TREE:
				turtle.draw("s9;c=x7;a0e1u5h2u1h1u1e1u1e1r1f1e1f1f1r1f1d1g1d1g1l1g1d5f1r1l6s4");
				break;
		}
	}
}
