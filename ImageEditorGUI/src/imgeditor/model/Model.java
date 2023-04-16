package imgeditor.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * This interface represents a Model.
 * It provides methods for loading and saving an image as well as a method for performing
 * various image operations on the specified image.
 */
public interface Model {

  /**
   * Load the image into memory given the InputStream and the name with which
   * it will be referenced in subsequent operations.
   *
   * @param in          is the input stream of the file
   * @param imageName   the name with which the image data will be referenced in operations
   *                    hereon, unless it is renamed
   * @param imageFormat the image format provided by the controller
   * @throws IOException if the provided InputStream is invalid
   */
  void load(InputStream in, String imageFormat, String imageName) throws IOException;

  /**
   * Save the image data to the provided OutputStream.
   * The way the image data is stored depends on the object of ImageIOHandler
   * passed by the controller.
   *
   * @param out         is the output stream of the file
   * @param imageFormat the image format provided by the controller
   * @param imageName   the name with which the image data is referenced
   * @throws IOException if the provided OutputStream is invalid
   */
  void save(OutputStream out, String imageFormat, String imageName) throws IOException;

  /**
   * Perform an operation on the image data retrieved using the provided image name(s).
   *
   * @param operationName the name of the operation to be performed
   * @param imageName     the name with which the image data is referenced
   * @param destImageName the name with which the resulting image data is to be referenced
   * @param commandArgs   an array of additional arguments that the operation may require (optional)
   */
  void operate(String operationName, List<String> imageName, List<String> destImageName,
               String... commandArgs);

}
