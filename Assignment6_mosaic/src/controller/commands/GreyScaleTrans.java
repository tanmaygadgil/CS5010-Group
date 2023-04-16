package controller.commands;

import java.io.IOException;
import model.transforms.ImageTransforms;
import model.ModelV2;
import model.transforms.GreyscaleTransform;

/**
 * This class allows the controller to call the greyscale command.
 */
public class GreyScaleTrans implements ImageProcessingCommand {

  private String imageName;
  private String destImage;

  /**
   * Initialize the greyscaletrans command.
   *
   * @param imageName image name.
   * @param destImage destination image name.
   */
  public GreyScaleTrans(String imageName, String destImage) {
    this.imageName = imageName;
    this.destImage = destImage;

  }

  @Override
  public void run(ModelV2 m) throws IOException {
    ImageTransforms grey = new GreyscaleTransform();
    m.callTransform(grey, this.imageName, this.destImage);

  }
}
