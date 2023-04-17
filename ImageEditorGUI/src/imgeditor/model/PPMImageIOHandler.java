package imgeditor.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class represents a PPMImageIOHandler.
 * Specifically, it represents an ASCII PPMImageIOHandler which supports ASCII PPM file data that
 * contains image data in text form - the width, height, maximum pixel value, and each pixel value
 * in a new line in the image file.
 * This class contains methods for reading the image data from an InputStream and
 * writing the pixel array to the provided OutputStream.
 */
class PPMImageIOHandler implements ImageIOHandler {

  @Override
  public ImageData read(InputStream in) throws IllegalArgumentException, IOException {
    /*
    The file at the specified path is read, and the pixel data in the ASCII PPM file is
    converted to a 3-dimensional Kernel.
    This pixel array and the corresponding image name provided by the controller is stored
    in a Pixel object which is then returned to the model.
     */
    Scanner sc;

    sc = new Scanner(in);

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maximumValue = sc.nextInt();

    if (width <= 0) {
      throw new IllegalArgumentException("Image width cannot be 0 or negative.");
    }

    if (height <= 0) {
      throw new IllegalArgumentException("Image height cannot be 0 or negative.");
    }

    int[][][] pixels = new int[width][height][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!sc.hasNext()) {
          throw new IOException("Image data contains less pixels than width or height.");
        }
        int r;
        int g;
        int b;
        try {
          r = sc.nextInt();
          g = sc.nextInt();
          b = sc.nextInt();
        } catch (NoSuchElementException e) {
          throw new IOException("Image data contains less pixels than width or height.");
        }

        if (r > maximumValue || g > maximumValue || b > maximumValue) {
          throw new IllegalArgumentException("Pixel value greater than maximum");
        }
        pixels[j][i][0] = r;
        pixels[j][i][1] = g;
        pixels[j][i][2] = b;
      }
    }
    if (sc.hasNext()) {
      throw new FileNotFoundException("Image data contains more pixels than width or height.");
    }
    return new ImageData(pixels);
  }

  @Override
  public void save(OutputStream out, ImageData imageData) {
    /*
    This method saves the pixel array of the image at the specified imagePath.
    A PrintWriter is used to write the ASCII PPM file line-by-line in the correct format.
     */
    int[][][] pixels = imageData.getPixels();
    int width = pixels.length;
    int height = pixels[0].length;
    int maxValue = 255;

    PrintWriter print = new PrintWriter(out);
    print.println("P3");
    print.println(width + " " + height);
    print.println(maxValue);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        print.print(pixels[j][i][0]);
        print.println();

        print.print(pixels[j][i][1]);
        print.println();

        print.print(pixels[j][i][2]);
        print.println();
      }
    }
    print.close();
  }
}
