package imgeditor.controller.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents a Blur command.
 * This command is used for blurring an image using Gaussian blur.
 */
public class Blur implements Command {

  private final List<String> imageNames;
  private final List<String> resultImageNames;

  /**
   * Construct a Blur object.
   *
   * @param imageName       the name of the existing image to be blurred
   * @param resultImageName the image name in which the blurred image data is to be stored
   */
  public Blur(String imageName, String resultImageName) {
    this.imageNames = new ArrayList<>();
    this.imageNames.add(imageName);
    this.resultImageNames = new ArrayList<>();
    this.resultImageNames.add(resultImageName);
  }

  @Override
  public void execute(Model m) throws IOException {
    m.operate("blur", this.imageNames, this.resultImageNames, "blur");
  }
}
