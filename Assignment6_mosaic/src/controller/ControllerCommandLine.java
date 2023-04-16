package controller;

import model.ModelV2;
import view.View;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class represents the command line implementation of the controller interface. This class
 * allows the user to use a command line to pass a script to run commands using the model.
 */
public class ControllerCommandLine extends AbstractController implements Controller {

  private InputStream in;

  private OutputStream out;

  /**
   * Initializes the ControllerCommandLine.
   *
   * @param model model to run commands through.
   * @param view  view to show outputs.
   */
  public ControllerCommandLine(ModelV2 model, View view) {
    super(model, view);
  }

  @Override
  public void run() throws IOException {
    while (true) {
      String command = this.view.getInput();

      if (command.strip().equals("exit")) {
        break;
      }

      this.parseAndCall(command);
    }
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


}
