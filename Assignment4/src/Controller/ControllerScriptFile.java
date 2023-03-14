package Controller;

import Model.Model;
import View.View;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import Model.ImageComponents;
import Model.Axes;
import java.util.Arrays;

public class ControllerScriptFile implements Controller {

  private final Model model;
  private final View view;

  public ControllerScriptFile(Model model, View view) {
    this.model = model;
    this.view = view;
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

    //red the script file
    reader = new BufferedReader(new FileReader(filepath));
    String line = reader.readLine();

    //parse till all lines are checked
    while (line != null) {
      //parse a single line
      String[] args = this.parseCommand(line);
      String res = null;
      if (args != null) {
        //if command is valid and not a command send to model
        res = this.callModel(args);
      }
      //print state to the view
      this.view.renderOutput(res);
      //get the next line
      line = reader.readLine();
    }
  }

  private String[] parseCommand(String command) {
    if (command == null){
      return null;
    }
    if (command.length() == 0){
      return null;
    }
    if (command.charAt(0) == '#') {
      //return an empty list
      // consider this to be a comment
      return null;
    } else {
      //splits it into command args and returns it
      return command.split(" ");
    }
  }

  /**
   * A function which holds the switch logic to call the model
   *
   * @param commandArgs A list of strings associated with the commands from teh script file
   * @return A string with a success/failure message
   */
  private String callModel(String[] commandArgs) {
//    System.out.println(Arrays.toString(commandArgs));
    // Handle unknown command.
    switch (commandArgs[0]) {
      // Load an image from the specified path and refer it to henceforth in the program by the given image name.
      case "load":
        try {
//          System.out.println(commandArgs[1]);
          this.model.load(commandArgs[1], commandArgs[2]);
          return "Image loaded";
        } catch (FileNotFoundException e) {

          return "Unable to load Image";
        }
// Save the image with the given name to the specified path which should include the name of the file.
      case "save":
        try {
          this.model.save(commandArgs[1], commandArgs[2]);
          return "Image saved successfully";
        } catch (IOException e) {
          return "Unable to save Image";
        }
// Create a greyscale image with the specified component of the image with the given name,
// and refer to it henceforth in the program by the given destination name.
// Get the right component from the enum
      case "greyscale":
        ImageComponents comp;
        if (commandArgs[1] == "red-component") {
          comp = ImageComponents.RED;
        } else if (commandArgs[1].equals("green-component")) {
          comp = ImageComponents.GREEN;
        } else if (commandArgs[1].equals("blue-component")) {
          comp = ImageComponents.BLUE;
        } else if (commandArgs[1].equals("luma-component")) {
          comp = ImageComponents.LUMA;
        } else if (commandArgs[1].equals("value-component")) {
          comp = ImageComponents.VALUE;
        } else if (commandArgs[1].equals("intensity-component")) {
          comp = ImageComponents.INTENSITY;
        } else {
          return "Invalid image component";
        }
        try {
          this.model.greyscale(comp, commandArgs[2], commandArgs[3]);
          return "conversion to greyscale successful";
        } catch (Exception e) {
          return "Could not convert Image to Greyscale";
        }
// Flip an image horizontally to create a new image, referred to henceforth by the given destination name.
      case "horizontal-flip":
        try {
          this.model.flip(Axes.HORIZONTAL, commandArgs[1], commandArgs[2]);
          return "horizontal flip successful";
        } catch (Exception e) {
          return "Horizontal flip unsuccessful";
        }
// Flip an image vertically to create a new image, referred to henceforth by the given destination name.
      case "vertical-flip":
        try {
          this.model.flip(Axes.VERTICAL, commandArgs[1], commandArgs[2]);
          return "vertical flip successful";
        } catch (Exception e) {
          return "Vertical flip unsuccessful";
        }
// Brighten the image by the given increment to create a new image, referred to henceforth by the given destination name.
      case "brighten":
        try {
          this.model.brighten(Integer.parseInt(commandArgs[1]), commandArgs[2], commandArgs[3]);
          return "brighten successful";
        } catch (Exception e) {
          return "Brighten unsuccessful";
        }
// Split the given image into three greyscale images containing its red, green and blue components respectively.
      case "rgb-split":
        try {
          this.model.rgbSplit(commandArgs[1], commandArgs[2], commandArgs[3], commandArgs[4]);
          return "RGB-split successful";
        } catch (Exception e) {
          return "RGB - split unsuccessful";
        }
// Combine the three greyscale images into a single image that gets its red, green and blue components from the three images respectively.
      case "rgb-combine":
        try {
          this.model.rgbCombine(commandArgs[1], commandArgs[2], commandArgs[3], commandArgs[4]);
          return "RGB-combine successful";
        } catch (Exception e) {
          return "RGB - combine unsuccessful";
        }
      default:
        return "Invalid Command";
    }

  }
}
