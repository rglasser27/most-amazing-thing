package com.apollowebworks.mostamazingthing.graphics.manager;

import android.content.res.Resources;
import android.graphics.Bitmap;
import com.apollowebworks.mostamazingthing.R;
import com.apollowebworks.mostamazingthing.graphics.exception.DecrunchException;
import com.apollowebworks.mostamazingthing.graphics.model.FullScreenImage;
import com.apollowebworks.mostamazingthing.graphics.model.RgbColor;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.apollowebworks.mostamazingthing.graphics.model.FullScreenImage.SCREEN_HEIGHT;
import static com.apollowebworks.mostamazingthing.graphics.model.FullScreenImage.SCREEN_WIDTH;

public class ImageManager {

	private static final int FIRST_OFFSET = 56;
	private static final int SECOND_OFFSET = FIRST_OFFSET + SCREEN_WIDTH * SCREEN_HEIGHT + 896;

	// 4 pixels represented per byte
	private static final int BUFFER_SIZE = 16399;
	private Map<String, FullScreenImage> imageMap = new HashMap<>();
	private Map<String, Bitmap> bitmaps = new HashMap<>();

	public void readImages(Resources resources) throws DecrunchException {
		readCrunchImage("auction", resources.openRawResource(R.raw.auction));
		readCrunchImage("carext", resources.openRawResource(R.raw.carext));
		readCrunchImage("carint", resources.openRawResource(R.raw.carint));
		readCrunchImage("elevpic", resources.openRawResource(R.raw.elevpic));
		readCrunchImage("smoke", resources.openRawResource(R.raw.smoke));
		readCrunchImage("store", resources.openRawResource(R.raw.store));
		readCrunchImage("table", resources.openRawResource(R.raw.table));
	}

	public FullScreenImage getImage(String name) {
		return imageMap.get(name);
	}

	public Bitmap getBitmap(String name) {
		return bitmaps.get(name);
	}

	private Bitmap getImageAsBitmap(FullScreenImage image) {
		Bitmap bitmap = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, Bitmap.Config.ARGB_8888);
		for (int y = 0; y < SCREEN_HEIGHT; y ++) {
			for (int x = 0; x < SCREEN_WIDTH; x++) {
				bitmap.setPixel(x, y, image.getPixel(x, y).getColorInt());
			}
		}
		return bitmap;
	}

	private void readCrunchImage(String name, InputStream stream) throws DecrunchException {
		FullScreenImage image = new FullScreenImage();
		try {
			byte[] bytes = new byte[BUFFER_SIZE];
			int bytesRead = stream.read(bytes);
			if (bytesRead < BUFFER_SIZE) {
				throw new DecrunchException("Wrong number of bytes in image");
			}
			scanLines(bytes, image, 1, FIRST_OFFSET);
			scanLines(bytes, image, 0, SECOND_OFFSET);
		} catch (IOException e) {
			throw new DecrunchException("IOException caught while reading file");
		}
		imageMap.put(name, image);
		bitmaps.put(name, getImageAsBitmap(image));
	}

	private void scanLines(byte[] bytes, FullScreenImage image, int startY, int offset) throws IOException {
		int y = startY;

		boolean done = false;
		while (!done) {
			updateImageLine(image, bytes, y, offset);
			y += 2;
			offset += SCREEN_WIDTH * 2;
			if (y >= FullScreenImage.SCREEN_HEIGHT) {
				done = true;
			}
		}
	}

	private void updateImageLine(FullScreenImage image, byte[] bytes, int y, int offset) {
		for (int x = 0; x < SCREEN_WIDTH; x++) {
			image.setPixel(x, y, getColorAt(bytes, x, offset));
		}
	}

	private RgbColor getColorAt(byte[] bytes, int x, int offset) {
		return readBytePartAsColor(getBitPair(bytes, x, offset));
	}

	private int getBitPair(byte[] bytes, int x, int offset) {
		int bit1 = getBit(bytes, offset + x * 2);
		int bit2 = getBit(bytes, offset + 1 + x * 2);
		return (bit1 << 1) + bit2;
	}

	private int getBit(byte[] bytes, int offset) {
		return (bytes[offset / 8] >> (8 - (offset % 8) - 1)) & 1;
	}

	private RgbColor readBytePartAsColor(int code) {
		return RgbColor.get(code);
	}
}
