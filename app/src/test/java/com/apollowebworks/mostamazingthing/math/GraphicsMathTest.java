package com.apollowebworks.mostamazingthing.math;

import com.apollowebworks.mostamazingthing.math.TestRect;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GraphicsMathTest {
	private static final float RELATIVE_WIDTH = 300.f;
	private static final float RELATIVE_HEIGHT = 200.f;
	private static final int CWIDTH = 1500;
	private static final int CHEIGHT = 1000;

	private static final int IMAGE_WIDTH = 80;
	private static final int IMAGE_HEIGHT = 100;

	private static final float MARGIN = 2.f;

	@Test
	public void centeredOnScreen() throws Exception {
		Assert.assertEquals(new TestRect(353.f, 5.f, 1146.f, 995.f), centeredOnScreen((float) IMAGE_WIDTH / IMAGE_HEIGHT));
	}

	private TestRect centeredOnScreen(float ratio) {

		float newHeight = RELATIVE_HEIGHT - MARGIN;
		float newWidth = newHeight * ratio;
		TestRect relativeSize = new TestRect(-newWidth / 2.f,
				newHeight / 2.f,
				newWidth / 2.f,
				-newHeight / 2.f);
		return adjustToScreen(relativeSize);
	}

	private TestRect adjustToScreen(TestRect rect) {
		int left = adjustX(rect.left);
		int top = adjustY(rect.top);
		int right = adjustX(rect.right);
		int bottom = adjustY(rect.bottom);
		return new TestRect(left,
				top,
				right,
				bottom);

	}

	private int adjustX(float x) {
		// The point starts out centered, so move it left
		float newX = x + (RELATIVE_WIDTH / 2.f);
		// Change from relative width to actual screen width
		return (int) (newX * CWIDTH / RELATIVE_WIDTH);
	}

	private int adjustY(float y) {
		// Positive y goes up instead of down in the relative notation, so invert it.
		// Also the point starts out centered, so move it up
		float newY = (RELATIVE_HEIGHT / 2.f) - y;
		// Change from relative width to actual screen width
		return (int) (newY * CHEIGHT / RELATIVE_HEIGHT);
	}

}
