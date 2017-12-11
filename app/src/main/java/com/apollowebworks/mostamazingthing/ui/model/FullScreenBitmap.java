package com.apollowebworks.mostamazingthing.ui.model;

/**
 * there are 320x200=23040 pixels in a screen
 * Each pixel takes two bits
 * Four pixels in a line = 1 byte
 */
public class FullScreenBitmap extends DosBitmap {
	public static final int SCREEN_WIDTH = 320;
	public static final int SCREEN_HEIGHT = 200;
	private static final int PIXELS = SCREEN_WIDTH * SCREEN_HEIGHT;

	public FullScreenBitmap() {
		super(SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
