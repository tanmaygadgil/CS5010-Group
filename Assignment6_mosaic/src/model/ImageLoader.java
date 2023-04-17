package model;

import java.io.IOException;
import java.io.InputStream;

/**
 * Defines and image loader interface for a specific format.
 */
public interface ImageLoader {

  /**
   * The load logic that loads a file of a specific format.
   * @param in inputstream object
   * @return an integer array representing the image (channel, height, width)
   */
  int[][][] load(InputStream in) throws IOException;

}
