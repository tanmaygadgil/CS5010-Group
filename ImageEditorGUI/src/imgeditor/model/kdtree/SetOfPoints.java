package imgeditor.model.kdtree;

import java.util.List;

/**
 * This interface represents a set of points as well as possible operations to be done on a set of
 * points.
 */
public interface SetOfPoints {

  /**
   * Take a single point and add it to the current set of points.
   *
   * @param point point to add to current set.
   */
  void add(Point2D point);

  /**
   * Return a list of all points in the current set.
   *
   * @return List of all points in current set.
   */
  List getPoints();


  /**
   * Return a list of all points within a given circle.
   *
   * @param center of circle given.
   * @param radius of circle given.
   * @return Returns all points within the circle given.
   */
  List allPointsWithinCircle(Point2D center, double radius);

  /**
   * Return the closest point in the set to the given point. If no such point exists, return null.
   *
   * @param point single query point.
   * @return the closest point to point, null if no such point exists.
   */
  Point2D closestPoint(Point2D point);
}
