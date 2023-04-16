package imgeditor.model;

/**
 * This class represents the implementation of a read-only model for the Image Editor program.
 * It is only used for retrieving a copy of the pixel array for the given image name, and does not
 * contain any methods which can manipulate the data in the model.
 */
public class ReadOnlyModelImpl implements ReadOnlyModel {

  private final ImageProcessingFactory factory;

  /**
   * Construct a ReadOnlyModelImpl object.
   * Sets the ImageProcessingFactory instance.
   */
  public ReadOnlyModelImpl() {
    this.factory = ImageProcessingFactory.getFactory();
  }

  @Override
  public int[][][] getImagePixels(String imageName) throws IllegalArgumentException {
    ImageData imageData = this.factory.getFromImageData(imageName);
    if (imageData != null) {
      return imageData.getPixels();
    } else {
      throw new IllegalArgumentException(
              String.format("Image name '%s' does not exist.\n", imageName));
    }
  }
}
