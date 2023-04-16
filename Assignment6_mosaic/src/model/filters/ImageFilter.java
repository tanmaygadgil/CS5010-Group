package model.filters;

/**
 * An interface that implements a convolution style image filter.
 */
public interface ImageFilter {

  /**
   * Logic encapsulating the Image filter operation.
   * @param image the image to be filtered
   * @return the filtered image.
   */
  int[][][] filter(int[][][] image);

}
