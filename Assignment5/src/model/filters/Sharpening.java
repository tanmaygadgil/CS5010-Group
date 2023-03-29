package model.filters;

/**
 * This class represents a sharpening filter.
 */
public class Sharpening extends AbstractFilter {

  /**
   * Initialize the sharpening kernel.
   */
  public Sharpening() {
    this.kernel = new double[][]{{-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}};
  }

  @Override
  public String toString() {
    return "Sharpening";
  }
}
