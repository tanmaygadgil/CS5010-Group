package model;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import model.filters.GaussianBlur;
import model.filters.ImageFilter;
import model.filters.Sharpening;
import model.loaders.PPMImageLoader;
import model.operations.DitherGreyscaleOperation;
import model.operations.ImageOperations;
import model.transforms.GreyscaleTransform;
import model.transforms.ImageTransforms;
import model.transforms.SepiaTransform;
import org.junit.Before;
import org.junit.Test;

/**
 * THis sia testing class out model Implementation.
 */
public class ModelImplTest {

  Model model;

  @Before
  public void setUp() {
    this.model = new ModelImpl();
  }


  @Test
  public void testLoad() throws IOException {
    model.load(new FileInputStream("test/Model/testImage.ppm"), "testImage", "ppm");
    model.save(new FileOutputStream("test/Model/testSaveImage.ppm"), "testImage", "ppm");

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

  @Test(expected = FileNotFoundException.class)
  public void testLoadThrowingException() throws FileNotFoundException {

    // the correct path is test/Model/testImage.ppm
    String path = "test/Model/testImage1.ppm";
    model.load(new FileInputStream(path), "testImage", "ppm");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testSaveThrowingException() throws IOException {

    model.load(new FileInputStream("test/Model/testImage.ppm"), "testImage", "ppm");
    model.save(new FileOutputStream("test/Model/testSaveImage.ppm"), "testImage1", "ppm");

  }


  @Test
  public void testSave() throws IOException {
    model.load(new FileInputStream("test/Model/testImage.ppm"), "testImage", "ppm");
    model.save(new FileOutputStream("test/Model/testSaveImage.ppm"), "testImage", "ppm");
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

    model.load(new FileInputStream(path), "testImage", "ppm");
    model.brighten(increment, "testImage", "testImageBrighten");
    model.save(new FileOutputStream("test/Model/testImageBrighten.ppm"), "testImageBrighten",
        "ppm");
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

    model.load(new FileInputStream(path), "testImageAscendingRows", "ppm");
    model.flip(Axes.VERTICAL, "testImageAscendingRows", "testImageAscendingRowsVertFlip");
    model.save(new FileOutputStream("test/Model/testImageAscendingRowsVertFlip.ppm"),
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


  @Test(expected = IllegalArgumentException.class)
  public void testFlipThrowingException() throws IOException {
    String path = "test/model/testImage.ppm";
    model.load(new FileInputStream(path), "testImage", "ppm");
    model.flip(Axes.HORIZONTAL, "testImage1", "testImageException");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleThrowingException() throws IOException {

    String path = "test/Model/testImage.ppm";
    model.load(new FileInputStream(path), "testImage", "ppm");
    model.greyscale(ImageComponents.RED, "testImage1", "testImageException");

  }


  @Test
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


  @Test
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


  @Test
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


  @Test(expected = IllegalArgumentException.class)
  public void testRgbSplitThrowingException() throws IOException {
    String path = "test/Model/testImage.ppm";
    model.load(new FileInputStream(path), "testImage", "ppm");
    model.rgbSplit("testImage1", "testImage2", "testImage3", "testImage4");

  }


  @Test
  public void testRgbSplit() throws IOException {

    String path = "test/Model/testImage.ppm";
    model.load(new FileInputStream(path), "testImage", "ppm");

    model.rgbSplit("testImage", "testImageRedSplit", "testImageGreenSplit", "testImageBlueSplit");
    model.rgbCombine("testImageCombine", "testImageRedSplit", "testImageGreenSplit",
        "testImageBlueSplit");
    model.save(new FileOutputStream("test/Model/combinedImage.ppm"), "testImageCombine", "ppm");

    String pathCombinedImage = "test/Model/combinedImage.ppm";
    int[][][] combinedImage = ImageUtil.readPPM(pathCombinedImage);
    int[][][] originalImage = ImageUtil.readPPM(path);

    assertEquals(combinedImage, originalImage);

  }


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

  @Test
  public void testGaussianBlur() throws IOException {
    ImageFilter blur = new GaussianBlur();

    ImageLoader loader = new PPMImageLoader();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    int[][][] image = loader.load(in);

    int[][][] filteredImage = blur.filter(image);

    assertEquals("[[[0, 0, 0, 0], [0, 1, 1, 0], [0, 0, 0, 0]], "
        + "[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]], "
        + "[[1, 2, 2, 1], [2, 3, 3, 2], [1, 2, 2, 1]]]", Arrays.deepToString(filteredImage));
  }

  @Test
  public void testDither() throws IOException {
    ImageOperations op = new DitherGreyscaleOperation();
    ImageTransforms grey = new GreyscaleTransform();
    ImageLoader loader = new PPMImageLoader();
    InputStream in = new FileInputStream("test/model/testImage2.ppm");

    int[][][] image = loader.load(in);
    int[][][] greyImg = grey.transform(image);
    int[][][] filteredImage = op.operate(greyImg);

    String expected = "[[[255, 255, 0, 0], [0, 0, 255, 255], [0, 255, 255, 0]], "
        + "[[255, 255, 0, 0], [0, 0, 255, 255], [0, 255, 255, 0]], "
        + "[[255, 255, 0, 0], [0, 0, 255, 255], [0, 255, 255, 0]]]";

    assertEquals(expected, Arrays.deepToString(filteredImage));

  }

  @Test
  public void testCallFilterGaussianBlur() throws IOException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    OutputStream out = new FileOutputStream("test/model/blurredImage.ppm");
    model.load(in, "testImage", "ppm");
    model.callFilter(new GaussianBlur(), "testImage", "blurredImage");
    model.save(out, "blurredImage", "ppm");

    ImageLoader loader = new PPMImageLoader();
    int[][][] filteredImage = loader.load(new FileInputStream("test/model/blurredImage.ppm"));
    assertEquals("[[[0, 0, 0, 0], [0, 1, 1, 0], [0, 0, 0, 0]], "
        + "[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]], "
        + "[[1, 2, 2, 1], [2, 3, 3, 2], [1, 2, 2, 1]]]", Arrays.deepToString(filteredImage));
  }

  @Test
  public void testSharpening() throws IOException {
    ImageFilter blur = new Sharpening();

    ImageLoader loader = new PPMImageLoader();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    int[][][] image = loader.load(in);

    int[][][] filteredImage = blur.filter(image);

    assertEquals("[[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]],"
        + " [[2, 3, 3, 2], [3, 5, 5, 3], [2, 3, 3, 2]], "
        + "[[3, 4, 4, 3], [5, 7, 7, 5], [3, 4, 4, 3]]]", Arrays.deepToString(filteredImage));
  }

  @Test
  public void testCallFilterSharpening() throws IOException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    OutputStream out = new FileOutputStream("test/model/sharpenedImage.ppm");
    model.load(in, "testImage", "ppm");
    model.callFilter(new Sharpening(), "testImage", "sharpenedImage");
    model.save(out, "sharpenedImage", "ppm");

    ImageLoader loader = new PPMImageLoader();
    int[][][] filteredImage = loader.load(new FileInputStream("test/model/sharpenedImage.ppm"));
    assertEquals("[[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]], "
        + "[[2, 3, 3, 2], [3, 5, 5, 3], [2, 3, 3, 2]], "
        + "[[3, 4, 4, 3], [5, 7, 7, 5], [3, 4, 4, 3]]]", Arrays.deepToString(filteredImage));
  }

  @Test
  public void testCallDitheringOperation() throws IOException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImage2.ppm");
    OutputStream out = new FileOutputStream("test/model/ditheredImage.ppm");
    model.load(in, "testImage", "ppm");
    model.callTransform(new GreyscaleTransform(), "testImage", "greyImage");
    model.callOperation(new DitherGreyscaleOperation(), "greyImage",
        "ditheredImage");
    model.save(out, "ditheredImage", "ppm");

    ImageLoader loader = new PPMImageLoader();
    int[][][] transformedImage = loader.load(new FileInputStream(
        "test/model/ditheredImage.ppm"));
    assertEquals("[[[255, 255, 0, 0], [0, 0, 255, 255], [0, 255, 255, 0]], "
            + "[[255, 255, 0, 0], [0, 0, 255, 255], [0, 255, 255, 0]], "
            + "[[255, 255, 0, 0], [0, 0, 255, 255], [0, 255, 255, 0]]]",
        Arrays.deepToString(transformedImage));
  }

  @Test
  public void testCallTransformSepiaTransform() throws IOException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    OutputStream out = new FileOutputStream("test/model/sepiatransformedImage.ppm");
    model.load(in, "testImage", "ppm");
    model.callTransform(new SepiaTransform(), "testImage",
        "sepiatransformedImage");
    model.save(out, "sepiatransformedImage", "ppm");

    ImageLoader loader = new PPMImageLoader();
    int[][][] transformedImage = loader.load(new FileInputStream(
        "test/model/sepiatransformedImage.ppm"));
    assertEquals(
        "[[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]], "
            + "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]], "
            + "[[1, 1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1]]]",
        Arrays.deepToString(transformedImage));
  }

  @Test
  public void testCallTransformGreyscaleTransform() throws IOException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    OutputStream out = new FileOutputStream("test/model/greyscaledImage.ppm");
    model.load(in, "testImage", "ppm");
    model.callTransform(new GreyscaleTransform(), "testImage", "greyscaledImage");
    model.save(out, "greyscaledImage", "ppm");

    ImageLoader loader = new PPMImageLoader();
    int[][][] transformedImage = loader.load(new FileInputStream(
        "test/model/greyscaledImage.ppm"));
    assertEquals(
        "[[[1, 1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1]]]",
        Arrays.deepToString(transformedImage));
  }

