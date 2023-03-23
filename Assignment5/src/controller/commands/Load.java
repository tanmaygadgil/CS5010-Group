package controller.commands;

import controller.ImageProcessingCommand;
import java.io.FileNotFoundException;
import model.Model;

public class Load implements ImageProcessingCommand {

  String filePath;
  String destImage;

  public Load(String filePath, String destImage){
    this.filePath = filePath;
    this.destImage = destImage;
  }

  @Override
  public void run(Model m) throws FileNotFoundException {
    m.load(filePath, destImage);
  }
}
