package Model;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ModelPPMMock implements Model {

  @Override
  public void load(String filePath, String destImage) throws FileNotFoundException {
    System.out.printf("In function load with arguments %s, %s%n", filePath,
        destImage);
  }

  @Override
  public void save(String filePath, String imageName) throws IOException {
    System.out.printf("In function save with arguments %s, %s%n", filePath,
        imageName);
  }

  @Override
  public void brighten(int increment, String imageName, String destImage) {
    System.out.printf("In function brighten with arguments %s, %s, %s%n", increment, imageName,
        destImage);
  }

  @Override
  public void rgbSplit(String imageName, String DestImageRed, String DestImageGreen,
      String DestImageBlue) {
    System.out.printf("In function rgbSplit with arguments %s, %s, %s, %s%n", imageName,
        DestImageRed,
        DestImageGreen,
        DestImageBlue);
  }

  @Override
  public void rgbCombine(String DestImage, String DestImageRed, String DestImageGreen,
      String DestImageBlue) {
    System.out.printf("In function rgbCombine with arguments %s, %s, %s, %s%n", DestImage,
        DestImageRed, DestImageGreen,
        DestImageBlue);
  }

  @Override
  public void flip(Axes axis, String imageName, String destImage) {
    System.out.printf("In function flip with arguments %s, %s, %s%n", axis, imageName, destImage);
  }

  @Override
  public void greyscale(ImageComponents RGB, String imageName, String destImage) {
    System.out.printf("In function greyscale with arguments %s, %s, %s%n", RGB, imageName,
        destImage);
  }
}
