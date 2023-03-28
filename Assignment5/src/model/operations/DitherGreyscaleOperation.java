package model.operations;

public class DitherGreyscaleOperation implements ImageOperations {

  public DitherGreyscaleOperation(){


  }
  @Override
  public int[][][] operate(int[][][] image) {
    if (image.length != 1){
      throw new IllegalStateException("Please input a greyscale image");
    }

    int height = image[0].length;
    int width = image[0][0].length;
    int[][][] newImage = new int[3][height][width];

    for(int i = 0; i < height; i++){
      for (int j = 0; j< width;j++ ){
        int oldColor = image[0][i][j];
        int newColor = oldColor  > 127 ? 255 : 0;
        int error = newColor - oldColor;
        newImage[0][i][j] = newColor;
        newImage[1][i][j] = newColor;
        newImage[2][i][j] = newColor;

        if (i < height -1){
          image[0][i+1][j] += error * (5 / 16);
        }
        if (i < height -1 & j < width -1 ){
          image[0][i+1][j+1] += error * (1 / 16);
        }
        if (i < height - 1 & j >0){
          image[0][i+1][j-1] += error * (3 / 16);
        }
        if (j < width -1){
          image[0][i][j+1] += error * (7 / 16);
        }
      }
    }
    return newImage;
  }
}
