package controller.commands;

import controller.ImageProcessingCommand;
import model.Model;
import model.ModelV2;

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
  public void run(ModelV2 m) {
    m.rgbCombine(destImage, destImageRed, destImageGreen, destImageBlue);
  }
}
