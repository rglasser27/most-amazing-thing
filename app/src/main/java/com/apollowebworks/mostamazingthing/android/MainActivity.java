package com.apollowebworks.mostamazingthing.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import com.apollowebworks.mostamazingthing.R;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;

import static com.apollowebworks.mostamazingthing.controller.SceneController.FPS;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {

	private SceneController sceneController;

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	private final View.OnTouchListener controllerOnTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (sceneController != null) {
				sceneController.onTouch(motionEvent);
			}
			return false;
		}
	};
	private ImageManager imageManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		MainView mContentView = findViewById(R.id.fullscreen_content);

		sceneController = new SceneController(getResources(), mContentView);
		mContentView.setSceneController(sceneController);

		new MainThread().start();
	}

	/**
	 * for tests
	 */
	public SceneController getSceneController() {
		return sceneController;
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
									sceneController.tick();
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
