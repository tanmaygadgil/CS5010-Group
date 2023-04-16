package controller.commands;

import java.io.IOException;
import model.ModelV2;

public class Mosaic implements ImageProcessingCommand{

  int numSeeds;
  String imageName;
  String destImage;

  public Mosaic(int numSeeds, String imageName, String destImage){
    this.numSeeds = numSeeds;
    this.imageName = imageName;
    this.destImage = destImage;
  }

  @Override
  public void run(ModelV2 m) throws IOException {
    m.callMosaic(numSeeds, imageName, destImage);
  }
}
