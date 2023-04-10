package view;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class InputFileChooser extends JFileChooser {

  public InputFileChooser(String title){
    super(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg",
        "gif");
    this.setFileFilter(filter);
    this.setDialogTitle(title);
  }

  public String getInput(){
    int retValue = this.showOpenDialog(InputFileChooser.this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = this.getSelectedFile();
      return f.getAbsolutePath();
    }
    return null;
  }

}
