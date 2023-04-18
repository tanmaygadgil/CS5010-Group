package imgeditor.controller.commands;

import imgeditor.controller.Command;
import imgeditor.model.Model;
import imgeditor.model.PickStrategy;
import imgeditor.model.RandomUniformPick;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * command for image mosaicking.
 */
public class ImageMosaicking implements Command {

  private final List<String> imageNames;
  private final List<String> resultImageNames;

  private final int seeds;

  /**
   * Construct a HorizontalFlip object.
   *
   * @param imageName       the name of the existing image to be flipped horizontally
   * @param resultImageName the image name in which the horizontally flipped
   *                        image data is to be stored
   */
  public ImageMosaicking(String imageName, String resultImageName, int seeds) {
    this.imageNames = new ArrayList<>();
    this.imageNames.add(imageName);
    this.seeds = seeds;
    this.resultImageNames = new ArrayList<>();
    this.resultImageNames.add(resultImageName);
  }


  @Override
  public void execute(Model m) throws IOException {
    m.operate("mosaic", this.imageNames, this.resultImageNames);
  }
}
