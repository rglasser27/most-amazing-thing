package com.apollowebworks.mostamazingthing.util;

public class StringUtil {

	private static final String BRACKETS = ("\\{.*?\\}");

	private StringUtil() {
	}

	public static String substitute(String base, String... terms) {
		String result = base;
		for (int i = 0; i < terms.length; i++) {
			result = result.replaceFirst(BRACKETS, terms[i]);
		}
		return result;
	}
}
