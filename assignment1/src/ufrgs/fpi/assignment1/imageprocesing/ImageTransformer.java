package ufrgs.fpi.assignment1.imageprocesing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ImageTransformer {
    private ImageTransformer() {
        //utility classes should not be instantiated
    }

    /**
     * Converts a (possibly colored) image into a new one in grayscale
     *
     * @param originalImage the possibly colored image
     * @return new image in grayscale
     */
    public static BufferedImage convertToGrayscale(BufferedImage originalImage) {
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
     * In case the image passed is not in grayscale, it will be converted prior to the quantization.
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

    private static int[] calculateHistogram(BufferedImage image) {
        int[] histogram = new int[256];
        Arrays.fill(histogram, 0);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int luminance = new FPIColor(image.getRGB(i, j)).paintItGray().getRed();
                histogram[luminance] += 1;
            }
        }

        return histogram;
    }

    public static BufferedImage getHistogramAsImage(BufferedImage image) {
        int[] histogram = calculateHistogram(image);
        int maxHistogramValue = getMaximum(histogram);

        if (maxHistogramValue > 255) {
            //normalize the histogram to fit on the window
            double scalingFactor = 255.0 / maxHistogramValue;

            for (int i = 0; i < 256; i++) {
                histogram[i] = (int) (histogram[i] * scalingFactor);
            }
        }

        return makeImageFromHistogramArray(histogram);
    }

    private static int getMaximum(int[] array) {
        int max = 0;

        for (int n : array) {
            max = Math.max(max, n);
        }

        return max;
    }

    private static BufferedImage makeImageFromHistogramArray(int[] histogram) {
        BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < 256; i++) {
            int columnValue = histogram[i];
            for (int j = 0; j < 256; j++) {
                if (j <= columnValue) {
                    image.setRGB(i, 255 - j, Color.BLACK.getRGB());
                } else {
                    image.setRGB(i, 255 - j, Color.WHITE.getRGB());
                }
            }
        }

        return image;
    }

    public static BufferedImage brightEnhancement(BufferedImage originalImage, int bright) {
        BufferedImage resultImage = getSizedImageForTransformation(originalImage);

        //fixme this is not working, always makes the picture black. need to figure out why

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                Color originalColor = new FPIColor(resultImage.getRGB(i, j)).paintItGray();
                int enhancedLuminance = originalColor.getGreen() + bright;

                if(enhancedLuminance > 255) {
                    enhancedLuminance = 255;
                }
                else if (enhancedLuminance < 0) {
                    enhancedLuminance = 0;
                }

                resultImage.setRGB(i, j, new Color(enhancedLuminance, enhancedLuminance, enhancedLuminance).getRGB());
            }
        }

        return resultImage;
    }
}
