package imgeditor.controller.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents a Sepia command.
 * This command is used for transforming the color of an image to Sepia.
 */
public class Sepia implements Command {

  private final List<String> imageNames;
  private final List<String> resultImageNames;

  /**
   * Construct a Sepia object.
   *
   * @param imageName       the name of the existing image for which sepia color transformation
   *                        is to be performed
   * @param resultImageName the image name in which the color transformed sepia
   *                        image data is to be stored
   */
  public Sepia(String imageName, String resultImageName) {
    this.imageNames = new ArrayList<>();
    this.imageNames.add(imageName);
    this.resultImageNames = new ArrayList<>();
    this.resultImageNames.add(resultImageName);
  }

  @Override
  public void execute(Model m) throws IOException {
    m.operate("sepia", this.imageNames, this.resultImageNames,
            "sepia");
  }
}
