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
   *
   * @param seeds
   * @param height
   * @param width
   */
  public RandomUniformPick(int seeds, int height, int width){
    this.seeds = seeds;
    this.height = height;
    this.width = width;
  }
  public RandomUniformPick(int seeds){
    this.seeds = seeds;
    this.height = null;
    this.width = null;
  }

  @Override
  public void setParameters(ArrayList<String> params){

    this.height = Integer.parseInt(params.get(0));
    this.width = Integer.parseInt(params.get(0));
  }
  @Override
  public List<Point2D> generate() {
    Random r = new Random();

    Set<Point2D> pointSet = new HashSet<>();

    int i = 0;

    while(i < this.seeds){
      Point2D point = new Point2D(r.nextInt(width), r.nextInt(height));
      pointSet.add(point);
      i = pointSet.size();
    }
    List<Point2D> list = new ArrayList<>(pointSet);

    return list;
  }

}
