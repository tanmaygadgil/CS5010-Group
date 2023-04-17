package model;

/**
 * This interface represents a mock model used for testing purposes.
 * It extends the ModelV2 interface.
 */
public interface MockModel extends ModelV2 {

  /**
   * This is a log object keeping track of the output.
   * @return A String log
   */
  String getLog();

}
