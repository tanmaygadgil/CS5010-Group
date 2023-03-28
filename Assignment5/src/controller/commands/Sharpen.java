package controller.commands;

import controller.ImageProcessingCommand;
import java.io.IOException;
import model.Filters.ImageFilter;
import model.Filters.Sharpening;
import model.ModelV2;

public class Sharpen implements ImageProcessingCommand {

  private String imagename;
  private String destimage;

  public Sharpen(String imagename, String destimage){
    this.imagename = imagename;
    this.destimage = destimage;

  }

  @Override
  public void run(ModelV2 m) throws IOException {
    ImageFilter sharpen = new Sharpening();
    System.out.println(this.destimage);
    m.callFilter(sharpen, this.imagename,this.destimage);
  }
}
