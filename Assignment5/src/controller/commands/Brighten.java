package controller.commands;

import model.ModelV2;

/**
 * This class allows the controller to call the brighten command.
 */
public class Brighten implements ImageProcessingCommand {

  int increment;
  String imageName;
  String destImage;

  /**
   * Initialize the brighten command.
   *
   * @param increment increment to brighten image by.
   * @param imageName image name.
   * @param destImage destination name.
   */
  public Brighten(int increment, String imageName, String destImage) {
    this.increment = increment;
    this.imageName = imageName;
    this.destImage = destImage;
  }

  @Override
  public void run(ModelV2 m) {
    m.brighten(increment, imageName, destImage);
  }
}
