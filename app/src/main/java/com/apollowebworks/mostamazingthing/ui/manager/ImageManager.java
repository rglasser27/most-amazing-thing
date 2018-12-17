package com.apollowebworks.mostamazingthing.ui.manager;

import android.content.res.Resources;
import android.graphics.Bitmap;
import com.apollowebworks.mostamazingthing.R;
import com.apollowebworks.mostamazingthing.ui.exception.DecrunchException;
import com.apollowebworks.mostamazingthing.ui.model.DosBitmap;
import com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap;
import com.apollowebworks.mostamazingthing.ui.model.RgbColor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap.SCREEN_HEIGHT;
import static com.apollowebworks.mostamazingthing.ui.model.FullScreenBitmap.SCREEN_WIDTH;

public class ImageManager {

	private static final int FIRST_OFFSET = 56;
	private static final int SECOND_OFFSET = FIRST_OFFSET + SCREEN_WIDTH * SCREEN_HEIGHT + 896;

	private static final int YOFF = 8;

//	private static int SCREEN_WIDTH_2 = 12;
//	private static int SCREEN_HEIGHT_2 = 17;
//	private static final int FIRST_OFFSET_2 = 16;
//	private static final int SECOND_OFFSET_2 = FIRST_OFFSET_2 + SCREEN_WIDTH_2 * SCREEN_HEIGHT_2 / 4;

	// 4 pixels represented per byte
	private static final int BUFFER_SIZE = 16399;

	public static final String JPLEFT_1 = "jpleft1";
	public static final String JPLEFT_2 = "jpleft2";
	public static final String JPRIGHT_1 = "jpright1";
	public static final String JPRIGHT_2 = "jpright2";
	public static final String AUCTION = "auction";
	public static final String CAREXT = "carext";
	public static final String CARINT = "carint";
	public static final String ELEVPIC = "elevpic";
	public static final String SMOKE = "smoke";
	public static final String STORE = "store";
	public static final String TABLE = "table";

	private Map<String, DosBitmap> imageMap = new HashMap<>();
	private Map<String, Bitmap> bitmaps = new HashMap<>();

	public void readImages(Resources resources) throws DecrunchException {

		readCrunchImage(AUCTION, resources.openRawResource(R.raw.auction), 0);
		readCrunchImage(CAREXT, resources.openRawResource(R.raw.carext), 0);
		readCrunchImage(CARINT, resources.openRawResource(R.raw.carint), 0);
		readCrunchImage(ELEVPIC, resources.openRawResource(R.raw.elevpic), 0);
		readCrunchImage(SMOKE, resources.openRawResource(R.raw.smoke), 8);
		readCrunchImage(STORE, resources.openRawResource(R.raw.store), 0);
		readCrunchImage(TABLE, resources.openRawResource(R.raw.table), 0);

		readNumbersAsBitmap(JPLEFT_1, resources.openRawResource(R.raw.jpleft1));
		readNumbersAsBitmap(JPLEFT_2, resources.openRawResource(R.raw.jpleft2));
		readNumbersAsBitmap(JPRIGHT_1, resources.openRawResource(R.raw.jpright1));
		readNumbersAsBitmap(JPRIGHT_2, resources.openRawResource(R.raw.jpright2));
	}

	public DosBitmap getImage(String name) {
		return imageMap.get(name);
	}

	public Bitmap getBitmap(String name) {
		return bitmaps.get(name);
	}

	private Bitmap getImageAsBitmap(DosBitmap image) {
		Bitmap bitmap = Bitmap.createBitmap(image.getBmWidth(), image.getBmHeight(), Bitmap.Config.ARGB_8888);
		for (int y = 0; y < image.getBmHeight(); y++) {
			for (int x = 0; x < image.getBmWidth(); x++) {
				bitmap.setPixel(x, y, image.getPixel(x, y).getColorInt());
			}
		}
		return bitmap;
	}

	private void readCrunchImage(String name, InputStream stream, int offset) throws DecrunchException {
		FullScreenBitmap image = new FullScreenBitmap();
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
		if (offset != 0) {
			shiftDown(image, offset);
		}
		imageMap.put(name, image);
		bitmaps.put(name, getImageAsBitmap(image));
	}

	private void shiftDown(FullScreenBitmap image, int offset) {
		for (int y = image.getBmHeight() - 1; y >= offset; y --) {
			for (int x = 0; x < image.getBmWidth(); x ++) {
				image.setPixel(x, y, image.getPixel(x, y-offset));
			}
		}
		for (int y = 0; y < offset; y ++) {
			for (int x = 0; x < image.getBmWidth(); x ++) {
				image.setPixel(x, y, RgbColor.BLACK);
			}
		}
	}

	private void readNumbersAsBitmap(String name, InputStream stream) throws DecrunchException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		List<List<RgbColor>> lines = new ArrayList<>();
		DosBitmap image;
		try {
			boolean done = false;
			while (!done) {
				done = true;
				String line = reader.readLine();
				if (line != null) {
					String[] values = line.split("\\s+");
					List<RgbColor> row = new ArrayList<>();
					for (String value : values) {
						if (!value.isEmpty()) {
							RgbColor color = RgbColor.get(Integer.parseInt(value));
//							if (color == RgbColor.BLACK) {
//								color = RgbColor.TRANSPARENT;
//							}
							row.add(color);
						}
					}
					if (row.size() > 0) {
						done = false;
						lines.add(row);
					}
				}
			}
			image = new DosBitmap(lines);
			Bitmap bitmap = getImageAsBitmap(image);
			imageMap.put(name, image);
			bitmaps.put(name, bitmap);
		} catch (IOException e) {
			throw new DecrunchException("IOException caught while reading file");
		}
	}


	private void scanLines(byte[] bytes, FullScreenBitmap image, int startY, int offset) throws IOException {
		int y = startY;

		boolean done = false;
		while (!done) {
			updateImageLine(image, bytes, y, offset);
			y += 2;
			offset += SCREEN_WIDTH * 2;
			if (y >= SCREEN_HEIGHT) {
				done = true;
			}
		}
	}

	private void updateImageLine(FullScreenBitmap image, byte[] bytes, int y, int offset) {
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
