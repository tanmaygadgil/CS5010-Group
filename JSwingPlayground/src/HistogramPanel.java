import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;
public class HistogramPanel extends JPanel {

  int[][][] image;

  public HistogramPanel() {
    setLayout(new GridLayout(4, 1));
    this.image = null;
  }

  public void setImage(int[][][] image) {

  }

  public void readAndLoad(String ImagePath) {
    BufferedImage img;
    try {
      img = ImageIO.read(new FileInputStream(ImagePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.image = convertToRgb(img);
    int height = this.image[0].length;
    int width = this.image[0][0].length;
    int[] redHist = getRedHistorgramValues();
    System.out.println(Arrays.toString(Arrays.stream(redHist).toArray()));
  }

  private int[][][] convertToRgb(BufferedImage img) {
    int height = img.getHeight();
    int width = img.getWidth();
    int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
    int[][][] image = new int[3][height][width];
    for (int i = 0; i < height * width; i++) {
      int pixel = pixels[i];
      int x = i % width;
      int y = i / width;
      image[0][y][x] = (pixel >> 16) & 0xff; // red
      image[1][y][x] = (pixel >> 8) & 0xff;  // green
      image[2][y][x] = pixel & 0xff;          // blue
    }

    return image;

  }

  public int[] getRedHistorgramValues() {
    int[] hist = new int[256];
    for (int i = 0; i < this.image[0].length; i++) {
      for (int j = 0; j < this.image[0][0].length; j++) {
        int val = this.image[0][i][j];
        hist[val] += 1;


      }


    }
    return hist;
  }

  public int[] getgreenHistorgramValues() {
    int[] hist = new int[256];
    for (int i = 0; i < this.image[0].length; i++) {
      for (int j = 0; j < this.image[0][0].length; j++) {
        int val = this.image[1][i][j];
        hist[val] += 1;


      }

    }
    return hist;
  }

  public int[] getBlueHistorgramValues() {
    int[] hist = new int[256];
    for (int i = 0; i < this.image[0].length; i++) {
      for (int j = 0; j < this.image[0][0].length; j++) {
        int val = this.image[2][i][j];
        hist[val] += 1;


      }

    }
    return hist;
  }

  public int[] getIntensityHistorgramValues() {
    int[] hist = new int[256];
    for (int i = 0; i < this.image[0].length; i++) {
      for (int j = 0; j < this.image[0][0].length; j++) {
        int val = (this.image[0][i][j]+ this.image[1][i][j]+ this.image[2][i][j]) / 3;
        hist[val] += 1;


      }

    }
    return hist;
  }


}

