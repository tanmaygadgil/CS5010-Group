package model;

public interface ViewModel {

  /**
   * A method for the view to get image data from the model.
   * @param imagename the name of the image in the model hashmap
   * @return The integer array for the hashmap.
   */
  int[][][] getImage(String imagename);
}
