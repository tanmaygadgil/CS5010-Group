package view;

import controller.Features;

/**
 * A basic GUI view interface.
 */
public interface IGUIView extends View {

  /**
   * Adds features that are supported by the controller.
   * @param features The features that the controller supports
   */
  void addFeatures(Features features);

  /**
   * resets the view.
   */
  void reset();

}
