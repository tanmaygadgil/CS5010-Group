package Controller;

import Model.Model;
import View.View;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ControllerScriptFile extends AbstractController{
  public ControllerScriptFile(Model model, View view) {
    super(model, view);
  }

  @Override
  public void run() throws IOException {
    //Run a loop to extract the file from the view
//    while (true) {
    //get the file path
    String filepath = this.view.getInput();
    System.out.println(filepath);

    this.parseAndCall(filepath);

//    }
  }

  /**
   * A function which acts as a facilitator to parse the input file and call the model functions
   *
   * @param filepath The location of the textfile to be parsed
   * @throws IOException Thrown if file does not exist
   */
  private void parseAndCall(String filepath) throws IOException {
    BufferedReader reader;

    //read the script file
    reader = new BufferedReader(new FileReader(filepath));
    String line = reader.readLine();

    //parse till all lines are checked
    while (line != null) {
      parseLine(line);
      //get the next line
      line = reader.readLine();
    }
  }


}
