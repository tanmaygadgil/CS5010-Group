package controller.commands;

import controller.ImageIOHandler;
import controller.ImageIOHandlerImpl;
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
    ImageIOHandler handler = new ImageIOHandlerImpl();
    OutputStream out = handler.getSaver(this.filePath);
    String format = handler.parseFormat(this.filePath);

    m.save(out, this.imageName, format);
  }
}
