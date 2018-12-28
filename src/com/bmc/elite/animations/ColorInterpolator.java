package com.bmc.elite.animations;

public final class ColorInterpolator {
 
	/**
	 * Returns an interpolated color between multiple colors.
	 * <p>
	 * Example steps and returns when using the colors red, green and blue:
	 * <ul>
	 * <li>0.0f - red</li>
	 * <li>0.25f - yellow</li>
	 * <li>0.5f - green</li>
	 * <li>0.75f - cyan</li>
	 * <li>1.0f - blue</li>
	 * </ul>
	 * 
	 * @param step
	 *            the interpolation step in range from 0.0f to 1.0f
	 * @param colors
	 *            multiple (at least one) colors to interpolate over
	 * 
	 * @return color corresponding to the step
	 */
	public static int interpolate(float step, int... colors) {
		// Cutoff to range between 0.0f and 1.0f
		step = Math.max(Math.min(step, 1.0f), 0.0f);
 
		switch (colors.length) {
		case 0:
			throw new IllegalArgumentException("At least one color required.");
 
		case 1:
			return colors[0];
 
		case 2:
			return interpolateTwoColors(step, colors[0], colors[1]);
 
		default:
			// Find local colors to interpolate between:
			
			// Index of first color, because cast from float to int rounds down
			int firstColorIndex = (int) (step * (colors.length - 1));
 
			// Special case: last color (step >= 1.0f)
			if (firstColorIndex == colors.length - 1) {
				return colors[colors.length - 1];
			}
 
			// Calculate localStep between local colors:
			
			// stepAtFirstColorIndex will be a bit smaller than step
			float stepAtFirstColorIndex = (float) firstColorIndex
					/ (colors.length - 1);
					
			// multiply to increase values to range between 0.0f and 1.0f
			float localStep = (step - stepAtFirstColorIndex)
					* (colors.length - 1);
					
			return interpolateTwoColors(localStep, colors[firstColorIndex],
					colors[firstColorIndex + 1]);
		}
 
	}
 
	/**
	 * Returns an interpolated color between two colors.
	 * 
	 * @param step
	 *            interpolation step in range from 0.0f to 1.0f
	 * @param color1
	 *            the first color
	 * @param color2
	 *            the second color
	 * 
	 * @return interpolated color which may lie between the two colors
	 */
	private static int interpolateTwoColors(float step, int color1, int color2) {
		// Cutoff to range between 0.0f and 1.0f
		step = Math.max(Math.min(step, 1.0f), 0.0f);
 
		// Calculate difference between alpha, red, green and blue channels
		int deltaAlpha = alpha(color2) - alpha(color1);
		int deltaRed = red(color2) - red(color1);
		int deltaGreen = green(color2) - green(color1);
		int deltaBlue = blue(color2) - blue(color1);
 
		// Result channel lies between first and second colors channel
		int resultAlpha = (int) (alpha(color1) + (deltaAlpha * step));
		int resultRed = (int) (red(color1) + (deltaRed * step));
		int resultGreen = (int) (green(color1) + (deltaGreen * step));
		int resultBlue = (int) (blue(color1) + (deltaBlue * step));
 
		// Cutoff to ranges between 0 and 255
		resultAlpha = Math.max(Math.min(resultAlpha, 255), 0);
		resultRed = Math.max(Math.min(resultRed, 255), 0);
		resultGreen = Math.max(Math.min(resultGreen, 255), 0);
		resultBlue = Math.max(Math.min(resultBlue, 255), 0);
 
		// Combine results
		return resultAlpha << 24 | resultRed << 16 | resultGreen << 8 | resultBlue;
	}
 
	/**
	 * returns alpha channel of a color
	 * 
	 * @param color
	 *            the color to get the alpha channel from
	 * @return alpha channel of the color
	 */
	private static int alpha(int color) {
		// shift bits: 0xDEADBEEF -> 0x000000DE
		// and apply mask: 0x000000DE -> 0x000000DE
		return color >> 24 & 0xFF;
	}
 
	/**
	 * returns red channel of a color
	 * 
	 * @param color
	 *            the color to get the red channel from
	 * @return red channel of the color
	 */
	private static int red(int color) {
		// shift bits: 0xDEADBEEF -> 0x0000DEAD
		// and apply mask: 0x0000DEAD -> 0x000000AD
		return color >> 16 & 0xFF;
	}
 
	/**
	 * returns green channel of a color
	 * 
	 * @param color
	 *            the color to get the green channel from
	 * @return green channel of the color
	 */
	private static int green(int color) {
		// shift bits: 0xDEADBEEF -> 0x00DEADBE
		// and apply mask: 0x00DEADBE -> 0x000000BE
		return color >> 8 & 0xFF;
	}
 
	/**
	 * returns blue channel of a color
	 * 
	 * @param color
	 *            the color to get the blue channel from
	 * @return blue channel of the color
	 */
	private static int blue(int color) {
		// shift bits: 0xDEADBEEF -> 0xDEADBEEF
		// and apply mask: 0xDEADBEEF -> 0x000000EF
		return color & 0xFF;
	}
 
}