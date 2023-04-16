package imgeditor.controller.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents a Greyscale command. This command is used for converting an RGB
 * image into greyscale by extracting the specified component. If no component is specified,
 * then the default is set to luma.
 */
public class Greyscale implements Command {
  private final String componentName;
  private final List<String> imageNames;
  private final List<String> resultImageNames;

  /**
   * Construct a Greyscale object.
   *
   * @param s Scanner to read the input parameters
   */
  public Greyscale(Scanner s) {
    String firstArgument = s.next();
    if (firstArgument.contains("component")) {
      this.componentName = firstArgument;
      this.imageNames = new ArrayList<>();
      this.imageNames.add(s.next());
    } else {
      this.componentName = "luma-component";
      this.imageNames = new ArrayList<>();
      this.imageNames.add(firstArgument);
    }
    this.resultImageNames = new ArrayList<>();
    this.resultImageNames.add(s.next());
  }

  @Override
  public void execute(Model m) throws IllegalArgumentException {
    try {
      m.operate("greyscale", this.imageNames, this.resultImageNames,
              this.componentName);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}

