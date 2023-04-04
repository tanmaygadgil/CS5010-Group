import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
  private int[][][] image;
  public ImagePanel() {
    this.image = null;
  }

  public void setImage(int[][][] image) {
    this.image = image;
    int height = image[0].length;
    int width = image[0][0].length;
    this.setPreferredSize(new Dimension( width, height));
    this.revalidate();
    this.repaint();
  }

  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    BufferedImage bufferedImage = rgbToBufferedImage(this.image);
    g.drawImage(bufferedImage, 0,0, image[0][0].length, image[0].length, this);
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
  private BufferedImage rgbToBufferedImage(int[][][] image) {
    int height = image[0].length;
    int width = image[0][0].length;
    int[][][] newImage;
    if (image.length == 1) {
      newImage = new int[3][height][width];
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          newImage[0][y][x] = image[0][y][x];
          newImage[1][y][x] = image[0][y][x];
          newImage[2][y][x] = image[0][y][x];
        }
      }

    } else {
      newImage = image;
    }
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = newImage[0][y][x];
        int green = newImage[1][y][x];
        int blue = newImage[2][y][x];
        int rgb = (red << 16) | (green << 8) | blue;
        bufferedImage.setRGB(x, y, rgb);
      }
    }
    return bufferedImage;
  }

  public void readAndLoad(String ImagePath) {
    BufferedImage img;
    try {
      img = ImageIO.read(new FileInputStream(ImagePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.image = convertToRgb(img);
    int height = this.image[0].length;
    int width = this.image[0][0].length;
    System.out.println(height);
    System.out.println(width);
    this.setPreferredSize(new Dimension( width, height));
    revalidate();
    repaint();

  }

}
