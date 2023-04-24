package imgeditor.model.kdtree;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the group nodes of the tree. Group nodes hold data such as the coefficients
 * of a line, a point, a list of all of the points in the initial tree, all the points on the same
 * line, left and right children and the depth of the current node.
 *
 * @param <T> Type of data to hold.
 */
public class GroupNode<T> extends AbstractTreeNode<T> {

  protected KDTreeNode<T> left;
  protected KDTreeNode<T> right;

  /**
   * Create a group node with given parameters.
   *
   * @param a     x coefficient assuming the line is of the form ax + by + c = 0.
   * @param b     y coefficient assuming the line is of the form ax + by + c = 0.
   * @param c     constant coefficient assuming the line is of the form ax + by + c = 0.
   * @param left  the left child of the current node.
   * @param right the right child of the current node.
   * @param p     a list of all points in the initial creation of the tree.
   * @param on    list of all points on the same line as this point.
   * @param depth the depth of the current node.
   */
  public GroupNode(int a, int b, int c, KDTreeNode<T> left, KDTreeNode<T> right, List<Point2D> p,
      List<Integer> on, int depth) {
    super((T) new Point2D(p.get(on.get(on.size() / 2)).get(0), p.get(on.get(on.size() / 2)).get(1)),
        a, b, c, p, on, depth);
    this.left = left;
    this.right = right;
  }

  /**
   * Create a new group node given the parameters.
   *
   * @param data data to be held by this node.
   * @param p    a list of all points in the initial tree.
   */
  public GroupNode(T data, List<Point2D> p) {
    super(data, p);
    this.left = new LeafNode<>();
    this.right = new LeafNode<>();
  }

  /**
   * Create a new group node given the parameters.
   *
   * @param data data to be held by this node.
   */
  public GroupNode(T data) {
    super(data);
    this.left = new LeafNode<>();
    this.right = new LeafNode<>();
  }

  /**
   * Create a new group node.
   */
  public GroupNode() {
    super();
    this.left = new LeafNode<>();
    this.right = new LeafNode<>();
  }

  @Override
  public List<Point2D> allPointsInCircle(Point2D center, double radius) {
    if (this.data == null) {
      return new ArrayList<>();
    }

    int sd = this.signedDistance(center);
    List<Point2D> points = new ArrayList<Point2D>();

    if (sd <= 0) {
      points = this.left.allPointsInCircle(center, radius);
      if (Math.abs(sd) < radius) {
        List<Point2D> l = this.right.allPointsInCircle(center, radius);
        points.addAll(l);
      }
    } else {
      points = this.right.allPointsInCircle(center, radius);
      if (Math.abs(sd) < radius) {
        List<Point2D> l = this.left.allPointsInCircle(center, radius);
        points.addAll(l);
      }
    }

    if (on == null) {
      if (center.distance((Point2D) this.data) <= radius) {
        points.add((Point2D) this.data);
      }
    } else {
      for (Integer i : on) {
        if (p.get(i).distance(center) <= radius) {
          points.add(p.get(i));
        }
      }
    }
    return points;
  }

  @Override
  public Point2D closestPoint(Point2D q) {
    if (this.data == null) {
      return null;
    }
    int sd = this.signedDistance(q);
    Point2D p;
    if (sd < 0) { //go left
      Point2D center = q;
      double radius = Double.MAX_VALUE;
      p = this.left.closestPoint(q);

      if (p != null) {
        radius = p.distance(q);
      }

      Point2D pp;
      if (p == null || Math.abs(sd) <= radius) {
        pp = closestPointInList(q, getPoints(this.on));
        if (pp == null) {
          pp = this.right.closestPoint(q);
        }
        if (pp.distance(q) < radius) {
          p = pp;
          radius = p.distance(q);
        }
      }

      if (Math.abs(sd) <= radius) {
        List<Point2D> list = this.right.allPointsInCircle(center, radius);
        pp = closestPointInList(q, list);
        if (pp != null) {
          if (p.distance(q) > q.distance(pp)) {
            p = pp;
          }
        }
      }
    } else { //go right
      Point2D center = q;
      double radius = Double.MAX_VALUE;
      p = this.right.closestPoint(q);

      if (p != null) {
        radius = p.distance(q);
      }

      Point2D pp;
      if (p == null || Math.abs(sd) <= radius) {
        pp = closestPointInList(q, getPoints(this.on));
        if (pp == null) {
          pp = this.left.closestPoint(q);
        }
        if (pp.distance(q) < radius) {
          p = pp;
          radius = p.distance(q);
        }
      }

      if (Math.abs(sd) <= radius) {
        List<Point2D> list = this.left.allPointsInCircle(center, radius);
        pp = closestPointInList(q, list);
        if (pp != null) {
          if (p.distance(q) > q.distance(pp)) {
            p = pp;
          }
        }
      }
    }

    return p;
  }

  private List<Point2D> getPoints(List<Integer> on) {
    List<Point2D> list = new ArrayList<>();
    if (on == null) {
      list.add((Point2D) this.data);
    } else {
      for (Integer i : on) {
        list.add(this.p.get(i));
      }
    }

    return list;
  }

  private Point2D closestPointInList(Point2D q, List<Point2D> l) {
    if (l.size() > 0) {
      double maxDist = Double.MAX_VALUE;
      Point2D pp = l.get(0); //closest point to Q on dividing line
      for (Point2D point : l) {
        if (point.distance(q) < maxDist) {
          pp = point;
          maxDist = point.distance(q);
        }
      }
      return pp;
    } else {
      return null;
    }
  }

  @Override
  public KDTreeNode<T> addChild(Point2D point, int depth) {
    if (depth == 0) {
      this.p.add(point);
    }
    if (this.data == null && depth == 0) {
      return (KDTreeNode<T>) new GroupNode<Point2D>(point, this.p);
    }
    Point2D temp = (Point2D) this.data;
    if (point.get(depth % 2) <= temp.get(depth % 2)) {
      this.left = this.left.addChild(point, depth + 1);
      return this;
    } else {
      this.right = this.right.addChild(point, depth + 1);
      return this;
    }
  }

  @Override
  public List<T> toList() {
    if (this.p == null) {
      return new ArrayList<>();
    } else {
      return (List<T>) this.p;
    }
  }

}
