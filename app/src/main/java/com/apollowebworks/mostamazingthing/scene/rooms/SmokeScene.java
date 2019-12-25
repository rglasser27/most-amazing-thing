package com.apollowebworks.mostamazingthing.scene.rooms;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import com.apollowebworks.mostamazingthing.R;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;
import com.apollowebworks.mostamazingthing.world.state.GameState;

import java.util.Random;

public class SmokeScene extends RoomScene {
	private static final String TAG = "SmokeScene";
	private static final int FULL_NAP = 1200;

	private final String[] smokeAdvice;
	private final String[] smokeChat;
	private final String[] items;
//	private final TextButton wakeButton;

	private Random random;
	private NapValue nap;

	private enum NapValue {
		ASLEEP,
		ADVICE,
		ITEM,
		HAS_ITEM,
		ANGRY
	}

	public SmokeScene(SceneController sceneController) {
		super(sceneController, 21, 4);
		Log.d(TAG, "Entering smoke's room");
		// TODO: Instead of making smoke_hey a button, make a new type of animation where it appears for a second, then disappears.
//		wakeButton = new TextButton(this, 18, 4, sceneController.getString(R.string.smoke_hey), controller.getTextPaint());
//		addButton(wakeButton);
		setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.SMOKE));

		smokeAdvice = controller.getStrings(R.array.smoke_advice);
//		smokeAdvice = Arrays.copyOfRange(controller.getStrings(R.array.smoke_advice), 0, 3);
		smokeChat = controller.getStrings(R.array.smoke_chat);
		items = controller.getStrings(R.array.smoke_items);

		random = new Random();
//		nap = 1000 + random.nextInt(2000);
		nap = NapValue.ASLEEP;
	}

	@Override
	public void drawToBuffer(Canvas canvas) {
		if (awake()) {
			// Draw Smoke's eyes?
		}
	}

	private boolean awake() {
//		return nap < FULL_NAP;
		return nap != NapValue.ASLEEP;
	}

	@Override
	public SceneId getId() {
		return SceneId.SMOKE;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	public boolean onDownTouch(PointF point) {
		wakeSmoke();
		return true;
	}

//	@Override
//	public void buttonEvent(TextButton button) {
//		if (button == wakeButton) {
//			wakeSmoke();
//		} else {
//			super.buttonEvent(button);
//		}
//	}

	private void wakeSmoke() {
		String[] lines = null;
		String line = null;
		Log.d(TAG, "Smoke is awake. Transitioning from state " + nap.name());
		GameState state = controller.getGameState();
		switch (nap) {
			case ASLEEP:
				line = smokeAdvice[random.nextInt(smokeAdvice.length)];
				int adviceCount = state.getAdvice() + 1;
				state.setAdvice(adviceCount);
				if (adviceCount > smokeAdvice.length) {
					line = controller.getString(R.string.smoke_repeat) + ";" + line;
				}
				int item = state.getItem();

				if (item > 0) {
					Log.d(TAG, "Skipping a step because player already has item [" + items[item - 1] + "]");
					nap = NapValue.HAS_ITEM;
				} else {
					nap = NapValue.ADVICE;
				}
				break;
			case ADVICE:
				lines = smokeChat;
				nap = NapValue.ITEM;
				break;
			case ITEM:
				int newItem = random.nextInt(items.length);
				state.setItem(newItem + 1);
				int itemValue = random.nextInt(8) + 1;
				state.setItemValue(itemValue);
				String itemName = items[newItem];
				Log.d(TAG, "Player now has new item [" + itemName + "], worth [" + itemValue + "]");
				line = controller.getString(R.string.smoke_auction) + " " + itemName + ".";
				nap = NapValue.HAS_ITEM;
				break;
			case HAS_ITEM:
				line = controller.getString(R.string.smoke_hasitem);
				nap = NapValue.ANGRY;
				break;
			default:
				line = controller.getString(R.string.smoke_angry);
		}
		Log.d(TAG, "New state is " + nap.name());
		if (lines != null) {
			line = lines[random.nextInt(lines.length)];
		}
		addTextAnimation(line, 9, 1);
	}
}
