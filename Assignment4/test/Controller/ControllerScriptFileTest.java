package Controller;
import View.MockScriptView;
import Model.ModelPPMMock;
import Model.Model;
import View.ViewScriptFile;
import View.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
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
    View v = new MockScriptView("scripts/script1");
    Model m = new ModelPPMMock();
    Controller c = new ControllerScriptFile(m, v);
  }

  @Test
  public void testSimpleInput() {
    String input = "test/scripts/script1.txt";
//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    System.setOut(new PrintStream(outputStream));
    View v = new MockScriptView(input);
    Model m = new ModelPPMMock();
    Controller c = new ControllerScriptFile(m, v);
    try {
      c.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
//    System.out.println(outputStream.toString().strip());
  }
}