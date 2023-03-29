package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class represents an IO handler for images which gets the correct loaders and savers for
 * handling images as well as the correct format.
 */
public class ImageIOHandlerImpl implements ImageIOHandler {

  /**
   * Returns the InputStream which the program will use to read files.
   *
   * @param filepath file path.
   * @return InputStream used to read files.
   * @throws FileNotFoundException if file does not exist.
   */
  public static InputStream getLoader(String filepath) throws FileNotFoundException {
    InputStream in = new FileInputStream(filepath);
    return in;
  }

  /**
   * Returns the OutputStream which the program will use to write to files.
   *
   * @param filepath file path.
   * @return OutputStream used to write to files.
   * @throws FileNotFoundException if file cannot be made.
   */
  public static OutputStream getSaver(String filepath) throws FileNotFoundException {
    OutputStream out = new FileOutputStream(filepath);
    return out;
  }

  /**
   * Return the file extension as a string.
   *
   * @param filename file path.
   * @return the file extension.
   */
  public static String parseFormat(String filename) {
    if (filename == null) {
      return null;
    }
    int extensionIndex = filename.lastIndexOf('.');
    if (extensionIndex == -1) {
      return "";
    }
    String extension = filename.substring(extensionIndex + 1);
    return extension;
  }
}
