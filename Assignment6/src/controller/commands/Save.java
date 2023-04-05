package controller.commands;

import controller.ImageIOHandler;
import controller.ImageIOHandlerImpl;
import java.io.IOException;
import java.io.OutputStream;
import model.ModelV2;

/**
 * This class allows the controller to call the save command.
 */
public class Save implements ImageProcessingCommand {

  String filePath;
  String imageName;

  /**
   * Initialize the save command.
   *
   * @param filePath  path of the file to save.
   * @param imageName image name.
   */
  public Save(String filePath, String imageName) {
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
