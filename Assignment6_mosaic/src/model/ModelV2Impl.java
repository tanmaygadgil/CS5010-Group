package model;

import model.kdtree.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import model.filters.ImageFilter;
import model.kdtree.PointKDTree;
import model.kdtree.SetOfPoints;
import model.operations.ImageOperations;
import model.transforms.ImageTransforms;

/**
 * This class extends the ModelImpl class, and provides all functionality from the previous model as
 * well as the capability to filter, transform and call an operation on an image.
 */
public class ModelV2Impl extends ModelImpl implements ModelV2 {

  public ModelV2Impl() {
    super();
  }

  @Override
  public void callFilter(ImageFilter filter, String imageName, String destName) {
    int[][][] image = this.imageMap.get(imageName);
    if (image != null) {
      image = filter.filter(image);
      this.imageMap.put(destName, image);
    } else {
      throw new IllegalStateException("Image does not exist in map");
    }

  }

  @Override
  public void callTransform(ImageTransforms transform, String imagename, String destname) {
    int[][][] image = this.imageMap.get(imagename);
    if (image != null) {
      image = transform.transform(image);
      this.imageMap.put(destname, image);
    } else {
      throw new IllegalStateException("Image does not exist in map");
    }
  }

  @Override
  public void callOperation(ImageOperations ops, String imagename, String destname) {
    int[][][] image = this.imageMap.get(imagename);
    if (image != null) {
      image = ops.operate(image);
      this.imageMap.put(destname, image);
    } else {
      throw new IllegalStateException("Image does not exist in map");
    }

  }

  public void callMosaic(int numSeeds, String imageName, String destName){
    int[][][] image = imageMap.get(imageName);
    Map<Point2D, List<Point2D>> map = new HashMap<>();
    int height = image[0].length;
    int width = image[0][0].length;

    Random r = new Random();
    List<Point2D> list = new ArrayList<>();
    //set random seeds
    for (int i = 0; i < numSeeds; i++) {
      Point2D point = new Point2D(r.nextInt(width), r.nextInt(height));
      list.add(point);
      map.put(point, new ArrayList<>());
    }
    SetOfPoints tree = new PointKDTree(list);

    Point2D point;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        point = new Point2D(i, j);
        map.get(tree.closestPoint(point)).add(point);
      }
    }

    for (List<Point2D> point2DS : map.values()) {
      int listSize = point2DS.size();

      int[] averages = new int[image.length];

      //sum the red, green and blue values
      for (Point2D point2D : point2DS) {
        for(int i = 0; i < averages.length; i++) {
          averages[i] += image[i][point2D.get(1)][point2D.get(0)];
        }
      }

      //divide to get average
      for(int i = 0; i < averages.length; i++) {
        averages[i] = averages[i] / listSize;
      }

      //set everything to the average
      for (Point2D point2D : point2DS) {
        for(int i = 0; i < averages.length; i++) {
          image[i][point2D.get(1)][point2D.get(0)] = averages[i];
        }
      }
    }

    imageMap.put(destName, image);
  }
}
