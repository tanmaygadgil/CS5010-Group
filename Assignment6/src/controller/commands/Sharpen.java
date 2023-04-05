package controller.commands;

import java.io.IOException;
import model.filters.ImageFilter;
import model.filters.Sharpening;
import model.ModelV2;

/**
 * This class allows the controller to call the sharpen command.
 */
public class Sharpen implements ImageProcessingCommand {

  private String imageName;
  private String destImage;

  /**
   * Initialize the sharpen command.
   *
   * @param imageName image name.
   * @param destImage destination image name.
   */
  public Sharpen(String imageName, String destImage) {
    this.imageName = imageName;
    this.destImage = destImage;
  }

  @Override
  public void run(ModelV2 m) throws IOException {
    ImageFilter sharpen = new Sharpening();
    System.out.println(this.destImage);
    m.callFilter(sharpen, this.imageName, this.destImage);
  }
}
