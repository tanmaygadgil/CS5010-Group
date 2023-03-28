package controller.commands;

import controller.ImageProcessingCommand;
import model.Model;
import model.ModelV2;

public class RGBSplit implements ImageProcessingCommand {

  String imageName;
  String destImageRed;
  String destImageGreen;
  String destImageBlue;

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
