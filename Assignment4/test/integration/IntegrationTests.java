package Integration;

import static org.junit.Assert.assertEquals;

import model.ImageUtil;
import controller.Controller;
import model.Model;
import view.View;
import model.ModelPPM;
import view.TextInputView;
import controller.ControllerCommandLine;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/**
 * Test class for integration tests.
 */
public class IntegrationTests {

  @Test
  public void testScriptVertical() throws IOException {
    //vertical flip the image and save it
    Model m = new ModelPPM();
    String input = "test/scripts/flipSimpleMat.txt";
    View v = new TextInputView("script", input);
    Controller c = new ControllerCommandLine(m, v);

    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    c.run();

    //create the expected mat
    int[][][] image = {{{9, 10, 11, 12}, {5, 6, 7, 8}, {1, 2, 3, 4}},
        {{9, 10, 11, 12}, {5, 6, 7, 8}, {1, 2, 3, 4}},
        {{9, 10, 11, 12}, {5, 6, 7, 8}, {1, 2, 3, 4}}};

    int[][][] vertFlip = ImageUtil.readPPM("test/Integration/incmat-vertical.txt");
    assertEquals(image, vertFlip);
  }

  @Test
  public void testSimpleCommandLine() throws IOException {
    String input =
        "load test/integration/incMatRGB.txt incmat\n" + "vertical-flip incmat incmat-vertical\n"
            + "save test/integration/incmat-vertical.txt incmat-vertical\n" + "exit\n";

    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    Model m = new ModelPPM();
    View v = new TextInputView();
    Controller c = new ControllerCommandLine(m, v);

    c.run();

    //create the expected mat
    int[][][] image = {{{9, 10, 11, 12}, {5, 6, 7, 8}, {1, 2, 3, 4}},
        {{9, 10, 11, 12}, {5, 6, 7, 8}, {1, 2, 3, 4}},
        {{9, 10, 11, 12}, {5, 6, 7, 8}, {1, 2, 3, 4}}};

    int[][][] vertFlip = ImageUtil.readPPM("test/integration/incmat-vertical.txt");
    assertEquals(image, vertFlip);
  }

  @Test
  public void testScript2() throws IOException {
    Model m = new ModelPPM();
    String input = "test/scripts/script2ForSimpleMat.txt";
    View v = new TextInputView("script", input);
    Controller c = new ControllerCommandLine(m, v);

    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    c.run();

    int[][][] expected = {{{72, 71, 70, 69}, {68, 67, 66, 65}, {64, 63, 62, 61}},
        {{22, 21, 20, 19}, {18, 17, 16, 15}, {14, 13, 12, 11}},
        {{22, 21, 20, 19}, {18, 17, 16, 15}, {14, 13, 12, 11}}};

    int[][][] result = ImageUtil.readPPM("test/Integration/incmat-red-tint.txt");
    assertEquals(expected, result);
  }

  @Test
  public void createGreyScaleImagesFromScript() throws IOException {
    Model m = new ModelPPM();
    String input = "test/scripts/greyScaleScript.txt";
    View v = new TextInputView("script", input);
    Controller c = new ControllerCommandLine(m, v);

    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    c.run();

    int[][][] original = ImageUtil.readPPM("test/Integration/inctensor.txt");

    //red
    int[][][] actual = ImageUtil.readPPM("test/Integration/inctensor-red.txt");
    assertEquals(original[0], actual[0]);
    //green
    actual = ImageUtil.readPPM("test/Integration/inctensor-green.txt");
    assertEquals(original[1], actual[0]);
    //blue
    actual = ImageUtil.readPPM("test/Integration/inctensor-blue.txt");
    assertEquals(original[2], actual[0]);
    //value
    actual = ImageUtil.readPPM("test/Integration/inctensor-value.txt");
    int[][][] expected = new int[1][3][4];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        expected[0][i][j] = Math.max(Math.max(original[0][i][j], original[1][i][j]),
            original[2][i][j]);
      }
    }
    assertEquals(expected, actual);
    //intensity
    actual = ImageUtil.readPPM("test/Integration/inctensor-intensity.txt");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        expected[0][i][j] =
            (original[0][i][j] + original[1][i][j] + original[2][i][j]) / original.length;
      }
    }
    assertEquals(expected, actual);
    //luma
    actual = ImageUtil.readPPM("test/Integration/inctensor-luma.txt");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        expected[0][i][j] = (int) (0.2126 * original[0][i][j] + 0.7152 * original[1][i][j]
            + 0.0722 * original[2][i][j]);
      }
    }
    assertEquals(expected, actual);
  }

}
