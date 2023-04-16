package imgeditor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an Adjust Brightness Image Operation.
 * This operation brightens or darkens an image given the increment value.
 * Each pixel in the image is increased or decreased by the specified value, up to the
 * minimum pixel value (0) or the maximum pixel value (255).
 */
class AdjustBrightness extends AbstractImageOperations {

  private static int helpBrighten(int adjustedPixelBrightness) {
    if (adjustedPixelBrightness < 0) {
      return 0;
    } else if (adjustedPixelBrightness > 255) {
      return 255;
    }
    return adjustedPixelBrightness;
  }

  @Override
  public List<ImageData> perform(List<ImageData> imageData, String... operationArgs) {

    int[][][] pixels = imageData.get(0).getPixels();
    int adjustBrightnessBy = Integer.parseInt(operationArgs[0]);
    int width = pixels.length;
    int height = pixels[0].length;
    int[][][] newPixels = getAlphaPixelsIfExists(pixels);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newPixels[j][i][0] = helpBrighten(pixels[j][i][0]
                + adjustBrightnessBy);
        newPixels[j][i][1] = helpBrighten(pixels[j][i][1]
                + adjustBrightnessBy);
        newPixels[j][i][2] = helpBrighten(pixels[j][i][2]
                + adjustBrightnessBy);

      }
    }
    List<ImageData> resultImageData = new ArrayList<>();
    resultImageData.add(new ImageData(newPixels));
    return resultImageData;
  }

}
