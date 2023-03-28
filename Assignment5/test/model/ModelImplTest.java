package model;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import model.Filters.GaussianBlur;
import model.Filters.ImageFilter;
import model.Filters.Sharpening;
import model.loaders.PPMImageLoader;
import model.operations.ImageOperations;
import model.operations.DitherGreyscaleOperation;
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
  public void loadPNG() {

  }

  @Test
  public void testGaussianBlur() throws IOException {
    ImageFilter blur = new GaussianBlur();

    ImageLoader loader = new PPMImageLoader();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    int[][][] image = loader.load(in);

    int[][][] filteredImage = blur.filter(image);

    assertEquals(
        "[[[0, 0, 0, 0], [0, 1, 1, 0], [0, 0, 0, 0]], "
            + "[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]], "
            + "[[1, 2, 2, 1], [2, 3, 3, 2], [1, 2, 2, 1]]]",
        Arrays.deepToString(filteredImage));
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
    assertEquals(
        "[[[0, 0, 0, 0], [0, 1, 1, 0], [0, 0, 0, 0]], "
            + "[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]], "
            + "[[1, 2, 2, 1], [2, 3, 3, 2], [1, 2, 2, 1]]]",
        Arrays.deepToString(filteredImage));
  }

  @Test
  public void testSharpening() throws IOException {
    ImageFilter blur = new Sharpening();

    ImageLoader loader = new PPMImageLoader();
    InputStream in = new FileInputStream("test/model/testImage.ppm");
    int[][][] image = loader.load(in);

    int[][][] filteredImage = blur.filter(image);

    assertEquals(
        "[[[1, 1, 1, 1], [1, 2, 2, 2], [1, 2, 2, 1]],"
            + " [[2, 3, 3, 2], [3, 5, 5, 4], [3, 4, 4, 3]],"
            + " [[3, 4, 4, 3], [5, 7, 8, 6], [4, 6, 6, 4]]]",
        Arrays.deepToString(filteredImage));
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
    assertEquals(
        "[[[1, 1, 1, 1], [1, 2, 2, 2], [1, 2, 2, 1]],"
            + " [[2, 3, 3, 2], [3, 5, 5, 4], [3, 4, 4, 3]],"
            + " [[3, 4, 4, 3], [5, 7, 8, 6], [4, 6, 6, 4]]]",
        Arrays.deepToString(filteredImage));
  }

  @Test
  public void testGreyscale() throws IOException {
    ModelV2 m = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/greenland_grid_velo.bmp");
    OutputStream out = new FileOutputStream("test/model/greenland_grid_velo_grey.jpg");

    m.load(in, "testImage", "bmp");
    m.greyscale(ImageComponents.BLUE, "testImage", "grey");
    m.save(out, "grey", "jpg");

  }

  @Test
  public void testDither() throws IOException {
    ModelV2 m = new ModelV2Impl();
    InputStream in = new FileInputStream("test/model/greenland_grid_velo_grey.jpg");
    OutputStream out = new FileOutputStream("test/model/greenland_grid_velo_dither.jpg");
    ImageOperations dither = new DitherGreyscaleOperation();
    m.load(in, "testImage", "jpg");
    m.callOperation(dither, "testImage", "dither");
    m.save(out, "dither", "jpg");

  }
}