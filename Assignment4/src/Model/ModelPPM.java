package Model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class ModelPPM implements Model {

  private final HashMap<String, int[][][]> imageMap;

  public ModelPPM(){
    this.imageMap = new HashMap<>();
  }
  @Override
  public void load(String filePath, String destImage) throws FileNotFoundException {
    //size = (3, 768, 1024)
    int[][][] image = ImageUtil.readPPM(filePath);
    this.imageMap.put(destImage, image);
  }

  @Override
  public void save(String filePath, String imageName) throws IOException {
    FileOutputStream fout = new FileOutputStream(filePath);

    int[][][] image = this.imageMap.get(imageName);
    int width = image[0][0].length;
    int height = image[0].length;

    fout.write("P3\n".getBytes());
    fout.write(String.format("%d %d\n255\n", width, height).getBytes());

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        fout.write((new Integer(image[0][i][j]).toString() + "\n").getBytes());
        fout.write((new Integer(image[1][i][j]).toString() + "\n").getBytes());
        fout.write((new Integer(image[2][i][j]).toString() + "\n").getBytes());
      }
    }

  }

  @Override
  public void brighten(int Increment, String imageName, String destImage) {

  }

  @Override
  public void flip(Axes axis, String imageName, String destImage) {

  }

  @Override
  public void greyscale(ImageComponents RGB, String imageName, String destImage) {

  }


  @Override
  public void rgbSplit(String DestImage, String DestImageRed, String DestImageGreen,
      String DestImageBlue) {

  }

  @Override
  public void rgbCombine(String DestImage, String DestImageRed, String DestImageGreen,
      String DestImageBlue) {

  }

}
