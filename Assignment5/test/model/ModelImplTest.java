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
import model.loaders.ConventionalImageLoader;
import model.loaders.PPMImageLoader;
import model.operations.ImageOperations;
import model.operations.DitherGreyscaleOperation;
import model.transforms.GreyscaleTransform;
import model.transforms.SepiaTransform;
import org.junit.Test;

public class ModelImplTest {


  @Test
  public void load() {
    InputStream in = null;
    try {
      in = new FileInputStream("test/Model/testImage.ppm");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      OutputStream out = new FileOutputStream("test/Model/testOutImage.ppm");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    Model m = new ModelImpl();
    OutputStream out = null;
    try {
      m.load(in, "testImage", "ppm");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    //PNG
    try {
      in = new FileInputStream("test/Model/greenland_grid_velo.bmp");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      out = new FileOutputStream("test/Model/testOutImage.bmp");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    try {
      m.load(in, "testPNGImage", "png");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      m.save(out, "testPNGImage", "png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }



  @Test
  public void testGaussianBlur() throws IOException {
    ImageFilter blur = new GaussianBlur();

    ImageLoader loader = new ConventionalImageLoader("jpg");
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

  @Test
  public void testDitherwhole() throws IOException {
    ModelV2 m = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/greenland_grid_velo_grey.jpg");
    OutputStream out = new FileOutputStream("test/model/greenland_grid_velo_dither.jpg");
    ImageOperations dither = new DitherGreyscaleOperation();
    m.load(in, "testImage", "jpg");
    m.callOperation(dither, "testImage", "dither");
    m.save(out, "dither", "jpg");

  }
}