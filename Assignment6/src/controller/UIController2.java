package controller;

import controller.commands.ImageProcessingCommand;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Function;
import javax.swing.JFrame;
import model.ModelV2;
import view.IGUIView;
import view.InputDialog;
import view.SimpleDialogBox;
import view.View;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UIController2 extends AbstractController implements Features {
  private InputStream in;

  private OutputStream out;

  /**
   * Initializes the ControllerCommandLine.
   *
   * @param model model to run commands through.
   * @param view  view to show outputs.
   */
  public UIController2(ModelV2 model, IGUIView view) {
    super(model, view);
    view.addFeatures(this);
  }

  @Override
  public void run() throws IOException {
    /*
    while (true) {
      String command = this.view.getInput();

      if (command.strip().equals("exit")) {
        break;
      }

      this.parseAndCall(command);
    }

     */
  }

  /**
   * A function which acts as a facilitator to parse the input file and call the model functions.
   *
   * @param command The location of the textfile to be parsed
   * @throws IOException Thrown if file does not exist
   */
  private void parseAndCall(String command) throws IOException {
    parseLine(command);
  }

  @Override
  protected String callModel(String[] commandArgs) throws IOException {
    ImageProcessingCommand c;
    Function<String[], ImageProcessingCommand> cmd;
    cmd = knownCommands.getOrDefault(commandArgs[0], null);
    if (cmd == null) {
      throw new IllegalArgumentException();
    } else {
      try {
        c = cmd.apply(commandArgs);
        c.run(model);
        return commandArgs[0] + " successful";
      } catch (Exception e) {
        e.printStackTrace();
        return commandArgs[0] + " unsuccessful";
      }
    }
  }

  /*
  ///////////////FOR NOW ALL IMAGES ARE JUST CALLED "image"//////////////////
   */

  @Override
  /*
  Apply button sends the text currently in the combobox to the controller and the
  relevant command is passed to callModel
   */
  public void callCommand(String command) throws IOException{

    if (command.equals("brighten")){
      final String commandValue;
      InputDialog db = new InputDialog((JFrame) view,"Please input the amount to brighten by");
//      db.addWindowListener(new WindowAdapter() {
//        public void windowClosed(WindowEvent e) {
//          commandValue[0] = db.getInput();
//          System.out.println("User input: " + commandValue[0]);
//        }
//      });
      commandValue = db.getInputAndWait();
      String[] commandArgs = {command, commandValue, "image", "image"};
      this.callModel(commandArgs);
      view.renderOutput("image");
    }else {
      String[] commandArgs = {command, "image", "image"};
      this.callModel(commandArgs);
      view.renderOutput("image");
    }
  }


  /*
  Load button should send the path here
   */
  @Override
  public void callLoad(String filePath) throws IOException {
    String[] loadCommands = {"load", filePath, "image"};
    this.callModel(loadCommands);
    view.renderOutput("image");
  }

  /*
  Save button should send the path here
   */
  @Override
  public void callSave(String filePath) {

    String[] saveCommands = {"save", filePath, "image"};

  }
}
