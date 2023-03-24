package model.loaders;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import model.ImageLoader;

public class ConventionalImageLoader implements ImageLoader {

  private String format;
  public ConventionalImageLoader(String format){
    this.format = format;
  }
  @Override
  public int[][][] load(InputStream in) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(in);
    int[][][] rgbImage = convertToRgb(bufferedImage);
    return rgbImage;
  }

  private int[][][] convertToRgb(BufferedImage img){
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
}
