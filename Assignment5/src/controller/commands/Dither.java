package controller.commands;

import controller.ImageProcessingCommand;
import java.io.IOException;
import model.ImageComponents;
import model.ImageTransforms;
import model.ModelV2;
import model.operations.ImageOperations;
import model.operations.DitherGreyscaleOperation;
import model.transforms.GreyscaleTransform;

public class Dither implements ImageProcessingCommand {

  private String imageName;
  private String destImage;
  public Dither(String imageName, String destImage){
    this.imageName = imageName;
    this.destImage = destImage;
  }
  @Override
  public void run(ModelV2 m) throws IOException {
    ImageOperations dither = new DitherGreyscaleOperation();
    ImageTransforms grey = new GreyscaleTransform();
    m.callTransform(grey, imageName, destImage);
    m.callOperation(dither, destImage, destImage);
  }
}
