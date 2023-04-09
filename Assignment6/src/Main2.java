import controller.Controller;
import controller.UIController;
import controller.UIController2;
import java.io.IOException;
import model.ModelV2;
import model.ModelV2Impl;
import model.ViewModel;
import model.ViewModelImpl;
import view.GUIView;
import view.GUIView2;
import view.IGUIView;

public class Main2 {
  /**
   * The main method is the entry point of the program. It parses command-line arguments, creates
   * instances of the Model, View, and Controller, and runs the program.
   *
   * @param args the command-line arguments passed to the program.
   * @throws IOException if file cannot be found.
   */
  public static void main(String[] args) throws IOException {
    /*
    boolean sFlag = false;
    String inputFile = null;
    //find script flag
    for (String arg : args) {
      if (sFlag) {
        inputFile = arg;
      }
      if (arg.equals("-file")) {
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

     */
    ModelV2 model = new ModelV2Impl();
    ViewModel vm = new ViewModelImpl(model);
    IGUIView view = new GUIView2(model);
    Controller controller = new UIController2(model, view);

    //controller.run();
  }
}
