package Model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class ModelPPM implements Model {

  private final HashMap<String, int[][][]> imageMap;

  public ModelPPM() {
    this.imageMap = new HashMap<>();
  }

  @Override
  public void load(String filePath, String destImage) throws FileNotFoundException {
    //size = (3, 768, 1024)
    int[][][] image = ImageUtil.readPPM(filePath);
    this.imageMap.put(destImage, image);
  }

  @Override
  public void save(String filePath, String imageName) throws IOException {
    FileOutputStream fout = new FileOutputStream(filePath);

    int[][][] image = this.imageMap.get(imageName);
    int width = image[0][0].length;
    int height = image[0].length;

    fout.write("P3\n".getBytes());
    fout.write(String.format("%d %d\n255\n", width, height).getBytes());

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        fout.write((new Integer(image[0][i][j]).toString() + "\n").getBytes());
        fout.write((new Integer(image[1][i][j]).toString() + "\n").getBytes());
        fout.write((new Integer(image[2][i][j]).toString() + "\n").getBytes());
      }
    }

  }

  @Override
  public void brighten(int increment, String imageName, String destImage) {
    int[][][] image = imageMap.get(imageName);
    for(int i = 0; i < image.length; i++) {
      for(int j = 0; j < image[0].length; j++) {
        for(int k = 0; k < image[0][0].length; k++) {
          image[i][j][k] = image[i][j][k] + increment;
        }
      }
    }

    imageMap.put(destImage, image);
  }

  @Override
  public void flip(Axes axis, String imageName, String destImage) {
    int[][][] image = this.imageMap.get(imageName);

    if (axis == Axes.HORIZONTAL) {
      for(int i = 0; i < image.length; i++) {
        image[i] = flipHorizontal(image[i]);
      }
      imageMap.put(destImage, image);
    } else if (axis == Axes.VERTICAL) {
      for (int i = 0; i < image.length; i++) {
        image[i] = flipVertical(image[i]);
      }
      imageMap.put(destImage, image);
    } //else throw error?
  }

  private int[][] flipVertical(int[][] image) {
    int[] temp = new int[image.length];
    for (int i = 0; i < image.length / 2; i++) {
      temp = image[image.length - i - 1];
      image[image.length - i - 1] = image[i];
      image[i] = temp;
    }

    return image;
  }

  private int[][] flipHorizontal(int[][] image) {
    for (int i = 0; i < image.length; i++) { //rows
      for (int j = 0; j < image[0].length / 2; j++) { //cols
        int temp = image[i][(image[0].length) - j - 1];
        image[i][image[0].length - j - 1] = image[i][j];
        image[i][j] = temp;
      }
    }

    return image;
  }

  @Override
  public void greyscale(ImageComponents RGB, String imageName, String destImage) {

  }


  @Override
  public void rgbSplit(String DestImage, String DestImageRed, String DestImageGreen,
      String DestImageBlue) {

  }

  @Override
  public void rgbCombine(String DestImage, String DestImageRed, String DestImageGreen,
      String DestImageBlue) {

  }

}
