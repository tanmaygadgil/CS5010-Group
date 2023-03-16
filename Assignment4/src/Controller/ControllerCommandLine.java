package Controller;

import Model.Model;
import View.View;
import Model.ImageComponents;
import Model.Axes;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ControllerCommandLine extends AbstractController implements Controller {

  private InputStream in;

  private OutputStream out;

  public ControllerCommandLine(Model model, View view) {
    super(model, view);
  }

  @Override
  public void run() throws IOException {
    while (true) {
      String command = this.view.getInput();

      if (command.strip().equals("exit")) {
        break;
      }
//      System.out.println(command);

      this.parseAndCall(command);
    }
  }

  /**
   * A function which acts as a facilitator to parse the input file and call the model functions
   *
   * @param command The location of the textfile to be parsed
   * @throws IOException Thrown if file does not exist
   */
  private void parseAndCall(String command) throws IOException {
    parseLine(command);
  }


}
