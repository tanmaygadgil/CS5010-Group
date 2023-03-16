package View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextInputViewTest {

  @org.junit.Test
  public void testScriptGetInput() throws IOException {
    String input = "test/scripts/script1.txt";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    View clv = new TextInputView("script", input);
    String output = clv.getInput();
//    System.out.println(output);
    assertEquals("load images/koala.ppm koala", output);
  }

  @Test
  public void testCommandModeInput() {
//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    System.setOut(new PrintStream(outputStream));
    String input = "rgb-combine koala koala-red koala-green\nexit";
    String outputString = "rgb-combine koala koala-red koala-green";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    View clv = new TextInputView();
    String output = clv.getInput();

    assertEquals(outputString, output);
  }

  @org.junit.Test
  public void renderOutput() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    View clv = new TextInputView();
    clv.renderOutput("This is a test String");

    String expecteOutput = "This is a test String";
    assertEquals(expecteOutput, outputStream.toString().strip());
  }

}