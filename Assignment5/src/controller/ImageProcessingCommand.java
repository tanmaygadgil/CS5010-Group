package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import model.Model;

public interface ImageProcessingCommand {

  void run(Model m) throws IOException;

}
