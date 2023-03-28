package controller;

import static junit.framework.TestCase.assertEquals;

import model.MockModelImpl;
import view.MockScriptView;
import model.MockModel;
import view.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Scanner;
import org.junit.Test;

/**
 * Test class for controller.
 */
public class ControllerCommandLineTest {

  @Test
  public void oneOfEach() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    String input = "load images/koala.ppm koala\n" + "brighten 10 koala koala-brighter \n"
        + "vertical-flip koala koala-vertical\n"
        + "horizontal-flip koala-vertical koala-vertical-horizontal\n"
        + "greyscale red-component koala koala-greyscale\n"
        + "save images/koala-gs.ppm koala-greyscale\n"
        + "rgb-split koala koala-red koala-green koala-blue\n" + "brighten 50 koala-red koala-red\n"
        + "rgb-combine koala-red-tint koala-red koala-green koala-blue\n" + "exit\n";
    String outputString = "In function load with arguments koala, ppm\n"
        + "In function brighten with arguments 10, koala, koala-brighter\n"
        + "In function flip with arguments VERTICAL, koala, koala-vertical\n"
        + "In function flip with arguments HORIZONTAL, koala-vertical, koala-vertical-horizontal\n"
        + "In function greyscale with arguments RED, koala, koala-greyscale\n"
        + "In function save with arguments koala-greyscale, ppm\n"
        + "In function rgbSplit with arguments koala, koala-red, koala-green, koala-blue\n"
        + "In function brighten with arguments 50, koala-red, koala-red\n"
        + "In function rgbCombine with arguments koala-red-tint, koala-red, koala-green, "
        + "koala-blue\n";
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
//    System.out.println(m.getLog());
  }

  //testing exceptions and incorrect number of arguments
  @Test
  public void loadError() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String input = "load images/koala.ppm\nexit";
    String outputString = "load unsuccessful\r\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(outputStream.toString(), outputString);
  }

  @Test
  public void saveError() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String input = "save images/koala.ppm\nexit";
    String outputString = "save unsuccessful\r\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(outputStream.toString(), outputString);
  }

  @Test
  public void verticalFlipError() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String input = "vertical-flip koala\nexit";
    String outputString = "vertical-flip unsuccessful\r\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(outputStream.toString(), outputString);
  }

  @Test
  public void horizontalFlipError() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String input = "horizontal-flip koala\nexit";
    String outputString = "horizontal-flip unsuccessful\r\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(outputStream.toString(), outputString);
  }

  @Test
  public void greyscaleArgumentsError() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String input = "greyscale value-component koala\nexit";
    String outputString = "greyscale unsuccessful\r\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(outputStream.toString(), outputString);
  }

  @Test
  public void greyscaleComponentError() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String input = "greyscale no-component koala\nexit";
    String outputString = "greyscale unsuccessful\r\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(outputStream.toString(), outputString);
  }

  @Test
  public void brightenError() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String input = "brighten str koala-red koala-red\nexit";
    String outputString = "brighten unsuccessful\r\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(outputStream.toString(), outputString);
  }

  @Test
  public void rgbSplitError() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String input = "rgb-split koala koala-red koala-green\nexit";
    String outputString = "rgb-split unsuccessful\r\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(outputStream.toString(), outputString);
  }

  @Test
  public void rgbCombineError() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String input = "rgb-combine koala koala-red koala-green\nexit";
    String outputString = "rgb-combine unsuccessful\r\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(outputStream.toString(), outputString);
  }

  @Test
  public void skipsWrongCommand() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    String input = "load hh\nrgb-combine koala koala-red koala-green koala-blue\nexit";
    String outputString = "load unsuccessful\r\n" + "rgb-combine successful\r\n";
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(outputStream.toString(), outputString);
  }

  @Test
  public void testSimpleInput() {
    String input = "test/scripts/script1.txt";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    Scanner scanner = new Scanner(new StringReader(input));
    String scriptString = "In function load with arguments koala, ppm\n"
        + "In function flip with arguments VERTICAL, koala, koala-vertical\n"
        + "In function save with arguments koala-vertical, ppm\n";

    View v = null;
    try {
      v = new MockScriptView(scanner, input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(scriptString, m.getLog());
  }

  @Test
  public void testLongScript() {
    //has one instance of all the given commands
    String input = "test/scripts/script2.txt";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    Scanner scanner = new Scanner(new StringReader(input));

    String scriptOutput = "In function load with arguments koala, ppm\n"
        + "In function brighten with arguments 10, koala, koala-brighter\n"
        + "In function flip with arguments VERTICAL, koala, koala-vertical\n"
        + "In function flip with arguments HORIZONTAL, koala-vertical, koala-vertical-horizontal\n"
        + "In function greyscale with arguments VALUE, koala, koala-greyscale\n"
        + "In function save with arguments koala-brighter, ppm\n"
        + "In function save with arguments koala-greyscale, ppm\n"
        + "In function rgbSplit with arguments koala, koala-red, koala-green, koala-blue\n"
        + "In function brighten with arguments 50, koala-red, koala-red\n"
        + "In function rgbCombine with arguments koala-red-tint, koala-red, koala-green, koala-blue\n"
        + "In function save with arguments koala-red-tint, ppm\n";
    View v = null;
    try {
      v = new MockScriptView(scanner, input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    MockModel m = new MockModelImpl();
    Controller c = new ControllerCommandLine(m, v);

    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(scriptOutput, m.getLog());
  }

}