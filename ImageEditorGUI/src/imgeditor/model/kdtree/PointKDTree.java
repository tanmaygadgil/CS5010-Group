package imgeditor.model.kdtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class represents a KDTree made up of points in 2 dimensional space. This class creates a KD
 * tree based on a list o points given to the tree. Any additional points added after initial
 * construction only adds points and does not change the structure of the initial tree. This tree
 * allows for searching for points within a given circle as well as the closest point in this tree
 * given a point.
 */
public class PointKDTree implements SetOfPoints {

  private KDTreeNode<Point2D> root;

  /**
   * Creates a PointKDTree recursively.
   *
   * @param p list of points to create tree from.
   * @throws IllegalArgumentException thrown when the list provided is null.
   */
  public PointKDTree(List<Point2D> p) throws IllegalArgumentException {
    if (p == null) {
      throw new IllegalArgumentException("No points to create tree from.");
    }

    if (p.size() == 0) {
      this.root = new GroupNode<>();
    } else {
      List<Integer> px = createIndexList(p);
      List<Integer> py = createIndexList(p);

      Collections.sort(px, new Comparator<Integer>() {
        public int compare(Integer o1, Integer o2) {
          return Integer.compare(p.get(o1).get(0), p.get(o2).get(0));
        }
      });
      Collections.sort(py, new Comparator<Integer>() {
        public int compare(Integer o1, Integer o2) {
          return Integer.compare(p.get(o1).get(0), p.get(o2).get(0));
        }
      });

      int threshold = 1;

      this.root = recursiveBuildTree(p, px, py, threshold, 0);
    }
  }

  private List createIndexList(List p) {
    List<Integer> l = new ArrayList<>();
    for (int i = 0; i < p.size(); i++) {
      l.add(i);
    }
    return l;
  }

  private KDTreeNode<Point2D> recursiveBuildTree(List<Point2D> p, List<Integer> px,
      List<Integer> py, int threshold, int depth) {
    if (px.size() == threshold) {
      return new GroupNode<>(p.get(px.get(0)), p);
    } else if (px.size() == 0) {
      return new LeafNode<>();
    }

    int a;
    int b;
    int c;

    if (depth % 2 == 0) {
      int median = p.get(px.get((px.size() - 1) / 2)).get(0);
      a = 1;
      b = 0;
      c = -median;
    } else {
      int median = p.get(py.get((py.size() - 1) / 2)).get(1);
      a = 0;
      b = 1;
      c = -median;
    }

    List<Integer> pxBefore = new ArrayList<>();
    List<Integer> pyBefore = new ArrayList<>();
    List<Integer> on = new ArrayList<>();
    List<Integer> pxAfter = new ArrayList<>();
    List<Integer> pyAfter = new ArrayList<>();

    for (int i : px) {
      int sd = signedDistance(p.get(i), a, b, c);
      if (sd < 0) {
        pxBefore.add(i);
      } else if (sd > 0) {
        pxAfter.add(i);
      } else {
        on.add(i);
      }
    }

    for (int i : py) {
      int sd = signedDistance(p.get(i), a, b, c);
      if (sd < 0) {
        pyBefore.add(i);
      } else if (sd > 0) {
        pyAfter.add(i);
      }
    }

    KDTreeNode<Point2D> left = recursiveBuildTree(p, pxBefore, pyBefore, threshold, depth + 1);
    KDTreeNode<Point2D> right = recursiveBuildTree(p, pxAfter, pyAfter, threshold, depth + 1);

    return new GroupNode<Point2D>(a, b, c, left, right, p, on, depth);
  }

  private int signedDistance(Point2D p, int a, int b, int c) {
    return a * p.get(0) + b * p.get(1) + c;
  }

  @Override
  public void add(Point2D point) {
    int depth = 0;
    this.root = this.root.addChild(point, depth);
  }

  @Override
  public List<Point2D> getPoints() {
    List<Point2D> result = this.root.toList();
    return result;
  }

  @Override
  public List<Point2D> allPointsWithinCircle(Point2D center, double radius) {
    if (center == null) {
      return new ArrayList<>();
    }
    List<Point2D> list;
    list = this.root.allPointsInCircle(center, radius);
    return list;
  }

  @Override
  public Point2D closestPoint(Point2D point) {
    if (point == null) {
      return null;
    }
    return this.root.closestPoint(point);
  }
}
