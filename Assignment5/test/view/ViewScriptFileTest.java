package view;


import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Test class for ViewScriptFile class.
 */
public class ViewScriptFileTest {

  @org.junit.Test
  public void testGetInput() throws IOException {
    String input = "test/scripts/script1.txt";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    View clv = new TextInputView("script", input);
    String filepath = clv.getInput();
    assertEquals("load images/koala.ppm koala", filepath);
  }

  @org.junit.Test
  public void renderOutput() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    View clv = new TextInputView();
    clv.renderOutput("This is a test String");

    String expectedOutput = "This is a test String";
    assertEquals(expectedOutput, outputStream.toString().strip());
  }
}