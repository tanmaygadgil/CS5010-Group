package imgeditor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an RGB Combine Image Operation.
 * This operation combines three input images each representing a channel (red, green, blue)
 * into a single RGB image.
 */
class RGBCombine extends AbstractImageOperations {

  @Override
  public List<ImageData> perform(List<ImageData> imageData, String... operationArgs)
          throws IllegalArgumentException {
    int red;
    int green;
    int blue;
    int[][][] redPixels = imageData.get(0).getPixels();
    int[][][] greenPixels = imageData.get(1).getPixels();
    int[][][] bluePixels = imageData.get(2).getPixels();

    if ((redPixels.length != greenPixels.length || redPixels.length != bluePixels.length)
            || (redPixels[0].length != greenPixels[0].length
            || redPixels[0].length != bluePixels[0].length)) {
      throw new IllegalArgumentException("The specified images have different dimensions.");
    }

    int[][][] combinedPixels = getAlphaPixelsIfExists(redPixels);
    for (int i = 0; i < redPixels[0].length; i++) {
      for (int j = 0; j < redPixels.length; j++) {
        red = redPixels[j][i][0];
        green = greenPixels[j][i][1];
        blue = bluePixels[j][i][2];
        for (int k = 0; k < redPixels[0][0].length; k++) {
          combinedPixels[j][i][0] = red;
          combinedPixels[j][i][1] = green;
          combinedPixels[j][i][2] = blue;
        }
      }
    }

    List<ImageData> resultImageData = new ArrayList<>();
    resultImageData.add(new ImageData(combinedPixels));
    return resultImageData;
  }
}
