package com.apollowebworks.mostamazingthing.math;

import java.util.Objects;

public class TestRect {
	float left;
	float top;
	float right;
	float bottom;

	TestRect(float left, float top, float right, float bottom) {
		this.left = left;
		this.right = right;
		this.bottom = bottom;
		this.top = top;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TestRect testRect = (TestRect) o;
		return Float.compare(testRect.left, left) == 0 &&
				Float.compare(testRect.top, top) == 0 &&
				Float.compare(testRect.right, right) == 0 &&
				Float.compare(testRect.bottom, bottom) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(left, right, bottom, top);
	}

	@Override
	public String toString() {
		return "TestRect{" +
				"left=" + left +
				", top=" + top +
				", right=" + right +
				", bottom=" + bottom +
				'}';
	}
}
