package controller.commands;

import controller.commands.ImageProcessingCommand;
import java.io.IOException;
import model.ImageTransforms;
import model.ModelV2;
import model.transforms.GreyscaleTransform;
import model.transforms.SepiaTransform;

public class GreyScaleTrans implements ImageProcessingCommand {
  private String imagename;
  private String destimage;

  public GreyScaleTrans(String imagename, String destimage){
    this.imagename = imagename;
    this.destimage = destimage;

  }
  @Override
  public void run(ModelV2 m) throws IOException {
    ImageTransforms grey = new GreyscaleTransform();
    m.callTransform(grey, this.imagename, this.destimage);

  }
}
