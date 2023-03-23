package controller.commands;

import controller.ImageProcessingCommand;
import model.ImageComponents;
import model.Model;

public class GreyScale implements ImageProcessingCommand {

  ImageComponents rGB;
  String imageName;
  String destImage;

  public GreyScale(ImageComponents rGB, String imageName, String destImage){
    this.rGB = rGB;
    this.imageName = imageName;
    this.destImage = destImage;
  }

  @Override
  public void run(Model m) {
    m.greyscale(rGB, imageName, destImage);
  }
}
