package controller.commands;

import controller.ImageProcessingCommand;
import model.ImageComponents;
import model.Model;
import model.ModelV2;

public class GreyScale implements ImageProcessingCommand {

  ImageComponents component;
  String imageName;
  String destImage;

  public GreyScale(String component, String imageName, String destImage){
    ImageComponents comp;
    String input = component;
    input = input.substring(0, input.indexOf("-")).toUpperCase();
    try {
      this.component = ImageComponents.valueOf(input);
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }
    this.imageName = imageName;
    this.destImage = destImage;
  }

  @Override
  public void run(ModelV2 m) {
    m.greyscale(component, imageName, destImage);
  }
}
