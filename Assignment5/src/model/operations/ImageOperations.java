package model.operations;

/**
 * An interface to define the behaviour of image operations.
 */
public interface ImageOperations {

  /**
   * Encapsulates the logic to run an image operation
   * @param image the image to be transformed
   * @return the operated image
   */
  int[][][] operate(int[][][] image);

}
