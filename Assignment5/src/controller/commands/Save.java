package controller.commands;

import controller.ImageIOHandlerImpl;
import controller.ImageProcessingCommand;
import java.io.IOException;
import java.io.OutputStream;
import model.Model;
import model.ModelV2;

public class Save implements ImageProcessingCommand {
  String filePath;
  String imageName;

  public Save(String filePath, String imageName){
    this.filePath = filePath;
    this.imageName = imageName;
  }
  @Override
  public void run(ModelV2 m) throws IOException {
    OutputStream out = ImageIOHandlerImpl.getSaver(this.filePath);
    String format = ImageIOHandlerImpl.parseFormat(this.filePath);
    m.save(out, this.imageName, format);
  }
}
