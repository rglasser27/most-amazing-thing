package com.apollowebworks.mostamazingthing.android;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.apollowebworks.mostamazingthing.controller.InSearchController;

public class MainView extends View {
	private InSearchController inSearchController;
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

	public void setInSearchController(final InSearchController inSearchController) {
		this.inSearchController = inSearchController;
		MainListeners listeners = new MainListeners(inSearchController);
		setOnTouchListener(listeners);
	}

	public InSearchController getInSearchController() {
		return inSearchController;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (inSearchController != null) {
			inSearchController.drawScene(canvas);
		}
	}


	public void setContext(Context context) {
		this.context = context;
	}
}
