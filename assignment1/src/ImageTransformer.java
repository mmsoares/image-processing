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
     * Returns a blank image from the same dimensions of the image passed as parameter to be used in a transformation
     *
     * @param originalImage Original image
     * @return Blank image with the same height and width of the original image
     */
    private static BufferedImage getSizedImageForTransformation(BufferedImage originalImage) {
        return new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
    }
}
