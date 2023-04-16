package imgeditor.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface represents an ImageIOHandler. It specifies functionality
 * for reading and saving an image.
 * The way an image is read and saved from the InputStream or OutputStream
 * is implementation-specific.
 */
interface ImageIOHandler {

  /**
   * Read image data from an InputStream.
   * Creates and returns an ImageData object using the pixels obtained by parsing
   * the data in the InputStream.
   *
   * @param in the file input stream
   * @return ImageData object containing pixels of the image
   * @throws IOException if an invalid InputStream is specified or if the number of
   *                     pixels in an image does not match the width or height dimensions
   */
  ImageData read(InputStream in) throws IOException;

  /**
   * Writes the pixels obtained from the ImageData object to the OutputStream.
   * @param out       the file output stream
   * @param imageData the image data to be saved
   * @throws IOException if the provided OutputStream is invalid
   */
  void save(OutputStream out, ImageData imageData) throws IOException;
}
