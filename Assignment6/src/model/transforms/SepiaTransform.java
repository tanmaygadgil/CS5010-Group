package model.transforms;

/**
 * This class represents the sepia transformation to be done on an image.
 */
public class SepiaTransform extends AbstractTransform implements ImageTransforms{

  /**
   * Defines the kernel for a sepia transform.
   */
  public SepiaTransform(){
    this.matrix = new double[][] {
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };
  }

  @Override
  public String toString(){ return "Sepia Transform";}

}
