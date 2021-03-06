package com.apollowebworks.mostamazingthing.ui.model;

import android.graphics.Color;
import android.util.SparseArray;

public enum RgbColor {
	BLACK(0),
	CYAN(1),
	MAGENTA(2),
	WHITE(3),
	TRANSPARENT(4);

	private final int value;
	static SparseArray<RgbColor> map;

	RgbColor(int value) {
		this.value = value;
	}

	public static RgbColor get(int i) {
		if (map == null) {
			map = new SparseArray<>();
			for (RgbColor color : RgbColor.values()) {
				map.put(color.value, color);
			}
		}
		return map.get(i);
	}

	public int getColorInt() {
		switch(this) {
			case WHITE: return Color.WHITE;
			case MAGENTA: return Color.MAGENTA;
			case CYAN: return Color.CYAN;
			case BLACK: return Color.BLACK;
			default: return 0x00000000;
		}
	}

	public int getValue() {
		return value;
	}
}
