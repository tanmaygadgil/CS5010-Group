package imgeditor.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * This is an abstract class for implementations of the ImageIOHandler interface.
 * It contains helper methods for reading and saving a raster image (jpg, png, bmp, etc.).
 */
abstract class AbstractRasterImageIOHandler implements ImageIOHandler {

  public abstract ImageData read(InputStream in) throws IOException;

  public abstract void save(OutputStream out, ImageData imageData) throws IOException;

  /**
   * Reads an image from the InputStream and loads the image pixels into an ImageData object.
   * This method is the same for all raster images (jpg, png, bmp, etc.) with only the number
   * of channels changing for png files.
   *
   * @param inputStream      input stream that contains the raw data from the file
   * @param numberOfChannels the number of channels in the image
   * @return an ImageData object containing the pixels of the image that was read
   * @throws IOException if there is an error reading the InputStream
   */
  protected ImageData readHelp(InputStream inputStream, int numberOfChannels) throws IOException {

    BufferedImage image;

    try {
      image = ImageIO.read(inputStream);
    } catch (IOException e) {
      throw new IOException("Error reading image file: " + e.getMessage());
    }

    int[][][] pixels = new int[image.getWidth()][image.getHeight()][numberOfChannels];

    boolean hasAlpha = numberOfChannels == 4;

    // Loop through each pixel of the image and retrieve its RGB values
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {

        Color color = new Color(image.getRGB(x, y), hasAlpha);

        pixels[x][y][0] = color.getRed();
        pixels[x][y][1] = color.getGreen();
        pixels[x][y][2] = color.getBlue();
        if (hasAlpha) {
          pixels[x][y][3] = color.getAlpha();
        }
      }
    }
    return new ImageData(pixels);
  }

  /**
   * Saves an image using the OutputStream and the provided ImageData object.
   * This method is the same for all raster images (jpg, png, bmp, etc.) with only the format name
   * and BufferedImage type changing based on the file extension.
   *
   * @param out        the OutputStream where the image data is to be written
   * @param imageData  the ImageData object containing the image pixels
   * @param formatName the file format (jpg, png, bmp, etc.) to save the image data in
   * @throws IOException if there is an error writing the image data to the OutputStream
   */
  protected void saveHelp(OutputStream out, ImageData imageData, String formatName)
          throws IOException {
    try {
      int[][][] pixels = imageData.getPixels();
      int bufferedImageType = formatName.equals("png") ? BufferedImage.TYPE_INT_ARGB
              : BufferedImage.TYPE_INT_RGB;
      // Create a BufferedImage object from the 3D array of RGB color values
      BufferedImage image = new BufferedImage(pixels.length, pixels[0].length,
              bufferedImageType);
      for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
          Color color;
          if (bufferedImageType == BufferedImage.TYPE_INT_ARGB) {
            if (pixels[0][0].length == 4) {
              color = new Color(pixels[x][y][0], pixels[x][y][1], pixels[x][y][2], pixels[x][y][3]);
            }
            else {
              color = new Color(pixels[x][y][0], pixels[x][y][1], pixels[x][y][2]);
            }
          } else {
            color = new Color(pixels[x][y][0], pixels[x][y][1], pixels[x][y][2]);
          }
          image.setRGB(x, y, color.getRGB());
        }
      }

      ImageIO.write(image, formatName, out);
    } catch (IOException e) {
      throw new IOException("Error writing image file: " + e.getMessage());
    }
  }

}
