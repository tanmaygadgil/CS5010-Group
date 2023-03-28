package controller.commands;

import model.Model;

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
  public void run(Model m) {
    m.brighten(increment, imageName, destImage);
  }
}
