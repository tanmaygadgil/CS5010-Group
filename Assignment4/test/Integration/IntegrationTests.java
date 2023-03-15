package Integration;

import static org.junit.Assert.assertEquals;

import Model.ImageUtil;
import Controller.ControllerScriptFile;
import Controller.Controller;
import Model.Model;
import View.View;
import Model.ModelPPM;
import View.TextInputView;
import View.ViewScriptFile;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class IntegrationTests {

  private void save(String filePath, int[][][] image) throws IOException {
    FileOutputStream fout = new FileOutputStream(filePath);

    if(image.length == 3) {
      fout.write("P3\n".getBytes());
    } else if (image.length == 1) {
      fout.write("P2\n".getBytes());
    }

    int width = image[0][0].length;
    int height = image[0].length;

    fout.write(String.format("%d %d\n255\n", width, height).getBytes());

    for (int i = 0; i < height; i++) { //rows
      for (int j = 0; j < width; j++) { //cols
        for(int k = 0; k < image.length; k++) {
          fout.write((new Integer(image[k][i][j]).toString() + "\n").getBytes());
        }
      }
    }
  }

  private void createIncMat() throws IOException {
    int[][][] image = new int[3][3][4];
    int width = image[0][0].length;
    int height = image[0].length;

    for(int i = 0; i < height; i++) {
      for(int j = 1; j <= width; j++) {
        for(int k = 0; k < image.length; k++) {
          image[k][i][j-1] = width * i + j;
        }
      }
    }

    this.save("test/Integration/incMatRGB.txt", image);
  }

  @Test
  public void testScriptVertical() throws IOException {
    //vertical flip the image and save it
    Model m = new ModelPPM();
    View v = new TextInputView("script");
    Controller c = new ControllerScriptFile(m, v);

    String input = "test/scripts/flipSimpleMat.txt";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    c.run();

    //create the expected mat
    int[][][] image = new int[3][3][4];
    //generating correct answer
    for(int i = 3; i >= 1; i--) {//rows
      for(int j = 1; j <= 4; j++) {//cols
        for(int k = 0; k < 3; k++) {//channels
          image[k][i-1][j-1] = 4 * (3-i) + j;
        }
        System.out.println(4*(3-i)+j);
      }
    }

    int[][][] vertFlip = ImageUtil.readPPM("test/Integration/incmat-vertical.txt");
    assertEquals(image, vertFlip);
  }

  public void testSimpleCommandLine() throws IOException{
    Model m = new ModelPPM();
    View v = new TextInputView("command");
    Controller c = new ControllerScriptFile(m, v);

    String input = "test/scripts/script1.txt";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    c.run();
  }

}
