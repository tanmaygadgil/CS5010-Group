package imgeditor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Dither Image Operation.
 * This operation first converts a color image into a greyscale image using the luma component,
 * and then into a dithered image using Floyd–Steinberg dithering.
 */
class Dither extends AbstractImageOperations {

  @Override
  public List<ImageData> perform(List<ImageData> imageData, String... operationArgs) {

    Greyscale greyscale = new Greyscale();
    ImageData greyscaleImgData =
        greyscale.perform(imageData, "luma-component").get(0);

    int[][][] pixels = greyscaleImgData.getPixels();
    int width = pixels.length;
    int height = pixels[0].length;

    final double[] weights = {7.0, 3.0, 5.0, 1.0};
    final double[] fractions = {16, 16, 16, 16};

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int oldColor = pixels[j][i][0];
        int newColor = (Math.abs(oldColor) < Math.abs(oldColor - 255)) ? 0 : 255;
        int errorColor = oldColor - newColor;
        pixels[j][i][0] = pixels[j][i][1] = pixels[j][i][2] = newColor;

        for (int k = 0; k < 4; k++) {
          double weight = weights[k];
          double fraction = fractions[k];
          int newRow = i;
          int newCol = j;

          switch (k) {
            case 0:
              newCol++;
              break;
            case 1:
              newRow++;
              newCol--;
              break;
            case 2:
              newRow++;
              break;
            case 3:
              newRow++;
              newCol++;
              break;
            default:
              break;
          }

          if (newRow >= height || newCol >= width || newCol < 0) {
            continue;
          }
          int newRowed = pixels[newCol][newRow][0];
          newRowed = clamp((int) Math.round(newRowed + (errorColor * weight / fraction)));
          pixels[newCol][newRow][0] = pixels[newCol][newRow][1]
                  = pixels[newCol][newRow][2] = newRowed;
        }
      }
    }
    List<ImageData> resultImageData = new ArrayList<>();
    resultImageData.add(new ImageData(pixels));
    return resultImageData;
  }

  private static int clamp(int x) {
    return Math.max(0, Math.min(255, x));
  }
}
