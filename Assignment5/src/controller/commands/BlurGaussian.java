package controller.commands;

import controller.ImageProcessingCommand;
import java.io.IOException;
import model.Filters.GaussianBlur;
import model.Filters.ImageFilter;
import model.Filters.Sharpening;
import model.ModelV2;

public class BlurGaussian implements ImageProcessingCommand {
  private String imagename;
  private String destimage;

  public BlurGaussian(String imagename, String destimage){
    this.imagename = imagename;
    this.destimage = destimage;

  }
  @Override
  public void run(ModelV2 m) throws IOException {
    ImageFilter blur = new GaussianBlur();
    m.callFilter(blur, this.imagename,this.destimage);
  }
}
