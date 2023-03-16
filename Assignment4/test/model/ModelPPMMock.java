package model;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is a mock of the model class for testing purposes.
 */
public class ModelPPMMock implements MockModel {

  private StringBuilder log = null;

  /**
   * Initialize the mock model.
   */
  public ModelPPMMock() {
    this.log = new StringBuilder();
  }

  @Override
  public void load(String filePath, String destImage) throws FileNotFoundException {
    this.log.append(String.format("In function load with arguments %s, %s\n", filePath, destImage));

  }

  @Override
  public void save(String filePath, String imageName) throws IOException {
    this.log.append(String.format("In function save with arguments %s, %s\n", filePath, imageName));
  }

  @Override
  public void brighten(int increment, String imageName, String destImage) {
    this.log.append(
        String.format("In function brighten with arguments %s, %s, %s\n", increment, imageName,
            destImage));
  }

  @Override
  public void rgbSplit(String imageName, String destImageRed, String destImageGreen,
      String destImageBlue) {
    this.log.append(String.format("In function rgbSplit with arguments %s, %s, %s, %s\n", imageName,
        destImageRed, destImageGreen, destImageBlue));
  }

  @Override
  public void rgbCombine(String destImage, String destImageRed, String destImageGreen,
      String destImageBlue) {
    this.log.append(
        String.format("In function rgbCombine with arguments %s, %s, %s, %s\n", destImage,
            destImageRed, destImageGreen, destImageBlue));
  }

  @Override
  public void flip(Axes axis, String imageName, String destImage) {
    this.log.append(
        String.format("In function flip with arguments %s, %s, %s\n", axis, imageName, destImage));
  }

  @Override
  public void greyscale(ImageComponents rGB, String imageName, String destImage) {
    this.log.append(
        String.format("In function greyscale with arguments %s, %s, %s\n", rGB, imageName,
            destImage));
  }

  public String getLog() {
    return this.log.toString();
  }
}
