package controller;

import java.io.IOException;

public interface Features {

  void callLoad(String filePath) throws IOException;

  void callSave(String filePath) throws IOException;

  void callCommand(String command) throws IOException;

}
