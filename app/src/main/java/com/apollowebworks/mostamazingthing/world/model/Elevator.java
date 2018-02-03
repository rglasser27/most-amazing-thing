package com.apollowebworks.mostamazingthing.world.model;

import android.graphics.*;

public class Elevator extends MoveableObject {

	private static final float WIDTH = 8f;
	private static final float HEIGHT = 11.f;
	private static final int TOP_OF_CABLE = 30;

	private final Paint elevatorCarPaint;
	private final Paint cablePaint;

	public Elevator(PointF position) {
		super(position);

		elevatorCarPaint = new Paint();
		elevatorCarPaint.setStrokeWidth(10);
		elevatorCarPaint.setColor(Color.WHITE);
		elevatorCarPaint.setStyle(Paint.Style.FILL);
		elevatorCarPaint.setAntiAlias(false);

		cablePaint = new Paint();
		cablePaint.setColor(Color.WHITE);
		cablePaint.setAntiAlias(false);
	}

	@Override
	public void draw(Canvas canvas) {
		RectF rect = new RectF(position.x - 3,
							   position.y + HEIGHT / 2,
							   position.x + 4,
							   position.y - HEIGHT / 2);
		canvas.drawRect(rect, elevatorCarPaint);
		canvas.drawLine(position.x,
						TOP_OF_CABLE,
						position.x,
						position.y,
						cablePaint);
	}
}
