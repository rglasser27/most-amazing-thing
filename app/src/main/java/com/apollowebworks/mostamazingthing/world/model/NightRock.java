package com.apollowebworks.mostamazingthing.world.model;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.apollowebworks.mostamazingthing.ui.Turtle;
import com.apollowebworks.mostamazingthing.ui.model.RgbColor;

public class NightRock implements WorldObject {

	public NightRock() {
	}

	/**
	 * 4820 DRAW "s32bm10,150"+ROCKMAN$+"s4":PAINT(50,150),1,3:RETURN
	 */
	@Override
	public void draw(Canvas canvas) {
		Turtle turtle = new Turtle(canvas);
		turtle.setColor(RgbColor.WHITE);
		String rockMan = "c3a0u2e1d1e2u1e2d2r1e1r2f1d1r1f1r1d1r1f1d1l1d1g1l11h2";
		String drawCommand = "s32bm10,150"+rockMan+"s4";
		// draw it a second time to redisplay the outline
		turtle.draw("C3BM78,160M72,145U18NM78,154R48NM112,154D18M112,160");
	}
}
