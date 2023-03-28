package model.operations;

public class DitherGreyscaleOperation implements ImageOperations {

  public DitherGreyscaleOperation() {

  }

  @Override
  public int[][][] operate(int[][][] image) {
    if (image.length != 1) {
      throw new IllegalStateException("Please input a greyscale image");
    }

    int height = image[0].length;
    int width = image[0][0].length;
    int[][][] newImage = new int[3][height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int oldColor = image[0][i][j];
//        int newColor = (int)(255 * Math.round(oldColor / 255));
        int newColor = oldColor > 127 ? 255 : 0;
        int error = oldColor - newColor;
        newImage[0][i][j] = newColor;
        newImage[1][i][j] = newColor;
        newImage[2][i][j] = newColor;

        if (i < height - 1) {
          image[0][i + 1][j] += error * (5.0 / 16);
        }
        if (i < height - 1 & j < width - 1) {
          image[0][i + 1][j + 1] += error * (1.0 / 16);
        }
        if (i < height - 1 & j > 0) {
          image[0][i + 1][j - 1] += error * (3.0 / 16);
        }
        if (j < width - 1) {
          image[0][i][j + 1] += error * (7.0 / 16);
        }
      }
    }
    return newImage;
  }

  @Override
  public String toString() {
    return "DitherOperation";
  }
}
