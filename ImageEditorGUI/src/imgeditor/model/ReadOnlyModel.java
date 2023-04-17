package imgeditor.model;

/**
 * This interface represents a Read-only Image Editor Model.
 * It contains a method which is used to retrieve the pixel array representing an image.
 * This method is not executable by any Model objects.
 * This interface does not offer any methods which can manipulate the data in the model.
 */
public interface ReadOnlyModel {

  /**
   * Return the image's pixel array. A copy of the array is returned to avoid direct access to the
   * array via the object.
   *
   * @param imageName the name with which the image is referenced
   * @return a 3d-array of the pixels of the image
   */
  int[][][] getImagePixels(String imageName) throws IllegalArgumentException;
}
