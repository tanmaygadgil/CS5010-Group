package controller.commands;

import controller.ImageProcessingCommand;
import model.Axes;
import model.Model;
import model.ModelV2;

public class Flip implements ImageProcessingCommand {

  Axes axes;
  String imageName;
  String destImage;
  public Flip(Axes axes, String imageName, String destImage){
    this.axes = axes;
    this.imageName = imageName;
    this.destImage = destImage;
  }

  @Override
  public void run(ModelV2 m) {
    m.flip(axes, imageName, destImage);
  }
}
