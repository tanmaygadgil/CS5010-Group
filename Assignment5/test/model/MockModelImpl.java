package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import model.filters.ImageFilter;
import model.operations.ImageOperations;

/**
 * The MockModelImpl class is an implementation of the MockModel interface, which provides methods
 * for loading and saving images, performing various image operations such as greyscale, flipping,
 * brightening, darkening, splitting and combining RGB channels, as well as applying image filters
 * and transformations. The class keeps track of all the function calls made to it by appending them
 * to a log string that can be retrieved using the getLog() method.
 */
public class MockModelImpl implements MockModel {

  private StringBuilder log = null;

  /**
   * Initialize the mock model.
   */
  public MockModelImpl() {
    this.log = new StringBuilder();
  }

  @Override
  public void load(InputStream in, String destImage, String format) throws FileNotFoundException {
    this.log.append(String.format("In function load with arguments %s, %s\n", destImage, format));
  }

  @Override
  public void save(OutputStream out, String imageName, String format) throws IOException {
    this.log.append(String.format("In function save with arguments %s, %s\n", imageName, format));
  }

  @Override
  public void greyscale(ImageComponents rGB, String imageName, String destImage) {
    this.log.append(
        String.format("In function greyscale with arguments %s, %s, %s\n", rGB, imageName,
            destImage));
  }

  @Override
  public void flip(Axes axis, String imageName, String destImage) {
    this.log.append(
        String.format("In function flip with arguments %s, %s, %s\n", axis, imageName, destImage));
  }

  @Override
  public void brighten(int increment, String imageName, String destImage) {
    this.log.append(
        String.format("In function brighten with arguments %s, %s, %s\n", increment, imageName,
            destImage));
  }

  @Override
  public void darken(int increment, String imageName, String destImage) {
    this.log.append(
        String.format("In function darken with arguments %s, %s, %s\n", increment, imageName,
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
  public String getLog() {
    return this.log.toString();
  }

  @Override
  public void callFilter(ImageFilter filter, String imagename, String destname) {
    this.log.append(
        String.format("In function callFilter with arguments %s, %s, %s\n", filter.toString(),
            imagename,
            destname));
  }

  @Override
  public void callTransform(ImageTransforms transform, String imagename, String destname) {
    this.log.append(
        String.format("In function callTransform with arguments %s, %s, %s\n", transform.toString(),
            imagename,
            destname));
  }

  @Override
  public void callOperation(ImageOperations ops, String imagename, String destname) {
    this.log.append(
        String.format("In function callOperation with arguments %s, %s, %s\n", ops.toString(),
            imagename,
            destname));
  }
}
