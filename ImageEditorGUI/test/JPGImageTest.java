import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.imageio.ImageIO;

import imgeditor.controller.Controller;
import imgeditor.controller.ImageEditorController;
import imgeditor.model.ImageEditorModel;
import imgeditor.model.Model;
import imgeditor.model.ReadOnlyModel;
import imgeditor.model.ReadOnlyModelImpl;
import imgeditor.view.TextView;
import imgeditor.view.TextViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * JUnit test class for testing JPG images.
 */
public class JPGImageTest extends AbstractImageEditorTest {

  public Model model;
  public ReadOnlyModel testModel;
  public TextView textView;
  public Appendable out;

  @Before
  public void setUp() {
    model = new ImageEditorModel();
    testModel = new ReadOnlyModelImpl();
    out = new StringBuilder();
    textView = new TextViewImpl(out);
  }

  @Test
  public void testImageLoad() throws IOException {
    Reader in = new StringReader(load("res/fox.jpg", "test"));

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    int[][][] imagePixels = testModel.getImagePixels("test");

    BufferedImage image = ImageIO.read(new FileInputStream("res/fox.jpg"));

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        Color color = new Color(image.getRGB(x, y));
        assertEquals(imagePixels[x][y][0], color.getRed());
        assertEquals(imagePixels[x][y][1], color.getGreen());
        assertEquals(imagePixels[x][y][2], color.getBlue());
      }
    }

    // ThroughScript
    in = new StringReader(
            run("jpgScripts/Load.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    imagePixels = testModel.getImagePixels("script");
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        Color color = new Color(image.getRGB(x, y));
        assertEquals(imagePixels[x][y][0], color.getRed());
        assertEquals(imagePixels[x][y][1], color.getGreen());
        assertEquals(imagePixels[x][y][2], color.getBlue());
      }
    }
  }

  @Test
  public void testHorizontalFlip() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "test")
                    + horizontal("test", "testH")
                    + save(tempFilePath, "testH")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalHorizontal.jpg"));

    // ThroughScript
    in = new StringReader(
            run("jpgScripts/HorizontalFlip.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("jpgScripts/HorizontalFlip.jpg", "OI/OriginalHorizontal.jpg"));
  }

  @Test
  public void testVerticalFlip() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "test")
                    + vertical("test", "testH")
                    + save(tempFilePath, "testH")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalVertical.jpg"));

    // ThroughScript
    in = new StringReader(
            run("jpgScripts/VerticalFlip.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("jpgScripts/VerticalFlip.jpg", "OI/OriginalVertical.jpg"));

  }

  @Test
  public void testBrightness() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "test")
                    + brighten(100, "test", "testB")
                    + save(tempFilePath, "testB")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalBrightened.jpg"));

    in = new StringReader(
            load("res/fox.jpg", "test")
                    + brighten(-100, "test", "testD")
                    + save(tempFilePath, "testD")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalDarkened.jpg"));

    // ThroughScript
    in = new StringReader(
            run("jpgScripts/Brightness.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("jpgScripts/Brightened.jpg", "OI/OriginalBrightened.jpg"));
    assertTrue(compareIOFiles("jpgScripts/Darkened.jpg", "OI/OriginalDarkened.jpg"));
  }

  @Test
  public void testGreyscale() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "test")
                    + greyscale("red-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRedGS.jpg"));

    //Green
    in = new StringReader(
            load("res/fox.jpg", "test")
                    + greyscale("green-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalGreenGS.jpg"));

    //Blue
    in = new StringReader(
            load("res/fox.jpg", "test")
                    + greyscale("blue-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalBlueGS.jpg"));

    //Value
    in = new StringReader(
            load("res/fox.jpg", "test")
                    + greyscale("value-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalValueGS.jpg"));

    //Intensity
    in = new StringReader(
            load("res/fox.jpg", "test")
                    + greyscale("intensity-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalIntensityGS.jpg"));

    //Luma
    in = new StringReader(
            load("res/fox.jpg", "test")
                    + greyscale("luma-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalLumaGS.jpg"));

    //Without Component
    in = new StringReader(
            load("res/fox.jpg", "test")
                    + "greyscale test testG"
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalLumaGS.jpg"));

    // ThroughScript
    in = new StringReader(
            run("jpgScripts/GreyScale.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("jpgScripts/RedGreyScale.jpg",
            "OI/OriginalRedGS.jpg"));
    assertTrue(compareIOFiles("jpgScripts/GreenGreyScale.jpg",
            "OI/OriginalGreenGS.jpg"));
    assertTrue(compareIOFiles("jpgScripts/BlueGreyScale.jpg",
            "OI/OriginalBlueGS.jpg"));
    assertTrue(compareIOFiles("jpgScripts/ValueGreyScale.jpg",
            "OI/OriginalValueGS.jpg"));
    assertTrue(compareIOFiles("jpgScripts/IntensityGreyScale.jpg",
            "OI/OriginalIntensityGS.jpg"));
    assertTrue(compareIOFiles("jpgScripts/LumaGreyScale.jpg",
            "OI/OriginalLumaGS.jpg"));
    assertTrue(compareIOFiles("jpgScripts/WithoutComponent.jpg",
            "OI/OriginalLumaGS.jpg"));
  }

  @Test
  public void testRGBSplit() throws IOException {
    String tempFilePath1;
    String tempFilePath2;
    String tempFilePath3;
    try {
      tempFilePath1 = getTempFilePath(".jpg");
      tempFilePath2 = getTempFilePath(".jpg");
      tempFilePath3 = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + save(tempFilePath1, "testR")
                    + save(tempFilePath2, "testG")
                    + save(tempFilePath3, "testB")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath1, "OI/OriginalRedSplit.jpg"));
    assertTrue(compareIOFiles(tempFilePath2, "OI/OriginalGreenSplit.jpg"));
    assertTrue(compareIOFiles(tempFilePath3, "OI/OriginalBlueSplit.jpg"));

    // ThroughScript
    in = new StringReader(
            run("jpgScripts/RGBSplit.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("jpgScripts/RedSplit.jpg", "OI/OriginalRedSplit.jpg"));
    assertTrue(compareIOFiles("jpgScripts/GreenSplit.jpg", "OI/OriginalGreenSplit.jpg"));
    assertTrue(compareIOFiles("jpgScripts/BlueSplit.jpg", "OI/OriginalBlueSplit.jpg"));
  }

  @Test
  public void testRGBCombine() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + rgbCombine("testC", "testR", "testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRGBCombineJPG.jpg"));

    // ThroughScript
    in = new StringReader(
            run("jpgScripts/RGBCombine.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("jpgScripts/RGBCombine.jpg", "OI/OriginalRGBCombineJPG.jpg"));
  }

  @Test
  public void testJPGToBMP() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + rgbCombine("testC", "testR", "testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRGBCombineJPG.bmp"));

    // ThroughScript
    in = new StringReader(
            run("jpgScripts/RGBCombineToBMP.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("jpgScripts/RGBCombineToBMP.bmp",
            "OI/OriginalRGBCombineJPG.bmp"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testC");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRGBCombineJPG.bmp"));
  }

  @Test
  public void testJPGToPNG() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + rgbCombine("testC", "testR", "testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRGBCombinePNG.png"));

    // ThroughScript
    in = new StringReader(
            run("jpgScripts/RGBCombineToPNG.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("jpgScripts/RGBCombineToPNG.png",
            "OI/OriginalRGBCombinePNG.png"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testC");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRGBCombinePNG.png"));
  }

  @Test
  public void testJPGToPPM() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + rgbCombine("testC", "testR", "testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(checkImageEqual(tempFilePath, "OI/foxJPGToPPM.ppm"));

    // ThroughScript
    in = new StringReader(
            run("jpgScripts/RGBCombineToPPM.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(checkImageEqual("jpgScripts/RGBCombineToPPM.ppm",
            "OI/foxJPGToPPM.ppm"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("test");
    assertTrue(compareImageData(imageColor, "OI/foxJPGToPPM.ppm"));
  }

  @Test
  public void testTypoBlur() {
    Reader in = new StringReader(
            " blurr res/imageWithLessPixels.jpg fox"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertEquals("Command 'blurr' not found.\n", out.toString());
  }

  @Test
  public void testBlur() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "fox")
                    + blur("fox", "fox")
                    + save(tempFilePath, "fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(compareIOFiles(tempFilePath, "OI/OriginalFoxBlur.jpg"));
    } catch (IOException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run jpgScripts/Blur.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(compareIOFiles("jpgScripts/Blur.jpg", "OI/OriginalFoxBlur.jpg"));
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testTypoSharpen() {

    Reader in = new StringReader(
            " sarpen res/imageWithLessPixels.png fox"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertEquals("Command 'sarpen' not found.\n", out.toString());
  }

  @Test
  public void testSharpen() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "fox")
                    + sharpen("fox", "fox")
                    + save(tempFilePath, "fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalSharpen.jpg"));
    } catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run jpgScripts/Sharpen.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(compareIOFiles("jpgScripts/Sharpen.jpg", "OI/OriginalSharpen.jpg"));
    } catch (IOException e) {
      fail();
    }

  }

  @Test
  public void testTypoSepia() {

    Reader in = new StringReader(
            " sapia res/imageWithLessPixels.jpg fox"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertEquals("Command 'sapia' not found.\n", out.toString());
  }

  @Test
  public void testSepia() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "fox")
                    + sepia("fox", "fox")
                    + save(tempFilePath, "fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalSepia.jpg"));
    } catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run jpgScripts/Sepia.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual("jpgScripts/Sepia.jpg", "OI/OriginalSepia.jpg"));
    } catch (IOException e) {
      fail();
    }

  }

  @Test
  public void testTypoDither() {
    Reader in = new StringReader(
            " dther res/imageWithLessPixels.jpg fox"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertEquals("Command 'dther' not found.\n", out.toString());
  }

  @Test
  public void testDither() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.jpg", "fox")
                    + dither("fox", "fox")
                    + save(tempFilePath, "fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalDither.jpg"));
    } catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run jpgScripts/Dither.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(compareIOFiles("jpgScripts/Dither.jpg", "OI/OriginalDither.jpg"));
    } catch (IOException e) {
      fail();
    }

  }


}
