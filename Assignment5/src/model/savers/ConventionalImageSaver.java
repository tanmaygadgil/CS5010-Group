package model.savers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import model.ImageSaver;

public class ConventionalImageSaver implements ImageSaver {

  private String format;
  public ConventionalImageSaver(String format) {
    this.format = format;
  }
  @Override
  public void save(int[][][] image, OutputStream out) throws IOException {
    BufferedImage bufferedImage = rgbToBufferedImage(image);
    ImageIO.write(bufferedImage, "jpg", out);
  }

  private BufferedImage rgbToBufferedImage(int[][][] image){
    int height = image[0].length;
    int width = image[0][0].length;
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = image[0][y][x];
        int green = image[1][y][x];
        int blue = image[2][y][x];
        int rgb = (red << 16) | (green << 8) | blue;
        bufferedImage.setRGB(x, y, rgb);
      }
    }
    return bufferedImage;
  }
}