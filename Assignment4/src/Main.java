import Controller.Controller;
import Controller.ControllerCommandLine;
import Model.Model;
import Model.ModelPPM;
import View.View;
import View.TextInputView;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    boolean sFlag = false;
    String inputFile = null;
    //find script flag
    for (String arg : args) {
      if (sFlag) {
        inputFile = arg;
      }
      if (arg.equals("-s")) {
        sFlag = true;

      }
    }
    View view;
    if (sFlag) {
      String mode = "script";
      view = new TextInputView(mode, inputFile);
    } else {
      view = new TextInputView();
    }

    Model model = new ModelPPM();
    Controller controller = new ControllerCommandLine(model, view);

    controller.run();
  }
}
