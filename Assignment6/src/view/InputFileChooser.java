package view;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Used to select the file to be loaded or saved.
 */
public class InputFileChooser extends JFileChooser {

  /**
   * Initializes the file chooser.
   *
   * @param title the title for the file chooser
   */
  public InputFileChooser(String title) {
    super(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "ASCII PPM and Conventional Images", "jpg",
        "gif", "ppm", "bmp", "png");
    this.setFileFilter(filter);
    this.setDialogTitle(title);
  }

  /**
   * Gets the input of the file.
   *
   * @return the entered filepath
   */
  public String getInput() {
    int retValue = this.showOpenDialog(InputFileChooser.this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = this.getSelectedFile();
      return f.getAbsolutePath();
    }
    return null;
  }

}
