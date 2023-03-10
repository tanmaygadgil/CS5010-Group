package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Controller {

  /**
   * A run method that works in a loop to execute inputs from the view.
   */
  void run() throws IOException;

}
