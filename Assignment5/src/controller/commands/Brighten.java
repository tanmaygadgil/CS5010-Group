package controller.commands;

import controller.ImageProcessingCommand;
import model.Model;
import model.ModelV2;

public class Brighten implements ImageProcessingCommand {

  int increment;
  String imageName;
  String destImage;

  public Brighten(int increment, String imageName, String destImage){
    this.increment = increment;
    this.imageName = imageName;
    this.destImage = destImage;
  }

  @Override
  public void run(ModelV2 m) {
    m.brighten(increment, imageName, destImage);
  }
}
