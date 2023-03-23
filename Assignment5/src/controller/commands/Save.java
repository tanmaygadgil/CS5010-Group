package controller.commands;

import controller.ImageProcessingCommand;
import java.io.IOException;
import model.Model;

public class Save implements ImageProcessingCommand {
  String filePath;
  String imageName;

  public Save(String filePath, String imageName){
    this.filePath = filePath;
    this.imageName = imageName;
  }
  @Override
  public void run(Model m) throws IOException {
    m.save(filePath, imageName);
  }
}
