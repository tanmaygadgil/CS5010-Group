package imgeditor.model.kdtree;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the leaf nodes of the tree. The leaf nodes can hold data, but does not have
 * children.
 *
 * @param <T> type of data to hold.
 */
public class LeafNode<T> extends AbstractTreeNode<T> {

  public LeafNode() {
    super();
  }

  @Override
  public KDTreeNode<T> addChild(Point2D point, int depth) {
    if (this.data == null) {
      List<Point2D> p = new ArrayList<>();
      p.add(point);
      return (KDTreeNode<T>) new GroupNode<Point2D>(point, this.p);
    }
    KDTreeNode<T> temp = new GroupNode<>(this.data);
    return temp.addChild(point, depth + 1);
  }

  @Override
  public List<T> toList() {
    return new ArrayList<>();
  }

  @Override
  public int signedDistance(Point2D p) {
    return 0;
  }

  @Override
  public List allPointsInCircle(Point2D center, double radius) {
    List<Point2D> list = new ArrayList<>();
    if (this.data != null) {
      Point2D p = (Point2D) this.data;
      if (p.distance(center) <= radius) {
        list.add(p);
      }
    }

    return list;
  }

  @Override
  public Point2D closestPoint(Point2D q) {
    return (Point2D) this.data;
  }

}
