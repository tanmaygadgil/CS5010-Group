package model.loaders;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import model.ImageLoader;

/**
 * This class represents an image loader for multiple well-known image formats.
 */
public class ConventionalImageLoader implements ImageLoader {

  /**
   * Initializes the image loader with the given format.
   */
  public ConventionalImageLoader() {
    //This
  }

  @Override
  public int[][][] load(InputStream in) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(in);
    int[][][] rgbImage = convertToRgb(bufferedImage);
    if (detectGreyscale(rgbImage)) {
      int height = rgbImage[0].length;
      int width = rgbImage[0][0].length;
      int[][][] grey = new int[1][height][width];
      grey[0] = rgbImage[0];
      return grey;
    } else {
      return rgbImage;
    }
  }

  private int[][][] convertToRgb(BufferedImage img) {
    int height = img.getHeight();
    int width = img.getWidth();
    int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
    int[][][] image = new int[3][height][width];
    for (int i = 0; i < height * width; i++) {
      int pixel = pixels[i];
      int x = i % width;
      int y = i / width;
      image[0][y][x] = (pixel >> 16) & 0xff; // red
      image[1][y][x] = (pixel >> 8) & 0xff;  // green
      image[2][y][x] = pixel & 0xff;          // blue
    }

    return image;

  }

  private boolean detectGreyscale(int[][][] image) {
    int height = image[0].length;
    int width = image[0][0].length;

    boolean isGrey = true;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        isGrey &= ((image[0][i][j] == image[1][i][j]) & (image[0][i][j] == image[1][i][j]));
      }
    }

    return isGrey;
  }

  @Override
  public String toString() {
    return "ConventionalLoader";
  }
}
