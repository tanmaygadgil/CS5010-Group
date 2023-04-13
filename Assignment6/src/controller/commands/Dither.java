package controller.commands;

import java.io.IOException;
import model.transforms.ImageTransforms;
import model.ModelV2;
import model.operations.ImageOperations;
import model.operations.DitherGreyscaleOperation;
import model.transforms.GreyscaleTransform;

/**
 * This class allows the controller to call the dither command.
 */
public class Dither implements ImageProcessingCommand {

  private String imageName;
  private String destImage;

  /**
   * Initialize the dither command.
   *
   * @param imageName image name.
   * @param destImage destination image name.
   */
  public Dither(String imageName, String destImage) {
    this.imageName = imageName;
    this.destImage = destImage;
  }

  @Override
  public void run(ModelV2 m) throws IOException {
    ImageOperations dither = new DitherGreyscaleOperation();
    ImageTransforms grey = new GreyscaleTransform();
    m.callTransform(grey, imageName, destImage);
    m.callOperation(dither, destImage, destImage);
  }
}