  @Test
  public void testGreyscale() throws IOException {

    ImageTransforms grey = new GreyscaleTransform();
    ImageLoader loader = new PPMImageLoader();
    InputStream in = new FileInputStream("test/model/testImage2.ppm");

    int[][][] image = loader.load(in);

    int[][][] greyImage = grey.transform(image);
    assertEquals(
        "[[[225, 163, 174, 70], [83, 97, 195, 140], [81, 86, 199, 106]]]",
        Arrays.deepToString(greyImage));


  }

  @Test
  public void testSepia() throws IOException {
    ImageTransforms sepia = new SepiaTransform();
    ImageLoader loader = new PPMImageLoader();
    InputStream in = new FileInputStream("test/model/testImage.ppm");

    int[][][] image = loader.load(in);

    int[][][] sepiaImage = sepia.transform(image);
    assertEquals(
        "[[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]], "
            + "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]],"
            + " [[1, 1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1]]]",
        Arrays.deepToString(sepiaImage));
  }

  @Test(expected = IllegalStateException.class)
  public void testCallFilterGaussianBlurException()
      throws IllegalStateException, FileNotFoundException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    OutputStream out = new FileOutputStream("test/model/blurredImage.ppm");
    model.load(in, "testImage", "ppm");
    model.callFilter(new GaussianBlur(), "testImage1", "blurredImage");
  }

  @Test(expected = IllegalStateException.class)
  public void testCallTransformSepiaTransformException() throws IllegalStateException,
      FileNotFoundException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    OutputStream out = new FileOutputStream("test/model/sepiatransformedImage.ppm");
    model.load(in, "testImage", "ppm");
    model.callTransform(new SepiaTransform(), "testImage1",
        "sepiatransformedImage");
  }

  @Test(expected = IllegalStateException.class)
  public void testCallDitheringOperationException() throws IllegalStateException,
      FileNotFoundException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImage2.ppm");
    OutputStream out = new FileOutputStream("test/model/ditheredImage.ppm");
    model.load(in, "testImage", "ppm");
    model.callTransform(new GreyscaleTransform(), "testImage", "greyImage");
    model.callOperation(new DitherGreyscaleOperation(), "greyImage1",
        "ditheredImage");
  }

  @Test(expected = IllegalStateException.class)
  public void testCallFilterSharpeningException()
      throws IllegalStateException, FileNotFoundException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    OutputStream out = new FileOutputStream("test/model/blurredImage.ppm");
    model.load(in, "testImage", "ppm");
    model.callFilter(new Sharpening(), "testImage1", "blurredImage");
  }


  @Test(expected = FileNotFoundException.class)
  public void testLoadException() throws IOException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImageException.ppm");
    model.load(in, "testImage", "ppm");
  }



  @Test (expected = IllegalArgumentException.class)
  public void testSaveException() throws IOException {
    ModelV2 model = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    model.load(in, "testImage", "ppm");
    OutputStream out = new FileOutputStream("test/model/testSaveException.ppm");
    model.save(out, "testImageSaveException", "ppm");

  }


}