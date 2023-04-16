package imgeditor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Greyscale Image Operation.
 * This operation converts a color image into a greyscale image based on the specified component.
 * If luma is specified as the component, it will convert the image to greyscale using the
 * color transformation matrix.
 */
class Greyscale extends AbstractImageOperations {

  @Override
  public List<ImageData> perform(List<ImageData> imageData, String... operationArgs) {

    String component = operationArgs[0];
    String[] componentName = component.split("-");

    if ((componentName.length != 2) || !componentName[1].equals("component")) {
      throw new IllegalArgumentException("Invalid component name.\n");
    }

    if (componentName[0].equals("luma")) {
      return new ColorTransformation().perform(imageData, "luma");
    }

    int[][][] pixels = imageData.get(0).getPixels();
    int width = pixels.length;
    int height = pixels[0].length;
    int[][][] newPixels = getAlphaPixelsIfExists(pixels);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = pixels[j][i][0];
        int green = pixels[j][i][1];
        int blue = pixels[j][i][2];
        int pixelGreyScaleValue = getGreyscaleValue(componentName[0], red, green, blue);
        newPixels[j][i][0] = pixelGreyScaleValue;
        newPixels[j][i][1] = pixelGreyScaleValue;
        newPixels[j][i][2] = pixelGreyScaleValue;
      }
    }
    List<ImageData> resultImageData = new ArrayList<>();
    resultImageData.add(new ImageData(newPixels));
    return resultImageData;
  }

  private static int getGreyscaleValue(String component, int red, int green, int blue)
          throws IllegalArgumentException {
    switch (component) {
      case "red":
        return red;
      case "green":
        return green;
      case "blue":
        return blue;
      case "value":
        return Math.max(red, Math.max(green, blue));
      case "intensity":
        return (red + green + blue) / 3;
      default:
        throw new IllegalArgumentException("Invalid component name.\n");
    }
  }
}
