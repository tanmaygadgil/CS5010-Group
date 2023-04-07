package integration;

import static org.junit.Assert.assertEquals;

import controller.Controller;
import controller.ControllerCommandLine;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import model.ImageLoader;
import model.ModelV2;
import model.ModelV2Impl;
import model.loaders.PPMImageLoader;
import org.junit.Test;
import view.TextInputView;
import view.View;

/**
 * IntegrationTestV2 class includes tests for testing the integration of different components of the
 * program. It tests the functionality of ModelV2Impl, ControllerCommandLine, PPMImageLoader,
 * TextInputView, and ImageUtil.
 */
public class IntegrationTestV2 {

  @Test
  public void testBlur() throws IOException {
    ModelV2 m = new ModelV2Impl();
    String input = "test/scripts/blurImage.txt";
    View v = new TextInputView("script", input);
    Controller c = new ControllerCommandLine(m, v);
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    c.run();

    ImageLoader loader = new PPMImageLoader();
    int[][][] filteredImage = loader.load(new FileInputStream("test/integration/blurredImage.ppm"));

    assertEquals("[[[0, 0, 0, 0], [0, 1, 1, 0], [0, 0, 0, 0]], "
        + "[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]], "
        + "[[1, 2, 2, 1], [2, 3, 3, 2], [1, 2, 2, 1]]]", Arrays.deepToString(filteredImage));
  }

  @Test
  public void testBlurCommandLine() throws IOException {
    ModelV2 m = new ModelV2Impl();
    String input =
        "load test/model/testImage.ppm testImage\n gaussian-blur testImage blurredImage\n "
            + "save test/integration/blurredImage.ppm blurredImage\nexit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    View v = new TextInputView();
    Controller c = new ControllerCommandLine(m, v);

    c.run();

    ImageLoader loader = new PPMImageLoader();
    int[][][] filteredImage = loader.load(new FileInputStream("test/integration/blurredImage.ppm"));

    assertEquals("[[[0, 0, 0, 0], [0, 1, 1, 0], [0, 0, 0, 0]], "
        + "[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]], "
        + "[[1, 2, 2, 1], [2, 3, 3, 2], [1, 2, 2, 1]]]", Arrays.deepToString(filteredImage));
  }

  @Test
  public void testSharpen() throws IOException {
    ModelV2 m = new ModelV2Impl();
    String input = "test/scripts/sharpenImage.txt";
    View v = new TextInputView("script", input);
    Controller c = new ControllerCommandLine(m, v);
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    c.run();

    ImageLoader loader = new PPMImageLoader();
    int[][][] filteredImage = loader.load(
        new FileInputStream("test/integration/sharpenedImage.ppm"));

    assertEquals("[[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]], "
        + "[[2, 3, 3, 2], [3, 5, 5, 3], [2, 3, 3, 2]], "
        + "[[3, 4, 4, 3], [5, 7, 7, 5], [3, 4, 4, 3]]]", Arrays.deepToString(filteredImage));
  }

  @Test
  public void testSharpenCommandLine() throws IOException {
    ModelV2 m = new ModelV2Impl();
    String input = "load test/model/testImage.ppm testImage\n sharpen testImage sharpenedImage\n "
        + "save test/integration/sharpenedImage.ppm sharpenedImage\nexit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    View v = new TextInputView();
    Controller c = new ControllerCommandLine(m, v);

    c.run();

    ImageLoader loader = new PPMImageLoader();
    int[][][] filteredImage = loader.load(
        new FileInputStream("test/integration/sharpenedImage.ppm"));

    assertEquals("[[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]], "
        + "[[2, 3, 3, 2], [3, 5, 5, 3], [2, 3, 3, 2]], "
        + "[[3, 4, 4, 3], [5, 7, 7, 5], [3, 4, 4, 3]]]", Arrays.deepToString(filteredImage));
  }

  @Test
  public void testDitherCommandLine() throws IOException {
    ModelV2 m = new ModelV2Impl();
    String input = "load test/model/testImage2.ppm testImage\n dither testImage ditheredImage\n "
        + "save test/integration/ditheredTestImage.ppm ditheredImage\nexit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    View v = new TextInputView();
    Controller c = new ControllerCommandLine(m, v);

    c.run();

    ImageLoader loader = new PPMImageLoader();
    int[][][] filteredImage = loader.load(
        new FileInputStream("test/integration/ditheredTestImage.ppm"));

    assertEquals("[[[255, 255, 0, 0], [0, 0, 255, 255], [0, 255, 255, 0]], "
            + "[[255, 255, 0, 0], [0, 0, 255, 255], [0, 255, 255, 0]], "
            + "[[255, 255, 0, 0], [0, 0, 255, 255], [0, 255, 255, 0]]]",
        Arrays.deepToString(filteredImage));
  }

  @Test
  public void testGreyscaleCommandLine() throws IOException {
    ModelV2 m = new ModelV2Impl();
    String input = "load test/model/testImage.ppm testImage\n greyscale testImage" +
        " greyscaledImageCommandLine\n" +
        " save test/integration/greyscaledImageCommandLine.ppm" +
        " greyscaledImageCommandLine\nexit";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    View v = new TextInputView();
    Controller c = new ControllerCommandLine(m, v);

    c.run();

    ImageLoader loader = new PPMImageLoader();
    int[][][] greyscaledImage = loader.load(new FileInputStream(
        "test/integration/greyscaledImageCommandLine.ppm"));

    assertEquals(
        "[[[1, 1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1]]]",
        Arrays.deepToString(greyscaledImage));
  }

  @Test
  public void testSepiaCommandLine() throws IOException {
    ModelV2 m = new ModelV2Impl();
    String input = "load test/model/testImage.ppm testImage\n" +
        " sepia testImage sepiadImageCommandLine\n" +
        " save test/integration/sepiadImageCommandLine.ppm sepiadImageCommandLine\nexit";

    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    View v = new TextInputView();
    Controller c = new ControllerCommandLine(m, v);

    c.run();

    ImageLoader loader = new PPMImageLoader();
    int[][][] sepiadImage = loader.load(new FileInputStream(
        "test/integration/sepiadImageCommandLine.ppm"));

    assertEquals(
        "[[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]], "
            + "[[2, 2, 2, 2], [2, 2, 2, 2], [2, 2, 2, 2]], "
            + "[[1, 1, 1, 1], [1, 1, 1, 1], [1, 1, 1, 1]]]",
        Arrays.deepToString(sepiadImage));
  }

  @Test
  public void testScript() throws IOException {
    ModelV2 m = new ModelV2Impl();
    String input = "test/scripts/script.txt";
    View v = new TextInputView("script", input);
    Controller c = new ControllerCommandLine(m, v);
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    c.run();

  }


}
