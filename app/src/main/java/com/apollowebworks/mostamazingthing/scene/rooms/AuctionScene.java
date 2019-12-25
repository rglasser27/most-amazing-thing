package com.apollowebworks.mostamazingthing.scene.rooms;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import com.apollowebworks.mostamazingthing.R;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.TextButton;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;
import com.apollowebworks.mostamazingthing.util.StringUtil;

/**
 * 6050 LOCATE 23,2:PRINT"For sale from from Smoke's travels:";:P=13:GOSUB 25070:LOCATE 25,10:PRINT LEFT$(B$,X-1);:P=43:GOSUB 25070
 * 6205 P=23:L=25:GOSUB 25320:P=2:GOSUB 25070:SOUND 2000,10:GOSUB 25070:LOCATE 23,12:PRINT"Fellow Metallicans!";:P=10:GOSUB 25070:P=23:L=25:GOSUB 25320
 * :LOCATE 23,8:PRINT"  How do you like this price:    CHIPS?";
 */
public class AuctionScene extends RoomScene {
	private static final String TAG = "RoomScene";

	private static final int LEFT = 1;
	private static final int CURRENT = 2;
	private static final int RIGHT = 3;
	private static final int REACTION = 4;
	private static final int FINAL = 5;

	private final TextButton buttonCurrentPrice;

	enum State {
		ENTER,
		PROPOSE,
		OFFER,
		DONE
	}

	private Integer price;

	public AuctionScene(SceneController sceneController) {
		super(sceneController);
		Log.d(TAG, "Entering the auction");
		this.setBackgroundImage(sceneController.getImageManager().getBitmap(ImageManager.AUCTION));
		Paint paint = controller.getTextPaint();
		price = 0;

		TextButton buttonLeft = new TextButton(LEFT, this, 23, 6, "<", paint);
		buttonCurrentPrice = new TextButton(CURRENT, this, 23, 8, "0", paint);
		TextButton buttonRight = new TextButton(RIGHT, this, 23, 10, ">", paint);
		TextButton buttonReaction = new TextButton(REACTION, this, 23, 13, controller.getString(R.string.auction_get_reaction_short), paint);
		TextButton buttonFinalPrice = new TextButton(FINAL, this, 23, 27, controller.getString(R.string.auction_final_price_short), paint);
		addButtons(buttonLeft, buttonRight, buttonCurrentPrice, buttonReaction, buttonFinalPrice);
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
		switch (button.getId()) {
			case LEFT:
				updatePrice(Math.max(0, price - 1));
				break;
			case RIGHT:
				updatePrice(Math.min(9, price + 1));
				break;
			case CURRENT:
			case REACTION:
				suggestPrice();
				break;
			case FINAL:
				setFinalPrice();
				break;
			default:
				super.buttonEvent(button);
		}
	}

	private void updatePrice(int newPrice) {
		price = newPrice;
		buttonCurrentPrice.setText(Integer.valueOf(newPrice).toString());
	}

	private void suggestPrice() {
		String offer = StringUtil.substitute(controller.getString(R.string.auction_offer_query), price.toString());
		Log.d(TAG, offer);
	}

	private void setFinalPrice() {
		String offer = StringUtil.substitute(controller.getString(R.string.auction_offer_final), price.toString());
		Log.d(TAG, offer);
	}
}
