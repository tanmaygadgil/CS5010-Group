package imgeditor.controller.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents a Load command.
 * This command is used for loading the image from the specified file path into memory.
 * It converts the image file path into an InputStream which is then given to the model.
 */
public class Load implements Command {

  private final String imagePath;
  private final String imageName;

  /**
   * Construct a Load object.
   *
   * @param imagePath the path to the image file
   * @param imageName the name to reference the image by in subsequent commands
   */
  public Load(String imagePath, String imageName) {
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

  @Override
  public void execute(Model m) throws IOException {
    InputStream in;
    try {
      in = new FileInputStream(this.imagePath);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File Not Found\n");
    }

    int fileExtensionIndex = imagePath.lastIndexOf('.');
    if (fileExtensionIndex == -1) {
      throw new FileNotFoundException("Filename does not contain an extension.\n");
    }
    String imageFormat = imagePath.substring(fileExtensionIndex).toLowerCase();

    m.load(in, imageFormat, this.imageName);
  }
}
