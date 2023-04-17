package imgeditor.view;

import imgeditor.controller.GUIController;

/**
 * This interface represents a GUIView. It offers methods for displaying status messages,
 * warning popup messages and methods for each operation that is supported by the program.
 */
public interface GUIView {

  /**
   * Display a popup warning message.
   * @param messageAfterExecution the message to be displayed in the popup window
   */
  void displayWarningPopup(String messageAfterExecution);

  /**
   * Display a dropdown popup menu.
   * @param command the command for which dropdown menu items need to be displayed
   * @return the selected menu item
   */
  String displayDropdown(String command);

  /**
   * Display an input dialog popup for brightness value.
   * @return the brightness value inputted by the user
   */
  String getBrightnessValue();

  /**
   * Display a browse popup window for opening a file from disk.
   * @return the file path selected by the user.
   */
  String load();

  /**
   * Display a browse popup window for saving a file to disk.
   * @return the file path selected by the user
   */
  String save();

  /**
   * Set the listeners for all the buttons in the GUI.
   * @param features the GUIController object.
   */
  void addFeatures(GUIController features);

  /**
   * Display the image on the GUI given its name.
   * @param imageNameToBeDisplayed the name of the image to be displayed
   */
  void displayImage(String imageNameToBeDisplayed);

  /**
   * Set the execution message.
   * @param currentMessage the message to be displayed
   */
  void setExecutionMessage(String currentMessage);

  /**
   * Display the histogram for the currently displayed image.
   * @param imageName the name of the current image
   */
  void displayHistogram(String imageName);

  /**
   * Display a dropdown menu for selecting a filter operation.
   * @return the name of the chosen filter operation
   */
  String filter();

  /**
   * Display a dropdown menu for selecting a color transformation.
   * @return the name of the chosen color transformation
   */
  String colorTransformation();
}
