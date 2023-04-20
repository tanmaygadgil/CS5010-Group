package imgeditor.model;

import imgeditor.model.kdtree.Point2D;
import imgeditor.model.kdtree.PointKDTree;
import imgeditor.model.kdtree.SetOfPoints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An image mosaicking operation that extends the AbstractImageOperations.
 * The seeds are generated using a random picking strategy.
 */
public class ImageMosaicking extends AbstractImageOperations {

  @Override
  public List<ImageData> perform(List<ImageData> imageData, String... operationArgs) {
    int numSeeds = Integer.valueOf(operationArgs[1]);
    int[][][] pixels = imageData.get(0).getPixels();
    int width = pixels.length;
    int height = pixels[0].length;

    //we might want to change this to expand better
    PickStrategy strategy = null;
    if(operationArgs[0].equals("random")) {
      strategy = new RandomUniformPick(numSeeds, width, height);
    }

    List<Point2D> seeds = strategy.generate();
    Map<Point2D, List<Point2D>> clusterMap = new HashMap<>();

    for(Point2D point : seeds) {
      clusterMap.put(point, new ArrayList<>());
    }

    SetOfPoints tree = new PointKDTree(seeds);
    Point2D point;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        point = new Point2D(i, j);
        //Add point to local cluster map
        clusterMap.get(tree.closestPoint(point)).add(point);
      }
    }

    for (List<Point2D> point2DS : clusterMap.values()) {
      int listSize = point2DS.size();
      //averages for each channel
      int[] averages = new int[pixels[0][0].length];

      //sum the red, green and blue values
      for (Point2D point2D : point2DS) {
        for (int i = 0; i < averages.length; i++) {
          averages[i] += pixels[point2D.get(0)][point2D.get(1)][i];
        }
      }

      for(int i = 0; i < averages.length; i++) {
        averages[i] = averages[i] / listSize;
      }

      for (Point2D point2D : point2DS) {
        for(int i = 0; i < averages.length; i++) {
          pixels[point2D.get(0)][point2D.get(1)][i] = averages[i];
        }
      }


    }
    List<ImageData> resultImageData = new ArrayList<>();
    resultImageData.add(new ImageData(pixels));
    return resultImageData;
  }
}
