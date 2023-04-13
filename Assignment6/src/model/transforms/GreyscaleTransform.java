package model.transforms;

/**
 * This class represents the greyscale transformations to be done on images.
 */
public class GreyscaleTransform extends AbstractTransform implements ImageTransforms {

  public GreyscaleTransform(){
    this.matrix = new double[][]{
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };
  }

  @Override
  public String toString() {
    return "Greyscale Transform";
  }
}
