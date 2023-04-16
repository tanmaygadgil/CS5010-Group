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
 * JUnit test class for testing PNG images.
 */
public class PNGImageTest extends AbstractImageEditorTest {

  public Model model;
  public ReadOnlyModel testModel;
  public TextView textView;
  public Appendable out;
  String[] args;

  @Before
  public void setUp() {
    model = new ImageEditorModel();
    testModel = new ReadOnlyModelImpl();
    out = new StringBuilder();
    textView = new TextViewImpl(out);
    args = new String[]{};
  }

  @Test
  public void testImageLoad() throws IOException {
    Reader in = new StringReader(load("res/fox.png", "test"));

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    int[][][] imagePixels = testModel.getImagePixels("test");

    BufferedImage image = ImageIO.read(new FileInputStream("res/fox.png"));

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
            run("pngScripts/Load.txt")
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
  public void testImageSave() throws IOException {
    Reader in = new StringReader(load("res/fox.png", "test")
            + save("res/fox-pngSave.png", "test")
    );

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    BufferedImage originalImage = ImageIO.read(new FileInputStream("res/fox.png"));
    BufferedImage imageThroughCode = ImageIO.read(new FileInputStream("res/fox-pngSave.png"));

    for (int y = 0; y < originalImage.getHeight(); y++) {
      for (int x = 0; x < originalImage.getWidth(); x++) {
        Color originalColor = new Color(originalImage.getRGB(x, y));
        Color imageColor = new Color(imageThroughCode.getRGB(x, y));
        assertEquals(originalColor.getRed(), imageColor.getRed());
        assertEquals(originalColor.getGreen(), imageColor.getGreen());
        assertEquals(originalColor.getBlue(), imageColor.getBlue());
      }
    }

    // ThroughScript
    in = new StringReader(
            run("pngScripts/Save.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("pngScripts/foxSave.png", "res/fox.png"));

  }

  @Test
  public void testHorizontalFlip() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "test")
                    + horizontal("test", "testH")
                    + save(tempFilePath, "testH")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalHorizontal.png"));

    // ThroughScript
    in = new StringReader(
            run("pngScripts/HorizontalFlip.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("pngScripts/HorizontalFlip.png",
            "OI/OriginalHorizontal.png"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testH");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalHorizontal.png"));
  }

  @Test
  public void testVerticalFlip() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "test")
                    + vertical("test", "testV")
                    + save(tempFilePath, "testV")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalVertical.png"));

