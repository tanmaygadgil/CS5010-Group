package imgeditor.model;

import imgeditor.model.kdtree.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Picks the four corners in a rectangular region.
 */
public class FourCornersPick implements PickStrategy {

  private Integer height;
  private Integer width;

  /**
   * Creates an instance of four corners pick.
   *
   * @param height height of the image.
   * @param width  width of the image.
   */
  public FourCornersPick(int height, int width) {
    this.height = height;
    this.width = width;
  }

  @Override
  public List generate() {
    Set<Point2D> pointSet = new HashSet<>();

    pointSet.add(new Point2D(0, 0));
    pointSet.add(new Point2D(width, 0));
    pointSet.add(new Point2D(0, height));
    pointSet.add(new Point2D(width, height));

    return new ArrayList<>(pointSet);
  }
}
