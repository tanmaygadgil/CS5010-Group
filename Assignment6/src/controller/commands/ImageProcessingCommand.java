package controller.commands;

import java.io.IOException;
import model.ModelV2;

/**
 * This interface represents the commands which will be called by the controller.
 */
public interface ImageProcessingCommand {

  /**
   * Runs the given command on the given model.
   *
   * @param m model to run command on.
   * @throws IOException if I/O exception occurs.
   */
  void run(ModelV2 m) throws IOException;

}
