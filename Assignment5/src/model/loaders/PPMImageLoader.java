package model.loaders;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import model.ImageLoader;

public class PPMImageLoader implements ImageLoader {

  public PPMImageLoader() {

  }

  @Override
  public int[][][] load(InputStream in) {
    Scanner sc;

    sc = new Scanner(in);
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!(token.equals("P3") || token.equals("P2"))) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int channels = 0;
    if (token.equals("P3")) {
      channels = 3;
    }
    if (token.equals("P2")) {
      channels = 1;
    }

    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    int[][][] image = readValues(sc, channels, height, width);

    return image;
  }

  private static int[][][] readValues(Scanner sc, int channels, int height, int width) {
    int[][][] image = new int[channels][height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < channels; k++) {
          int val = sc.nextInt();
          image[k][i][j] = val;
        }
      }
    }

    return image;
  }

  @Override
  public String toString(){
    return "PPMLoader";
  }

}
