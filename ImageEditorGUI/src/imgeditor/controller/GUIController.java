package imgeditor.controller;

/**
 * This interface represents a Controller for the Image Editor program with a GUI.
 * It provides methods for setting up the view and for each operation that is supported.
 * It contains all the features that will be provided by the program.
 */
public interface GUIController {

  /**
   * Initializes all the listeners in the view.
   * An object of GUIController is passed to the view.
   */
  void setView();

  /**
   * Load an image from the file system. The supported image types to be loaded are
   * dependent on the implementation.
   * @return true if the image was loaded successfully, false otherwise.
   */
  boolean load();

  /**
   * Save an image to the file system. The supported image types that can be saved are dependent
   * on the implementation.
   * @return true if the file was saved successfully, false otherwise.
   */
  boolean save();

  /**
   * Perform horizontal flip on the image that is being currently displayed on the GUI.
   */
  void horizontalFlip();

  /**
   * Perform vertical flip on the image that is being currently displayed on the GUI.
   */
  void verticalFlip();

  /**
   * Perform dithering on the image that is being currently displayed on the GUI.
   */
  void dither();

  /**
   * Adjust the brightness of the image that is being currently displayed on the GUI.
   */
  void adjustBrightness();

  /**
   * Perform greyscale on the image that is being currently displayed on the GUI.
   */
  void greyscale();

  /**
   * Split the image into three components.
   * The image of each component will be saved on disk.
   */
  void rgbSplit();

  /**
   * Combine three images into a single RGB image.
   * The three input images will be loaded from disk.
   */
  void rgbCombine();

  /**
   * Apply a filter to the image that is being currently displayed on the GUI.
   * The types of filters supported are dependent on the implementation.
   */
  void filter();

  /**
   * Apply a color transformation on the image that is being currently displayed on the GUI.
   * The types of color transformation supported are dependent on the implementation.
   */
  void colorTransformation();

  /**
   * Apply a mosaic effect on the image that is being currently displayed on the GUI.
   */
  void mosaic();
}
