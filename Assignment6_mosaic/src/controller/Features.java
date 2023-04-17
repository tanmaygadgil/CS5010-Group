package controller;

import java.io.IOException;

/**
 * An interface that defines all the functions expected from a gui controller.
 */
public interface Features {

  /**
   * Enables a load operation.
   *
   * @param filePath  the file path to be loaded
   * @param imageName the name of the image to be stored as
   * @throws IOException raised when the file doesn't exist
   */
  void callLoad(String filePath, String imageName) throws IOException;

  /**
   * Enables a save operation.
   *
   * @param filePath  the file path to be saved
   * @param imageName the name of the image to be saved
   * @throws IOException raised when the file doesn't exist
   */
  void callSave(String filePath, String imageName) throws IOException;

  /**
   * A generic all to a command. the implementation defines how this is run
   *
   * @param command the command name
   * @throws IOException raised when there is an issue
   */
  void callCommand(String command) throws IOException;


}
