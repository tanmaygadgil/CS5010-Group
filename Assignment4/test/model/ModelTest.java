package model;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {

  Model model;

  @Before
  public void setUp() {
    this.model = new ModelPPM();
  }


  @Test
  public void testLoad() throws IOException {

    model.load("test/Model/testImage.ppm", "testImage");
    model.save("test/Model/testSaveImage.ppm", "testImage");
    int[][][] imageSaved = ImageUtil.readPPM("test/Model/testSaveImage.ppm");
    int[][][] imageOriginal = ImageUtil.readPPM("test/Model/testImage.ppm");

    assertEquals(imageOriginal.length, imageSaved.length);
    assertEquals(imageOriginal[0].length, imageSaved[0].length);
    assertEquals(imageOriginal[0][0].length, imageSaved[0][0].length);

    for (int i = 0; i < imageSaved.length; i++) {
      for (int j = 0; j < imageSaved[0].length; j++) {
        for (int k = 0; k < imageSaved[0][0].length; k++) {
          assertEquals(imageOriginal[i][j][k], imageSaved[i][j][k]);
        }
      }
    }

  }

  @Test (expected = FileNotFoundException.class)
  public void testLoadThrowingException() throws FileNotFoundException {

    // the correct path is test/Model/testImage.ppm
    String path = "test/Model/testImage1.ppm";
    model.load(path, "testImage");
  }


  @Test (expected = IOException.class)
  public void testSaveThrowingException() throws IOException {

    model.load("test/Model/testImage.ppm", "testImage");
    model.save("test/Model/testSaveImage.ppm", "testImage1");

  }


  @Test
  public void testSave() throws IOException {

    model.load("test/Model/testImage.ppm", "testImage");
    model.save("test/Model/testSaveImage.ppm", "testImage");
    int[][][] imageSaved = ImageUtil.readPPM("test/Model/testSaveImage.ppm");
    int[][][] imageOriginal = ImageUtil.readPPM("test/Model/testImage.ppm");

    assertEquals(imageOriginal.length, imageSaved.length);
    assertEquals(imageOriginal[0].length, imageSaved[0].length);
    assertEquals(imageOriginal[0][0].length, imageSaved[0][0].length);

    assertEquals(imageOriginal, imageSaved);
  }

  @Test
  public void testBrighten() throws IOException {
    String path = "test/Model/testImage.ppm";
    int[][][] image = ImageUtil.readPPM(path);
    int increment = 10;

    model.load(path, "testImage");
    model.brighten(increment, "testImage", "testImageBrighten");
    model.save("test/Model/testImageBrighten.ppm", "testImageBrighten");
    int[][][] imageBrighten = ImageUtil.readPPM("test/Model/testImageBrighten.ppm");

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        for (int k = 0; k < image[0][0].length; k++) {
          assertEquals(imageBrighten[i][j][k] - image[i][j][k], 10);
        }
      }
    }

  }

  @Test
  public void testFlipVertical() throws IOException {

    String path = "test/Model/testImageAscendingRows.ppm";
    int[][][] image = new int[3][3][4];
    //generating correct answer
    for (int i = 3; i >= 1; i--) {
      for (int j = 3; j >= 1; j--) {
        for (int k = 4; k >= 1; k--) {
          image[3 - i][3 - j][4 - k] = j;
        }
      }
    }

    model.load(path, "testImageAscendingRows");
    model.flip(Axes.VERTICAL, "testImageAscendingRows",
        "testImageAscendingRowsVertFlip");
    model.save("test/Model/testImageAscendingRowsVertFlip.ppm",
        "testImageAscendingRowsVertFlip");
    int[][][] imageVert = ImageUtil.readPPM("test/Model/testImageAscendingRowsVertFlip.ppm");

    assertEquals(imageVert, image);
  }

  @Test
  public void testFlipHorizontal() throws IOException {
    String path = "test/Model/testImageAscendingCols.ppm";
    int[][][] image = new int[3][3][4];
    for (int i = 3; i >= 1; i--) {
      for (int j = 3; j >= 1; j--) {
        for (int k = 4; k >= 1; k--) {
          image[3 - i][3 - j][4 - k] = k;
        }
      }
    }

    model.load(path, "testImageAscendingCols");
    model.flip(Axes.HORIZONTAL, "testImageAscendingCols",
        "testImageAscendingColsHoriFlip");
    model.save("test/Model/testImageAscendingColsHoriFlip.ppm",
        "testImageAscendingColsHoriFlip");
    int[][][] imageHori = ImageUtil.readPPM("test/Model/testImageAscendingColsHoriFlip.ppm");

    assertEquals(imageHori, image);
  }


  @Test
  public void testImageUtilGreyScale() throws IOException {
    int[][][] image = new int[1][3][4];
    for(int i = 0; i < 3; i++){
      for(int j = 1; j <= 4; j++) {
        image[0][i][j-1] = 4*i+j;
      }
    }

    model.load("test/Model/greyImage.ppm", "greyImage");
    model.save("test/Model/greyImageSave.ppm", "greyImage");
    int[][][] imageGreyScale = ImageUtil.readPPM("test/Model/greyImageSave.ppm");
  }


