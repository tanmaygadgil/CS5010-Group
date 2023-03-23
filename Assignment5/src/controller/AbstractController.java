package controller;

import controller.commands.Brighten;
import controller.commands.Flip;
import controller.commands.GreyScale;
import controller.commands.Load;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import model.Model;
import controller.commands.RGBCombine;
import controller.commands.RGBSplit;
import controller.commands.Save;
import view.View;
import java.io.IOException;
import model.Axes;

/**
 * This class represents a set of methods all controllers need.
 */
public abstract class AbstractController implements Controller {

  protected final Model model;
  protected final View view;
  protected final Map<String, Function<String[], ImageProcessingCommand>> knownCommands;

  public AbstractController(Model model, View view) {
    this.model = model;
    this.view = view;
    this.knownCommands = new HashMap<>();
    this.create();
  }

  private void create() {
    knownCommands.put("load", s -> new Load(s[1], s[2]));
    knownCommands.put("save", s -> new Save(s[1], s[2]));
    knownCommands.put("brighten", s -> new Brighten(Integer.parseInt(s[1]), s[2], s[3]));
    knownCommands.put("horizontal-flip", s -> new Flip(Axes.HORIZONTAL, s[1], s[2]));
    knownCommands.put("vertical-flip", s -> new Flip(Axes.VERTICAL, s[1], s[2]));
    knownCommands.put("greyscale", s -> new GreyScale(s[1], s[2], s[3]));
    knownCommands.put("rgb-split", s -> new RGBSplit(s[1], s[2], s[3], s[4]));
    knownCommands.put("rgb-combine", s -> new RGBCombine(s[1], s[2], s[3], s[4]));
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

  protected void parseLine(String line) throws IOException {
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
   * @param commandArgs A list of strings associated with the commands from the script file.
   * @return A string with a success/failure message.
   */
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
      } catch(Exception e) {
        return commandArgs[0] + " unsuccessful";
      }
    }

  }

}
