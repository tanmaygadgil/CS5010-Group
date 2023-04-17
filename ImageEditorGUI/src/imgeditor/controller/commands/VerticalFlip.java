package imgeditor.controller.commands;

import java.util.ArrayList;
import java.util.List;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents a Vertical Flip command. This command is used for flipping
 * the input image vertically.
 */
public class VerticalFlip implements Command {
  private final List<String> imageNames;
  private final List<String> resultImageNames;

  /**
   * Construct a VerticalFlip object.
   *
   * @param imageName       the name of the existing image to be flipped vertically
   * @param resultImageName the image name in which the vertically flipped
   *                        image data is to be stored
   */
  public VerticalFlip(String imageName, String resultImageName) {
    this.imageNames = new ArrayList<>();
    this.imageNames.add(imageName);
    this.resultImageNames = new ArrayList<>();
    this.resultImageNames.add(resultImageName);
  }

  @Override
  public void execute(Model m) throws IllegalArgumentException {
    m.operate("vertical-flip", imageNames, resultImageNames);
  }
}
