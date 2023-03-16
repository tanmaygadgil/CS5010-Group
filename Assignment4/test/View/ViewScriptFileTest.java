package View;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Assert;

public class ViewScriptFileTest {

  @org.junit.Test
  public void testGetInput() throws IOException {
    String input = "test/scripts/script1.txt";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    View clv = new TextInputView("script", input);
    String filepath = clv.getInput();
    System.out.println(filepath);
//    assertEquals("test/scripts/script1.txt", filepath);
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