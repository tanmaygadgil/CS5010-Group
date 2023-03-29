package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface represents possible actions to be done on a PPM file.
 */
public interface Model {

  /**
   * Reads an image file from the specified filePath, converts it to a three-dimensional array of
   * integers, and stores it in the imageMap with the specified destImage name.
   *
   * @param in  path of image to read.
   * @param destImage name of the new image to store.
   * @param format The file format to be saved or read
   * @throws FileNotFoundException if file does not exist.
   */
  void load(InputStream in, String destImage, String format) throws FileNotFoundException;

  /**
   * Writes the specified image to the specified filePath in PPM format.
   *
   * @param out  path to save the image file to.
   * @param imageName name of the image to save.
   * @param format The file format to be saved or read
   * @throws IOException if there is an error writing the file.
   */
  void save(OutputStream out, String imageName, String format) throws IOException;

  /**
   * Convert an image to greyscale based on the imagecomponent given.
   *
   * @param rGB       component to use for greyscale conversion.
   * @param imageName name of image to greyscale.
   * @param destImage name of the new image to store.
   */
  void greyscale(ImageComponents rGB, String imageName, String destImage);

  /**
   * Flip an image according to the Axes and store the result in a new image.
   *
   * @param axis      the axis along which to flip the image.
   * @param imageName the name of the image to flip.
   * @param destImage name of the new image to store.
   */
  void flip(Axes axis, String imageName, String destImage);

  /**
   * Increases the brightness of the specified image by the specified increment and stores the
   * result in the imageMap with the specified destImage name.
   *
   * @param increment amount to increase the brightness by.
   * @param imageName image to brighten.
   * @param destImage name of the new image to store.
   */
  void brighten(int increment, String imageName, String destImage);

  /**
   * Decreases the brightness of the specified image by the specified increment and stores the
   * result in the imageMap with the specified destImage name.
   *
   * @param increment amount to increase the brightness by.
   * @param imageName image to brighten.
   * @param destImage name of the new image to store.
   */
  void darken(int increment, String imageName, String destImage);


  /**
   * Splits the RGB channels of the image into separate components and stores them as individual
   * images in the image map.
   *
   * @param imageName      the name of the image to be split
   * @param destImageRed   the name of the image to be created for the red channel component
   * @param destImageGreen the name of the image to be created for the green channel component
   * @param destImageBlue  the name of the image to be created for the blue channel component
   */
  void rgbSplit(String imageName, String destImageRed, String destImageGreen, String destImageBlue);

  /**
   * Combines individual red, green, and blue color channels into a single RGB image.
   *
   * @param destImage      the name of the destination image
   * @param destImageRed   the name of the image containing the red channel
   * @param destImageGreen the name of the image containing the green channel
   * @param destImageBlue  the name of the image containing the blue channel
   * @throws IllegalArgumentException if any of the input images do not exist in the image map
   */
  void rgbCombine(String destImage, String destImageRed, String destImageGreen,
      String destImageBlue);

}