//  @Test (expected = ???.class)
//  public void testFlipThrowingException() throws ??? {
  @Test (expected = IOException.class)
  public void testFlipThrowingException() throws IOException {
    String path = "test/Model/testImage.ppm";
    model.load(path, "testImage");
    model.flip(Axes.HORIZONTAL, "testImage1", "testImageException");
  }


//  @Test (expected = ????.class)
//  public void testGreyscaleThrowingException() throws ??? {
  @Test (expected = IOException.class)
  public void testGreyscaleThrowingException() throws IOException {

    String path = "test/Model/testImage.ppm";
    model.load(path, "testImage");
    model.greyscale(ImageComponents.RED, "testImage1", "testImageException");

  }


  @Test
//  public void testGreyscaleRed() throws ???? {
  public void testGreyscaleRed() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(path, "testImage");
    model.greyscale(ImageComponents.RED, "testImage", "testImageGreyscaledRed");
    model.save("test/Model/testImageGreyscaledRed.ppm", "testImageGreyscaledRed");

    int[][][] expectedImage = ImageUtil.readPPM(
            "test/Model/expectedImageGreyscaledRed.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM("test/Model/testImageGreyscaleRed.ppm");
    assertEquals(expectedImage, grayScaledImage);

  }

  @Test
//  public void testGreyscaleGreen() throws ???? {
  public void testGreyscaleGreen() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(path, "testImage");
    model.greyscale(ImageComponents.GREEN, "testImage",
            "testImageGreyscaledGreen");
    model.save("test/Model/testImageGreyscaledGreen.ppm",
            "testImageGreyscaledGreen");

    int[][][] expectedImage = ImageUtil.readPPM(
            "test/Model/expectedImageGreyscaledGreen.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM(
            "test/Model/testImageGreyscaleGreen.ppm");

    assertEquals(expectedImage, grayScaledImage);

  }



  @Test
//  public void testGreyscaleBlue() throws ???? {
  public void testGreyscaleBlue() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(path, "testImage");
    model.greyscale(ImageComponents.BLUE, "testImage",
            "testImageGreyscaledBlue");
    model.save("test/Model/testImageGreyscaledBlue.ppm",
            "testImageGreyscaledBlue");

    int[][][] expectedImage = ImageUtil.readPPM(
            "test/Model/expectedImageGreyscaledBlue.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM(
            "test/Model/testImageGreyscaleBlue.ppm");

    assertEquals(expectedImage, grayScaledImage);

  }


  @Test

//  public void testGreyscaleLuma() throws ???? {
  public void testGreyscaleLuma() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(path, "testImage");
    model.greyscale(ImageComponents.LUMA, "testImage",
            "testImageGreyscaledLuma");
    model.save("test/Model/testImageGreyscaledLuma.ppm",
            "testImageGreyscaledLuma");

    int[][][] expectedImage = ImageUtil.readPPM(
            "test/Model/expectedImageGreyscaledLuma.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM(
            "test/Model/testImageGreyscaleLuma.ppm");

    assertEquals(expectedImage, grayScaledImage);

  }

  // Value

  @Test
