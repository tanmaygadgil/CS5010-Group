package integration;

import static org.junit.Assert.assertEquals;

import controller.Controller;
import controller.ControllerCommandLine;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import model.ImageLoader;
import model.ImageUtil;
import model.Model;
import model.ModelImpl;
import model.ModelV2;
import model.ModelV2Impl;
import model.loaders.PPMImageLoader;
import org.junit.Test;
import view.TextInputView;
import view.View;

/**
 * Test class for integration tests.
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
    String input = "load test/model/testImage.ppm testImage\n gaussian-blur testImage blurredImage\n "
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
    int[][][] filteredImage = loader.load(new FileInputStream("test/integration/sharpenedImage.ppm"));

    assertEquals("[[[1, 1, 1, 1], [1, 2, 2, 1], [1, 1, 1, 1]], "
        + "[[2, 3, 3, 2], [3, 5, 5, 3], [2, 3, 3, 2]], "
        + "[[3, 4, 4, 3], [5, 7, 7, 5], [3, 4, 4, 3]]]", Arrays.deepToString(filteredImage));
  }

  @Test
  public void testAllCommands() throws IOException{
    ModelV2 m = new ModelV2Impl();
    String input = "test/scripts/allCommands.txt";
    View v = new TextInputView("script", input);
    Controller c = new ControllerCommandLine(m, v);
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    c.run();
  }

}
