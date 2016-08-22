import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    private static final String BASE_PATH = "C:\\Users\\Matheus\\IdeaProjects\\image-processing\\";
    private static final String OUTPUT_LOCATION = BASE_PATH + "output\\";
    private static final String SAMPLES_LOCATION = BASE_PATH + "samples\\";

    private static void testTask1() {
        try {
            for (Images imageEnum : Images.values()) {
                String fileName = imageEnum.getFileName();
                BufferedImage image = JPEGHandler.readImage(SAMPLES_LOCATION + fileName);
                JPEGHandler.writeImage(image, OUTPUT_LOCATION + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testTask1();
    }
}
