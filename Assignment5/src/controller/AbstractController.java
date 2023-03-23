package controller;

import controller.commands.Brighten;
import controller.commands.Flip;
import controller.commands.GreyScale;
import controller.commands.Load;
import java.util.Map;
import java.util.function.Function;
import model.Model;
import controller.commands.RGBCombine;
import controller.commands.RGBSplit;
import controller.commands.Save;
import view.View;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.ImageComponents;
import model.Axes;

/**
 * This class represents a set of methods all controllers need.
 */
public abstract class AbstractController implements Controller {

  protected final Model model;
  protected final View view;

  public AbstractController(Model model, View view) {
    this.model = model;
    this.view = view;
  }

  protected String[] parseCommand(String command) {
    if (command == null) {
      return null;
    }
    if (command.length() == 0) {
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

  protected void parseLine(String line) {
    //parse a single line
    String[] args = this.parseCommand(line);
    String res = null;
    if (args != null) {
      //if command is valid and not a command send to model
      res = this.callModel(args);
    }
    //print state to the view
    if (res != null) {
      this.view.renderOutput(res);
    }
  }

  /**
   * A function which holds the switch logic to call the model.
   *
   * @param commandArgs A list of strings associated with the commands from teh script file.
   * @return A string with a success/failure message.
   */
  protected String callModel(String[] commandArgs) {
    ImageProcessingCommand cmd = null;
    // Handle unknown command.
    try {
      switch (commandArgs[0]) {
        // Load an image from the specified path and refer it to henceforth in the program by the
        // given image name.
        case "load":
          cmd = new Load(commandArgs[1], commandArgs[2]);
          break;
        // Save the image with the given name to the specified path which should include the name of
        //the file.
        case "save":
          cmd = new Save(commandArgs[1], commandArgs[2]);
          break;
        // Create a greyscale image with the specified component of the image with the given name,
        // and refer to it henceforth in the program by the given destination name.
        // Get the right component from the enum
        case "greyscale":
          ImageComponents comp;
          String input = commandArgs[1];
          input = input.substring(0, input.indexOf("-")).toUpperCase();
          try {
            comp = ImageComponents.valueOf(input);
          } catch (Exception e) {
            return "Invalid image component";
          }
          cmd = new GreyScale(comp, commandArgs[2], commandArgs[3]);
          break;
        // Flip an image horizontally to create a new image, referred to henceforth by the
        // given destination name.
        case "horizontal-flip":
          cmd = new Flip(Axes.HORIZONTAL, commandArgs[1], commandArgs[2]);
          break;
        // Flip an image vertically to create a new image, referred to henceforth by the
        // given destination name.
        case "vertical-flip":
          cmd = new Flip(Axes.VERTICAL, commandArgs[1], commandArgs[2]);
          break;
        // Brighten the image by the given increment to create a new image, referred to henceforth
        // by the given destination name.
        case "brighten":
          cmd = new Brighten(Integer.parseInt(commandArgs[1]), commandArgs[2], commandArgs[3]);
          break;
        // Split the given image into three greyscale images containing its red, green and blue
        // components respectively.
        case "rgb-split":
          cmd = new RGBSplit(commandArgs[1], commandArgs[2], commandArgs[3], commandArgs[4]);
          break;
        // Combine the three greyscale images into a single image that gets its red, green and blue
        // components from the three images respectively.
        case "rgb-combine":
          cmd = new RGBCombine(commandArgs[1], commandArgs[2], commandArgs[3], commandArgs[4]);
          break;
        default:
          cmd = null;
          break;
      }

      if (cmd != null) {
        cmd.run(model);
        return commandArgs[0] + " successful";
      } else {
        return "Invalid Command";
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return "Unable to find Image";
    } catch (IOException e) {
      e.printStackTrace();
      return "Unable to save Image";
    } catch (Exception e) {
      e.printStackTrace();
      return commandArgs[0] + " unsuccessful";
    }
  }

}
