package controller.commands;

import java.io.IOException;
import model.Model;
import model.ModelV2;

public interface ImageProcessingCommand {

  void run(ModelV2 m) throws IOException;

}
