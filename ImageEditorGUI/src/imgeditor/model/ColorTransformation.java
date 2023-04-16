package imgeditor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * This class represents a Color Transformation Image Operation.
 * This operation transforms the color in each channel of each pixel based on a specific kernel.
 */
class ColorTransformation extends AbstractImageOperations {

  private final Map<String, Function<Integer, double[][]>> getKernel;

  /**
   * Construct a ColorTransformation object.
   */
  ColorTransformation() {
    getKernel = new HashMap<>();
    getKernel.put("sepia", i -> Kernel.Sepia.getMatrix());
    getKernel.put("luma", i -> Kernel.LumaGreyscale.getMatrix());
  }

  @Override
  public List<ImageData> perform(List<ImageData> imageData, String... operationArgs) {
    String colorTransform = operationArgs[0];
    double[][] kernel = getKernel.get(colorTransform).apply(0);
    int[][][] pixels = imageData.get(0).getPixels();
    int width = pixels.length;
    int height = pixels[0].length;

    int[][][] newPixels = getAlphaPixelsIfExists(pixels);

    int kernelHeight = kernel.length;
    int kernelWidth = kernel[0].length;
    double red = 0;
    double green = 0;
    double blue = 0;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int ki = 0; ki < kernelHeight; ki++) {
          double sum = 0.0;
          for (int kj = 0; kj < kernelWidth; kj++) {
            sum += (pixels[j][i][kj] * kernel[ki][kj]);
          }
          switch (ki) {
            case 0:
              red = sum;
              break;
            case 1:
              green = sum;
              break;
            case 2:
              blue = sum;
              break;
            default:
              break;
          }
        }

        newPixels[j][i][0] = (int) (Math.round(red) > 0 ? Math.min(Math.round(red), 255)
                : Math.max(Math.round(red), 0));
        newPixels[j][i][1] = (int) (Math.round(green) > 0 ? Math.min(Math.round(green), 255)
                : Math.max(Math.round(green), 0));
        newPixels[j][i][2] = (int) (Math.round(blue) > 0 ? Math.min(Math.round(blue), 255)
                : Math.max(Math.round(blue), 0));
      }
    }

    List<ImageData> resultImageData = new ArrayList<>();
    resultImageData.add(new ImageData(newPixels));
    return resultImageData;
  }
}

