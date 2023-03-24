package controller.commands;

import controller.ImageIOHandlerImpl;
import controller.ImageProcessingCommand;
import java.io.IOException;
import java.io.OutputStream;
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
    OutputStream out = ImageIOHandlerImpl.getSaver(this.filePath);
    String format = ImageIOHandlerImpl.parseFormat(this.filePath);
    m.save(out, this.imageName, format);
  }
}
