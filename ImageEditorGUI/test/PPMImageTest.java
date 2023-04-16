import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


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
 * JUnit test class for testing PPM images.
 */
public class PPMImageTest extends AbstractImageEditorTest {

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
  public void testPPMToBMP() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.ppm", "test")
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
            run("ppmScripts/RGBCombineToBMP.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("ppmScripts/RGBCombineToBMP.bmp",
            "OI/OriginalRGBCombine.bmp"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testC");
    assertTrue(compareIOPixels(imageColor, "test/OriginalRGBCombine.bmp"));
  }

  @Test
  public void testPPMToPNG() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.ppm", "test")
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
            run("ppmScripts/RGBCombineToPNG.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("ppmScripts/RGBCombineToPNG.png",
            "OI/OriginalRGBCombine.png"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("testC");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRGBCombine.png"));
  }

  @Test
  public void testPPMToJPG() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.ppm", "test")
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
            run("ppmScripts/RGBCombineToJPG.txt")
    );
    controller = new ImageEditorController(in, model, textView);
    controller.execute();
    assertTrue(compareIOFiles("ppmScripts/RGBCombineToJPG.jpg",
            "OI/OriginalRGBCombine.jpg"));
  }

  @Test
  public void testTypoBlur() {
    Reader in = new StringReader(
            " blurr res/imageWithLessPixels.ppm fox"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
    }
    catch (FileNotFoundException e) {
      fail();
    }
    assertEquals("Command 'blurr' not found.\n", out.toString());
  }

  @Test
  public void testBlur() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.ppm", "fox")
                    + blur("fox" , "fox")
                    + save(tempFilePath,"fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
    }
    catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(checkImageEqual(tempFilePath, "OI/OriginalFoxBlur.ppm"));

    // Through Script
    in = new StringReader(
            "run ppmScripts/Blur.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual("ppmScripts/Blur.ppm", "OI/OriginalFoxBlur.ppm"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxBlur");
      boolean equality = compareImageData(imageCombinedPixels,
              "OI/OriginalFoxBlur.ppm");
      assertTrue(equality);
    }
    catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testTypoSharpen() {

    Reader in = new StringReader(
            " sarpen res/imageWithLessPixels.ppm fox"
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
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.ppm", "fox")
                    + sharpen("fox" , "fox")
                    + save(tempFilePath,"fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalSharpen.ppm"));
    }
    catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run ppmScripts/Sharpen.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual("ppmScripts/Sharpen.ppm",
              "OI/OriginalSharpen.ppm"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxSharpen");
      boolean equality = compareImageData(imageCombinedPixels,
              "OI/OriginalSharpen.ppm");
      assertTrue(equality);
    }
    catch (FileNotFoundException e) {
      fail();
    }

  }

  @Test
  public void testTypoSepia() {

    Reader in = new StringReader(
            " sapia res/imageWithLessPixels.ppm fox"
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
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.ppm", "fox")
                    + sepia("fox", "fox")
                    + save(tempFilePath, "fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalSepia.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run ppmScripts/Sepia.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual("ppmScripts/Sepia.ppm", "OI/OriginalSepia.ppm"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxSepia");
      boolean equality = compareImageData(imageCombinedPixels,
              "OI/OriginalSepia.ppm");
      assertTrue(equality);
    }
    catch (FileNotFoundException e) {
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
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.ppm", "fox")
                    + dither("fox" , "fox")
                    + save(tempFilePath,"fox")
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath,"OI/OriginalDither.ppm"));
    }
    catch (FileNotFoundException e) {
      fail();
    }

    // Through Script
    in = new StringReader(
            "run ppmScripts/Dither.txt"
    );
    try {
      Controller controller = new ImageEditorController(in, model, textView);
      controller.execute();
      assertTrue(checkImageEqual("ppmScripts/Dither.ppm", "OI/OriginalDither.ppm"));
      int[][][] imageCombinedPixels = testModel.getImagePixels("foxDither");
      boolean equality = compareImageData(imageCombinedPixels,
              "OI/OriginalDither.ppm");
      assertTrue(equality);
    }
    catch (FileNotFoundException e) {
      fail();
    }

  }


}
