package com.apollowebworks.mostamazingthing.scene.title;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.apollowebworks.mostamazingthing.R;
import com.apollowebworks.mostamazingthing.android.MainActivity;
import com.apollowebworks.mostamazingthing.android.MainView;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

//@Run(JUnit45AndHigherRunnerImpl.class)
//@Config(constants = BuildConfig.class)

@RunWith(AndroidJUnit4.class)
public class AndTitleSceneTest {
	public static final int CWIDTH = 1500;
	public static final int CHEIGHT = 1000;
	@Rule
	public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

	private TitleScene titleScene;
	private Canvas canvas;
	private Resources resources;
	private Drawable drawable;
	private SceneController sceneController;
	private MainActivity mainActivity;
	private MainView view;

	@Before
	public void setUp() throws Exception {
		Context appContext = InstrumentationRegistry.getTargetContext();
		mainActivity = mActivityRule.getActivity();
//		SceneController sceneController =
//				assertEquals("hello", appContext.getResources());
//		canvas = new Canvas.class);
//		drawable = mock(Drawable.class);

//		Activity mainActivity = Robolectric.buildActivity(MainActivity.class)
//				.create()
//				.resume()
//				.get();

//		resources = mainActivity.getResources();
//
//		when(canvas.getWidth()).thenReturn(1500);
//		when(canvas.getHeight()).thenReturn(1000);
//
		Bitmap bitmap = Bitmap.createBitmap(CWIDTH, CHEIGHT, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);

		sceneController = mainActivity.getSceneController();
//		titleScene = sceneController.activateScene(SceneId.TITLE);
		view = mainActivity.findViewById(R.id.fullscreen_content);
	}

//	@Test
	public void drawToBuffer() throws Exception {

		view.draw(canvas);
//		titleScene.draw(canvas, resources);
//		verify(drawable, times(1)).draw(canvas);
	}

//	@Test
	public void centeredOnScreen() throws Exception {
		assertRectsAreEqual(new Rect(1, 1, 1, 1), titleScene.centeredOnScreen(canvas, 50));

	}

	private void assertRectsAreEqual(Rect r1, Rect r2) {
		assertEquals(r1.left, r2.left);
		assertEquals(r1.right, r2.right);
		assertEquals(r1.top, r2.top);
		assertEquals(r1.bottom, r2.bottom);
	}

}
