package controller.commands;

import controller.commands.ImageProcessingCommand;
import java.io.IOException;
import model.ImageTransforms;
import model.ModelV2;
import model.ModelV2Impl;
import model.transforms.SepiaTransform;

public class Sepia implements ImageProcessingCommand {

  private String imagename;
  private String destimage;

  public Sepia(String imagename, String destimage){
    this.imagename = imagename;
    this.destimage = destimage;

  }
  @Override
  public void run(ModelV2 m) throws IOException {
    ImageTransforms sepia = new SepiaTransform();
    m.callTransform(sepia, this.imagename, this.destimage);

  }
}
