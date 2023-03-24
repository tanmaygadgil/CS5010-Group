package controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An IO handler that parses image files and sends the respective input an output readers
 */
public interface ImageIOHandler {

  static InputStream getLoader(String filepath) throws FileNotFoundException {
    return null;
  }

  static OutputStream getSaver(String filepath) throws FileNotFoundException {
    return null;
  }

  static String parseFormat(String filepath) {
    return null;
  }
}
