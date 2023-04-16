package model.kdtree;
import java.util.List;

/**
 * This interface represents a KDTree node and all methods functions it should provide.
 *
 * @param <T> type of data to hold.
 */
public interface KDTreeNode<T> {

  /**
   * Add the given point as a child to the node in this tree. If the current node does not contain a
   * point, then a new node with the point will be returned.
   *
   * @param point point to add ot the current tree.
   * @param depth depth of the current node, starting at zero.
   * @return the resulting tree starting at this node.
   */
  KDTreeNode<T> addChild(Point2D point, int depth);

  /**
   * Get the signed distance from this node to the given point.
   *
   * @param p point to find signed distance from.
   * @return the signed distance from the point p given.
   */
  int signedDistance(Point2D p);

  /**
   * Find all points within this tree for a given circle centered at center with given radius.
   *
   * @param center center point of the circle to find points in.
   * @param radius radius of circle to find points in.
   * @return a list of the points within the circle.
   */
  List allPointsInCircle(Point2D center, double radius);

  /**
   * Return the point in the tree which is closest to the query point.
   *
   * @param q query point to find closest point.
   * @return
   */
  Point2D closestPoint(Point2D q);

  /**
   * Convert the tree into a list.
   *
   * @return the resulting list
   */
  List<T> toList();

}
