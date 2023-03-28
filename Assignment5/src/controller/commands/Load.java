package controller.commands;

import controller.ImageIOHandlerImpl;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    InputStream in = ImageIOHandlerImpl.getLoader(this.filePath);
    String format = ImageIOHandlerImpl.parseFormat(this.filePath);
    m.load(in, destImage, format);
  }
}
