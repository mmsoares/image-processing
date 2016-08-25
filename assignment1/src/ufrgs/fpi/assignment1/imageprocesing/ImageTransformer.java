package ufrgs.fpi.assignment1.imageprocesing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageTransformer {

    /**
     * Converts a (possibly colored) image into a new one in shades of gray
     *
     * @param originalImage the possibly colored image
     * @return new image in shades of gray
     */
    public static BufferedImage convertToShadesOfGray(BufferedImage originalImage) {
        BufferedImage resultImage = getSizedImageForTransformation(originalImage);

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                Color color = new FPIColor(originalImage.getRGB(i, j)).paintItGray();
                resultImage.setRGB(i, j, color.getRGB());
            }
        }

        return resultImage;
    }

    /**
     * Mirrors an image vertically
     *
     * @param originalImage original image to be mirrored
     * @return image mirrored vertically
     */
    public static BufferedImage mirrorVertically(BufferedImage originalImage) {
        BufferedImage resultImage = getSizedImageForTransformation(originalImage);

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                int pixelHeight = originalImage.getHeight() - j - 1;
                resultImage.setRGB(i, pixelHeight, originalImage.getRGB(i, j));
            }
        }

        return resultImage;
    }

    /**
     * Mirrors an image horizontally
     *
     * @param originalImage original image to be mirrored
     * @return image mirrored horizontally
     */
    public static BufferedImage mirrorHorizontally(BufferedImage originalImage) {
        BufferedImage resultImage = getSizedImageForTransformation(originalImage);

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                int pixelWidth = originalImage.getWidth() - i - 1;
                resultImage.setRGB(pixelWidth, j, originalImage.getRGB(i, j));
            }
        }

        return resultImage;
    }

    /**
     * Quantize an image into a certain amount of shades of gray.
     * In case the image passed is not in shades of gray, it will be converted prior to the quantization.
     *
     * @param originalImage the original image to be quantized
     * @param shades        number of shades of gray to quantize the image
     * @return image quantized in <code>shades</code> shades of gray
     */
    public static BufferedImage quantizeImage(BufferedImage originalImage, int shades) {
        BufferedImage resultImage = getSizedImageForTransformation(originalImage);

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                int luminance = new FPIColor(originalImage.getRGB(i, j)).paintItGray().getRed();
                int quantizedLuminance = getQuantizedLuminance(shades, luminance);
                resultImage.setRGB(i, j, new Color(quantizedLuminance, quantizedLuminance, quantizedLuminance).getRGB());
            }
        }

        return resultImage;
    }

    private static int getQuantizedLuminance(int shades, int luminance) {
        return (int) ((float) shades * luminance / 255f);    //casts are needed to prevent truncation from always making the result equal to 0
    }

    /**
     * Returns a blank image from the same dimensions of the image passed as parameter to be used in a transformation
     *
     * @param originalImage Original image
     * @return Blank image with the same height and width of the original image
     */
    private static BufferedImage getSizedImageForTransformation(BufferedImage originalImage) {
        return new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
    }
}
