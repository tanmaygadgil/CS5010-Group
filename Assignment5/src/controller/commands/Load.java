package controller.commands;

import controller.ImageIOHandler;
import controller.ImageIOHandlerImpl;
import java.io.FileNotFoundException;
import java.io.InputStream;
import model.Model;
import model.ModelV2;

public class Load implements ImageProcessingCommand {

  String filePath;
  String destImage;

  public Load(String filePath, String destImage){
    this.filePath = filePath;
    this.destImage = destImage;
  }

  @Override
  public void run(ModelV2 m) throws FileNotFoundException {
    ImageIOHandler handler = new ImageIOHandlerImpl();
    InputStream in = handler.getLoader(this.filePath);
    String format = handler.parseFormat(this.filePath);
    m.load(in, destImage, format);
  }
}
