package model.transforms;

/**
 * This class represents the greyscale transformations to be done on images.
 */
public class GreyscaleTransform extends AbstractTransform implements ImageTransforms {

  /**
   * Defines the kernel for a greyscale transform.
   */
  public GreyscaleTransform() {
    this.matrix = new double[][]{
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };
  }

  @Override
  public int[][][] transform(int[][][] image) {

    int[][][] newimage;
    //Check if image is already greyscale
    if (image.length == 1) {
      System.out.println("Doing this");
      return image;
    } else {
      newimage = super.transform(image);
    }

    //All greyscale images are represented internally as single dimension images
    int width = newimage[0][0].length;
    int height = newimage[0].length;
    int[][][] returnImage = new int[1][height][width];
    returnImage[0] = newimage[0];
    return returnImage;
  }

  @Override
  public String toString() {
    return "Greyscale Transform";
  }
}
