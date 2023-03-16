import controller.Controller;
import controller.ControllerCommandLine;
import model.Model;
import model.ModelPPM;
import view.View;
import view.TextInputView;
import java.io.IOException;

/**
 * The Main class is the entry point of the program. It parses command-line arguments, creates
 * instances of the Model, View, and Controller, and runs the program.
 */
public class Main {

  /**
   * The main method is the entry point of the program. It parses command-line arguments, creates
   * instances of the Model, View, and Controller, and runs the program.
   *
   * @param args the command-line arguments passed to the program.
   * @throws IOException if file cannot be found.
   */
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
