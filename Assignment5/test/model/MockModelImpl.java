package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockModelImpl implements MockModel {

  @Override
  public void load(InputStream in, String destImage, String format) throws FileNotFoundException {

  }

  @Override
  public void save(OutputStream out, String imageName, String format) throws IOException {

  }

  @Override
  public void greyscale(ImageComponents rGB, String imageName, String destImage) {

  }

  @Override
  public void flip(Axes axis, String imageName, String destImage) {

  }

  @Override
  public void brighten(int increment, String imageName, String destImage) {

  }

  @Override
  public void rgbSplit(String imageName, String destImageRed, String destImageGreen,
      String destImageBlue) {

  }

  @Override
  public void rgbCombine(String destImage, String destImageRed, String destImageGreen,
      String destImageBlue) {

  }

  @Override
  public String getLog() {
    return null;
  }
}
