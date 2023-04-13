package model;

/**
 * An implementation of the view model.
 */
public class ViewModelImpl implements ViewModel {

  ModelV2 model;

  public ViewModelImpl(ModelV2 model) {
    this.model = model;
  }

  @Override
  public int[][][] getImage(String imageName) {

    return this.model.getImage(imageName);
  }

  @Override
  public float[][] getHistogramValues(String imagename) {
    return this.model.getHistogramValues(imagename);
  }
}
