package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SimpleDialogBox extends JFrame {
  private JTextField textField;
  private JButton okButton;
  private String input;
  private boolean inputEntered;

  public SimpleDialogBox(String title) {
    super(title);
    inputEntered = false;
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    textField = new JTextField(20);
    okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        input = textField.getText();
        inputEntered = true;
      }
    });
    JPanel panel = new JPanel();
    panel.add(textField);
    panel.add(okButton);
    add(panel, BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(null); // center the frame on the screen
    setResizable(false); // prevent resizing the frame
    setVisible(true);
  }

  public String getInput() {
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
    dispose();
    return input;
  }
}
