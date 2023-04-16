package model;

import model.filters.ImageFilter;
import model.operations.ImageOperations;
import model.transforms.ImageTransforms;

/**
 * An enhanced model package which can call encapsulated operations like
 * 1. Colour Transformations
 * 2. Filters
 * 3. Other operations
 */
public interface ModelV2 extends Model {

  void callFilter(ImageFilter filter, String imagename, String destname);

  void callTransform(ImageTransforms transform, String imagename, String destname);

  void callOperation(ImageOperations ops, String imagename, String destname);

  void callMosaic(int numSeeds, String imageName, String destName);
}
