package Model;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ModelPPMMock implements Model {

  @Override
  public void load(String filePath, String destImage) throws FileNotFoundException {

  }

  @Override
  public void save(String filePath, String imageName) throws IOException {

  }

  @Override
  public void brighten(int Increment, String imageName, String destImage) {

  }

  @Override
  public void rgbSplit(String imageName, String DestImageRed, String DestImageGreen,
      String DestImageBlue) {

  }

  @Override
  public void rgbCombine(String DestImage, String DestImageRed, String DestImageGreen,
      String DestImageBlue) {

  }

  @Override
  public void flip(Axes axis, String imageName, String destImage) {

  }

  @Override
  public void greyscale(ImageComponents RGB, String imageName, String destImage) {

  }
}
