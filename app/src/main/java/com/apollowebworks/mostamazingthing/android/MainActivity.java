package com.apollowebworks.mostamazingthing.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import com.apollowebworks.mostamazingthing.R;
import com.apollowebworks.mostamazingthing.controller.InSearchController;

import static com.apollowebworks.mostamazingthing.controller.InSearchController.FPS;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {

	private InSearchController inSearchController;

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	private final View.OnTouchListener controllerOnTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (inSearchController != null) {
				inSearchController.onTouch(motionEvent);
			}
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		MainView mContentView = findViewById(R.id.fullscreen_content);

		inSearchController = new InSearchController(getResources(), mContentView);
		mContentView.setInSearchController(inSearchController);

		new MainThread().start();
	}

	/**
	 * for tests
	 */
	public InSearchController getInSearchController() {
		return inSearchController;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}


	private class MainThread extends Thread {
		boolean done = false;

		@Override
		public void run() {
			try {
				synchronized (this) {
					wait(1000);
					while (!done) {
						synchronized (this) {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									inSearchController.tick();
								}
							});
						}
						sleep(1000 / FPS);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		void kill() {
			done = true;
		}
	}
}
