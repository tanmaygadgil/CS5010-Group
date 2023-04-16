package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * A JSwing panel used to visualize the image that is being worked on.
 */
public class ImagePanel extends JPanel {

  private int[][][] image;

  /**
   * Initializes the image panel.
   */
  public ImagePanel() {
    setSize(new Dimension(500, 800)); // set the preferred size of the panel

    reset();
    // Repaint the JPanel to show the new blank background
    revalidate();
    repaint();

  }

  /**
   * Resets the image view to a blank canvas.
   */
  public void reset() {
    BufferedImage blankImage = new BufferedImage(getWidth(), getHeight(),
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = blankImage.createGraphics();
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, getWidth(), getHeight());
    this.image = convertToRgb(blankImage);
    setBackground(Color.WHITE);
  }

  /**
   * Sets the image in the canvas.
   *
   * @param image the image object to be set.
   */
  public void setImage(int[][][] image) {
    this.image = image;
    int height = image[0].length;
    int width = image[0][0].length;
    this.setPreferredSize(new Dimension(width, height));
    this.revalidate();
    this.repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    BufferedImage bufferedImage = rgbToBufferedImage(this.image);
    g.drawImage(bufferedImage, 0, 0, image[0][0].length, image[0].length, this);
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


}
