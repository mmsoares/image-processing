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
     * Turns a colored pixel into grayscale
     *
     * @return a new Color in grayscale
     */
    public Color paintItGray() {
        if(isGrayscale()) {
            return this;
        }
        else {
            int luminance = calculateLuminance(this.getRed(), this.getGreen(), this.getBlue());
            return new FPIColor(luminance, luminance, luminance);
        }
    }

    private static int calculateLuminance(int red, int green, int blue) {
        return (int) ((0.299 * red) + (0.587 * green) + (0.114 * blue));
    }

    /**
     * Checks if a pixel is in grayscale
     * @return true if the R, G and B components of the pixel have the same value, false otherwise
     */
    public boolean isGrayscale() {
        return this.getBlue() == this.getRed() && this.getRed() == this.getGreen();
    }
}