    // ThroughScript
    in = new StringReader(
            run("pngScripts/VerticalFlip.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("pngScripts/VerticalFlip.png",
            "OI/OriginalVertical.png"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testV");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalVertical.png"));
  }

  @Test
  public void testBrightness() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "test")
                    + brighten(100, "test", "testB")
                    + save(tempFilePath, "testB")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalBrightened.png"));

    in = new StringReader(
            load("res/fox.png", "test")
                    + brighten(-100, "test", "testD")
                    + save(tempFilePath, "testD")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalDarkened.png"));

    // ThroughScript
    in = new StringReader(
            run("pngScripts/Brightness.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("pngScripts/Brightened.png",
            "OI/OriginalBrightened.png"));
    assertTrue(compareIOFiles("pngScripts/Darkened.png",
            "OI/OriginalDarkened.png"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testB");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalBrightened.png"));
    imageColor = testModel.getImagePixels("testD");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalDarkened.png"));
  }

  @Test
  public void testGreyscale() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "test")
                    + greyscale("red-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRedGS.png"));

    //Green
    in = new StringReader(
            load("res/fox.png", "test")
                    + greyscale("green-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalGreenGS.png"));

    //Blue
    in = new StringReader(
            load("res/fox.png", "test")
                    + greyscale("blue-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalBlueGS.png"));

    //Value
    in = new StringReader(
            load("res/fox.png", "test")
                    + greyscale("value-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalValueGS.png"));

    //Intensity
    in = new StringReader(
            load("res/fox.png", "test")
                    + greyscale("intensity-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalIntensityGS.png"));

    //Luma
    in = new StringReader(
            load("res/fox.png", "test")
                    + greyscale("luma-component", "test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalLumaGS.png"));

    //Without Image Name
    in = new StringReader(
            load("res/fox.png", "test")
                    + "greyscale test testW"
                    + save(tempFilePath, "testW")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalLumaGS.png"));

    // ThroughScript
    in = new StringReader(
            run("pngScripts/GreyScale.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("pngScripts/RedGreyScale.png",
            "OI/OriginalRedGS.png"));
    assertTrue(compareIOFiles("pngScripts/GreenGreyScale.png",
            "OI/OriginalGreenGS.png"));
    assertTrue(compareIOFiles("pngScripts/BlueGreyScale.png",
            "OI/OriginalBlueGS.png"));
    assertTrue(compareIOFiles("pngScripts/ValueGreyScale.png",
            "OI/OriginalValueGS.png"));
    assertTrue(compareIOFiles("pngScripts/IntensityGreyScale.png",
            "OI/OriginalIntensityGS.png"));
    assertTrue(compareIOFiles("pngScripts/LumaGreyScale.png",
            "OI/OriginalLumaGS.png"));
    assertTrue(compareIOFiles("pngScripts/WithoutComponent.png",
            "OI/OriginalLumaGS.png"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testR");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRedGS.png"));
    imageColor = testModel.getImagePixels("testG");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalGreenGS.png"));
    imageColor = testModel.getImagePixels("testB");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalBlueGS.png"));
    imageColor = testModel.getImagePixels("testV");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalValueGS.png"));
    imageColor = testModel.getImagePixels("testI");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalIntensityGS.png"));
    imageColor = testModel.getImagePixels("testL");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalLumaGS.png"));
    imageColor = testModel.getImagePixels("testW");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalLumaGS.png"));
  }

  @Test
  public void testRGBSplit() throws IOException {
    String tempFilePath1;
    String tempFilePath2;
    String tempFilePath3;
    try {
      tempFilePath1 = getTempFilePath(".png");
      tempFilePath2 = getTempFilePath(".png");
      tempFilePath3 = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + save(tempFilePath1, "testR")
                    + save(tempFilePath2, "testG")
                    + save(tempFilePath3, "testB")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath1, "OI/OriginalRedSplit.png"));
    assertTrue(compareIOFiles(tempFilePath2, "OI/OriginalGreenSplit.png"));
    assertTrue(compareIOFiles(tempFilePath3, "OI/OriginalBlueSplit.png"));

    // ThroughScript
    in = new StringReader(
            run("pngScripts/RGBSplit.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("pngScripts/RedSplit.png", "OI/OriginalRedSplit.png"));
    assertTrue(compareIOFiles("pngScripts/GreenSplit.png", "OI/OriginalGreenSplit.png"));
    assertTrue(compareIOFiles("pngScripts/BlueSplit.png", "OI/OriginalBlueSplit.png"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testR");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRedSplit.png"));
    imageColor = testModel.getImagePixels("testG");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalGreenSplit.png"));
    imageColor = testModel.getImagePixels("testB");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalBlueSplit.png"));
  }

  @Test
  public void testRGBCombine() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + rgbCombine("testC", "testR", "testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRGBCombine.png"));

    // ThroughScript
    in = new StringReader(
            run("pngScripts/RGBCombine.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("pngScripts/RGBCombine.png", "OI/OriginalRGBCombine.png"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testC");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRGBCombine.png"));
  }

  @Test
  public void testPNGToBMP() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + rgbCombine("testC", "testR", "testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRGBCombine.bmp"));

    // ThroughScript
    in = new StringReader(
            run("pngScripts/RGBCombineToBMP.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("pngScripts/RGBCombineToBMP.bmp",
            "OI/OriginalRGBCombine.bmp"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testC");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRGBCombine.bmp"));
  }

  @Test
  public void testPNGToPPM() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + rgbCombine("testC", "testR", "testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(checkImageEqual(tempFilePath, "OI/fox.ppm"));

    // ThroughScript
    in = new StringReader(
            run("pngScripts/RGBCombineToPPM.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(checkImageEqual("bmpScripts/RGBCombineToPPM.ppm",
            "res/fox.ppm"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testC");
    assertTrue(compareImageData(imageColor, "res/fox.ppm"));
  }

  @Test
  public void testPNGToJPG() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + rgbCombine("testC", "testR", "testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRGBCombine.jpg"));

    // ThroughScript
    in = new StringReader(
            run("pngScripts/RGBCombineToJPG.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("pngScripts/RGBCombineToJPG.jpg",
            "OI/OriginalRGBCombine.jpg"));
  }

  @Test
  public void testTypoBlur() {
    Reader in = new StringReader(
            " blurr res/imageWithLessPixels.png fox"
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
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "fox")
                    + blur("fox", "fox")
                    + save(tempFilePath, "fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(compareIOFiles(tempFilePath, "OI/OriginalFoxBlur.png"));
    } catch (IOException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run pngScripts/Blur.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(compareIOFiles("pngScripts/Blur.png",
              "OI/OriginalFoxBlur.png"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxBlur");
      assertTrue(compareIOPixels(imageCombinedPixels,
              "OI/OriginalFoxBlur.png"));
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
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "fox")
                    + sharpen("fox", "fox")
                    + save(tempFilePath, "fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalSharpen.png"));
    } catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run pngScripts/Sharpen.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual("pngScripts/Sharpen.png", "OI/OriginalSharpen.png"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxSharpen");
      assertTrue(compareIOPixels(imageCombinedPixels,
              "OI/OriginalSharpen.png"));
    } catch (IOException e) {
      fail();
    }

  }

  @Test
  public void testTypoSepia() {

    Reader in = new StringReader(
            " sapia res/imageWithLessPixels.png fox"
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
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "fox")
                    + sepia("fox", "fox")
                    + save(tempFilePath, "fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalSepia.png"));
    } catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run pngScripts/Sepia.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual("pngScripts/Sepia.png", "OI/OriginalSepia.png"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxSepia");
      assertTrue(compareIOPixels(imageCombinedPixels,
              "OI/OriginalSepia.png"));
    } catch (IOException e) {
      fail();
    }

  }

  @Test
  public void testTypoDither() {
    Reader in = new StringReader(
            " dther res/imageWithLessPixels.ppm fox"
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
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.png", "fox")
                    + dither("fox", "fox")
                    + save(tempFilePath, "fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalDither.png"));
    } catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run pngScripts/Dither.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(compareIOFiles("pngScripts/Dither.png", "OI/OriginalDither.png"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxDither");
      assertTrue(compareIOPixels(imageCombinedPixels,
              "OI/OriginalDither.png"));
    } catch (IOException e) {
      fail();
    }

  }

}
