package com.apollowebworks.mostamazingthing.scene.rooms;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import com.apollowebworks.mostamazingthing.R;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.TextButton;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;

/**
 * 6050 LOCATE 23,2:PRINT"For sale from from Smoke's travels:";:P=13:GOSUB 25070:LOCATE 25,10:PRINT LEFT$(B$,X-1);:P=43:GOSUB 25070
 * 6205 P=23:L=25:GOSUB 25320:P=2:GOSUB 25070:SOUND 2000,10:GOSUB 25070:LOCATE 23,12:PRINT"Fellow Metallicans!";:P=10:GOSUB 25070:P=23:L=25:GOSUB 25320
 * :LOCATE 23,8:PRINT"  How do you like this price:    CHIPS?";
 */
public class AuctionScene extends RoomScene {
	private static final String TAG = "RoomScene";
	private final TextButton buttonLeft;
	private final TextButton buttonRight;
	private final TextButton buttonCurrentPrice;
	private final TextButton buttonReaction;
	private final TextButton buttonFinalPrice;

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

		buttonLeft = new TextButton(this, 23, 8, "<", paint);
		buttonCurrentPrice = new TextButton(this, 23, 10, "0", paint);
		buttonRight = new TextButton(this, 23, 12, ">", paint);
		buttonReaction = new TextButton(this, 23, 15, controller.getString(R.string.auction_get_reaction), paint);
		buttonFinalPrice = new TextButton(this, 23, 15, controller.getString(R.string.auction_final_price), paint);
//		addButtons(buttonLeft, buttonRight, buttonReaction, buttonFinalPrice);
		addButtons(buttonLeft, buttonRight, buttonCurrentPrice);

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
		if (button == buttonLeft) {
			updatePrice(Math.max (0, price - 1));
		} else if (button == buttonRight) {
			updatePrice(Math.min(9, price + 1));
		} else if (button == buttonCurrentPrice) {
			suggestPrice();
		} else if (button == buttonReaction) {
			suggestPrice();
		} else if (button == buttonFinalPrice){
			setFinalPrice();
		}
	}

	private void updatePrice(int newPrice) {
		price = newPrice;
		buttonCurrentPrice.setText(Integer.valueOf(newPrice).toString());
	}

	private void suggestPrice() {
		Log.d(TAG, controller.getString(R.string.auction_offer_query));
	}

	private void setFinalPrice() {

	}

}
