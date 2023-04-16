package imgeditor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an RGB Split Image Operation.
 * This operation splits a single RGB image into
 * three images each representing a channel (red, green, blue).
 */
class RGBSplit extends AbstractImageOperations {

  @Override
  public List<ImageData> perform(List<ImageData> imageData, String... operationArgs) {
    Greyscale greyscale = new Greyscale();
    List<ImageData> resultImageData = new ArrayList<>();
    resultImageData.add(greyscale.perform(imageData, "red-component").get(0));
    resultImageData.add(greyscale.perform(imageData, "green-component").get(0));
    resultImageData.add(greyscale.perform(imageData, "blue-component").get(0));
    return resultImageData;
  }
}
