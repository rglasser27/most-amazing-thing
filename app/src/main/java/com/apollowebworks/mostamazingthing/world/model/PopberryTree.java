package com.apollowebworks.mostamazingthing.world.model;

import android.graphics.*;
import com.apollowebworks.mostamazingthing.graphics.Turtle;
import com.apollowebworks.mostamazingthing.graphics.model.RgbColor;

public class PopberryTree implements WorldObject {
	private static final int SPIKES_PER_BRANCH = 50;
	private RectF[] spikes;

	public PopberryTree() {
		spikes = new RectF[3 * SPIKES_PER_BRANCH];
		randomizeSpikes(spikes, 0, SPIKES_PER_BRANCH, 40, 110);
		randomizeSpikes(spikes, SPIKES_PER_BRANCH, 2*SPIKES_PER_BRANCH, 45, 70);
		randomizeSpikes(spikes, 2*SPIKES_PER_BRANCH, 3*SPIKES_PER_BRANCH, 90, 95);
	}

	private void randomizeSpikes(RectF[] spikes, int first, int last, int centerX, int centerY) {
		for (int i = first; i < last; i++) {
			spikes[i] = new RectF(centerX,
					centerY,
					centerX + (float) Math.random() * 60 - 30,
					centerY + (float) Math.random() * 60 - 30
			);
		}
	}

	@Override
	public void draw(Canvas canvas) {
		Turtle turtle = new Turtle(canvas);
		turtle.setColor(RgbColor.WHITE);
		String drawCommand = "bm40,180e10u50h10r5f5u40h5r5f5d50e30r5g35d40f10l25";
		turtle.draw(drawCommand);
		turtle.fillPath(RgbColor.CYAN);
		// draw it a second time to redisplay the outline
		turtle.draw(drawCommand);

		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		for (int i = 0; i < SPIKES_PER_BRANCH*3; i ++) {
			if (spikes[i] != null) {
				canvas.drawLine(spikes[i].left, spikes[i].top, spikes[i].right, spikes[i].bottom, paint);
			}
		}
	}
}
