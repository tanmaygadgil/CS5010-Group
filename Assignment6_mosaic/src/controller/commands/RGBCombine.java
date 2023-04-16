package controller.commands;

import model.ModelV2;

/**
 * This class allows the controller to call the rgb-combine command.
 */
public class RGBCombine implements ImageProcessingCommand {

  String destImage;
  String destImageRed;
  String destImageGreen;
  String destImageBlue;

  /**
   * Initialize rgb combine command.
   *
   * @param destImage      destination image name.
   * @param destImageRed   red destination image name.
   * @param destImageGreen green destination image name.
   * @param destImageBlue  blue destination image name.
   */
  public RGBCombine(String destImage, String destImageRed, String destImageGreen,
      String destImageBlue) {
    this.destImage = destImage;
    this.destImageRed = destImageRed;
    this.destImageGreen = destImageGreen;
    this.destImageBlue = destImageBlue;
  }

  @Override
  public void run(ModelV2 m) {
    m.rgbCombine(destImage, destImageRed, destImageGreen, destImageBlue);
  }
}
