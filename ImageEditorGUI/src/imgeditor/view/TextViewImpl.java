package imgeditor.view;

import java.io.IOException;

/**
 * This class represents a Text View implementation.
 * All the output of this view will be appended in the Appendable object.
 * If System.out is specified as the input to the constructor, all the logged messages will
 * be displayed on the console as well.
 */
public class TextViewImpl implements TextView {

  private final Appendable out;

  /**
   * Construct a TextViewImpl object.
   * @param out the Appendable object in which the messages are to be appended
   */
  public TextViewImpl(Appendable out) {
    this.out = out;
  }

  @Override
  public void showSuccessMessage(String message) {
    try {
      out.append(message);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void showErrorMessage(String message) {
    try {
      out.append(message);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
