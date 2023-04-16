package imgeditor.controller.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents a Sharpen command.
 * This command is used for sharpening an image using a sharpening filter.
 */
public class Sharpen implements Command {

  private final List<String> imageNames;
  private final List<String> resultImageNames;

  /**
   * Construct a Sharpen object.
   *
   * @param imageName       the name of the existing image to be sharpened
   * @param resultImageName the image name in which the sharpened image data is to be stored
   */
  public Sharpen(String imageName, String resultImageName) {
    this.imageNames = new ArrayList<>();
    this.imageNames.add(imageName);
    this.resultImageNames = new ArrayList<>();
    this.resultImageNames.add(resultImageName);
  }

  @Override
  public void execute(Model m) throws IOException {
    m.operate("sharpen", this.imageNames, this.resultImageNames,
            "sharpen");
  }
}
