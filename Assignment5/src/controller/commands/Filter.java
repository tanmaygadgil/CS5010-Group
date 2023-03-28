package controller.commands;

import java.io.IOException;
import model.filters.ImageFilter;
import model.Model;
import model.ModelV2;

public class Filter implements ImageProcessingCommand {

  private ImageFilter filter;
  private String imageName;
  private String destName;

  public Filter(ImageFilter filter, String imageName, String destName){
    this.filter = filter;
    this.imageName = imageName;
    this.destName = destName;
  }

  @Override
  public void run(ModelV2 m) throws IOException {
    m.callFilter(filter, imageName, destName);
  }
}
