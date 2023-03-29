package controller.commands;

import model.ModelV2;

public class Darken implements ImageProcessingCommand {

  int increment;
  String imageName;
  String destImage;

  public Darken(int increment, String imageName, String destImage){
    this.increment = increment;
    this.imageName = imageName;
    this.destImage = destImage;
  }

  @Override
  public void run(ModelV2 m) {
    m.darken(increment, imageName, destImage);
  }
}
