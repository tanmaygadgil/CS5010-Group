package controller.commands;

import controller.ImageProcessingCommand;
import model.Model;

public class RGBCombine implements ImageProcessingCommand {

  String destImage;
  String destImageRed;
  String destImageGreen;
  String destImageBlue;
  public RGBCombine(String destImage, String destImageRed, String destImageGreen,
      String destImageBlue) {
    this.destImage = destImage;
    this.destImageRed = destImageRed;
    this.destImageGreen = destImageGreen;
    this.destImageBlue = destImageBlue;
  }

  @Override
  public void run(Model m) {
    m.rgbCombine(destImage, destImageRed, destImageGreen, destImageBlue);
  }
}
