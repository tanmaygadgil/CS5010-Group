package Controller;
import static junit.framework.TestCase.assertEquals;

import View.MockScriptView;
import Model.ModelPPMMock;
import Model.Model;
import Model.MockModel;
import View.ViewScriptFile;
import View.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;


public class ControllerScriptFileTest {

  private View v;
  private Model m;
  private Controller c;
  @Before
  public void init(){

  }
  @Test
  public void initTest(){
    String input = "\"scripts/script1\"";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    Scanner scanner = new Scanner(new StringReader(input));
    View v = new MockScriptView(scanner);
    Model m = new ModelPPMMock();
    Controller c = new ControllerScriptFile(m, v);
  }

  @Test
  public void testSimpleInput() {
    String input = "test/scripts/script1.txt";
//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    System.setOut(new PrintStream(outputStream));
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    Scanner scanner = new Scanner(new StringReader(input));
    String scriptString = "In function load with arguments images/koala.ppm, koala\n"
        + "In function brighten with arguments 10, koala, koala-brighter\n";

    View v = new MockScriptView(scanner);
    MockModel m = new ModelPPMMock();
    Controller c = new ControllerScriptFile(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(scriptString, m.getLog());
  }

  @Test
  public void testLongScript(){
    //has one instance of all the given commands
    String input = "test/scripts/script2.txt";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    Scanner scanner = new Scanner(new StringReader(input));

    String scriptOutput = "In function load with arguments images/koala.ppm, koala\n"
        + "In function brighten with arguments 10, koala, koala-brighter\n"
        + "In function flip with arguments VERTICAL, koala, koala-vertical\n"
        + "In function flip with arguments HORIZONTAL, koala-vertical, koala-vertical-horizontal\n"
        + "In function greyscale with arguments VALUE, koala, koala-greyscale\n"
        + "In function save with arguments images/koala-brighter.ppm, koala-brighter\n"
        + "In function save with arguments images/koala-gs.ppm, koala-greyscale\n"
        + "In function load with arguments images/upper.ppm, koala\n"
        + "In function rgbSplit with arguments koala, koala-red, koala-green, koala-blue\n"
        + "In function brighten with arguments 50, koala-red, koala-red\n"
        + "In function rgbCombine with arguments koala-red-tint, koala-red, koala-green, koala-blue\n"
        + "In function save with arguments images/koala-red-tint.ppm, koala-red-tint";
    View v = new MockScriptView(scanner);
    MockModel m = new ModelPPMMock();
    Controller c = new ControllerScriptFile(m, v);

    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
//    System.out.println(m.getLog());
    assertEquals(scriptOutput, m.getLog());
  }
}