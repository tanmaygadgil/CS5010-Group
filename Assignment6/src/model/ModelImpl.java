package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import model.loaders.ConventionalImageLoader;
import model.loaders.PPMImageLoader;
import model.savers.ConventionalImageSaver;
import model.savers.PPMImageSaver;

/**
 * The ModelImpl class represents an image file and provides functionality for loading, saving, and
 * modifying the image. The image is stored as a HashMap where the key is a String representing the
 * image name and the value is a three-dimensional array of integers representing the red, green and
 * blue pixels.
 */
public class ModelImpl implements Model {

  Map<String, int[][][]> imageMap;
  int width;
  int height;

  /**
   * Initializes the hashmap.
   */
  public ModelImpl() {
    imageMap = new HashMap<>();
  }

  @Override
  public void brighten(int increment, String imageName, String destImage) {
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Image name not found in hashmap");
    }
    int[][][] image = imageMap.get(imageName);
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < height; j++) {
        for (int k = 0; k < width; k++) {
          if (image[i][j][k] + increment > 255) {
            image[i][j][k] = 255;
          } else if (image[i][j][k] + increment < 0) {
            image[i][j][k] = 0;
          } else {
            image[i][j][k] = image[i][j][k] + increment;
          }
        }
      }
    }

    imageMap.put(destImage, image);
  }

  @Override
  public void darken(int increment, String imageName, String destImage) {
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Image name not found in hashmap");
    }
    int[][][] image = imageMap.get(imageName);
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < height; j++) {
        for (int k = 0; k < width; k++) {
          if (image[i][j][k] - increment > 255) {
            image[i][j][k] = 255;
          } else if (image[i][j][k] - increment < 0) {
            image[i][j][k] = 0;
          } else {
            image[i][j][k] = image[i][j][k] - increment;
          }
        }
      }
    }

    imageMap.put(destImage, image);
  }


  @Override
  public void flip(Axes axis, String imageName, String destImage) {
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Image name not found in hashmap");
    }
    int[][][] image = this.imageMap.get(imageName);

    if (axis == Axes.HORIZONTAL) {
      for (int i = 0; i < image.length; i++) {
        image[i] = flipHorizontal(image[i]);
      }
      imageMap.put(destImage, image);
    } else if (axis == Axes.VERTICAL) {
      for (int i = 0; i < image.length; i++) {
        image[i] = flipVertical(image[i]);
      }
      imageMap.put(destImage, image);
    }
  }

  private int[][] flipVertical(int[][] image) {
    int[] temp = new int[image.length];
    for (int i = 0; i < height / 2; i++) {
      temp = image[height - i - 1];
      image[height - i - 1] = image[i];
      image[i] = temp;
    }

    return image;
  }

  private int[][] flipHorizontal(int[][] image) {
    for (int i = 0; i < height; i++) { //rows
      for (int j = 0; j < width / 2; j++) { //cols
        int temp = image[i][width - j - 1];
        image[i][width - j - 1] = image[i][j];
        image[i][j] = temp;
      }
    }

    return image;
  }

  @Override
  public void load(InputStream in, String destImage, String format) throws FileNotFoundException {
    int[][][] image = null;
    if (format.equals("ppm")) {
      ImageLoader loader = new PPMImageLoader();
      try {
        image = loader.load(in);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    } else {
      ImageLoader loader = new ConventionalImageLoader();
      try {
        image = loader.load(in);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    if (image == null) {
      throw new FileNotFoundException();
    }

    this.height = image[0].length;
    this.width = image[0][0].length;
    this.imageMap.put(destImage, image);

  }

  @Override
  public void save(OutputStream out, String imageName, String format) throws IOException {
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Image name not found in hashmap");
    }
    int[][][] image = this.imageMap.get(imageName);
    ImageSaver saver;
    if (format.equals("ppm")) {
      saver = new PPMImageSaver();
    } else {
      saver = new ConventionalImageSaver(format);
    }
    saver.save(image, out);


  }

  @Override
  public void greyscale(ImageComponents rGB, String imageName, String destImage) {
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Image name not found in hashmap");
    }
    int[][][] greyscale = new int[1][height][width];

    switch (rGB) {
      case RED:
        greyscale = getRed(imageName);
        break;
      case GREEN:
        greyscale = getGreen(imageName);
        break;
      case BLUE:
        greyscale = getBlue(imageName);
        break;
      case LUMA:
        greyscale = greyscaleLuma(imageName);
        break;
      case VALUE:
        greyscale = greyscaleValue(imageName);
        break;
      case INTENSITY:
        greyscale = greyscaleIntensity(imageName);
        break;
      default:
        break;
    }

    imageMap.put(destImage, greyscale);
  }

  private int[][][] getRed(String imageName) {
    int[][][] image = imageMap.get(imageName);
    if (image.length == 1) {
      throw new IllegalStateException("cannot get component of a greyscale image");
    }
    int[][][] grey = new int[1][height][width];
    grey[0] = image[0];

    return grey;
  }

  private int[][][] getGreen(String imageName) {
    int[][][] image = imageMap.get(imageName);
    if (image.length == 1) {
      throw new IllegalStateException("cannot get component of a greyscale image");
    }
    int[][][] grey = new int[1][height][width];
    grey[0] = image[1];

    return grey;
  }

  private int[][][] getBlue(String imageName) {
    int[][][] image = imageMap.get(imageName);
    if (image.length == 1) {
      throw new IllegalStateException("cannot get component of a greyscale image");
    }
    int[][][] grey = new int[1][height][width];
    grey[0] = image[2];

    return grey;
  }

  private int[][][] greyscaleValue(String imageName) {
    int[][][] image = imageMap.get(imageName);
    int[][][] grey = new int[1][height][width];
    if (image.length == 1) {
      throw new IllegalStateException("cannot get component of a greyscale image");
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grey[0][i][j] = Math.max(Math.max(image[0][i][j], image[1][i][j]), image[2][i][j]);
      }
    }

    return grey;
  }

  private int[][][] greyscaleIntensity(String imageName) {
    int[][][] image = imageMap.get(imageName);
    int[][][] grey = new int[1][height][width];
    if (image.length == 1) {
      throw new IllegalStateException("cannot get component of a greyscale image");
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grey[0][i][j] = (image[0][i][j] + image[1][i][j] + image[2][i][j]) / image.length;
      }
    }

    return grey;
  }

  private int[][][] greyscaleLuma(String imageName) {
    int[][][] image = imageMap.get(imageName);
    int[][][] grey = new int[1][height][width];
    if (image.length == 1) {
      throw new IllegalStateException("cannot get component of a greyscale image");
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grey[0][i][j] = (int) (0.2126 * image[0][i][j] + 0.7152 * image[1][i][j]
            + 0.0722 * image[2][i][j]);
      }
    }

    return grey;
  }

  @Override
  public void rgbSplit(String imageName, String destImageRed, String destImageGreen,
      String destImageBlue) {
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Image name not found in hashmap");
    }
    imageMap.put(destImageRed, getRed(imageName));
    imageMap.put(destImageGreen, getGreen(imageName));
    imageMap.put(destImageBlue, getBlue(imageName));
  }

  @Override
  public void rgbCombine(String destImage, String destImageRed, String destImageGreen,
      String destImageBlue) {
    if (!imageMap.containsKey(destImageRed) || !imageMap.containsKey(destImageGreen)
        || !imageMap.containsKey(destImageBlue)) {
      throw new IllegalArgumentException("Image name not found in hashmap");
    }
    int[][][] image = new int[3][height][width];
    image[0] = imageMap.get(destImageRed)[0];
    image[1] = imageMap.get(destImageGreen)[0];
    image[2] = imageMap.get(destImageBlue)[0];

    imageMap.put(destImage, image);
  }

  private boolean isGrey(int[][][] image) {
    return image.length == 1;
  }

  @Override
  public int[][][] getImage(String imagename) {
    if (!imageMap.containsKey(imagename)) {
      throw new IllegalArgumentException("Image name not found in hashmap");
    } else {
      return imageMap.get(imagename);
    }
  }

  @Override
  public float[][] getHistogramValues(String imagename) {
    float[][] histVals = new float[4][];
    histVals[0] = generateHistogramValues(imagename, 0);
    histVals[1] = generateHistogramValues(imagename, 1);
    histVals[2] = generateHistogramValues(imagename, 2);
    histVals[3] = generateHistogramValues(imagename, 3);
    return histVals;
  }

  /**
   * Creates counts for histogram values.
   *
   * @param imagename the name of the image to be processed
   * @param component 0-Intensity, 1-red, 2- green, 3-blue
   * @return an array with the histogram values
   */
  private float[] generateHistogramValues(String imagename, int component) {
    int[][][] inputImage;
    int index;
    float[] hist = new float[256];
    float maxval = 0;
    //check if images exist
    if (!imageMap.containsKey(imagename)) {
      throw new IllegalArgumentException("Image name not found in hashmap");
    } else {
      inputImage = imageMap.get(imagename);
    }
    //if greyscale just get the first value
    if (isGrey(inputImage)) {
      maxval = 0;
      for (int i = 0; i < inputImage[0].length; i++) {
        for (int j = 0; j < inputImage[0][0].length; j++) {
          int val = inputImage[0][i][j];
          hist[val] += 1;
          maxval = Math.max(hist[val], maxval);
        }
      }

    } else {

      if (component == 0) {
        maxval = 0;
        for (int i = 0; i < inputImage[0].length; i++) {
          for (int j = 0; j < inputImage[0][0].length; j++) {
            int val = (inputImage[0][i][j] + inputImage[1][i][j] + inputImage[2][i][j]) / 3;
            hist[val] += 1;
            maxval = Math.max(hist[val], maxval);
          }
        }
      } else {
        index = component - 1;
        maxval = 0;
        for (int i = 0; i < inputImage[0].length; i++) {
          for (int j = 0; j < inputImage[0][0].length; j++) {
            int val = inputImage[index][i][j];
            hist[val] += 1;
            maxval = Math.max(hist[val], maxval);
          }
        }
      }
    }
    //Normalize

    for (int i = 0; i < hist.length; i++) {
      hist[i] /= (float) maxval;
    }
    return hist;
  }


}
