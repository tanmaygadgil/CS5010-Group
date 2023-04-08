package model;

import java.util.HashMap;

public class ViewModelImpl implements ViewModel {
  final HashMap<String, int[][][]> imageMap;

  public ViewModelImpl(){
    this.imageMap = new HashMap<>();
  }

  @Override
  public int[][][] getImage(String imageName) {
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Image name not found in hashmap");
    } else {
      return imageMap.get(imageName);
    }
  }
}
