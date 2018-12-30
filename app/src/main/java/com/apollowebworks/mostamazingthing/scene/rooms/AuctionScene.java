package com.apollowebworks.mostamazingthing.scene.rooms;

import android.graphics.Canvas;
import android.util.Log;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.TextButton;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 6050 LOCATE 23,2:PRINT"For sale from from Smoke's travels:";:P=13:GOSUB 25070:LOCATE 25,10:PRINT LEFT$(B$,X-1);:P=43:GOSUB 25070
 */
public class AuctionScene extends RoomScene {
	private static final String TAG = "RoomScene";
	private final List<TextButton> numberButtons;

	int price = 0;

	public AuctionScene(SceneController sceneController) {
		super(sceneController);
		this.setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.AUCTION));
		numberButtons = new ArrayList<>();
		for (Integer i = 0; i < 10; i++) {
			TextButton button = new TextButton(this, 23, 8 + (2 * i), i.toString(), controller.getTextPaint());
			numberButtons.add(button);
			addButton(button);
		}
	}

	@Override
	public void drawToBuffer(Canvas canvas) {

	}

	@Override
	public SceneId getId() {
		return SceneId.AUCTION;
	}

	@Override
	public void buttonEvent(TextButton button) {
		for (int i = 0; i < 10; i++) {
			if (button == numberButtons.get(i)) {
				price = i;
				Log.d(TAG, "Here is my price: " + i);
				return;
			}
		}
		super.buttonEvent(button);
	}
}
