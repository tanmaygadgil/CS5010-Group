package model.Filters;

public abstract class AbstractFilter implements ImageFilter{

  double[][] kernel;

  @Override
  public int[][][] filter(int[][][] image) {
    int[][][] filteredImage = new int[image.length][image[0].length][image[0][0].length];
    for(int i = 0; i < image.length; i++) {
      filteredImage[i] = convEntireImage(image[i], kernel);
    }

    return filteredImage;
  }

  protected int convOnePixel(int[][] image, double[][] kernel, int row, int col){
    double out = 0;
    row = row - ((kernel.length - 1) / 2);
    col = col - ((kernel.length - 1) / 2);
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel[0].length; j++) {
        if (row + i < 0 || col + j < 0) {
          continue;
        }
        if (row + i > image.length - 1 || col + j > image[0].length - 1) {
          continue;
        }
        out += image[row + i][col + j] * kernel[i][j];
        if (out > 255) {
          out = 255;
        } else if (out < 0) {
          out = 0;
        }
      }
    }

    return (int) out;
  }

  protected int[][] convEntireImage(int[][] image, double[][] kernel){
    int[][] imageFiltered = new int[image.length][image[0].length];
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        imageFiltered[i][j] = convOnePixel(image, kernel, i, j);
      }
    }

    return imageFiltered;
  }
}
