package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputDialog extends JDialog {
  private JTextField textField;
  private JButton okButton;
  private String input;
  private boolean inputEntered;

  public InputDialog(JFrame parentFrame, String title)  {
    super(parentFrame, title, true); // use modal option for JDialog to block input to parent
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    textField = new JTextField(20);
    okButton = new JButton("OK");
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

  public String getInput(){
    return input;
  }

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
