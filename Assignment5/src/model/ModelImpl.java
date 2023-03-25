package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import model.loaders.ConventionalImageLoader;
import model.loaders.PPMImageLoader;
import model.savers.ConventionalImageSaver;
import model.savers.PPMImageSaver;

public class ModelImpl implements Model {
  final HashMap<String, int[][][]> imageMap;
  int width;
  int height;

  public ModelImpl() {
    this.imageMap = new HashMap<>();
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
    if (format.equals("ppm")){
        ImageLoader loader = new PPMImageLoader();
      try {
        image = loader.load(in);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    } else {
      ImageLoader loader = new ConventionalImageLoader(format);
      try {
        image = loader.load(in);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    if(image == null){
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
    if (format.equals("ppm")){
      saver = new PPMImageSaver();
    }else {
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
    int[][][] grey = new int[1][height][width];
    grey[0] = image[0];

    return grey;
  }

  private int[][][] getGreen(String imageName) {
    int[][][] image = imageMap.get(imageName);
    int[][][] grey = new int[1][height][width];
    grey[0] = image[1];

    return grey;
  }

  private int[][][] getBlue(String imageName) {
    int[][][] image = imageMap.get(imageName);
    int[][][] grey = new int[1][height][width];
    grey[0] = image[2];

    return grey;
  }

  private int[][][] greyscaleValue(String imageName) {
    int[][][] image = imageMap.get(imageName);
    int[][][] grey = new int[1][height][width];

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
}
