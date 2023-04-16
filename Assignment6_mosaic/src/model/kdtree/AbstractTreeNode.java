package model.kdtree;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a set of operations all methods every TreeNode needs.
 *
 * @param <T> type of data to hold.
 */
public abstract class AbstractTreeNode<T> implements KDTreeNode<T> {

  //since
  protected T data;
  protected int a;
  protected int b;
  protected int c;
  protected List<Point2D> p;
  protected List<Integer> on;
  protected int depth;

  /**
   * Create an abstract tree node with given parameters.
   *
   * @param data  data to held in current node.
   * @param a     x coefficient assuming the line is of the form ax + by + c = 0.
   * @param b     y coefficient assuming the line is of the form ax + by + c = 0.
   * @param c     constant coefficient assuming the line is of the form ax + by + c = 0.
   * @param p     list of all points in the initial creation of tree.
   * @param on    list of all points on the same line as this point.
   * @param depth the depth of the current node.
   */
  public AbstractTreeNode(T data, int a, int b, int c, List<Point2D> p, List<Integer> on,
      int depth) {
    this.data = data;
    this.a = a;
    this.b = b;
    this.c = c;
    this.p = p;
    this.on = on;
    this.depth = depth;
  }

  /**
   * Create an abstract tree node with given parameters.
   *
   * @param data data to be held in current node.
   * @param p    list of all points in the initial creation of tree.
   */
  public AbstractTreeNode(T data, List<Point2D> p) {
    this.data = data;
    this.p = p;
  }

  /**
   * Create an abstract tree node with given parameters.
   *
   * @param data data to be held in current node.
   */
  public AbstractTreeNode(T data) {
    this.data = data;
    this.p = new ArrayList<>();
  }

  /**
   * Create an abstract tree node.
   */
  public AbstractTreeNode() {
    //This constructor is used to create an empty leaf node.
    this.p = new ArrayList<>();
  }

  /**
   * Get the signed distance from a point to a line with coefficients a, b, c. The line is of the
   * form ax + by + c = 0
   *
   * @param p point to find signed distance from.
   * @return a negative distance if the point is to the left or below the current point or positive
   *         if the point is to the right or above the current point.
   */
  public int signedDistance(Point2D p) {
    return this.a * p.get(0) + this.b * p.get(1) + this.c;
  }
}
