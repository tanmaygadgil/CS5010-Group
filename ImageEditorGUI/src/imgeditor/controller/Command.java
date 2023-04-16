package imgeditor.controller;

import java.io.IOException;

import imgeditor.model.Model;

/**
 * This interface represents a Command.
 * A command defines the type of operation to be executed on the image.
 */
public interface Command {

  /**
   * Given a model, executes the specified command.
   *
   * @param m the model
   * @throws IOException if any I/O error occurs
   */
  void execute(Model m) throws IOException;
}
