package com.apollowebworks.mostamazingthing.scene.misc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import com.apollowebworks.mostamazingthing.controller.InSearchController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;

public class MarqueeScene extends Scene {

	public MarqueeScene(InSearchController inSearchController) {
		super(inSearchController);
	}

	@Override
	public SceneId getId() {
		return SceneId.MARQUEE;
	}

	@Override
	public void draw(Canvas canvas, Resources resources, Context context) {
		newFrame();
		drawText(5, 5, "Dear Kiddo,");
		drawText(8, 3, "The Most Amazing Thing is out");
		drawText(10, 1, "there somewhere, but you'll need");
		drawText(12, 1, "my help to find it. Come to my");

		drawText(14,1, "underground room to prepare for");
		drawText(16,1, "your journey.");

		drawText( 20,6,"Yours Truly,");
		drawText( 22,13,"Uncle Smoke Bailey");

		drawFinalFrame(canvas);
	}
}
