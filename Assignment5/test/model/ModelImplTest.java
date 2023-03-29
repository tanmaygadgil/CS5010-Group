package model;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for testing the model.
 */
public class ModelImplTest {

  Model model;
  InputStream in;
  OutputStream out;

  @Before
  public void setUp() {
    this.model = new ModelImpl();
  }


  @Test
  public void testLoad() throws IOException {
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    OutputStream out = new FileOutputStream("test/model/testSaveImage.ppm");
    model.load(in, "testImage", "ppm");
    model.save(out, "testImage", "ppm");

    int[][][] imageSaved = ImageUtil.readPPM("test/model/testSaveImage.ppm");
    int[][][] imageOriginal = ImageUtil.readPPM("test/model/testImage.ppm");

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

  @Test(expected = FileNotFoundException.class)
  public void testLoadThrowingException() throws FileNotFoundException {

    // the correct path is test/Model/testImage.ppm
    String path = "test/model/testImage1.ppm";
    in = new FileInputStream(path);
    model.load(in, "testImage", "ppm");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testSaveThrowingException() throws IOException {
    in = new FileInputStream("test/model/testImage.ppm");
    out = new FileOutputStream("test/model/testSaveImage.ppm");
    model.load(in, "testImage", "ppm");
    model.save(out, "testImage1", "ppm");

  }


  @Test
  public void testSave() throws IOException {
    in = new FileInputStream("test/model/testImage.ppm");
    out = new FileOutputStream("test/model/testSaveImage.ppm");
    model.load(in, "testImage", "ppm");
    model.save(out, "testImage", "ppm");
    int[][][] imageSaved = ImageUtil.readPPM("test/model/testSaveImage.ppm");
    int[][][] imageOriginal = ImageUtil.readPPM("test/model/testImage.ppm");

    assertEquals(imageOriginal.length, imageSaved.length);
    assertEquals(imageOriginal[0].length, imageSaved[0].length);
    assertEquals(imageOriginal[0][0].length, imageSaved[0][0].length);

    assertEquals(imageOriginal, imageSaved);
  }

  @Test
  public void testBrighten() throws IOException {
    String path = "test/model/testImage.ppm";
    int[][][] image = ImageUtil.readPPM(path);
    int increment = 10;
    in = new FileInputStream(path);
    out = new FileOutputStream("test/model/testImageBrighten.ppm");

    model.load(in, "testImage", "ppm");
    model.brighten(increment, "testImage", "testImageBrighten");
    model.save(out, "testImageBrighten", "ppm");
    int[][][] imageBrighten = ImageUtil.readPPM("test/model/testImageBrighten.ppm");

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        for (int k = 0; k < image[0][0].length; k++) {
          assertEquals(imageBrighten[i][j][k] - image[i][j][k], 10);
        }
      }
    }

  }

