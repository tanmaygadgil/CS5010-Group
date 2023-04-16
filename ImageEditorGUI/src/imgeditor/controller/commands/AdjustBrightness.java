package imgeditor.controller.commands;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents an Adjust Brightness command. This command is used for brightening
 * or darkening an image based on the increment value specified.
 */
public class AdjustBrightness implements Command {

  private final int brightness;
  private final List<String> imageNames;
  private final List<String> resultImageNames;

  /**
   * Construct an AdjustBrightness object.
   *
   * @param brightness      the brightness to increase / decrease by
   * @param imageName       the name of the existing image to be brightened / darkened
   * @param resultImageName the image name in which the brightened / darkened
   *                        image data is to be stored
   * @throws IllegalArgumentException if the brightness value is not an integer
   */
  public AdjustBrightness(String brightness, String imageName, String resultImageName)
          throws IllegalArgumentException {
    try {
      this.brightness = Integer.parseInt(brightness);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Brightness value must be an integer.\n");
    }
    this.imageNames = new ArrayList<>();
    this.imageNames.add(imageName);
    this.resultImageNames = new ArrayList<>();
    this.resultImageNames.add(resultImageName);
  }

  @Override
  public void execute(Model m) throws FileNotFoundException {
    m.operate("brighten", this.imageNames, this.resultImageNames,
            this.brightness + "");
  }
}
