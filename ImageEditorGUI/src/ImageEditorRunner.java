import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import imgeditor.controller.Controller;
import imgeditor.controller.ImageEditorController;
import imgeditor.controller.GUIController;
import imgeditor.controller.GUIControllerImpl;
import imgeditor.model.ImageEditorModel;
import imgeditor.model.Model;
import imgeditor.model.ReadOnlyModel;
import imgeditor.model.ReadOnlyModelImpl;
import imgeditor.view.GUIViewImpl;
import imgeditor.view.GUIView;
import imgeditor.view.TextView;
import imgeditor.view.TextViewImpl;

/**
 * This class is the runner for the Image Editor. The program execution begins here.
 */
public class ImageEditorRunner {
  /**
   * Main method for running the program. This method calls the controller and begins the
   * processing of commands through the command line.
   * This method creates a HashMap of all the command line arguments passed which begin with '-'.
   * If no command line arguments are passed, the GUI will be opened.
   * If the -file argument followed by a file path is passed, the program will run the commands
   * in the file and exit immediately afterwards.
   * If the -text argument is passed, the program will run in interactive scripting mode
   * where the user can type in commands via the command line.
   * @param args default main args
   */
  public static void main(String[] args) {

    final Map<String, List<String>> argsOptionsMap = new HashMap<>();
    List<String> argOptions = null;

    for (final String arg : args) {
      if (arg.charAt(0) == '-') {
        if (arg.length() < 2) {
          throw new IllegalArgumentException(String.format("Error at argument %s", arg));
        }
        argOptions = new ArrayList<>();
        argsOptionsMap.put(arg, argOptions);
      } else if (argOptions != null) {
        argOptions.add(arg);
      } else {
        throw new IllegalArgumentException("Illegal command line argument usage");
      }
    }

    Model model = new ImageEditorModel();

    // No args -> run the program in GUI mode
    if (argsOptionsMap.isEmpty()) {
      ReadOnlyModel rModel = new ReadOnlyModelImpl();
      GUIView guiView = new GUIViewImpl("Image Editor", rModel);
      GUIController controller = new GUIControllerImpl(model, guiView);
      controller.setView();
    }

    // Args -> check for -text and -file
    else {
      TextView textView = new TextViewImpl(System.out);
      Controller controller = null;
      Readable in;

      // -text -> run the program in interactive scripting mode
      if (argsOptionsMap.containsKey("-text")) {
        if (argsOptionsMap.get("-text").size() != 0) {
          System.err.println("Invalid number of arguments for -text");
          System.exit(1);
        }
        in = new InputStreamReader(System.in);
        controller = new ImageEditorController(in, model, textView);
      }

      // -file -> run the program with a script file
      else if (argsOptionsMap.containsKey("-file")) {
        if (argsOptionsMap.get("-file").size() != 1) {
          System.err.println("Invalid number of arguments for -file");
          System.exit(1);
        } else {
          in = new StringReader("run " + args[1] + "\nq");
          controller = new ImageEditorController(in, model, textView);
        }
      }
      else {
        System.err.println("Invalid arguments passed. Supported arguments: -file, -text");
        System.exit(1);
      }

      try {
        controller.execute();
      }
      catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }
}