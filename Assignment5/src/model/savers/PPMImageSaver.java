package model.savers;

import java.io.IOException;
import java.io.OutputStream;
import model.ImageSaver;

public class PPMImageSaver implements ImageSaver {

  public PPMImageSaver() {

  }

  @Override
  public void save(int[][][] image, OutputStream out) throws IOException {
    int height = image[0].length;
    int width = image[0][0].length;
    if (image.length == 3) {
      out.write("P3\n".getBytes());
    } else if (image.length == 1) {
      out.write("P2\n".getBytes());
    }
    out.write(String.format("%d %d\n255\n", width, height).getBytes());

    for (int i = 0; i < height; i++) { //rows
      for (int j = 0; j < width; j++) { //cols
        for (int k = 0; k < image.length; k++) {
          out.write((new Integer(image[k][i][j]).toString() + "\n").getBytes());
        }
      }
    }
  }
}
