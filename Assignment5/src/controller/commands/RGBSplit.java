package controller.commands;

import model.Model;

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
  public void run(Model m) {
    m.rgbSplit(imageName, destImageRed, destImageGreen, destImageBlue);
  }
}
