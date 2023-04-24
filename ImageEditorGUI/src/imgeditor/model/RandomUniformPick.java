package imgeditor.model;

import imgeditor.model.kdtree.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Picks 2d points using a random uniform distribution in a rectangular region.
 */
public class RandomUniformPick implements PickStrategy {

  private Integer seeds;
  private Integer height;
  private Integer width;

  /**
   * Creates a constructor for the random uniform pick. The values are picked using a random uniform
   * distribution between the bounds of height and width.
   *
   * @param seeds  Number of seeds to be picked
   * @param height the height of the image
   * @param width  the width of the image
   */
  public RandomUniformPick(int seeds, int height, int width) {
    this.seeds = seeds;
    this.height = height;
    this.width = width;
  }

  @Override
  public List<Point2D> generate() {
    Random r = new Random();

    Set<Point2D> pointSet = new HashSet<>();

    int i = 0;

    while (i < this.seeds) {
      Point2D point = new Point2D(r.nextInt(width), r.nextInt(height));
      pointSet.add(point);
      i = pointSet.size();
    }

    return new ArrayList<>(pointSet);
  }

}
