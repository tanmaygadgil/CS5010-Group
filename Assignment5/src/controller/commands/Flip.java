package controller.commands;

import model.Axes;
import model.ModelV2;

/**
 * This class allows the controller to call the flip command.
 */
public class Flip implements ImageProcessingCommand {

  Axes axes;
  String imageName;
  String destImage;

  /**
   * Initialize the flip command.
   *
   * @param axes      axes to flip image.
   * @param imageName image name.
   * @param destImage destination image name.
   */
  public Flip(Axes axes, String imageName, String destImage) {
    this.axes = axes;
    this.imageName = imageName;
    this.destImage = destImage;
  }

  @Override
  public void run(ModelV2 m) {
    m.flip(axes, imageName, destImage);
  }
}
