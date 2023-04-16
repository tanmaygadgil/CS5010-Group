package view;

/**
 * This interface represents the possible actions to be done by an implementation of view.
 */
public interface View {

  /**
   * Get an input in a specific format. This will be represented as a string.
   */

  String getInput();

  /**
   * A funtion to output a string depending on the implementation.
   *
   * @param inputString The string to be represented
   */
  void renderOutput(String inputString);

  void reset();
}
