package com.apollowebworks.mostamazingthing.android;

import android.view.MotionEvent;
import android.view.View;
import com.apollowebworks.mostamazingthing.controller.InSearchController;

public class MainListeners implements View.OnTouchListener {

	private InSearchController controller;

	MainListeners(InSearchController controller) {
		this.controller = controller;
	}

	void addListenersToActivity(View view) {
		view.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return controller.onTouch(motionEvent);
	}
}
