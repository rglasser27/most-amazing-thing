package com.apollowebworks.mostamazingthing.scene.carext;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import com.apollowebworks.mostamazingthing.controller.SceneController;
import com.apollowebworks.mostamazingthing.scene.Scene;
import com.apollowebworks.mostamazingthing.scene.SceneId;
import com.apollowebworks.mostamazingthing.ui.components.Turtle;
import com.apollowebworks.mostamazingthing.ui.manager.ImageManager;
import com.apollowebworks.mostamazingthing.world.model.JetpackGuy;
import com.apollowebworks.mostamazingthing.world.model.MetallicaEntrance;
import com.apollowebworks.mostamazingthing.world.model.NightRock;
import com.apollowebworks.mostamazingthing.world.model.PopberryTree;
import com.apollowebworks.mostamazingthing.world.model.WorldObject;

import java.util.Arrays;
import java.util.List;

public class CarExtScene extends Scene {

	private static final String TAG = CarExtScene.class.getName();

	private final RectF doorArea;
	private RectF entranceArea;

	private List<WorldObject> objectsInScene;
	private final JetpackGuy jetpackGuy;
	private int distanceFromCar = 0;

	public CarExtScene(SceneController sceneController) {
		super(sceneController);
		Bitmap carBackground = sceneController.getImageManager().getBitmap(ImageManager.CAREXT);
		this.setBackgroundImage(carBackground);
		PopberryTree tree = new PopberryTree();
		NightRock rock = new NightRock();
		MetallicaEntrance entrance = new MetallicaEntrance();
		Bitmap[] jetpackFrames = new Bitmap[]{
				sceneController.getImageManager().getBitmap(ImageManager.JPLEFT_2),
				sceneController.getImageManager().getBitmap(ImageManager.JPLEFT_1),
				sceneController.getImageManager().getBitmap(ImageManager.JPRIGHT_1),
				sceneController.getImageManager().getBitmap(ImageManager.JPRIGHT_2),
		};
		jetpackGuy = new JetpackGuy(new PointF(250, 150), jetpackFrames);
		objectsInScene = Arrays.asList(entrance, jetpackGuy);
//		objectsInScene = Arrays.asList(tree, jetpackGuy);

		doorArea = new RectF(227, 135, 264, 171);
		entranceArea = new RectF(70, 125, 119, 153);
	}

	@Override
	public SceneId getId() {
		return SceneId.CAREXT;
	}

	@Override
	public void drawToBuffer(Canvas canvas) {
		if (distanceFromCar == 0) {
			drawText(19, 21, ">>B-Liner");
			drawText(19, 33, ">>>");
			for (WorldObject o : objectsInScene) {
				o.draw(canvas);
			}
		} else {
			canvas.drawColor(Color.BLACK);
			jetpackGuy.draw(canvas);
			Turtle turtle = new Turtle(canvas);

			// Draw an arrow toward the car. This is not in the original version, but it's a helpful guide.
			if (distanceFromCar < 0) {
				turtle.draw("bm270,20r10h4m280,20g4");
			} else {
				turtle.draw("bm30,20l10e3m20,21f3");
			}
		}
	}

	@Override
	protected boolean onDownTouch(PointF point) {
		if (distanceFromCar == 0) {
			boolean handled = checkHotspots(point);
			if (handled) {
				return true;
			}
		}
		return jetpackGuy.handleTouch(point);
	}

	private boolean checkHotspots(PointF targetLocation) {
		if (isScreenAreaSelected(targetLocation, doorArea)) {
			jetpackGuy.stop();
			controller.activateScene(SceneId.CARINT);
			return true;
		} else if (isAtMetallica() && isScreenAreaSelected(targetLocation, entranceArea)) {
			jetpackGuy.stop();
			controller.activateScene(SceneId.ELEVATOR);
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the avatar is in the target location, and the user tapped on the target location.
	 */
	private boolean isScreenAreaSelected(PointF targetLocation, RectF location) {
		return location.contains(targetLocation.x, targetLocation.y)
				&& location.contains(jetpackGuy.getPosition().x, jetpackGuy.getPosition().y);
	}

	@Override
	public boolean tick(Long msElapsed) {
		boolean jetpackGuyMoved = jetpackGuy.move(msElapsed);
		if (jetpackGuyMoved) {
			int switchedLocations = jetpackGuy.checkSides();
			if (switchedLocations != 0) {
				this.distanceFromCar += switchedLocations;
			}
		}

		return jetpackGuyMoved;
	}

	private boolean isAtMetallica() {
		return true;
	}
}
