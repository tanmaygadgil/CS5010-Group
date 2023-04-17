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
 * JUnit test class for testing BMP images.
 */
public class BMPImageTest extends AbstractImageEditorTest {

  public Model model;
  public ReadOnlyModel testModel;
  public TextView textView;
  public Appendable out;
  String[] args;

  @Before
  public void setUp() {
    model = new ImageEditorModel();
    //testModel = (ReadOnlyModel) model;
    testModel = new ReadOnlyModelImpl();
    out = new StringBuilder();
    textView = new TextViewImpl(out);
    args = new String[]{};
  }

  @Test
  public void testImageLoad() throws IOException {
    Reader in = new StringReader(load("res/fox.bmp", "test"));

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    int[][][] imagePixels = testModel.getImagePixels("test");

    BufferedImage image = ImageIO.read(new FileInputStream("res/fox.bmp"));

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        Color color = new Color(image.getRGB(x, y));
        assertEquals(imagePixels[x][y][0] , color.getRed());
        assertEquals(imagePixels[x][y][1] , color.getGreen());
        assertEquals(imagePixels[x][y][2] , color.getBlue());
      }
    }

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/Load.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    imagePixels = testModel.getImagePixels("script");
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        Color color = new Color(image.getRGB(x, y));
        assertEquals(imagePixels[x][y][0] , color.getRed());
        assertEquals(imagePixels[x][y][1] , color.getGreen());
        assertEquals(imagePixels[x][y][2] , color.getBlue());
      }
    }
  }

  @Test
  public void testImageSave() throws IOException {
    Reader in = new StringReader(load("res/fox.bmp", "test")
            + save("res/fox-bmpSave.bmp", "test")
    );

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    BufferedImage originalImage = ImageIO.read(new FileInputStream("res/fox.bmp"));
    BufferedImage imageThroughCode = ImageIO.read(new FileInputStream("res/fox-bmpSave.bmp"));

    for (int y = 0; y < originalImage.getHeight(); y++) {
      for (int x = 0; x < originalImage.getWidth(); x++) {
        Color originalColor = new Color(originalImage.getRGB(x, y));
        Color imageColor = new Color(imageThroughCode.getRGB(x, y));
        assertEquals(originalColor.getRed() , imageColor.getRed());
        assertEquals(originalColor.getGreen() , imageColor.getGreen());
        assertEquals(originalColor.getBlue() , imageColor.getBlue());
      }
    }

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/Save.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("bmpScripts/foxSave.bmp", "res/fox.bmp"));

  }

  @Test
  public void testHorizontalFlip() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "test")
                    + horizontal("test", "testH")
                    + save(tempFilePath, "testH")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalHorizontal.bmp"));

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/HorizontalFlip.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("bmpScripts/HorizontalFlip.bmp",
            "OI/OriginalHorizontal.bmp"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testH");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalHorizontal.bmp"));
  }

  @Test
  public void testVerticalFlip() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "test")
                    + vertical("test", "testV")
                    + save(tempFilePath, "testV")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalVertical.bmp"));

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/VerticalFlip.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("bmpScripts/VerticalFlip.bmp", "OI/OriginalVertical.bmp"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testV");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalVertical.bmp"));
  }

  @Test
  public void testBrightness() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "test")
                    + brighten(100,"test", "testB")
                    + save(tempFilePath, "testB")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalBrightened.bmp"));

    in = new StringReader(
            load("res/fox.bmp", "test")
                    + brighten(-100,"test", "testD")
                    + save(tempFilePath, "testD")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalDarkened.bmp"));

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/Brightness.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("bmpScripts/Brightened.bmp", "OI/OriginalBrightened.bmp"));
    assertTrue(compareIOFiles("bmpScripts/Darkened.bmp", "OI/OriginalDarkened.bmp"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testB");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalBrightened.bmp"));
    imageColor = testModel.getImagePixels("testD");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalDarkened.bmp"));
  }

  @Test
  public void testGreyscale() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "test")
                    + greyscale("red-component","test", "testG")
                    + save(tempFilePath, "testG")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRedGS.bmp"));

    //Green
    in = new StringReader(
            load("res/fox.bmp", "test")
                    + greyscale("green-component","test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalGreenGS.bmp"));

    //Blue
    in = new StringReader(
            load("res/fox.bmp", "test")
                    + greyscale("blue-component","test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalBlueGS.bmp"));

    //Value
    in = new StringReader(
            load("res/fox.bmp", "test")
                    + greyscale("value-component","test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalValueGS.bmp"));

    //Intensity
    in = new StringReader(
            load("res/fox.bmp", "test")
                    + greyscale("intensity-component","test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalIntensityGS.bmp"));

    //Luma
    in = new StringReader(
            load("res/fox.bmp", "test")
                    + greyscale("luma-component","test", "testG")
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalLumaGS.bmp"));

    //Without component name
    in = new StringReader(
            load("res/fox.bmp", "test")
                    + " greyscale test testG"
                    + save(tempFilePath, "testG")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalLumaGS.bmp"));

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/GreyScale.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("bmpScripts/RedGreyScale.bmp", "OI/OriginalRedGS.bmp"));
    assertTrue(compareIOFiles("bmpScripts/GreenGreyScale.bmp", "OI/OriginalGreenGS.bmp"));
    assertTrue(compareIOFiles("bmpScripts/BlueGreyScale.bmp", "OI/OriginalBlueGS.bmp"));
    assertTrue(compareIOFiles("bmpScripts/ValueGreyScale.bmp",
            "OI/OriginalValueGS.bmp"));
    assertTrue(compareIOFiles("bmpScripts/IntensityGreyScale.bmp",
            "OI/OriginalIntensityGS.bmp"));
    assertTrue(compareIOFiles("bmpScripts/LumaGreyScale.bmp", "OI/OriginalLumaGS.bmp"));
    assertTrue(compareIOFiles("bmpScripts/WithoutComponent.bmp", "OI/OriginalLumaGS.bmp"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testR");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRedGS.bmp"));
    imageColor = testModel.getImagePixels("testG");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalGreenGS.bmp"));
    imageColor = testModel.getImagePixels("testB");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalBlueGS.bmp"));
    imageColor = testModel.getImagePixels("testV");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalValueGS.bmp"));
    imageColor = testModel.getImagePixels("testI");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalIntensityGS.bmp"));
    imageColor = testModel.getImagePixels("testL");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalLumaGS.bmp"));
    imageColor = testModel.getImagePixels("testW");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalLumaGS.bmp"));
  }

  @Test
  public void testRGBSplit() throws IOException {
    String tempFilePath1;
    String tempFilePath2;
    String tempFilePath3;
    try {
      tempFilePath1 = getTempFilePath(".bmp");
      tempFilePath2 = getTempFilePath(".bmp");
      tempFilePath3 = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "test")
                    + rgbSplit("test", "testR","testG","testB")
                    + save(tempFilePath1, "testR")
                    + save(tempFilePath2, "testG")
                    + save(tempFilePath3, "testB")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath1, "OI/OriginalRedSplit.bmp"));
    assertTrue(compareIOFiles(tempFilePath2, "OI/OriginalGreenSplit.bmp"));
    assertTrue(compareIOFiles(tempFilePath3, "OI/OriginalBlueSplit.bmp"));

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/RGBSplit.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("bmpScripts/RedSplit.bmp", "OI/OriginalRedSplit.bmp"));
    assertTrue(compareIOFiles("bmpScripts/GreenSplit.bmp", "OI/OriginalGreenSplit.bmp"));
    assertTrue(compareIOFiles("bmpScripts/BlueSplit.bmp", "OI/OriginalBlueSplit.bmp"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testR");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRedSplit.bmp"));
    imageColor = testModel.getImagePixels("testG");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalGreenSplit.bmp"));
    imageColor = testModel.getImagePixels("testB");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalBlueSplit.bmp"));
  }

  @Test
  public void testRGBCombine() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "test")
                    + rgbSplit("test", "testR","testG","testB")
                    + rgbCombine("testC", "testR","testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRGBCombine.bmp"));

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/RGBCombine.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("bmpScripts/RGBCombine.bmp", "OI/OriginalRGBCombine.bmp"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testC");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRGBCombine.bmp"));
  }


  @Test
  public void testBMPToPNG() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "test")
                    + rgbSplit("test", "testR","testG","testB")
                    + rgbCombine("testC", "testR","testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRGBCombine.png"));

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/RGBCombineToPNG.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("bmpScripts/RGBCombineToPNG.png",
            "OI/OriginalRGBCombine.png"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testC");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRGBCombine.png"));
  }

  @Test
  public void testBMPToPPM() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "test")
                    + rgbSplit("test", "testR","testG","testB")
                    + rgbCombine("testC", "testR","testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(checkImageEqual(tempFilePath, "OI/fox.ppm"));

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/RGBCombineToPPM.txt")
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
  public void testBMPToJPG() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "test")
                    + rgbSplit("test", "testR","testG","testB")
                    + rgbCombine("testC", "testR","testG",
                    "testB")
                    + save(tempFilePath, "testC")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRGBCombine.jpg"));

    // ThroughScript
    in = new StringReader(
            run("bmpScripts/RGBCombineToJPG.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("bmpScripts/RGBCombineToJPG.jpg",
            "OI/OriginalRGBCombine.jpg"));
  }

  @Test
  public void testBlur() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "fox")
                    + blur("fox" , "fox")
                    + save(tempFilePath,"fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(compareIOFiles(tempFilePath, "OI/OriginalFoxBlur.bmp"));
    } catch (IOException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run bmpScripts/Blur.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual("bmpScripts/Blur.bmp", "OI/OriginalFoxBlur.bmp"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxBlur");
      assertTrue(compareIOPixels(imageCombinedPixels, "OI/OriginalFoxBlur.bmp"));
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testTypoSharpen() {

    Reader in = new StringReader(
            " sarpen res/imageWithLessPixels.bmp fox"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
    }
    catch (FileNotFoundException e) {
      fail();
    }
    assertEquals("Command 'sarpen' not found.\n", out.toString());
  }

  @Test
  public void testSharpen() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "fox")
                    + sharpen("fox" , "fox")
                    + save(tempFilePath,"fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalSharpen.bmp"));
    }
    catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run bmpScripts/Sharpen.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual("bmpScripts/Sharpen.bmp", "OI/OriginalSharpen.bmp"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxSharpen");
      assertTrue(compareIOPixels(imageCombinedPixels, "OI/OriginalSharpen.bmp"));
    } catch (IOException e) {
      fail();
    }

  }

  @Test
  public void testTypoSepia() {

    Reader in = new StringReader(
            " sapia res/imageWithLessPixels.bmp fox"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
    }
    catch (FileNotFoundException e) {
      fail();
    }
    assertEquals("Command 'sapia' not found.\n", out.toString());
  }

  @Test
  public void testSepia() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "fox")
                    + sepia("fox", "fox")
                    + save(tempFilePath, "fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalSepia.bmp"));
    } catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run bmpScripts/Sepia.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(compareIOFiles("bmpScripts/Sepia.bmp", "OI/OriginalSepia.bmp"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxSepia");
      assertTrue(compareIOPixels(imageCombinedPixels, "OI/OriginalSepia.bmp"));
    } catch (IOException e) {
      fail();
    }

  }

  @Test
  public void testTypoDither() {
    Reader in = new StringReader(
            " dther res/imageWithLessPixels.bmp fox"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
    }
    catch (FileNotFoundException e) {
      fail();
    }
    assertEquals("Command 'dther' not found.\n", out.toString());
  }

  @Test
  public void testDither() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.bmp", "fox")
                    + dither("fox" , "fox")
                    + save(tempFilePath,"fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath,"OI/OriginalDither.bmp"));
    }
    catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run bmpScripts/Dither.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(compareIOFiles("bmpScripts/Dither.bmp", "OI/OriginalDither.bmp"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxDither");
      assertTrue(compareIOPixels(imageCombinedPixels, "OI/OriginalDither.bmp"));
    } catch (IOException e) {
      fail();
    }

  }

}
