package Controller;

import Model.ModelPPMMock;
import Model.Model;
import View.ViewScriptFile;
import View.View;
import org.junit.Test;


public class ControllerScriptFileTest {
  @Test
  public void initTest(){
    View v = new ViewScriptFile();
    Model m = new ModelPPMMock();
    Controller c = new ControllerScriptFile(m, v);


  }
}