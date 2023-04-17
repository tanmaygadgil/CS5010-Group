package imgeditor.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This is a helper class for the model which provides the object of ImageOperations
 * and / or ImageIOHandler based on the provided operation name or image format given to it.
 * Any new operations and image formats to be supported will be added to the HashMap.
 * It also contains a map of the image names and corresponding ImageData objects.
 * Thus, the Model implementation(s) do not need to change in case
 * of new operations or image formats.
 */
class ImageProcessingFactory {

  private final Map<String, Function<Integer, ImageOperations>> imageOperationsMap;
  private final Map<String, Function<Integer, ImageIOHandler>> imageIOHandlerMap;
  private final Map<String, ImageData> imageNamesDataMap;
  private static ImageProcessingFactory factory = null;


  /**
   * Construct an ImageProcessingFactory object.
   * This constructor initializes and populates the imageOperationsMap with the supported operations
   * and the imageIOHandlerMap with the supported image types
   */
  private ImageProcessingFactory() {

    imageNamesDataMap = new HashMap<>();

    imageOperationsMap = new HashMap<>();
    imageOperationsMap.put("horizontal-flip", i -> new HorizontalFlip());
    imageOperationsMap.put("vertical-flip", i -> new VerticalFlip());
    imageOperationsMap.put("brighten", i -> new AdjustBrightness());
    imageOperationsMap.put("greyscale", i -> new Greyscale());
    imageOperationsMap.put("rgb-split", i -> new RGBSplit());
    imageOperationsMap.put("rgb-combine", i -> new RGBCombine());
    imageOperationsMap.put("blur", i -> new Filter());
    imageOperationsMap.put("sharpen", i -> new Filter());
    imageOperationsMap.put("sepia", i -> new ColorTransformation());
    imageOperationsMap.put("dither", i -> new Dither());

    imageIOHandlerMap = new HashMap<>();
    imageIOHandlerMap.put(".ppm", i -> new PPMImageIOHandler());
    imageIOHandlerMap.put(".jpg", i -> new JPGImageIOHandler());
    imageIOHandlerMap.put(".png", i -> new PNGImageIOHandler());
    imageIOHandlerMap.put(".bmp", i -> new BMPImageIOHandler());
  }

  /**
   * Return the ImageProcessingFactory instance.
   * If the ImageProcessingFactory has not been instantiated before,
   * a new instance will be created which will be used for all subsequent calls.
   * @return the ImageProcessingFactory instance
   */
  static ImageProcessingFactory getFactory() {
    if (factory == null) {
      factory = new ImageProcessingFactory();
    }
    return factory;
  }

  /**
   * Returns the correct ImageIOHandler for the given image format.
   * @param imageFormat the image format provided by the controller
   * @return the correct ImageIOHandler object for the given image format
   * @throws IllegalArgumentException if the image format provided is not supported
   */
  ImageIOHandler getImageHandler(String imageFormat) throws IllegalArgumentException {
    if (!imageIOHandlerMap.containsKey(imageFormat)) {
      throw new IllegalArgumentException("Unsupported file format.\n");
    }
    return imageIOHandlerMap.get(imageFormat).apply(0);
  }

  /**
   * Returns the correct ImageOperations object for the given operation name.
   * @param operationName the operation name to be executed provided by the controller
   * @return the correct ImageOperations object for the given operation name
   * @throws IllegalArgumentException if the operation provided is invalid
   */
  Function<Integer, ImageOperations> getImageOperation(String operationName)
          throws IllegalArgumentException {
    if (!imageOperationsMap.containsKey(operationName)) {
      throw new IllegalArgumentException("Unsupported operation.\n");
    }
    return imageOperationsMap.get(operationName);
  }

  /**
   * Store the image name and corresponding ImageData in the HashMap.
   * @param imageName the name of the image
   * @param imageData the data of the image
   */
  void putInImageDataMap(String imageName, ImageData imageData) {
    this.imageNamesDataMap.put(imageName, imageData);
  }

  /**
   * Return the image data corresponding to the specified image name.
   * @param imageName the image name for which the image data is to be retrieved
   * @return the ImageData object corresponding to the specified image name
   * @throws IllegalArgumentException if the specified image name does not exist
   */
  ImageData getFromImageData(String imageName) throws IllegalArgumentException {
    if (!imageNamesDataMap.containsKey(imageName)) {
      throw new IllegalArgumentException(
              String.format("Image name '%s' does not exist.\n", imageName));
    }
    return this.imageNamesDataMap.get(imageName);
  }
}
