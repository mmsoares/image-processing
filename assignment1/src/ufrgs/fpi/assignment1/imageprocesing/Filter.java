package ufrgs.fpi.assignment1.imageprocesing;

/**
 * Created by Matheus on 03/09/2016.
 */
public enum Filter {
    //TODO review all these values
    GAUSSIAN(new double[][] {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}}),
    LAPLACIAN(new double[][] {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}}),
    HIGH_PASS(new double[][] {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}}),
    PREWITT_HX(new double[][] {{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}}),
    PREWITT_HX_HY(new double[][] {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}}),
    SOBEL_HX(new double[][] {{-1, 0, 1}, {-2, 0, -2}, {-1, 0, 1}}),
    SOBEL_HY(new double[][] {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}});

    private final double[][] matrix;

    Filter(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}