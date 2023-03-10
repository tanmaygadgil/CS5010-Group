import Controller.Controller;
import Controller.ControllerScriptFile;
import Model.Model;
import Model.ModelPPMMock;
import View.View;
import View.ViewScriptFile;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    View v = new ViewScriptFile();
    Model m = new ModelPPMMock();
    Controller c = new ControllerScriptFile(m, v);

    c.run();
  }
}
