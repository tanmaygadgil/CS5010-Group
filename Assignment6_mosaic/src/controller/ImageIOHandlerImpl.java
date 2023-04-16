package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class represents an image handler, which gets the loaders and savers for an image as well
 * as the file format.
 */
public class ImageIOHandlerImpl implements ImageIOHandler {

  public ImageIOHandlerImpl() {
    //This pattern will implement more complex elements which will need a constructor.
  }

  public InputStream getLoader(String filepath) throws FileNotFoundException {
    InputStream in = new FileInputStream(filepath);
    return in;
  }

  public OutputStream getSaver(String filepath) throws FileNotFoundException {
    OutputStream out = new FileOutputStream(filepath);
    return out;
  }

  /**
   * Returns the file extension given the file name.
   *
   * @param filename file name.
   * @return the file extension.
   */
  public String parseFormat(String filename) {

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
