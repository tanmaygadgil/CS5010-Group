package imgeditor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * This class represents a Filter Image Operation.
 * This operation applies a filter to the provided input image and returns an image with the
 * applied filter. Each filter matrix represents a different filter to be applied
 * like blur, sharpen, etc.
 */
class Filter extends AbstractImageOperations {

  private final Map<String, Function<Integer, double[][]>> getFilter;

  /**
   * Construct a Filter object.
   */
  Filter() {
    getFilter = new HashMap<>();
    getFilter.put("blur", i -> Kernel.Blur.getMatrix());
    getFilter.put("sharpen", i -> Kernel.Sharpen.getMatrix());
  }

  @Override
  public List<ImageData> perform(List<ImageData> imageData, String... operationArgs) {
    String filter = operationArgs[0];
    double[][] kernel = getFilter.get(filter).apply(0);
    int[][][] pixels = imageData.get(0).getPixels();
    int width = pixels.length;
    int height = pixels[0].length;

    int[][][] newPixels = getAlphaPixelsIfExists(pixels);

    int kernelHeight = kernel.length;
    int kernelWidth = kernel[0].length;
    int kernelHeightOffset = kernelHeight / 2;
    int kernelWidthOffset = kernelWidth / 2;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        float sumR = 0;
        float sumG = 0;
        float sumB = 0;
        for (int ki = 0; ki < kernelHeight; ki++) {
          for (int kj = 0; kj < kernelWidth; kj++) {
            int pixelX = i + ki - kernelHeightOffset;
            int pixelY = j + kj - kernelWidthOffset;
            if (pixelX < 0 || pixelX >= height) {
              break;
            } else if (pixelY < 0 || pixelY >= width) {
              continue;
            } else {
              sumR += pixels[pixelY][pixelX][0] * kernel[ki][kj];
              sumG += pixels[pixelY][pixelX][1] * kernel[ki][kj];
              sumB += pixels[pixelY][pixelX][2] * kernel[ki][kj];
            }
          }
        }

        newPixels[j][i][0] = Math.round(sumR) > 0 ? Math.min(Math.round(sumR), 255)
                : Math.max(Math.round(sumR), 0);
        newPixels[j][i][1] = Math.round(sumG) > 0 ? Math.min(Math.round(sumG), 255)
                : Math.max(Math.round(sumG), 0);
        newPixels[j][i][2] = Math.round(sumB) > 0 ? Math.min(Math.round(sumB), 255)
                : Math.max(Math.round(sumB), 0);
      }
    }
    List<ImageData> resultImageData = new ArrayList<>();
    resultImageData.add(new ImageData(newPixels));
    return resultImageData;
  }
}

