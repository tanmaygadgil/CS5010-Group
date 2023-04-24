package imgeditor.model;

import java.util.List;

/**
 * defines an interface to generate points.
 */
public interface PickStrategy<T> {


  /**
   * Generates the list of points.
   *
   * @return a list oof points with a generic type
   */
  List<T> generate();
}
