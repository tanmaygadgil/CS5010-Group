package Controller;

import Model.Model;
import View.View;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ControllerScriptFile implements Controller {

  private Model model;
  private View view;

  public ControllerScriptFile(Model model, View view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void run() throws IOException {
    //Run a loop to extract the file from the view
    while (true) {
      //get the file path
      String filepath = this.view.getInput();

      this.parseAndCall(filepath);

    }
  }

  /**
   * A function which acts as a facilitator to parse the input file and call the model functions
   * @param filepath The location of the textfile to be parsed
   * @throws IOException Thrown if file does not exist
   */
  private void parseAndCall(String filepath) throws IOException {
    BufferedReader reader;

    reader = new BufferedReader(new FileReader(filepath));
    String line = reader.readLine();

    while (line != null) {
      String[] args = this.parseCommand(line);
      if (args != null) {
        String res = this.callModel(args);
      }
      line = reader.readLine();
    }
  }
  private String[] parseCommand(String command) {
    if (command.charAt(0) == '#') {
      //return a empty list
      // consider this to be a comment
      return null;
    } else {
      //splits it into command args and returns it
      return command.split(" ");
    }
  }

  /**
   * A function which holds the switch logic to call the model
   * @param commandArgs
   * @return
   */
  private String callModel(String[] commandArgs) {
    switch (commandArgs[0]){
      case "load":
        // Load an image from the specified path and refer it to henceforth in the program by the given image name.
        this.model.load(imagePath, imageName);
        break;
      case "save":
        // Save the image with the given name to the specified path which should include the name of the file.
        this.model.save(imagePath, imageName);
        break;
      case "greyscale":
        // Create a greyscale image with the specified component of the image with the given name,
        // and refer to it henceforth in the program by the given destination name.
        this.model.createGreyscaleImage(imageName, component, destImageName);
        break;
      case "horizontal-flip":
        // Flip an image horizontally to create a new image, referred to henceforth by the given destination name.
        this.model.flipImageHorizontally(imageName, destImageName);
        break;
      case "vertical-flip":
        // Flip an image vertically to create a new image, referred to henceforth by the given destination name.
        this.model.flipImageVertically(imageName, destImageName);
        break;
      case "brighten":
        // Brighten the image by the given increment to create a new image, referred to henceforth by the given destination name.
        this.model.brightenImage(imageName, increment, destImageName);
        break;
      case "rgb-split":
        // Split the given image into three greyscale images containing its red, green and blue components respectively.
        this.model.splitRGB(imageName, destImageNameRed, destImageNameGreen, destImageNameBlue);
        break;
      case "rgb-combine":
        // Combine the three greyscale images into a single image that gets its red, green and blue components from the three images respectively.
        this.model.combineRGB(imageName, redImage, greenImage, blueImage);
        break;
      default:
        // Handle unknown command.
        break;
    }


    return null;
  }
}
