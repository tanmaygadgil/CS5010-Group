package controller.commands;

import java.io.IOException;
import model.filters.GaussianBlur;
import model.filters.ImageFilter;
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
