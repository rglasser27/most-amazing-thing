package com.apollowebworks.mostamazingthing.android;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.apollowebworks.mostamazingthing.controller.SceneController;

public class MainView extends View {
	private SceneController sceneController;
	private Context context;

	public MainView(Context context) {
		super(context);
		setContext(context);
	}

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setSceneController(final SceneController sceneController) {
		this.sceneController = sceneController;
		MainListeners listeners = new MainListeners(sceneController);
		setOnTouchListener(listeners);
	}

	public SceneController getSceneController() {
		return sceneController;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (sceneController != null) {
			sceneController.drawScene(canvas);
		}
	}


	public void setContext(Context context) {
		this.context = context;
	}
}
