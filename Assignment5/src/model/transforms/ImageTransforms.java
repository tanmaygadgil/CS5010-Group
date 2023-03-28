package model.transforms;

/**
 * An interface defining the behaviour of an image transformation.
 */
public interface ImageTransforms {

  /**
   * A method that encapsulates the logic to run a filter operation
   * @param image the image ot be transformed
   * @return A transformed Image
   */
  int[][][] transform(int[][][] image);

}
