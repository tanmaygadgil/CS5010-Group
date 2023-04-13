package model.transforms;

public class AbstractTransform implements ImageTransforms {

  double[][] matrix;

  @Override
  public int[][][] transform(int[][][] image) {
    int length = image.length;
    int width = image[0][0].length;
    int height = image[0].length;

    int[][][] sepiaImage = new int[length][height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < length; k++) {
          sepiaImage[k][i][j] = dotProduct(matrix[k],
              new int[]{image[0][i][j], image[1][i][j], image[0][i][j]});
        }
      }
    }

    return sepiaImage;
  }

  protected int dotProduct(double[] vec1, int[] vec2) {
    double sum = 0;
    for (int i = 0; i < vec1.length; i++) {
      sum += vec1[i] * vec2[i];
    }

    if(sum > 255) {
      return 255;
    } else if (sum < 0) {
      return 0;
    } else {
      return (int) sum;
    }
  }

}
