package controller.commands;

import model.ModelV2;

/**
 * This class allows the controller to call the rgbsplit command.
 */
public class RGBSplit implements ImageProcessingCommand {

  String imageName;
  String destImageRed;
  String destImageGreen;
  String destImageBlue;

  /**
   * Initialize the rgb split command.
   *
   * @param imageName      image name.
   * @param destImageRed   destination image name for red component.
   * @param destImageGreen destination image name for green component.
   * @param destImageBlue  destination image name for blue component.
   */
  public RGBSplit(String imageName, String destImageRed, String destImageGreen,
      String destImageBlue) {
    this.imageName = imageName;
    this.destImageRed = destImageRed;
    this.destImageGreen = destImageGreen;
    this.destImageBlue = destImageBlue;
  }

  @Override
  public void run(ModelV2 m) {
    m.rgbSplit(imageName, destImageRed, destImageGreen, destImageBlue);
  }
}
