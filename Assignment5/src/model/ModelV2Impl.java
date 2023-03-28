package model;

import model.Filters.ImageFilter;
import model.operations.ImageOperations;

public class ModelV2Impl extends ModelImpl implements ModelV2 {

  public ModelV2Impl(){
    super();
  }
  @Override
  public void callFilter(ImageFilter filter, String imagename, String destname) {
    int[][][] image = this.imageMap.get(imagename);
    if (image != null) {
      image = filter.filter(image);
      this.imageMap.put(destname, image);
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

}
