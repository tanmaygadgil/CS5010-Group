package controller;

import static junit.framework.TestCase.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Scanner;
import model.MockModel;
import model.MockModelImpl;
import org.junit.Test;
import view.MockScriptView;
import view.View;

/**
 * This extends the previous set of tests to test the new commands.
 */
public class ControllerCommandLineV2Test extends ControllerCommandLineTest {

  @Test
  public void testFilters() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    String input = "load images/koala.ppm koala\n" + "gaussian-blur koala koalaBlur \n"
        + "sharpen koala koalaSharp\n" + "exit\n";
    String outputString = "In function load with arguments koala, ppm\n"
        + "In function callFilter with arguments GaussianBlur, koala, koalaBlur\n"
        + "In function callFilter with arguments Sharpening, koala, koalaSharp\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.println(m.getLog());
    System.setOut(new PrintStream(outputStream));
    assertEquals(m.getLog(), outputString);
  }

  @Test
  public void testTransforms() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    String input = "load images/koala.ppm koala\n" + "greyscale koala koalaGrey \n"
        + "sepia koala koalaSepia\n" + "exit\n";
    String outputString = "In function load with arguments koala, ppm\n"
        + "In function callTransform with arguments Greyscale Transform, koala, koalaGrey\n"
        + "In function callTransform with arguments Sepia Transform, koala, koalaSepia\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.println(m.getLog());
    System.setOut(new PrintStream(outputStream));
    assertEquals(m.getLog(), outputString);
  }

  @Test
  public void testOps() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    String input = "load images/koala.ppm koala\n" + "dither koala koalaDither \n" + "exit\n";
    String outputString = "In function load with arguments koala, ppm\n"
        + "In function callTransform with arguments Greyscale Transform, koala, koalaDither\n"
        + "In function callOperation with arguments DitherOperation, koalaDither, koalaDither\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.println(m.getLog());
    System.setOut(new PrintStream(outputStream));
    assertEquals(m.getLog(), outputString);
  }

}
