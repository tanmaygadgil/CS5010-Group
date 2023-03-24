package controller;

import java.io.InputStream;
import java.io.OutputStream;

public class ImageIOHandlerImpl implements ImageIOHandler {

  @Override
  public InputStream load(String filepath) {
    return null;
  }

  @Override
  public OutputStream save(String filepath) {
    return null;
  }

  private String parseFormat(String filename){
    if (filename == null | !filename.contains(".")){
      throw new IllegalArgumentException();
    }

    String[] split = filename.split(".");
    return split[split.length - 1];
  }
}
