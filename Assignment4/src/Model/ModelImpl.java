package Model;
import Model.ImageUtil;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ModelImpl implements Model {

  private HashMap<String, int[][][]> hashMap = new HashMap<>();

  @Override
  public void load(String imageName, String destImage) {
    //size = (3, 768, 1024)
    int[][][] image = ImageUtil.readPPM(destImage);
    hashMap.put(imageName, image);
  }

  @Override
  public void save(String filePath, String imageName) throws IOException {
    FileOutputStream fout = new FileOutputStream(filePath);

    int[][][] image = hashMap.get(imageName);
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
}
