package imgeditor.model;

/**
 * This enum contains various kernels used in image operations.
 * Each value represents a Kernel matrix which is to be used for
 * filter or color transformation operations.
 */
enum Kernel {

  Blur(new double[][]{
          {1.0 / 16, 1.0 / 8, 1.0 / 16},
          {1.0 / 8, 1.0 / 4, 1.0 / 4},
          {1.0 / 16, 1.0 / 8, 1.0 / 16}
  }),
  Sharpen(new double[][]{
          {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
  }),
  Sepia(new double[][]{
          {0.393, 0.769, 0.189},
          {0.394, 0.686, 0.168},
          {0.272, 0.534, 0.131}
  }),
  LumaGreyscale(new double[][]{
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722}
  });

  private final double[][] matrix;

  /**
   * Construct a Kernel enum.
   *
   * @param matrix the kernel matrix created when one of the enum values are specified
   */
  Kernel(double[][] matrix) {
    this.matrix = matrix;
  }

  /**
   * Returns the kernel matrix for this object.
   * @return the kernel matrix
   */
  double[][] getMatrix() {
    return this.matrix;
  }

}
