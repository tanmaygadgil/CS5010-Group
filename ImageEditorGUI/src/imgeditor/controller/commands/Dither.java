package imgeditor.controller.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents a Dither command.
 * This command is used for dithering an image using Floyd–Steinberg dithering.
 */
public class Dither implements Command {

  private final List<String> imageNames;
  private final List<String> resultImageNames;

  /**
   * Construct a Dither object.
   *
   * @param imageName       name of the existing image to be dithered
   * @param resultImageName the image name in which the dithered image data is to be stored
   */
  public Dither(String imageName, String resultImageName) {
    this.imageNames = new ArrayList<>();
    this.imageNames.add(imageName);
    this.resultImageNames = new ArrayList<>();
    this.resultImageNames.add(resultImageName);
  }

  @Override
  public void execute(Model m) throws IOException {
    m.operate("dither", this.imageNames, this.resultImageNames);
  }
}
