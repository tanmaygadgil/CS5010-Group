package controller.commands;

import model.ImageComponents;
import model.ModelV2;

/**
 * This class allows the controller to call the greyscale command.
 */
public class GreyScale implements ImageProcessingCommand {

  ImageComponents component;
  String imageName;
  String destImage;

  /**
   * Initialize the greyscale command.
   *
   * @param component component to create greyscale image from.
   * @param imageName image name.
   * @param destImage destination image name.
   */
  public GreyScale(String component, String imageName, String destImage) {
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
