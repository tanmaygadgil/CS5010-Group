package model.transforms;

/**
 * This class is an abstract representation of a transformer.
 */
public abstract class AbstractTransform implements ImageTransforms {

  double[][] kernel;

  @Override
  public int[][][] transform(int[][][] image) {
    int length = image.length;
    int width = image[0][0].length;
    int height = image[0].length;

    int[][][] transformedImage = new int[length][height][width];

    transformedImage = transformation(image, kernel);

    return transformedImage;

  }

  protected int[][][] transformation(int[][][] image, double[][] kernel) {
    int length = image.length;
    int width = image[0][0].length;
    int height = image[0].length;

    int[][][] transformImage = new int[length][height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        transformImage[0][i][j] = (int) (kernel[0][0] * image[0][i][j]
                + kernel[0][1] * image[1][i][j] + kernel[0][2] * image[2][i][j]);

        if (transformImage[0][i][j] > 255) {
          transformImage[0][i][j] = 255;
        }

        transformImage[1][i][j] = (int) (kernel[1][0] * image[0][i][j]
                + kernel[1][1] * image[1][i][j] + kernel[1][2] * image[2][i][j]);

        if (transformImage[1][i][j] > 255) {
          transformImage[1][i][j] = 255;
        }
        transformImage[2][i][j] = (int) (kernel[2][0] * image[0][i][j]
                + kernel[2][1] * image[1][i][j] + kernel[2][2] * image[2][i][j]);
        if (transformImage[2][i][j] > 255) {
          transformImage[2][i][j] = 255;
        }
      }
    }

    return transformImage;
  }


}
