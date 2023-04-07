package controller.commands;

import java.io.IOException;
import model.filters.GaussianBlur;
import model.filters.ImageFilter;
import model.ModelV2;

/**
 * This class allows the controller to call the gaussian-blur command.
 */
public class BlurGaussian implements ImageProcessingCommand {

  private String imagename;
  private String destimage;

  /**
   * Initialize the gaussian blur command.
   *
   * @param imagename name of image.
   * @param destimage name of destination image.
   */
  public BlurGaussian(String imagename, String destimage) {
    this.imagename = imagename;
    this.destimage = destimage;

  }

  @Override
  public void run(ModelV2 m) throws IOException {
    ImageFilter blur = new GaussianBlur();
    m.callFilter(blur, this.imagename, this.destimage);
  }
}
