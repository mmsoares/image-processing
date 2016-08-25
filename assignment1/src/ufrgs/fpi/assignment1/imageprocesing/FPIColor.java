package ufrgs.fpi.assignment1.imageprocesing;

import java.awt.Color;

class FPIColor extends Color {
    private FPIColor(int r, int g, int b) {
        super(r, g, b);
    }

    public FPIColor(int rgb) {
        super(rgb);
    }

    /**
     * Turns a colored pixel into shades of gray
     *
     * @return a new Color in shades of gray
     */
    public Color paintItGray() {
        int luminance = calculateLuminance(this.getRed(), this.getGreen(), this.getBlue());
        return new FPIColor(luminance, luminance, luminance);
    }

    private static int calculateLuminance(int red, int green, int blue) {
        return (int) ((0.299 * red) + (0.587 * green) + (0.114 * blue));
    }
}
