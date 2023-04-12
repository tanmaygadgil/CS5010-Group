package model.transforms;

/**
 * This class represents the greyscale transformations to be done on images.
 */
public class GreyscaleTransform implements model.ImageTransforms {

  @Override
  public int[][][] transform(int[][][] image) {

    if (image.length == 1){
      return image;
    }
    int length = image.length;
    int width = image[0][0].length;
    int height = image[0].length;

    int[][][] greyscaledImage = new int[length][height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < length; k++) {
          greyscaledImage[k][i][j] = (int) (0.2126 * image[0][i][j] + 0.7152 * image[1][i][j]
              + 0.0722 * image[2][i][j]);
        }
      }
    }

    int[][][] returnImage = new int[1][height][width];
    returnImage[0] = greyscaledImage[0];
    return returnImage;
  }

  @Override
  public String toString() {
    return "Greyscale Transform";
  }
}
