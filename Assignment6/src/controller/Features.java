package controller;

import java.io.IOException;

public interface Features {

  void callLoad(String filePath, String imageName) throws IOException;

  void callSave(String filePath, String imageName) throws IOException;

  void callCommand(String command) throws IOException;


}
