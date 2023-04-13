package controller;

import controller.commands.ImageProcessingCommand;
import java.util.function.Function;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.ModelV2;
import view.IGUIView;
import view.InputDialog;
import view.InputFileChooser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A UIController that implements all given features and extends the abstract controller.
 */
public class UIController extends AbstractController implements Features {

  private InputStream in;

  private OutputStream out;

  /**
   * Initializes the ControllerCommandLine.
   *
   * @param model model to run commands through.
   * @param view  view to show outputs.
   */
  public UIController(ModelV2 model, IGUIView view) {
    super(model, view);
    view.addFeatures(this);
  }

  @Override
  public void run() throws IOException {
    throw new UnsupportedOperationException("The GUI controller does not have an event loop");
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
        return commandArgs[0] + " unsuccessful " + e.getMessage();
      }
    }
  }

  @Override
  /*
  Apply button sends the text currently in the combobox to the controller and the
  relevant command is passed to callModel
  */
  public void callCommand(String command) throws IOException {
    String[] commandArgs;
    switch (command) {
      case "darken":
      case "brighten":
        final String commandValue;
        String res = null;
        String error = null;
        try {
          InputDialog db = new InputDialog((JFrame) view, "Please input the amount to brighten by");
          commandValue = db.getInputAndWait();
          commandArgs = new String[]{command, commandValue, "image", "image"};
          res = this.callModel(commandArgs);
          view.renderOutput("image");
        } catch (Exception e) {
          error = e.getMessage();
        }
        if (res.contains("unsuccessful")) {
          JOptionPane.showMessageDialog(null, res,
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
        break;

      case "rgb-split":
        res = null;
        final String filePathRed;
        final String filePathGreen;
        final String filePathBlue;
        InputFileChooser fChooseRed;
        InputFileChooser fChooseGreen;
        InputFileChooser fChooseBlue;
        try {
          fChooseRed = new InputFileChooser("Save the Red Component");
          filePathRed = fChooseRed.getInput();
          fChooseGreen = new InputFileChooser("Save the Green Component");
          filePathGreen = fChooseGreen.getInput();
          fChooseBlue = new InputFileChooser("Save the Blue Component");
          filePathBlue = fChooseBlue.getInput();

          commandArgs = new String[]{command, "image", "red", "green", "blue"};
          res = this.callModel(commandArgs);
          this.callSave(filePathRed, "red");
          this.callSave(filePathGreen, "green");
          this.callSave(filePathBlue, "blue");
        } catch (Exception e) {
          error = e.getMessage();
        }
        if (res.contains("unsuccessful")) {
          JOptionPane.showMessageDialog(null, res,
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
        break;
      case "rgb-combine":
        res = null;
        try {
          fChooseRed = new InputFileChooser("Load the Red Component");
          filePathRed = fChooseRed.getInput();
          fChooseGreen = new InputFileChooser("Load the Green Component");
          filePathGreen = fChooseGreen.getInput();
          fChooseBlue = new InputFileChooser("Load the Blue Component");
          filePathBlue = fChooseBlue.getInput();

          this.callLoad(filePathRed, "red");
          this.callLoad(filePathGreen, "green");
          this.callLoad(filePathBlue, "blue");

          commandArgs = new String[]{command, "combinedImage", "red", "green", "blue"};
          res = this.callModel(commandArgs);
          InputFileChooser fChooseSave = new InputFileChooser("Save the Combined Image");
          this.callSave(fChooseSave.getInput(), "combinedImage");
        } catch (Exception e) {
          error = e.getMessage();
        }
        if (res.contains("unsuccessful")) {
          JOptionPane.showMessageDialog(null, res,
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
        break;
      default:
        res = null;
        try {
          commandArgs = new String[]{command, "image", "image"};
          res = this.callModel(commandArgs);
          view.renderOutput("image");
        } catch (Exception e) {
          error = e.getMessage();
        }
        if (res.contains("unsuccessful")) {
          JOptionPane.showMessageDialog(null, res,
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
        break;
    }

  }

  @Override
  public void callLoad(String filePath, String imageName) throws IOException {
    String[] loadCommands = {"load", filePath, imageName};
    this.callModel(loadCommands);
    view.renderOutput("image");
  }


  @Override
  public void callSave(String filePath, String imageName) throws IOException {

    String[] saveCommands = {"save", filePath, imageName};
    this.callModel(saveCommands);
    view.reset();

  }
}
