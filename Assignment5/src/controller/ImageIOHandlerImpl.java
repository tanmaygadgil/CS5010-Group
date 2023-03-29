package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageIOHandlerImpl implements ImageIOHandler {

  public ImageIOHandlerImpl() {

  }

  public InputStream getLoader(String filepath) throws FileNotFoundException {
    InputStream in = new FileInputStream(filepath);
    return in;
  }

  public OutputStream getSaver(String filepath) throws FileNotFoundException {
    OutputStream out = new FileOutputStream(filepath);
    return out;
  }

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
