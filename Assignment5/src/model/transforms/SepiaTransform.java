package model.transforms;

public class SepiaTransform implements model.ImageTransforms {

  @Override
  public int[][][] transform(int[][][] image) {
    int length = image.length;
    int width = image[0][0].length;
    int height = image[0].length;

    int[][][] sepiaImage = new int[length][height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        sepiaImage[0][i][j] = (int) (0.393 * image[0][i][j] + 0.769 * image[1][i][j]
            + 0.189 * image[2][i][j]);

        if (sepiaImage[0][i][j] > 255){
          sepiaImage[0][i][j] = 255;
        }


        sepiaImage[1][i][j] = (int) (0.349 * image[0][i][j] + 0.686 * image[1][i][j]
            + 0.168 * image[2][i][j]);

        if (sepiaImage[1][i][j] > 255){
          sepiaImage[1][i][j] = 255;
        }
        sepiaImage[2][i][j] = (int) (0.272 * image[0][i][j] + 0.534 * image[1][i][j]
            + 0.131 * image[2][i][j]);
        if (sepiaImage[2][i][j] > 255){
          sepiaImage[2][i][j] = 255;
        }
      }
    }

    return sepiaImage;
  }
}
