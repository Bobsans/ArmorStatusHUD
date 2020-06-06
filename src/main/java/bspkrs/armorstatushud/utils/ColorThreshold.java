package bspkrs.armorstatushud.utils;

import java.util.List;

public class ColorThreshold implements Comparable<ColorThreshold> {
    private final int threshold;
    private final String colorCode;

    public ColorThreshold(int t, String c) {
        threshold = t;
        colorCode = c;
    }

    public static String getColorCode(List<ColorThreshold> colorList, int value) {
        for (ColorThreshold ct : colorList) {
            if (value <= ct.threshold) {
                return ct.colorCode;
            }
        }

        return "f";
    }

    @Override
    public String toString() {
        return threshold + ", " + colorCode;
    }

    @Override
    public int compareTo(ColorThreshold o) {
        return Integer.compare(this.threshold, o.threshold);
    }
}
