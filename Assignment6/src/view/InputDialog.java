package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A dialog box to obtain additional inputs.
 */
public class InputDialog extends JDialog {

  private JTextField textField;
  private String input;
  private boolean inputEntered;

  /**
   * Intitializes the dialog box.
   *
   * @param parentFrame the frame that this box is attached to
   * @param title       The title of the dialog box
   */
  public InputDialog(JFrame parentFrame, String title) {
    super(parentFrame, title, true); // use modal option for JDialog to block input to parent
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    textField = new JTextField(20);
    JButton okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        input = textField.getText();
        inputEntered = true;
        dispose();
      }
    });
    JPanel panel = new JPanel();
    panel.add(textField);
    panel.add(okButton);
    add(panel, BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(parentFrame); // center the dialog on the parent frame
    setResizable(false); // prevent resizing the dialog
  }

  /**
   * Simple method to return the input asynchronously.
   *
   * @return the input received
   */
  public String getInput() {
    return input;
  }

  /**
   * Method to force the program to pause until an input is given.
   *
   * @return the input received
   */
  public String getInputAndWait() {
    inputEntered = false;
    setVisible(true);
    while (!inputEntered) {
      try {
        Thread.sleep(10); // wait a little before checking again
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    return input;
  }
}
