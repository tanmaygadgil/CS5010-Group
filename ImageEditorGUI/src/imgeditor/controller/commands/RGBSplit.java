package imgeditor.controller.commands;


import java.util.ArrayList;
import java.util.List;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents an RGBSplit command. This command is used to split a single RGB image
 * into three images - red component, green component and blue component.
 */
public class RGBSplit implements Command {
  private final List<String> imageNames;
  private final List<String> resultImageNames;

  /**
   * Construct an RGBSplit object.
   *
   * @param imageName            the existing image name
   * @param resultRedImageName   the name of the red component output image
   * @param resultGreenImageName the name of the green component output image
   * @param resultBlueImageName  the name of the blue component output image
   */
  public RGBSplit(String imageName, String resultRedImageName,
                  String resultGreenImageName, String resultBlueImageName) {
    this.imageNames = new ArrayList<>();
    this.imageNames.add(imageName);
    this.resultImageNames = new ArrayList<>();
    this.resultImageNames.add(resultRedImageName);
    this.resultImageNames.add(resultGreenImageName);
    this.resultImageNames.add(resultBlueImageName);
  }

  @Override
  public void execute(Model m) throws IllegalArgumentException {
    m.operate("rgb-split", this.imageNames, this.resultImageNames);
  }
}
