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
                resultImage.setRGB(i, j, getRgb(quantizedLuminance, quantizedLuminance, quantizedLuminance));
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

    public static int[] calculateHistogram(BufferedImage image) {
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

    public static BufferedImage histogramEqualization(BufferedImage originalImage) {
        BufferedImage resultImage = getSizedImageForTransformation(originalImage);

        int[] histogram = calculateHistogram(originalImage);
        int[] cumulativeHistogram = calculateCumulativeHistogram(histogram, originalImage);

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                int red = cumulativeHistogram[getRed(originalImage, i, j)];
                int green = cumulativeHistogram[getGreen(originalImage, i, j)];
                int blue = cumulativeHistogram[getBlue(originalImage, i, j)];

                resultImage.setRGB(i, j, getRgb(red, green, blue));
            }
        }

        return resultImage;
    }

    public static int[] calculateCumulativeHistogram(int[] histogram, BufferedImage image) {
        int[] cumulativeHistogram = new int[256];
        cumulativeHistogram[0] = histogram[0];

        for (int i = 1; i < 256; i++) {
            cumulativeHistogram[i] = cumulativeHistogram[i - 1] + histogram[i];
        }

        double alpha = getAlphaFactor(image);

        for (int i = 0; i < 256; i++) {
            cumulativeHistogram[i] = (int) (alpha * cumulativeHistogram[i]);
        }

        return cumulativeHistogram;
    }

    private static int pixelsInImage(BufferedImage image) {
        return image.getHeight() * image.getWidth();
    }

    private static double getAlphaFactor(BufferedImage image) {
        return 255.0 / pixelsInImage(image);
    }

    private static int getMaximum(int[] array) {
        int max = 0;

        for (int n : array) {
            max = Math.max(max, n);
        }

        return max;
    }

    public static BufferedImage makeImageFromHistogramArray(int[] histogram) {
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

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                int red = getRed(originalImage, i, j) + bright;
                int green = getGreen(originalImage, i, j) + bright;
                int blue = getBlue(originalImage, i, j) + bright;

                resultImage.setRGB(i, j, getRgb(red, green, blue));
            }
        }

        return resultImage;
    }

    public static BufferedImage contrastAdjust(BufferedImage originalImage, int contrast) {
        BufferedImage resultImage = getSizedImageForTransformation(originalImage);

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                int red = getRed(originalImage, i, j) * contrast;
                int green = getGreen(originalImage, i, j) * contrast;
                int blue = getBlue(originalImage, i, j) * contrast;

                resultImage.setRGB(i, j, getRgb(red, green, blue));
            }
        }

        return resultImage;
    }

    public static BufferedImage negative(BufferedImage originalImage) {
        BufferedImage resultImage = getSizedImageForTransformation(originalImage);

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                int red = 255 - getRed(originalImage, i, j);
                int green = 255 - getGreen(originalImage, i, j);
                int blue = 255 - getBlue(originalImage, i, j);

                resultImage.setRGB(i, j, getRgb(red, green, blue));
            }
        }

        return resultImage;
    }

    public static BufferedImage rotateMinus90(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        BufferedImage resultImage = new BufferedImage(originalImage.getHeight(), width, BufferedImage.TYPE_INT_RGB);


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                int destRgb = originalImage.getRGB(i, j);
                resultImage.setRGB(j, width - i - 1, destRgb);
            }
        }

        return resultImage;
    }

    public static BufferedImage rotatePlus90(BufferedImage originalImage) {
        return rotateMinus90(rotateMinus90(rotateMinus90(originalImage)));
    }


        private static int getRed(BufferedImage image, int i, int j) {
        int rgb = image.getRGB(i, j);
        return new Color(rgb).getRed();
    }

    private static int getGreen(BufferedImage image, int i, int j) {
        int rgb = image.getRGB(i, j);
        return new Color(rgb).getGreen();
    }

    private static int getBlue(BufferedImage image, int i, int j) {
        int rgb = image.getRGB(i, j);
        return new Color(rgb).getBlue();
    }

    private static int getRgb(int red, int green, int blue) {
        return new Color(truncateToPixelRange(red), truncateToPixelRange(green), truncateToPixelRange(blue)).getRGB();
    }

    private static int truncateToMaxPixelValue(int value) {
        return value > 255 ? 255 : value;
    }

    private static int truncateToMinPixelValue(int value) {
        return value < 0 ? 0 : value;
    }

    private static int truncateToPixelRange(int value) {
        return truncateToMaxPixelValue(truncateToMinPixelValue(value));
    }
}
