package model;

public interface ViewModel {

  /**
   * A method for the view to get image data from the model.
   *
   * @param imageName the name of the image in the model hashmap
   * @return The integer array for the hashmap.
   */
  int[][][] getImage(String imageName);

  /**
   * Computes the histogram values of an image. If the image is greyscale only the intensity values
   * are sent and not the RGB.
   *
   * @param imageName the name of the image to be processed
   * @return the array of histogram arrays
   */
  float[][] getHistogramValues(String imageName);
}
