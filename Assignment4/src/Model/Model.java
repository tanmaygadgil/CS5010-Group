package Model;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Model {

  void load(String filePath, String destImage) throws FileNotFoundException;

  void save(String filePath, String imageName) throws IOException;

  void greyscale(ImageComponents RGB, String imageName, String destImage);

  void flip(Axes axis, String imageName, String destImage );

  void brighten(int Increment, String imageName, String destImage);

  void rgbSplit(String imageName, String DestImageRed,
      String DestImageGreen,
      String DestImageBlue);

  void rgbCombine(String DestImage, String DestImageRed,
      String DestImageGreen,
      String DestImageBlue);

}
