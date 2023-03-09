package Model;


import java.io.FileNotFoundException;
import java.io.IOException;


public interface Model {

  void load(String imageName, String destImage);

  void save(String filePath, String imageName) throws IOException;



}
