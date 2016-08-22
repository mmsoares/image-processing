import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class JPEGHandler {

    private static final String JPG = "jpg";

    /**
     * Writes a JPEG image to a specified path in the file system.
     *
     * @param image the BufferedImage.
     * @param path  Absolute or relative path of where to write the image.
     * @throws IOException in case the specified path is not accessible or doesn't exist
     */
    public static void writeImage(BufferedImage image, String path) throws IOException {
        File outputFile = new File(path);
        ImageIO.write(image, JPG, outputFile);
    }

    /**
     * Reads a JPEG file from a specified path in the file system.
     *
     * @param path Absolute or relative path of where to read the image from.
     * @return An object of type <code>BufferedImage</code> with the decoded images
     * @throws IOException In case the specified path doesn't exist, is not accessible or is not a JPEG image.
     */
    public static BufferedImage readImage(String path) throws IOException {
        JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(new FileInputStream(path));
        return jpegDecoder.decodeAsBufferedImage();
    }
}
