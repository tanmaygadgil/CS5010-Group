package imgeditor.controller.commands;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import imgeditor.controller.Command;
import imgeditor.model.Model;

/**
 * This class represents a Save command. This command is used for saving an image that
 * was previously loaded and may have been operated on by other commands on the disk at the
 * specified file path.
 * Converts the output file path into an OutputStream which is then passed to the model.
 */
public class Save implements Command {

  private final String imagePath;
  private final String imageName;

  /**
   * Construct a Save object.
   *
   * @param imagePath the path where the image is to be saved
   * @param imageName the name of the existing image to be saved
   */
  public Save(String imagePath, String imageName) {
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

  @Override
  public void execute(Model m) throws IOException {
    OutputStream out;
    try {
      out = new BufferedOutputStream(new FileOutputStream(this.imagePath));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("Invalid file path to save the image.");
    }

    int fileExtensionIndex = imagePath.lastIndexOf('.');
    if (fileExtensionIndex == -1) {
      throw new FileNotFoundException("Filename does not contain an extension.\n");
    }
    String imageFormat = imagePath.substring(fileExtensionIndex).toLowerCase();

    m.save(out, imageFormat, this.imageName);
  }

}
