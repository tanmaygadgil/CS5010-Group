package imgeditor.view;

/**
 * This interface represents a text-based view. All the messages in the view will be displayed
 * in text form.
 * The location where the messages will be displayed is implementation-specific.
 */
public interface TextView {

  /**
   * Send an error message to the view
   * in case a command failed to execute correctly.
   *
   * @param message the success message to be displayed which is provided by the controller
   */
  void showSuccessMessage(String message);

  /**
   * Send a success message to the view
   * in case a command was able to execute successfully.
   *
   * @param message the error message to be displayed which is provided by the controller
   */
  void showErrorMessage(String message);
}
