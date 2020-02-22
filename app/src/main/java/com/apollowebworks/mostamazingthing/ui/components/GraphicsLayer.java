package com.apollowebworks.mostamazingthing.ui.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import com.apollowebworks.mostamazingthing.animation.AnimationChain;
import com.apollowebworks.mostamazingthing.animation.TextAnimation;
import com.apollowebworks.mostamazingthing.controller.SceneController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphicsLayer {
	private static final String TAG = GraphicsLayer.class.getName();

	private boolean visible;
	private final SceneController controller;
	private final Paint textPaint;
	private List<PointF> moarDots;
	private List<TextButton> buttons;
	private TextAnimation textAnimation;
	private AnimationChain animations;
	private boolean debugTouch;

	public GraphicsLayer(SceneController controller) {
		visible = true;
		this.controller = controller;
		textPaint = controller.getTextPaint();
		moarDots = new ArrayList<>();
		buttons = new ArrayList<>();
		debugTouch = false;
	}

	public void addButton(TextButton button) {
		buttons.add(button);
	}

	public void addButtons(TextButton... buttons) {
		Arrays.stream(buttons).forEach(this::addButton);
	}

	public void draw(Canvas canvas) {
		if (!visible) {
			return;
		}
		if (debugTouch) {
			for (PointF dot : moarDots) {
				Paint paint = new Paint();
				paint.setColor(Color.WHITE);
				canvas.drawCircle(dot.x, dot.y, 1, paint);
			}
		}
		if (textAnimation != null) {
			textAnimation.draw(canvas);
		}
		for (TextButton b : buttons) {
			b.draw(canvas);
		}
	}


	/**
	 * Handle a touch event from the app
	 *
	 * @param action the action from a {@link MotionEvent}
	 * @param point  where did the event occur
	 * @return true if anything changed and needs to be redrawn
	 */
	public final boolean onTouch(int action, PointF point) {
		debugTouch(action, point);
		return buttons.stream().anyMatch(button -> button.handle(action, point));
	}

	public void debugTouch(int action, PointF point) {
		if (action == MotionEvent.ACTION_DOWN) {
			Log.d(TAG, "Touched a point on the screen (" + point.x + ", " + point.y + ")");
			debugTouchDown(point);
		}
	}

	protected void addDot(PointF location) {
		moarDots.add(location);
		if (moarDots.size() > 10) {
			moarDots.remove(0);
		}
	}

	protected void debugTouchDown(PointF point) {
		moarDots.add(new PointF(point.x, point.y));
		if (moarDots.size() > 10) {
			moarDots.remove(0);
		}
	}
}
