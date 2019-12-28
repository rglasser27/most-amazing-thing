package com.apollowebworks.mostamazingthing.ui.components;

import android.graphics.Paint;
import android.graphics.PointF;
import com.apollowebworks.mostamazingthing.animation.AnimationChain;
import com.apollowebworks.mostamazingthing.animation.TextAnimation;
import com.apollowebworks.mostamazingthing.controller.SceneController;

import java.util.ArrayList;
import java.util.List;

public class GraphicsLayer {
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
}
