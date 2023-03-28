package controller.commands;

import model.Axes;
import model.Model;

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
  public void run(Model m) {
    m.flip(axes, imageName, destImage);
  }
}
