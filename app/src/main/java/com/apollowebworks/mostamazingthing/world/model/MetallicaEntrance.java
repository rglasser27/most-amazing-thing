package com.apollowebworks.mostamazingthing.world.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.apollowebworks.mostamazingthing.ui.components.Turtle;
import com.apollowebworks.mostamazingthing.ui.model.RgbColor;

/**
 * 4510 LINE(72,127)-(96,142-(I-TOG)*3) ,C:LINE -(120,127) ,C:LINE -((96+(I-TOG)*4),142) ,C:LINE -(114,154) ,C:LINE -(96,142+(I-TOG)*2) ,C:LINE -(78,154) ,C:LINE -((96-(I-TOG)*4),142) ,C:LINE -(72,127) ,C:RETURN
 * 4770 DRAW"C3BM78,160M72,145U18NM78,154R48NM112,154D18M112,160":LINE(78,154)-(112,160),3,BF:PAINT(73,145),3,3:PAINT(119,145),3,3:LINE(79,155)-(79,159) ,0:LINE(113,155)-(113,159) ,0
 * 4780 PAINT (96,142),2,3:RETURN

 */
public class MetallicaEntrance implements WorldObject {

	public MetallicaEntrance() {
	}

	/**
	 * 4820 DRAW "s32bm10,150"+ROCKMAN$+"s4":PAINT(50,150),1,3:RETURN
	 */
	@Override
	public void draw(Canvas canvas) {
//		String metallica = "s4c3r8m-1,+7l6m-1,-7";
//		String drawCommand = "s32bm10,150" + rockMan + "s4";
		// draw it a second time to redisplay the outline
//		turtle.draw("C3BM78,160M72,145U18NM78,154R48NM112,154D18M112,160");

//		turtle.draw("u5r5d5l5");


		// Line 4770, just drawing the inner tank
		Turtle turtle = new Turtle(canvas);
		turtle.draw("C3BM78,154M72,127R48M120,127M112,154M78,154");
		turtle.fillPath(RgbColor.MAGENTA);


		// Line 4770, left side
//		turtle.draw("C3BM78,160M72,145U18M78,154");
//		turtle.draw("C3BM78,160M72,145U18NM78,154R48NM112,154D18M112,160");

		// draw lines to fill edges
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		canvas.drawLine(78, 153, 114, 153, paint);
		canvas.drawLine(114, 152, 78, 152, paint);
		canvas.drawLine(73, 128, 119, 128, paint);
		canvas.drawLine(119, 129, 73, 129, paint);
		canvas.drawLine(72, 127, 120, 127, paint);

		// TODO: Fill out the rest of the entrance image
		// Line 4770, continued
//		turtle.
//		LINE(78,154)-(112,160)
//		turtle.draw()
//		turtle.draw("C3BM78,160M72,145U18");

//		canvas.drawLine(78,154,112,160, paint);
//		,3,BF:PAINT(73,145),3,3:PAINT(119,145),3,3:
//		canvas.drawLine(79,155,79,159,paint);
//		canvas.drawLine(113,155,113,159, paint);

//		LINE(72,127)-(96,142-(I-TOG)*3) ,C:LINE -(120,127) ,C:LINE -((96+(I-TOG)*4),142) ,C:LINE -(114,154) ,C:LINE -(96,142+(I-TOG)*2) ,C:LINE -(78,154) ,C:LINE -((96-(I-TOG)*4),142) ,C:LINE -(72,127) ,C:RETURN
	}
}
