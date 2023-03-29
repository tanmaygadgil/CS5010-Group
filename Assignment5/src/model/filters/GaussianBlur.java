package model.filters;

/**
 * This class represents the gaussian blur command.
 */
public class GaussianBlur extends AbstractFilter implements ImageFilter {

  /**
   * Initialize the gaussian blur to apply to images.
   */
  public GaussianBlur() {
    this.kernel = new double[][]{{1.0 / 16, 1.0 / 8, 1.0 / 16}, {1.0 / 8, 1.0 / 4, 1.0 / 8},
        {1.0 / 16, 1.0 / 8, 1.0 / 16}};

  }

  @Override
  public String toString() {
    return "GaussianBlur";
  }
}
