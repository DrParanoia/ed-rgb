package com.bmc.elite.mappings;

public class ColorGroups {
    public static final Integer[] HUD_MODE_COMBAT = colorsToPercentArray(255, 80, 0);
    public static final Integer[] HUD_MODE_DISCOVERY = colorsToPercentArray(0, 160, 255);

    public static final Integer[] OTHER = colorsToPercentArray(60, 0, 0);
    public static final Integer[] HUD = colorsToPercentArray(255, 80, 0);
    public static final Integer[] SHIP_STUFF = colorsToPercentArray(0, 255, 0);
    public static final Integer[] CAMERA = colorsToPercentArray(71, 164, 79);

    public static final Integer[] DEFENCE = colorsToPercentArray(0, 220, 255);
    public static final Integer[] DEFENCE_DIMMED = colorsToPercentArray(0, 100, 114);
    public static final Integer[] OFFENCE = colorsToPercentArray(255, 0, 0);
    public static final Integer[] OFFENCE_DIMMED = colorsToPercentArray(127, 0, 0);
    public static final Integer[] MOVEMENT_SPEED = colorsToPercentArray(136, 0, 255);
    public static final Integer[] MOVEMENT_SPEED_DIMMED = colorsToPercentArray(68, 0, 127);

    public static final Integer[] MOVEMENT_SECONDARY = colorsToPercentArray(255, 0, 255);
    public static final Integer[] WING = colorsToPercentArray(0, 0, 255);
    public static final Integer[] NAVIGATION = colorsToPercentArray(255, 220, 0);

    public static final Integer[] MODE_ENABLE = colorsToPercentArray(153, 167, 255);
    public static final Integer[] MODE_DISABLE = colorsToPercentArray(61, 88, 156);


    private static Integer colorToPercent(int colorValue) {
        return Math.round((float)100/255*colorValue);
    }
    private static Integer[] colorsToPercentArray(int red, int green, int blue) {
        return new Integer[] {
                colorToPercent(red),
                colorToPercent(green),
                colorToPercent(blue)
        };
    }
}
