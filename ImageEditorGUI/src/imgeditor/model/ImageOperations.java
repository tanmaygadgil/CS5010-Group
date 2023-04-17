package imgeditor.model;

import java.util.List;

/**
 * This interface represents image operations.
 * An operation can take one or more ImageData objects as input
 * and may optionally require additional arguments for its execution.
 */
interface ImageOperations {

  /**
   * This method is used to perform the operation on the given ImageData objects.
   *
   * @param imageData     list of images that the operation needs
   * @param operationArgs the optional additional arguments required for performing the operation
   * @return a list of ImageData objects containing the updated pixels after
   *         performing the operation
   */
  List<ImageData> perform(List<ImageData> imageData, String... operationArgs);
}
