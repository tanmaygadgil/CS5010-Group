package imgeditor.controller;

import imgeditor.controller.commands.ImageMosaicking;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import imgeditor.controller.commands.AdjustBrightness;
import imgeditor.controller.commands.Blur;
import imgeditor.controller.commands.Dither;
import imgeditor.controller.commands.Greyscale;
import imgeditor.controller.commands.HorizontalFlip;
import imgeditor.controller.commands.Load;
import imgeditor.controller.commands.RGBCombine;
import imgeditor.controller.commands.RGBSplit;
import imgeditor.controller.commands.Save;
import imgeditor.controller.commands.Sepia;
import imgeditor.controller.commands.Sharpen;
import imgeditor.controller.commands.VerticalFlip;
import imgeditor.model.Model;
import imgeditor.view.TextView;

/**
 * This class represents an implementation of the Controller interface for the Image Editor program.
 * It takes input from the user and executes the correct commands based on the input. It uses the
 * model for performing the operations and uses the view to display success or error messages.
 */
public class ImageEditorController implements Controller {

  private final Model imageEditorModel;
  private final Map<String, Function<Scanner, Command>> knownCommands;
  private final Readable in;
  private final TextView textView;

  /**
   * Construct an ImageEditorController object.
   *
   * @param in       input to the controller representing the commands
   * @param model    object of the model which the controller calls for performing operations
   * @param textView object of the textView which the controller uses for displaying success or
   *                 error messages
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public ImageEditorController(Readable in, Model model, TextView textView)
      throws IllegalArgumentException {
    if (in == null) {
      throw new IllegalArgumentException("Controller input cannot be null.");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (textView == null) {
      throw new IllegalArgumentException("TextView cannot be null");
    }
    this.in = in;
    this.imageEditorModel = model;
    this.textView = textView;

    this.knownCommands = new HashMap<>();
    knownCommands.put("load", s -> new Load(s.next(), s.next()));
    knownCommands.put("save", s -> new Save(s.next(), s.next()));
    knownCommands.put("greyscale", s -> new Greyscale(s));
    knownCommands.put("horizontal-flip", s -> new HorizontalFlip(s.next(), s.next()));
    knownCommands.put("vertical-flip", s -> new VerticalFlip(s.next(), s.next()));
    knownCommands.put("brighten", s -> new AdjustBrightness(s.next(), s.next(), s.next()));
    knownCommands.put("rgb-split", s -> new RGBSplit(s.next(), s.next(), s.next(), s.next()));
    knownCommands.put("rgb-combine", s -> new RGBCombine(s.next(), s.next(), s.next(), s.next()));
    knownCommands.put("blur", s -> new Blur(s.next(), s.next()));
    knownCommands.put("sharpen", s -> new Sharpen(s.next(), s.next()));
    knownCommands.put("sepia", s -> new Sepia(s.next(), s.next()));
    knownCommands.put("dither", s -> new Dither(s.next(), s.next()));
    knownCommands.put("mosaic", s -> new ImageMosaicking(s.next(), s.next(), s.next()));
  }

  @Override
  public void execute() {

    Scanner scanner = new Scanner(this.in);

    while (scanner.hasNext()) {
      try {
        // This returns true when the 'exit' or 'quit' command is given
        if (executeCommand(scanner, imageEditorModel)) {
          return;
        }
      } catch (IllegalArgumentException | FileNotFoundException e) {
        textView.showErrorMessage(e.getMessage());
        if (scanner.hasNextLine()) {
          scanner.nextLine();
        }
      }
    }
  }

  /*
  Helper method for executing a command. Takes in a scanner object and the model.
  If first checks for the quit / exit command.
  Any comment lines (input starting with '#') are skipped. It looks up the command in the
  known commands list and executes that command if found. Otherwise, it throws an exception.
   */
  private boolean executeCommand(Scanner scanner, Model model)
      throws FileNotFoundException, IllegalArgumentException {
    Command c;
    String in = scanner.next().toLowerCase();
    if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit") || in.equalsIgnoreCase("exit")) {
      return true;
    } else if (in.charAt(0) == '#') {
      scanner.nextLine();
      return false;
    } else if (in.equals("run")) {
      run(scanner.next());
      return false;
    }
    Function<Scanner, Command> cmd = knownCommands.getOrDefault(in, null);
    if (cmd == null) {
      throw new IllegalArgumentException(String.format("Command '%s' not found.\n", in));
    } else {
      try {
        // This creates the command object with the parameters specified in knownCommands
        c = cmd.apply(scanner);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
      try {
        // Execute the command
        c.execute(model);

        // Logging that the command was successful
        textView.showSuccessMessage(String.format("%s command successful\n", in));
      } catch (IllegalArgumentException | IOException e) {
        // Logging command failure
        textView.showErrorMessage(e.getMessage());
        if (scanner.hasNextLine()) {
          scanner.nextLine();
        }
        return false;
      }
    }
    return false;
  }

  /*
   * This method accepts a file path as input and runs all the commands in the file line-by-line.
   * All lines starting with '#' are assumed to be comments and are ignored.
   */
  private void run(String scriptFile) {
    BufferedReader reader;

    try {
      reader = new BufferedReader(new FileReader(scriptFile));
      String line;

      while ((line = reader.readLine()) != null) {

        if (line.isEmpty() || line.charAt(0) == '#') {
          continue;
        }

        try {
          if (executeCommand(new Scanner(line), imageEditorModel)) {
            return;
          }
        } catch (IllegalArgumentException e) {
          textView.showErrorMessage(e.getMessage());
          break;
        }
      }
      reader.close();
    } catch (IOException e) {
      textView.showErrorMessage(e.getMessage());
    }
  }
}
