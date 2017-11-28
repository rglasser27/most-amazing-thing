package com.apollowebworks.mostamazingthing.world.model;

import android.content.res.Resources;
import android.graphics.*;
import com.apollowebworks.mostamazingthing.math.DrawUtil;

public class Elevator extends WorldObject {

	private static final float WIDTH = 8f;
	private static final float HEIGHT = 11.f;

	private final Paint elevatorCarPaint;

	public Elevator(PointF position) {
		super(position);

		elevatorCarPaint = new Paint();
		elevatorCarPaint.setStrokeWidth(10);
		elevatorCarPaint.setColor(Color.WHITE);
		elevatorCarPaint.setStyle(Paint.Style.FILL);
	}

	@Override
	public void draw(Canvas canvas, Resources resources) {
		RectF rect = new RectF(position.x - WIDTH / 2,
				position.y + HEIGHT / 2,
				position.x + WIDTH / 2,
				position.y - HEIGHT / 2);
		canvas.drawRect(DrawUtil.adjustToScreen(rect, canvas), elevatorCarPaint);
	}
}
