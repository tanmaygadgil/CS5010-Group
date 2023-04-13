package controller.commands;

import java.io.IOException;
import model.ImageTransforms;
import model.ModelV2;
import model.transforms.SepiaTransform;

/**
 * This class allows the controller to call the sepia command.
 */
public class Sepia implements ImageProcessingCommand {

  private String imageName;
  private String destImage;

  /**
   * Initialize the sepia command.
   *
   * @param imageName image name.
   * @param destImage destination image name.
   */
  public Sepia(String imageName, String destImage) {
    this.imageName = imageName;
    this.destImage = destImage;

  }

  @Override
  public void run(ModelV2 m) throws IOException {

    ImageTransforms sepia = new SepiaTransform();
    //ImageTransforms sepia = (ImageTransforms) new SepiaTransform();
    m.callTransform(sepia, this.imageName, this.destImage);

  }
}
