import controller.Controller;
import controller.ControllerCommandLine;
import controller.UIController;
import java.io.IOException;
import model.ModelV2;
import model.ModelV2Impl;
import model.ViewModel;
import model.ViewModelImpl;
import view.GUIView;
import view.IGUIView;
import view.TextInputView;

/**
 * The main entrypoint of the jar file.
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
    boolean tFlag = false;
    String inputFile = null;
    //find script flag
    for (String arg : args) {
      if (sFlag) {
        inputFile = arg;
      }
      if (tFlag) {
        break;
      }
      if (arg.equals("-file")) {
        sFlag = true;

      }
      if (arg.equals("-text")) {
        tFlag = true;

      }
    }

    //Initiate model
    ModelV2 model = new ModelV2Impl();

    //check mode
    if (sFlag | tFlag) {
      TextInputView view;
      String mode;
      if (sFlag) {
        mode = "script";
        view = new TextInputView(mode, inputFile);
      } else {
        view = new TextInputView();
      }
      Controller controller = new ControllerCommandLine(model, view);
      controller.run();
    } else {
      //if using the UI
      ViewModel vm = new ViewModelImpl(model);
      IGUIView view = new GUIView(vm);
      Controller controller = new UIController(model, view);
    }

  }
}
