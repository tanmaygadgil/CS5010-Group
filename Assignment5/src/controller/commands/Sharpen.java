package controller.commands;

import java.io.IOException;
import model.filters.ImageFilter;
import model.filters.Sharpening;
import model.ModelV2;

public class Sharpen implements ImageProcessingCommand {

  private String imageName;
  private String destImage;

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
