package imgeditor.controller.commands;


import java.util.ArrayList;
import java.util.List;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents an RGBCombine command.
 * This command is used for combining a red, green and blue component images
 * into a single RGB image.
 */
public class RGBCombine implements Command {
  private final List<String> resultImageNames;
  private final List<String> imageNames;

  /**
   * Construct an RGBCombine object.
   *
   * @param resultImageName the image name in which the RGB combined image data is to be stored
   * @param imageNameRed    the name of the red component of the image
   * @param imageNameGreen  the name of the green component of the image
   * @param imageNameBlue   the name of the blue component of the image
   */
  public RGBCombine(String resultImageName, String imageNameRed,
                    String imageNameGreen, String imageNameBlue) {
    this.imageNames = new ArrayList<>();
    this.imageNames.add(imageNameRed);
    this.imageNames.add(imageNameGreen);
    this.imageNames.add(imageNameBlue);
    this.resultImageNames = new ArrayList<>();
    this.resultImageNames.add(resultImageName);
  }

  @Override
  public void execute(Model m) throws IllegalArgumentException {
    m.operate("rgb-combine", this.imageNames, this.resultImageNames);
  }
}