//  public void testGreyscaleValue() throws ???? {
  public void testGreyscaleValue() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(path, "testImage");
    model.greyscale(ImageComponents.VALUE, "testImage",
            "testImageGreyscaledValue");
    model.save("test/Model/testImageGreyscaledValue.ppm",
            "testImageGreyscaledValue");

    int[][][] expectedImage = ImageUtil.readPPM(
            "test/Model/expectedImageGreyscaledValue.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM(
            "test/Model/testImageGreyscaleValue.ppm");

    assertEquals(expectedImage, grayScaledImage);

  }

  // Intensity

  @Test
//  public void testGreyscaleIntensity() throws ????? {
  public void testGreyscaleIntensity() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(path, "testImage");
    model.greyscale(ImageComponents.INTENSITY, "testImage",
            "testImageGreyscaledIntensity");
    model.save("test/Model/testImageGreyscaledIntensity.ppm",
            "testImageGreyscaledIntensity");

    int[][][] expectedImage = ImageUtil.readPPM(
            "test/Model/expectedImageGreyscaledIntensity.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM(
            "test/Model/testImageGreyscaleIntensity.ppm");

    assertEquals(expectedImage, grayScaledImage);

  }


//  @Test (expected = ???.class)
//  public void testRgbSplitThrowingException() throws ??? {
  @Test (expected = IOException.class)
  public void testRgbSplitThrowingException() throws IOException {
    String path = "test/Model/testImage.ppm";
    model.load(path, "testImage");
    model.rgbSplit("testImage1", "testImage2", "testImage3",
            "testImage4");

  }



  @Test
  //public void testRgbSplit() throws ???? {
  public void testRgbSplit() throws IOException {

    String path = "test/Model/testImage.ppm";
    model.load(path, "testImage");

    model.rgbSplit("testImage", "testImageRedSplit",
            "testImageGreenSplit","testImageBlueSplit");
    model.rgbCombine("testImageCombine", "testImageRedSplit",
            "testImageGreenSplit",
            "testImageBlueSplit");
    model.save("test/Model/combinedImage.ppm", "combinedImage");

    String pathCombinedImage = "test/Model/combinedImage.ppm";
    int[][][] combinedImage = ImageUtil.readPPM(pathCombinedImage);
    int[][][] originalImage = ImageUtil.readPPM(path);

    assertEquals(combinedImage, originalImage);

  }


  //@Test (expected = ???.class)
  //public void testRgbCombineThrowingException() throws ??? {
  @Test (expected = IOException.class)
  public void testRgbCombineThrowingException() throws IOException {

    String pathRed = "test/Model/testImageRedSplit.ppm";
    String pathGreen = "test/Model/testImageGreenSplit.ppm";
    String pathBlue = "test/Model/testImageBlueSplit.ppm";

    model.load(pathRed, "ImageRedSplit");
    model.load(pathGreen, "ImageGreenSplit");
    model.load(pathBlue, "ImageBlueSplit");

    model.rgbCombine("testImageCombine", "ImageRedSplit1",
            "ImageGreenSplit","ImageBlueSplit");
    model.rgbCombine("testImageCombine", "ImageRedSplit",
            "ImageGreenSplit1","ImageBlueSplit");
    model.rgbCombine("testImageCombine", "ImageRedSplit",
            "ImageGreenSplit","ImageBlueSplit1");
  }


  @Test
  //public void testRgbCombine() throws ??? {
    public void testRgbCombine() throws IOException{

    String pathRed = "test/Model/testImageRedSplit.ppm";
    String pathGreen = "test/Model/testImageGreenSplit.ppm";
    String pathBlue = "test/Model/testImageBlueSplit.ppm";

    model.load(pathRed, "ImageRedSplit");
    model.load(pathGreen, "ImageGreenSplit");
    model.load(pathBlue, "ImageBlueSplit");


    model.rgbCombine("testImageCombine", "ImageRedSplit",
            "ImageGreenSplit","ImageBlueSplit");
    model.save("test/Model/testImageCombine.ppm", "testImageCombine");

    String pathCombinedImage = "test/Model/testImageCombine.ppm";
    int[][][] combinedImage = ImageUtil.readPPM(pathCombinedImage);

    String pathOriginalImage = "test/Model/testImage.ppm";
    int[][][] originalImage = ImageUtil.readPPM(pathOriginalImage);

    assertEquals(combinedImage, originalImage);

  }


}
