package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageIOHandlerImpl implements ImageIOHandler {

  public static InputStream getLoader(String filepath) throws FileNotFoundException {
    InputStream in = new FileInputStream(filepath);
    return in;
  }

  public static OutputStream getSaver(String filepath) throws FileNotFoundException {
    OutputStream out = new FileOutputStream(filepath);
    return out;
  }

  public static String parseFormat(String filename) {
//    if (filename == null | !filename.contains(".")) {
//      throw new IllegalArgumentException();
//    }
//
//    String[] split = filename.split(".");
//    return split[split.length - 1];
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
