package model;

import java.util.HashMap;

public class ViewModelImpl implements ViewModel {
  ModelV2 model;
  public ViewModelImpl(ModelV2 model){
    this.model = model;
  }

  @Override
  public int[][][] getImage(String imageName) {

    return this.model.getImage(imageName);
//    if (!imageMap.containsKey(imageName)) {
//      throw new IllegalArgumentException("Image name not found in hashmap");
//    } else {
//      return imageMap.get(imageName);
//    }
  }

  @Override
  public float[][] getHistogramValues(String imagename) {
    return this.model.getHistogramValues(imagename);
  }
}
