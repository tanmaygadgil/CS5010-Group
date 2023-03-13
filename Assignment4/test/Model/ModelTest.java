package Model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {

  Model model;

  @Before
  public void setUp() {
    this.model = new ModelPPM();
  }

  @Test
  public void testSave() throws IOException {
    Model model = new ModelPPM();

    model.load("test/Model/testImage.ppm", "testImage");
    model.save("test/Model/testSaveImage.ppm", "testImage");
    int[][][] imageSaved = ImageUtil.readPPM("test/Model/testSaveImage.ppm");
    int[][][] imageOriginal = ImageUtil.readPPM("test/Model/testImage.ppm");

    assertEquals(imageOriginal.length, imageSaved.length);
    assertEquals(imageOriginal[0].length, imageSaved[0].length);
    assertEquals(imageOriginal[0][0].length, imageSaved[0][0].length);

    for (int i = 0; i < imageSaved.length; i++) {
      for (int j = 0; j < imageSaved[0].length; j++) {
        for (int k = 0; k < imageSaved[0][0].length; k++) {
          assertEquals(imageOriginal[i][j][k], imageSaved[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testBrighten() throws IOException {
    String path = "test/Model/testImage.ppm";
    int[][][] image = ImageUtil.readPPM(path);
    int increment = 10;

    model.load(path, "testImage");
    model.brighten(increment, "testImage", "testImageBrighten");
    model.save("test/Model/testImageBrighten.ppm", "testImageBrighten");
    int[][][] imageBrighten = ImageUtil.readPPM("test/Model/testImageBrighten.ppm");

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        for (int k = 0; k < image[0][0].length; k++) {
          assertEquals(imageBrighten[i][j][k] - image[i][j][k], 10);
        }
      }
    }
  }

  @Test
  public void testFlipVertical() throws IOException {
    String path = "test/Model/testImageAscendingRows.ppm";
    int[][][] image = new int[3][3][4];
    for (int i = 3; i >= 1; i--) {
      for (int j = 3; j >= 1; j--) {
        for (int k = 4; k >= 1; k--) {
          image[3 - i][3 - j][4 - k] = j;
        }
      }
    }

    model.load(path, "testImageAscendingRows");
    model.flip(Axes.VERTICAL, "testImageAscendingRows",
        "testImageAscendingRowsVertFlip");
    model.save("test/Model/testImageAscendingRowsVertFlip.ppm",
        "testImageAscendingRowsVertFlip");
    int[][][] imageVert = ImageUtil.readPPM("test/Model/testImageVert.ppm");

    assertEquals(imageVert, image);
  }

  @Test
  public void testFlipHorizontal() throws IOException {
    String path = "test/Model/testImageAscendingCols.ppm";
    int[][][] image = new int[3][3][4];
    for (int i = 3; i >= 1; i--) {
      for (int j = 3; j >= 1; j--) {
        for (int k = 4; k >= 1; k--) {
          image[3 - i][3 - j][4 - k] = k;
        }
      }
    }

    model.load(path, "testImageAscendingCols");
    model.flip(Axes.HORIZONTAL, "testImageAscendingCols",
        "testImageAscendingColsHoriFlip");
    model.save("test/Model/testImageAscendingColsHoriFlip.ppm",
        "testImageAscendingColsHoriFlip");
    int[][][] imageHori = ImageUtil.readPPM("test/Model/testImageAscendingColsHoriFlip.ppm");

    assertEquals(imageHori, image);
  }



}
