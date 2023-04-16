package imgeditor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an abstract class that implements the ImageOperations interface.
 * This class will be extended by all the classes that represent individual image operations.
 */
abstract class AbstractImageOperations implements ImageOperations {

  protected enum FlipType {
    Horizontal,
    Vertical
  }

  /**
   * Returns a 3d pixel array with or without an alpha component
   * depending on the size of the third dimension.
   *
   * @param pixels the original pixel array
   * @return a new pixel array which may or may not contain the alpha component
   */
  protected int[][][] getAlphaPixelsIfExists(int[][][] pixels) {
    int numberOfComponents = pixels[0][0].length;
    int[][][] newPixels = new int[pixels.length][pixels[0].length][numberOfComponents];

    if (numberOfComponents == 4) {
      for (int i = 0; i < pixels[0].length; i++) {
        for (int j = 0; j < pixels.length; j++) {
          newPixels[j][i][3] = pixels[j][i][3];
        }
      }
    }
    return newPixels;
  }

  /**
   * This is a helper method for the horizontal and vertical flip commands.
   * It performs the flip operation on the image data based on the type of flip specified.
   *
   * @param imageData the image data to be flipped
   * @param flipType  the type of flip to be performed. This is an enum value.
   * @return the flipped image data
   */
  protected List<ImageData> flipHelper(List<ImageData> imageData, FlipType flipType) {
    int[][][] pixels = imageData.get(0).getPixels();
    int width = pixels.length;
    int height = pixels[0].length;
    int numberOfComponents = pixels[0][0].length;

    int[][][] newPixels = new int[width][height][numberOfComponents];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rowVal;
        int colVal;
        switch (flipType) {
          case Horizontal:
            rowVal = width - j - 1;
            colVal = i;
            break;

          case Vertical:
            rowVal = j;
            colVal = height - i - 1;
            break;

          default:
            throw new IllegalArgumentException("Invalid flip type");
        }
        System.arraycopy(pixels[rowVal][colVal], 0, newPixels[j][i], 0,
                numberOfComponents);
      }
    }
    List<ImageData> resultImageData = new ArrayList<>();
    resultImageData.add(new ImageData(newPixels));
    return resultImageData;
  }

}
