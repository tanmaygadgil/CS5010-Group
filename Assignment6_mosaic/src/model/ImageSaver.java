package model;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An inteface that defines a stndard image saver class.
 */
public interface ImageSaver {

  /**
   * The operation that defines the save funtion.
   * @param image the image to be saved
   * @param out the outputstream.
   */
  void save(int[][][] image, OutputStream out) throws IOException;

}
