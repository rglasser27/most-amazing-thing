package com.apollowebworks.mostamazingthing.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import com.apollowebworks.mostamazingthing.ui.model.RgbColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://hwiegman.home.xs4all.nl/gw-man/
 * Example: "bm155,29u1l1u2e3u2r2d2f3d2l1d1u1l5"
 * "m155,29" -> set cursor at 155x,29y
 * "m+10,-20" -> move relative to current position
 * "u1" = up 1
 * "l1" = left 1
 * <p>
 * u = up
 * d = down
 * l = left
 * r = right
 * e = up/right
 * f = down/right
 * h = up/left
 * g = down/left
 * c[0-4] = pick a new color
 * s9; = change step size, i.e., multiply moves by a number
 * a = rotate directions like this:
 * a[0-3] = set angle in increments of 90
 * ta[0-360] = turn angle
 * a2 = down -> up; left -> right
 * b (by itself) = don't draw anything for the next command only
 * paint(144,OY+2) = ??
 * <p>
 * SMALL (map) images from Logo.txt:
 * 20130 TREE$="s9;c=x7;a0e1u5h2u1h1u1e1u1e1r1f1e1f1f1r1f1d1g1d1g1l1g1d5f1r1l6s4
 * 20140 PUMP$="c3a0r3d3l3u6l3d3r3g3e6be2f1r1f1d1f1d1f1d1g1d1g2l1g1l1g1l2h1l1h1l1h1l1h2u1h1u3e1u1e2r1e1r1e1r2f1r2f2"
 * 20150 ROCKMAN$="c3a0u2e1d1e2u1e2d2r1e1r2f1d1r1f1r1d1r1f1d1l1d1g1l11h2"
 * 20160 HC$="c3a0r3f3d3g3l3h3u3e3d1g2d3f2r3e2u3h2l4"
 * 20170 HUTBE$="c3d14r14m-4,-8nl6u3m+4,-3nd14l14m+4,+3nr6d3m-4,+8br9u4l4d4br2u4"
 * 20180 BULKHD$="s5c3r14d6m-3,+7u2m+3,-11m-11,+11m-3,-11m+11,+11l8d2nr8m-3,-7u6"
 * 20190 HUT$="s8c3bm25,150r58m-20,-38m-10,-6l18m-20,+24m+10,+20m+20,-38nm-10,-6r18bd38u14l16d14br8u14"
 * <p>
 * Big tree! see GROUND.TXT
 * 4790 DRAW"bm40,180e10u50h10r5f5u40h5r5f5d50e30r5g35d40f10l25":PAINT(50,179),1,3
 * 4800 X=40:Y=110:GOSUB 4810:X=45:Y=70:GOSUB 4810:X=90:Y=95:GOSUB 4810
 * 4810 FOR Z4=1 TO 50:LINE(X,Y)-STEP(RND*60-30,RND*60-30):NEXT:RETURN
 */
public class Turtle {
	private final Pattern drawPattern;
	private final Pattern movePattern;
	private final Pattern singleValuePattern;
	private final Canvas canvas;
	private Paint paint;

	// Starting position is at the center of the screen
	private Point position;
	private Point returnTo;
	private int scale;
	private boolean penDown;

	// Save the path in case it needs to be filled
	private Path path;
	private Paint fillPaint;

	public Turtle(Canvas canvas) {
		position = new Point(160, 100);
		this.canvas = canvas;

		paint = new Paint();
		paint.setAntiAlias(false);
		updatePaint(3);

		fillPaint = new Paint();
		fillPaint.setAntiAlias(false);
		fillPaint.setStyle(Paint.Style.FILL);

		String moveString = "m(\\d+),(\\d+)";
		String singleValueString = "([udlrefhcsag])(\\d+)";
		movePattern = Pattern.compile(moveString);
		singleValuePattern = Pattern.compile(singleValueString);
		drawPattern = Pattern.compile("b|n|" + moveString + "|" + singleValueString);
		resetPath();

		scale = 1;
		penDown = false;
	}

	private void resetPath() {
		path = new Path();
		path.moveTo(position.x, position.y);
	}

	private void updatePaint(int value) {
		paint.setColor(RgbColor.get(value).getColorInt());
	}

	public void draw(String commandLine) {
		Matcher matcher = drawPattern.matcher(commandLine.toLowerCase());
		while (matcher.find()) {
			String command = matcher.group(0);
			switch (command) {
				case "c":
					updatePaint(Integer.parseInt(matcher.group(1)));
					break;
				case "b":
					penUp();
					break;
				case "n":
					markReturnPoint();
					break;
				default:
					handleMovementCommand(command);
					break;
			}
		}
	}

	public void penUp() {
		penDown = false;
	}

	private void markReturnPoint() {
		returnTo = position;
	}

	private void returnToPrevious() {
		if (returnTo != null) {
			moveTo(returnTo);
			returnTo = null;
		}
	}

	private void handleMovementCommand(String command) {
		Matcher matcher = movePattern.matcher(command);
		if (matcher.matches()) {
			moveTo(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
		} else {
			matcher = singleValuePattern.matcher(command);
			if (matcher.matches()) {
				handleDirectionMove(matcher.group(1), Integer.parseInt(matcher.group(2)));
			} else {
				Log.d("Turtle", "Didn't know what to do with " + command);
			}
		}
		returnToPrevious();
	}

	public void moveTo(int newX, int newY) {
		if (penDown) {
			canvas.drawLine(position.x, position.y, newX, newY, paint);
			// Update the fill path
			path.lineTo(newX, newY);
		} else {
			path.reset();
			path.moveTo(newX, newY);
		}
		position = new Point(newX, newY);
		penDown = true;
	}

	private void moveTo(Point newPosition) {
		moveTo(newPosition.x, newPosition.y);
	}

	private void handleDirectionMove(String command, int magnitude) {
		switch (command) {
			case "c":
				updatePaint(magnitude);
				break;
			case "s":
				scale = magnitude;
				break;
			case "u":
				scaledMove(magnitude, 0, -1);
				break;
			case "d":
				scaledMove(magnitude, 0, 1);
				break;
			case "l":
				scaledMove(magnitude, -1, 0);
				break;
			case "r":
				scaledMove(magnitude, 1, 0);
				break;
			case "e":
				scaledMove(magnitude, 1, -1);
				break;
			case "h":
				scaledMove(magnitude, -1, -1);
				break;
			case "g":
				scaledMove(magnitude, -1, 1);
				break;
			case "f":
				scaledMove(magnitude, 1, 1);
				break;
		}
	}

	private void scaledMove(int magnitude, int stepX, int stepY) {
		moveTo(position.x + magnitude * stepX * scale, position.y + magnitude * stepY * scale);
	}

	private void fillPath(RgbColor color, boolean redrawOutline) {
		fillPaint.setColor(color.getColorInt());
		canvas.drawPath(path, fillPaint);

		if (redrawOutline) {
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawPath(path, paint);
		}

		resetPath();
	}

	public void fillPath(RgbColor color) {
		fillPath(color, true);
	}

	public void setColor(RgbColor color) {
		updatePaint(color.getValue());
	}
}
