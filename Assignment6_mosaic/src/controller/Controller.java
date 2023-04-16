package controller;

import java.io.IOException;

/**
 * This interface represents a controller, which interacts with the view and model in order to
 * execute needed commands.
 */
public interface Controller {

  /**
   * A run method that works in a loop to execute inputs from the view.
   */
  void run() throws IOException;

}
