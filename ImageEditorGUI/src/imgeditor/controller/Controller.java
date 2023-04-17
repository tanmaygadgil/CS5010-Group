package imgeditor.controller;

import java.io.FileNotFoundException;

/**
 * Controller interface for the image editor program.
 * It provides a method for starting the execution.
 */
public interface Controller {

  /**
   * Starts the controller execution.
   *
   * @throws FileNotFoundException if the file path is provided and is invalid
   */
  void execute() throws FileNotFoundException;
}
