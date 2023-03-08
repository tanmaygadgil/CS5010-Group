package View;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Assert;

public class ViewScriptFileTest {

  @org.junit.Test
  public void getInput() {
    String input = "testpath";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    View clv = new ViewScriptFile();
    String filepath = clv.getInput();
    assertEquals("testpath", filepath);
  }

  @org.junit.Test
  public void renderOutput() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    View clv = new ViewScriptFile();
    clv.renderOutput("This is a test String");

    String expecteOutput = "This is a test String";
    assertEquals(expecteOutput, outputStream.toString().strip());
  }
}