  @Test
  public void testDarken() throws IOException {
    String path = "test/model/testImage.ppm";
    int[][][] image = ImageUtil.readPPM(path);
    int increment = 10;
    in = new FileInputStream(path);
    out = new FileOutputStream("test/model/testImageDarken.ppm");

    model.load(in, "testImage", "ppm");
    model.darken(increment, "testImage", "testImageDarken");
    model.save(out, "testImageDarken", "ppm");
    int[][][] imageDarken = ImageUtil.readPPM("test/model/testImageDarken.ppm");

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        for (int k = 0; k < image[0][0].length; k++) {
          assertEquals(imageDarken[i][j][k], 0);
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

    model.load(new FileInputStream(path), "testImageAscendingRows", "ppm");
    model.flip(Axes.VERTICAL, "testImageAscendingRows", "testImageAscendingRowsVertFlip");
    model.save(new FileOutputStream("test/model/testImageAscendingRowsVertFlip.ppm"),
        "testImageAscendingRowsVertFlip", "ppm");
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

    model.load(new FileInputStream(path), "testImageAscendingCols", "ppm");
    model.flip(Axes.HORIZONTAL, "testImageAscendingCols", "testImageAscendingColsHoriFlip");
    model.save(new FileOutputStream("test/Model/testImageAscendingColsHoriFlip.ppm"),
        "testImageAscendingColsHoriFlip", "ppm");
    int[][][] imageHori = ImageUtil.readPPM("test/Model/testImageAscendingColsHoriFlip.ppm");

    assertEquals(imageHori, image);
  }


  @Test
  public void testImageUtilGreyScale() throws IOException {
    int[][][] image = new int[1][3][4];
    for (int i = 0; i < 3; i++) {
      for (int j = 1; j <= 4; j++) {
        image[0][i][j - 1] = 4 * i + j;
      }
    }

    model.load(new FileInputStream("test/Model/greyImage.ppm"), "greyImage", "ppm");
    model.save(new FileOutputStream("test/Model/greyImageSave.ppm"), "greyImage", "ppm");
    int[][][] imageGreyScale = ImageUtil.readPPM("test/Model/greyImageSave.ppm");
  }


  //  @Test (expected = ???.class)
//  public void testFlipThrowingException() throws ??? {
  @Test(expected = IllegalArgumentException.class)
  public void testFlipThrowingException() throws IOException {
    String path = "test/Model/testImage.ppm";
    model.load(new FileInputStream(path), "testImage", "ppm");
    model.flip(Axes.HORIZONTAL, "testImage1", "testImageException");
  }


  //  @Test (expected = ????.class)
//  public void testGreyscaleThrowingException() throws ??? {
  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleThrowingException() throws IOException {

    String path = "test/Model/testImage.ppm";
    model.load(new FileInputStream(path), "testImage", "ppm");
    model.greyscale(ImageComponents.RED, "testImage1", "testImageException");

  }


  @Test
//  public void testGreyscaleRed() throws ???? {
  public void testGreyscaleRed() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(new FileInputStream(path), "testImage", "ppm");
    model.greyscale(ImageComponents.RED, "testImage", "testImageGreyscaledRed");
    model.save(new FileOutputStream("test/Model/testImageGreyscaledRed.ppm"),
        "testImageGreyscaledRed", "ppm");

    int[][][] expectedImage = ImageUtil.readPPM("test/Model/expectedImageGreyscaledRed.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM("test/Model/testImageGreyscaleRed.ppm");
    assertEquals(expectedImage, grayScaledImage);

  }

  @Test
//  public void testGreyscaleGreen() throws ???? {
  public void testGreyscaleGreen() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(new FileInputStream(path), "testImage", "ppm");
    model.greyscale(ImageComponents.GREEN, "testImage", "testImageGreyscaledGreen");
    model.save(new FileOutputStream("test/Model/testImageGreyscaledGreen.ppm"),
        "testImageGreyscaledGreen", "ppm");

    int[][][] expectedImage = ImageUtil.readPPM("test/Model/expectedImageGreyscaledGreen.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM("test/Model/testImageGreyscaleGreen.ppm");

    assertEquals(expectedImage, grayScaledImage);

  }


  @Test
//  public void testGreyscaleBlue() throws ???? {
  public void testGreyscaleBlue() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(new FileInputStream(path), "testImage", "ppm");
    model.greyscale(ImageComponents.BLUE, "testImage", "testImageGreyscaledBlue");
    model.save(new FileOutputStream("test/Model/testImageGreyscaledBlue.ppm"),
        "testImageGreyscaledBlue", "ppm");

    int[][][] expectedImage = ImageUtil.readPPM("test/Model/expectedImageGreyscaledBlue.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM("test/Model/testImageGreyscaleBlue.ppm");

    assertEquals(expectedImage, grayScaledImage);

  }


  @Test

//  public void testGreyscaleLuma() throws ???? {
  public void testGreyscaleLuma() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(new FileInputStream(path), "testImage", "ppm");
    model.greyscale(ImageComponents.LUMA, "testImage", "testImageGreyscaledLuma");
    model.save(new FileOutputStream("test/Model/testImageGreyscaledLuma.ppm"),
        "testImageGreyscaledLuma", "ppm");

    int[][][] expectedImage = ImageUtil.readPPM("test/Model/expectedImageGreyscaledLuma.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM("test/Model/testImageGreyscaleLuma.ppm");

    assertEquals(expectedImage, grayScaledImage);

  }

  // Value

  @Test
//  public void testGreyscaleValue() throws ???? {
  public void testGreyscaleValue() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(new FileInputStream(path), "testImage", "ppm");
    model.greyscale(ImageComponents.VALUE, "testImage", "testImageGreyscaledValue");
    model.save(new FileOutputStream("test/Model/testImageGreyscaledValue.ppm"),
        "testImageGreyscaledValue", "ppm");

    int[][][] expectedImage = ImageUtil.readPPM("test/Model/expectedImageGreyscaledValue.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM("test/Model/testImageGreyscaleValue.ppm");

    assertEquals(expectedImage, grayScaledImage);

  }

  // Intensity

  @Test
//  public void testGreyscaleIntensity() throws ????? {
  public void testGreyscaleIntensity() throws IOException {

    String path = "test/Model/testImage.ppm";

    model.load(new FileInputStream(path), "testImage", "ppm");
    model.greyscale(ImageComponents.INTENSITY, "testImage", "testImageGreyscaledIntensity");
    model.save(new FileOutputStream("test/Model/testImageGreyscaledIntensity.ppm"),
        "testImageGreyscaledIntensity", "ppm");

    int[][][] expectedImage = ImageUtil.readPPM("test/Model/expectedImageGreyscaledIntensity.ppm");
    int[][][] grayScaledImage = ImageUtil.readPPM("test/Model/testImageGreyscaleIntensity.ppm");

    assertEquals(expectedImage, grayScaledImage);

  }


  //  @Test (expected = ???.class)
//  public void testRgbSplitThrowingException() throws ??? {
  @Test(expected = IllegalArgumentException.class)
  public void testRgbSplitThrowingException() throws IOException {
    String path = "test/Model/testImage.ppm";
    model.load(new FileInputStream(path), "testImage", "ppm");
    model.rgbSplit("testImage1", "testImage2", "testImage3", "testImage4");

  }


  @Test
  //public void testRgbSplit() throws ???? {
  public void testRgbSplit() throws IOException {

    String path = "test/model/testImage.ppm";
    model.load(new FileInputStream(path), "testImage", "ppm");

    model.rgbSplit("testImage", "testImageRedSplit", "testImageGreenSplit", "testImageBlueSplit");
    model.rgbCombine("testImageCombine", "testImageRedSplit", "testImageGreenSplit",
        "testImageBlueSplit");
    model.save(new FileOutputStream("test/model/combinedImage.ppm"), "testImageCombine", "ppm");

    String pathCombinedImage = "test/model/combinedImage.ppm";
    int[][][] combinedImage = ImageUtil.readPPM(pathCombinedImage);
    int[][][] originalImage = ImageUtil.readPPM(path);

    assertEquals(combinedImage, originalImage);

  }


  //@Test (expected = ???.class)
  //public void testRgbCombineThrowingException() throws ??? {
  @Test(expected = IllegalArgumentException.class)
  public void testRgbCombineThrowingException() throws IOException {

    String pathRed = "test/Model/testImageRedSplit.ppm";
    String pathGreen = "test/Model/testImageGreenSplit.ppm";
    String pathBlue = "test/Model/testImageBlueSplit.ppm";

    model.load(new FileInputStream(pathRed), "ImageRedSplit", "ppm");
    model.load(new FileInputStream(pathGreen), "ImageGreenSplit", "ppm");
    model.load(new FileInputStream(pathBlue), "ImageBlueSplit", "ppm");

    model.rgbCombine("testImageCombine", "ImageRedSplit1", "ImageGreenSplit", "ImageBlueSplit");
    model.rgbCombine("testImageCombine", "ImageRedSplit", "ImageGreenSplit1", "ImageBlueSplit");
    model.rgbCombine("testImageCombine", "ImageRedSplit", "ImageGreenSplit", "ImageBlueSplit1");
  }


  @Test
  //public void testRgbCombine() throws ??? {
  public void testRgbCombine() throws IOException {

    String pathRed = "test/Model/testImageRedSplit.ppm";
    String pathGreen = "test/Model/testImageGreenSplit.ppm";
    String pathBlue = "test/Model/testImageBlueSplit.ppm";

    model.load(new FileInputStream(pathRed), "ImageRedSplit", "ppm");
    model.load(new FileInputStream(pathGreen), "ImageGreenSplit", "ppm");
    model.load(new FileInputStream(pathBlue), "ImageBlueSplit", "ppm");

    model.rgbCombine("testImageCombine", "ImageRedSplit", "ImageGreenSplit", "ImageBlueSplit");
    model.save(new FileOutputStream("test/Model/testImageCombine.ppm"), "testImageCombine", "ppm");

    String pathCombinedImage = "test/Model/testImageCombine.ppm";
    int[][][] combinedImage = ImageUtil.readPPM(pathCombinedImage);

    String pathOriginalImage = "test/Model/testImage.ppm";
    int[][][] originalImage = ImageUtil.readPPM(pathOriginalImage);

    assertEquals(combinedImage, originalImage);

  }


}