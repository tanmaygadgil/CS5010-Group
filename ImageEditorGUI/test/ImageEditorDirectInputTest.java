import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import imgeditor.controller.Controller;
import imgeditor.controller.ImageEditorController;
import imgeditor.model.ImageEditorModel;
import imgeditor.model.Model;
import imgeditor.model.ReadOnlyModel;
import imgeditor.model.ReadOnlyModelImpl;
import imgeditor.view.TextView;
import imgeditor.view.TextViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * JUnit test class for the Controller and Model implementations.
 * It tests all the commands and methods from the controller and model.
 * It also contains tests to check if there are any typos in the commands and / or arguments.
 */
public class ImageEditorDirectInputTest extends AbstractImageEditorTest {

  static class MockModel implements Model {

    private final StringBuilder mockLog;

    MockModel(StringBuilder mockLog) {
      this.mockLog = mockLog;
    }

    @Override
    public void load(InputStream in, String imageFormat, String imageName)
            throws IOException {
      mockLog.append("InputStream:").append(new String(in.readAllBytes()))
              .append(" ImageFormat:").append(imageFormat)
              .append(" ImageName:").append(imageName);
    }

    @Override
    public void save(OutputStream out, String imageFormat, String imageName)
            throws IOException {
      mockLog.append(" ImageFormat:").append(imageFormat)
              .append(" ImageName:").append(imageName);
    }

    @Override
    public void operate(String operationName, List<String> imageName, List<String> destImageName,
                        String... commandArgs) {
      mockLog.append(" OperationName:").append(operationName)
              .append(" ImageName:").append(imageName).append(" DestImageName:")
              .append(destImageName).append(" commandArgs:").append(Arrays.toString(commandArgs));

    }

  }

  @Test
  public void testMockModel() throws IOException {
    Reader in = new StringReader("load res/fox.ppm fox "
            + "horizontal-flip fox foxH "
            + "vertical-flip fox foxV "
            + "brighten 50 fox foxB "
            + "greyscale red-component fox foxR "
            + "greyscale fox foxR "
            + "blur fox foxR "
            + "sharpen fox foxR "
            + "sepia fox foxR "
            + "dither fox foxR "
            + "rgb-split foxSplit foxRed foxGreen foxBlue "
            + "rgb-combine foxCombine foxRed foxGreen foxBlue "
            + "save res/foxS.ppm foxCombine");


    StringBuilder mockLog = new StringBuilder();

    Model mockModel = new MockModel(mockLog);

    Controller controller = new ImageEditorController(in, mockModel, textView);
    controller.execute();
    InputStream input;
    try {
      input = new FileInputStream("res/fox.ppm");
    } catch (IOException e) {
      throw new IOException("File Not Found\n");
    }

    assertEquals("InputStream:" + new String(input.readAllBytes())
                    + " ImageFormat:.ppm" + " ImageName:fox"
                    + " OperationName:horizontal-flip ImageName:[fox] DestImageName:[foxH] "
                    + "commandArgs:[]"
                    + " OperationName:vertical-flip ImageName:[fox] DestImageName:[foxV] "
                    + "commandArgs:[]"
                    + " OperationName:brighten ImageName:[fox] DestImageName:[foxB] "
                    + "commandArgs:[50]"
                    + " OperationName:greyscale ImageName:[fox] DestImageName:[foxR] "
                    + "commandArgs:[red-component]"
                    + " OperationName:greyscale ImageName:[fox] DestImageName:[foxR] "
                    + "commandArgs:[luma-component]"
                    + " OperationName:blur ImageName:[fox] DestImageName:[foxR] "
                    + "commandArgs:[blur]"
                    + " OperationName:sharpen ImageName:[fox] DestImageName:[foxR] "
                    + "commandArgs:[sharpen]"
                    + " OperationName:sepia ImageName:[fox] DestImageName:[foxR] "
                    + "commandArgs:[sepia]"
                    + " OperationName:dither ImageName:[fox] DestImageName:[foxR] "
                    + "commandArgs:[]"
                    + " OperationName:rgb-split ImageName:[foxSplit] "
                    + "DestImageName:[foxRed, foxGreen, foxBlue] commandArgs:[]"
                    + " OperationName:rgb-combine ImageName:[foxRed, foxGreen, foxBlue] "
                    + "DestImageName:[foxCombine] commandArgs:[]"
                    + " ImageFormat:.ppm"
                    + " ImageName:foxCombine",
            mockLog.toString());
  }

  public Model model;
  public ReadOnlyModel testModel;
  public TextView textView;
  public Appendable out;
  public String[] args;

  @Before
  public void setUp() {
    model = new ImageEditorModel();
    testModel = new ReadOnlyModelImpl();
    out = new StringBuilder();
    textView = new TextViewImpl(out);
    args = new String[]{};
  }

  @Test
  public void testTypoLoad() {
    Reader in = new StringReader("laod res/mountains.ppm mtn");
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Command 'laod' not found."));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testTypoSave() {
    Reader in = new StringReader("load res/mountains.ppm mtn saave res/mountainsaved.ppm mtn");
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Command 'saave' not found."));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testTypoGreyscaleCommand() {
    Reader in = new StringReader("load res/mountains.ppm mtn greyscle luma-component mtn "
            + "mtngrey");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Command 'greyscle' not found."));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testTypoGreyscaleArgument() {

    String[] invalidGreyscaleCommands = {
        "load res/mountains.ppm mtn greyscale valu-component mtn mtngrey",
        "load res/mountains.ppm mtn greyscale lume-component mtn mtngrey",
        "load res/mountains.ppm mtn greyscale intensty-component mtn mtngrey",
        "load res/mountains.ppm mtn greyscale value-compoent mtn mtngrey",
        "load res/mountains.ppm mtn greyscale redd-component mtn mtngrey",
        "load res/mountains.ppm mtn greyscale freen-component mtn mtngrey",
        "load res/mountains.ppm mtn greyscale blu-component mtn mtngrey"
    };

    for (String invalidGreyscaleCommand : invalidGreyscaleCommands) {
      Reader in = new StringReader(invalidGreyscaleCommand);

      Controller controller = new ImageEditorController(in, model, textView);

      try {
        controller.execute();
        assertTrue(out.toString().contains("Invalid component name."));
      } catch (FileNotFoundException e) {
        fail();
      }
    }
  }

  @Test
  public void testTypoBrighten() {
    Reader in = new StringReader("load res/mountains.ppm mtn brigthen 100 mtn mtn");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Command 'brigthen' not found."));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testTypoHorizontalFlip() {
    Reader in = new StringReader("load res/mountains.ppm mtn gorizontal-flip mtn mtn");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Command 'gorizontal-flip' not found."));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testTypoVerticalFlip() {
    Reader in = new StringReader("load res/mountains.ppm mtn vertixal-flup mtn mtn");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Command 'vertixal-flup' not found."));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testTypoRGBSplit() {
    Reader in = new StringReader("load res/mountains.ppm mtn rgn-split mtn mtnr mtng mtnb");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Command 'rgn-split' not found."));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testTypoRGBCombine() {
    Reader in = new StringReader("load res/mountains.ppm mtn rgb-split mtn mtnr mtng mtnb "
            + "rgb-comvine mtncombined mtnr mtng mtnb");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Command 'rgb-comvine' not found."));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testSaveDestinationFolderNotExists() {
    Reader in = new StringReader("load res/mountains.ppm mtn save nofolder/mtns.ppm mtn");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Invalid file path to save the image."));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testSaveInvalidFileType() {
    Reader in = new StringReader("load res/mountains.ppm mtn save res/mtns.pxyz mtn");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Unsupported file format.\n"));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testSavePathWithWhitespace() {
    Reader in = new StringReader("load res/mountains.ppm mtn save res/mtns new.ppm mtn");
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Filename does not contain an extension.\n"));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testEmptyCommand() {
    Reader in = new StringReader("");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().isEmpty());
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testWhitespaceCommand() {
    Reader in = new StringReader("       \r         \n           \t           \t");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().isEmpty());
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testTempFileSave() {
    String tempFilePath = null;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      fail();
    }

    Reader in = new StringReader(
            load("res/fox.ppm", "fox")
                    + save(tempFilePath, "fox")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("load command successful"));
      assertTrue(out.toString().contains("save command successful"));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testCommandWithDifferentCase() {
    Reader in = new StringReader("load res/mountains.ppm mtn Load res/mountains.ppm mtn "
            + "LOad ReS/mOUntaiNs.pPM mtn1 LOAd res/Mountains.ppm mtn2 "
            + "LOAD RES/MOUNTAINS.PPM mtn3");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertEquals(5, countSubstringOccurrences(out.toString(), "load command successful"));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testCommandWithComments() {
    Reader in = new StringReader("#load res/mountains.ppm mtn\n"
            + "load res/mountains.ppm mtn1 #horizontal-flip check\n "
            + "horizontal-flip mtn1 mtn1-flipped");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertEquals(1, countSubstringOccurrences(out.toString(), "load command successful"));
      assertEquals(1, countSubstringOccurrences(out.toString(),
              "horizontal-flip command successful"));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testWrongCommands() {
    Reader in = new StringReader("loadnew koala.ppm koala\n load res/mountains.ppm mtn\n "
            + "rotate koala");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    String[] checkOutputMessages = {
        "Command 'loadnew' not found.",
        "load command successful",
        "Command 'rotate' not found."
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));
  }

  @Test
  public void testWrongFileExtensionLoad() {
    Reader in = new StringReader(
            load("res/mountains.ppn", "mtn")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("File Not Found\n"));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testWrongFileExtensionSave() {
    Reader in = new StringReader(
            load("res/mountains.ppm", "mtn")
                    + save("res/mountainsSaved.pmm", "mtn")
    );


    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Unsupported file format.\n"));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testWrongImageName() {
    Reader in = new StringReader(
            load("res/mountains.ppm", "mtn")
                    + horizontal("abc", "abc")
                    + horizontal("xyz", "xyznew")
                    + vertical("xyznew", "xyznewvert")
                    + greyscale("blue-component", "mtns", "mtns-grey")
                    + rgbSplit("fox", "foxR", "foxG", "foxB")
                    + rgbCombine("foxRGB", "foxR", "foxG", "foxB")
                    + save("res/savedImage.ppm", "foxABC")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    String[] checkOutputMessages = {
        "load command successful",
        "Image name 'abc' does not exist.",
        "Image name 'xyz' does not exist.",
        "Image name 'xyznew' does not exist.",
        "Image name 'mtns' does not exist.",
        "Image name 'fox' does not exist.",
        "Image name 'foxR' does not exist.",
        "Image name 'foxABC' does not exist."
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullControllerIn() {
    new ImageEditorController(null, model, textView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullControllerModel() {
    Reader in = new StringReader("");
    new ImageEditorController(in, null, textView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullControllerView() {
    Reader in = new StringReader("");
    new ImageEditorController(in, model, null);
  }

  @Test
  public void testBrightenValueNotAnInteger() {

    String[] invalidBrightenCommands = {
        "load res/mountains.ppm mtn\n brighten abc mtn mtn-bright\n",
        "load res/mountains.ppm mtn brighten 10.0 mtn mtn"
    };

    for (String invalidBrightenCommand : invalidBrightenCommands) {
      Reader in = new StringReader(invalidBrightenCommand);

      Controller controller = new ImageEditorController(in, model, textView);

      try {
        controller.execute();
        assertTrue(out.toString().contains(
                "Brightness value must be an integer."));
      } catch (FileNotFoundException e) {
        fail();
      }
    }
  }

  @Test
  public void testSpacesAfterCommand() {
    String tempFilename;
    try {
      tempFilename = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader("load                     res/mountains.ppm           mtn\n"
            + "save            " + tempFilename + "                             mtn");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    String[] checkOutputMessages = {
        "load command successful",
        "save command successful"
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));
  }

  @Test
  public void testCommandsInNewLine() {
    Reader in = new StringReader("load\n res/mountains.ppm\n mtn\n save\n "
            + "res/savedMountains.ppm\n mtn");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    String[] checkOutputMessages = {
        "load command successful",
        "save command successful"
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));
  }

  @Test
  public void testBrightnessMaxValue() {
    Reader in = new StringReader(
            load("OI/fox.ppm", "fox")
                    + brighten(1000, "fox", "fox-maxbright")
                    + save("res/fox-maxbright.ppm", "fox-maxbright")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/fox-maxbright.ppm",
              "OI/fox-white.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imagePixels = testModel.getImagePixels("fox-maxbright");
      assertTrue(compareImageData(imagePixels, "OI/fox-white.ppm"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testBrightnessMinValue() {
    Reader in = new StringReader(
            load("OI/fox.ppm", "fox")
                    + brighten(-3500, "fox", "fox-minbright")
                    + save("res/fox-minbright.ppm", "fox-minbright")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/fox-minbright.ppm",
              "OI/fox-black.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imagePixels = testModel.getImagePixels("fox-minbright");
      assertTrue(compareImageData(imagePixels, "OI/fox-black.ppm"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testSameImageOverwrite() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/test.ppm", "test")
                    + load("res/fox.ppm", "testfox")
                    + load("res/test.ppm", "testfox")
                    + save(tempFilePath, "testfox"));

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    assertTrue(checkImageEqual("res/test.ppm", tempFilePath));

    try {
      int[][][] imagePixels = testModel.getImagePixels("testfox");
      assertTrue(compareImageData(imagePixels, "res/test.ppm"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testImageLoad() {
    Reader in = new StringReader(load("res/fox.ppm", "test"));

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    try {
      int[][][] imagePixels = testModel.getImagePixels("test");
      boolean equality = compareImageData(imagePixels, "res/fox.ppm");
      assertTrue(equality);
    } catch (IllegalArgumentException e) {
      fail();
    }

  }

  @Test
  public void testImageSave() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.ppm", "fox")
                    + save(tempFilePath, "fox")
    );

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    String[] checkOutputMessages = {
        "load command successful",
        "save command successful"
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));

    try {
      boolean equality = checkImageEqual(tempFilePath, "res/fox.ppm");
      assertTrue(equality);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }


  @Test
  public void testHorizontalFlipCommands() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.ppm", "test")
                    + horizontal("test", "testH")
                    + save(tempFilePath, "testH")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath,
              "OI/OriginalHorizontal.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imagePixels = testModel.getImagePixels("testH");
      boolean equality = compareImageData(imagePixels, "OI/OriginalHorizontal.ppm");
      assertTrue(equality);
    } catch (IllegalArgumentException e) {
      fail();
    }

  }

  @Test
  public void testVerticalFlipCommands() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(
            load("res/fox.ppm", "test")
                    + vertical("test", "testV")
                    + save(tempFilePath, "testV"));

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual(tempFilePath,
              "OI/OriginalVertical.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imagePixels = testModel.getImagePixels("testV");
      boolean equality = compareImageData(imagePixels, "OI/OriginalVertical.ppm");
      assertTrue(equality);
    } catch (IllegalArgumentException e) {
      fail();
    }

  }

  @Test
  public void testBrightenCommands() {
    Reader in = new StringReader(
            load("res/fox.ppm", "test")
                    + brighten(50, "test", "testIB")
                    + brighten(-50, "test", "testDB")
                    + save("res/increaseBrightnessImage.ppm", "testIB")
                    + save("res/decreaseBrightnessImage.ppm", "testDB"));

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/increaseBrightnessImage.ppm",
              "OI/OriginalIncreasedBrightness.ppm"));

      assertTrue(checkImageEqual("res/decreaseBrightnessImage.ppm",
              "OI/OriginalDecreasedBrightness.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imagePixelsIB = testModel.getImagePixels("testIB");
      int[][][] imagePixelsDB = testModel.getImagePixels("testDB");
      boolean equality = compareImageData(imagePixelsIB,
              "OI/OriginalIncreasedBrightness.ppm");
      boolean newEquality = compareImageData(imagePixelsDB,
              "OI/OriginalDecreasedBrightness.ppm");
      assertTrue(equality);
      assertTrue(newEquality);
    } catch (IllegalArgumentException e) {
      fail();
    }

  }

  @Test
  public void testGreyScaleCommands() {
    Reader in = new StringReader(
            load("res/fox.ppm", "test")
                    + greyscale("red-component", "test", "testR")
                    + greyscale("green-component", "test", "testG")
                    + greyscale("blue-component", "test", "testB")
                    + greyscale("value-component", "test", "testV")
                    + greyscale("intensity-component", "test", "testI")
                    + greyscale("luma-component", "test", "testL")
                    + "greyscale test testW"
                    + save("res/redGreyScale.ppm", "testR")
                    + save("res/greenGreyScale.ppm", "testG")
                    + save("res/blueGreyScale.ppm", "testB")
                    + save("res/valueGreyScale.ppm", "testV")
                    + save("res/intensityGreyScale.ppm", "testI")
                    + save("res/lumaGreyScale.ppm", "testL")
                    + save("res/WithoutComponent.ppm", "testW")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/redGreyScale.ppm",
              "OI/OriginalRedGreyScale.ppm"));

      assertTrue(checkImageEqual("res/greenGreyScale.ppm",
              "OI/OriginalGreenGreyScale.ppm"));

      assertTrue(checkImageEqual("res/blueGreyScale.ppm",
              "OI/OriginalBlueGreyScale.ppm"));

      assertTrue(checkImageEqual("res/valueGreyScale.ppm",
              "OI/OriginalValueGreyScale.ppm"));

      assertTrue(checkImageEqual("res/intensityGreyScale.ppm",
              "OI/OriginalIntensityGreyScale.ppm"));

      assertTrue(checkImageEqual("res/lumaGreyScale.ppm",
              "OI/OriginalLumaGreyScale.ppm"));

      assertTrue(checkImageEqual("res/WithoutComponent.ppm",
              "OI/OriginalLumaGreyScale.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imageRedPixels = testModel.getImagePixels("testR");
      int[][][] imageGreenPixels = testModel.getImagePixels("testG");
      int[][][] imageBluePixels = testModel.getImagePixels("testB");
      int[][][] imageValuePixels = testModel.getImagePixels("testV");
      int[][][] imageIntensityPixels = testModel.getImagePixels("testI");
      int[][][] imageLumaPixels = testModel.getImagePixels("testL");
      int[][][] imageWithoutPixels = testModel.getImagePixels("testW");
      boolean redEquality = compareImageData(imageRedPixels,
              "OI/OriginalRedGreyScale.ppm");
      boolean greenEquality = compareImageData(imageGreenPixels,
              "OI/OriginalGreenGreyScale.ppm");
      boolean blueEquality = compareImageData(imageBluePixels,
              "OI/OriginalBlueGreyScale.ppm");
      boolean valueEquality = compareImageData(imageValuePixels,
              "OI/OriginalValueGreyScale.ppm");
      boolean intensityEquality = compareImageData(imageIntensityPixels,
              "OI/OriginalIntensityGreyScale.ppm");
      boolean lumaEquality = compareImageData(imageLumaPixels,
              "OI/OriginalLumaGreyScale.ppm");
      boolean withoutImageNameEquality = compareImageData(imageWithoutPixels,
              "OI/OriginalLumaGreyScale.ppm");

      assertTrue(redEquality);
      assertTrue(greenEquality);
      assertTrue(blueEquality);
      assertTrue(valueEquality);
      assertTrue(intensityEquality);
      assertTrue(lumaEquality);
      assertTrue(withoutImageNameEquality);
    } catch (IllegalArgumentException e) {
      fail();
    }

  }

  @Test
  public void testRGBSplitCommands() {
    Reader in = new StringReader(
            load("res/fox.ppm", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + save("res/RedSplit.ppm", "testR")
                    + save("res/GreenSplit.ppm", "testG")
                    + save("res/BlueSplit.ppm", "testB")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/RedSplit.ppm",
              "OI/OriginalRedGreyScale.ppm"));
      assertTrue(checkImageEqual("res/GreenSplit.ppm",
              "OI/OriginalGreenGreyScale.ppm"));
      assertTrue(checkImageEqual("res/BlueSplit.ppm",
              "OI/OriginalBlueGreyScale.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imageRedPixels = testModel.getImagePixels("testR");
      int[][][] imageGreenPixels = testModel.getImagePixels("testG");
      int[][][] imageBluePixels = testModel.getImagePixels("testB");
      boolean redEquality = compareImageData(imageRedPixels,
              "OI/OriginalRedGreyScale.ppm");
      boolean greenEquality = compareImageData(imageGreenPixels,
              "OI/OriginalGreenGreyScale.ppm");
      boolean blueEquality = compareImageData(imageBluePixels,
              "OI/OriginalBlueGreyScale.ppm");
      assertTrue(redEquality);
      assertTrue(greenEquality);
      assertTrue(blueEquality);
    } catch (IllegalArgumentException e) {
      fail();
    }

  }

  @Test
  public void testRGBCombineCommands() {
    Reader in = new StringReader(
            load("OI/OriginalRedGreyScale.ppm", "testR")
                    + load("OI/OriginalGreenGreyScale.ppm", "testG")
                    + load("OI/OriginalBlueGreyScale.ppm", "testB")
                    + rgbCombine("testC", "testR", "testG", "testB")
                    + save("res/RedCombine.ppm", "testC")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/RedCombine.ppm",
              "OI/fox.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imageCombinedPixels = testModel.getImagePixels("testC");
      boolean equality = compareImageData(imageCombinedPixels,
              "OI/fox.ppm");
      assertTrue(equality);
    } catch (IllegalArgumentException e) {
      fail();
    }

  }

  // Perform multiple operations one after the other.
  // This test checks the resulting image with a pre-created image with the same operations
  // done in the same order in the OI/ folder.
  @Test
  public void testCombinations1() {
    String redImgTempFilePath;
    String greenImgTempFilePath;
    String blueImgTempFilePath;
    String vertBrightImgTempFilePath;
    String vertBrightDarkImgTempFilePath;
    try {
      redImgTempFilePath = getTempFilePath(".ppm");
      greenImgTempFilePath = getTempFilePath(".ppm");
      blueImgTempFilePath = getTempFilePath(".ppm");
      vertBrightImgTempFilePath = getTempFilePath(".ppm");
      vertBrightDarkImgTempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Reader in = new StringReader(load("res/fox.ppm", "test")
            + horizontal("test", "testH")
            + rgbSplit("testH", "testR", "testG", "testB")
            + save(redImgTempFilePath, "testR")
            + save(greenImgTempFilePath, "testG")
            + save(blueImgTempFilePath, "testB")
            + load("res/fox.ppm", "test1")//load same image different imgName
            + vertical("test1", "testV")
            + brighten(50, "testV", "testBr")
            + save(vertBrightImgTempFilePath, "testBr")
            + brighten(-50, "testBr", "testD")//imagName used after save
            + save(vertBrightDarkImgTempFilePath, "testD")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual(redImgTempFilePath,
              "OI/LOAD-HF-RED.ppm"));
      assertTrue(checkImageEqual(greenImgTempFilePath,
              "OI/LOAD-HF-GREEN.ppm"));
      assertTrue(checkImageEqual(blueImgTempFilePath,
              "OI/LOAD-HF-BLUE.ppm"));
      assertTrue(checkImageEqual(vertBrightImgTempFilePath,
              "OI/LOAD-VF-B50.ppm"));
      assertTrue(checkImageEqual(vertBrightDarkImgTempFilePath,
              "OI/LOAD-VF-B50-D50.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imageLoadPixels = testModel.getImagePixels("test");
      int[][][] imageLHFPixels = testModel.getImagePixels("testH");
      int[][][] imageLHFRedPixels = testModel.getImagePixels("testR");
      int[][][] imageLHFGreenPixels = testModel.getImagePixels("testG");
      int[][][] imageLHFBluePixels = testModel.getImagePixels("testB");
      int[][][] loadSameImageWithAnotherImgNamePixels = testModel.getImagePixels("test1");
      int[][][] newImgNameVFPixels = testModel.getImagePixels("testV");
      int[][][] newImgNameVFBrPixels = testModel.getImagePixels("testBr");
      int[][][] newImgNameVFBrDPixels = testModel.getImagePixels("testD");
      boolean equality = compareImageData(imageLoadPixels,
              "res/fox.ppm");
      boolean equality1 = compareImageData(imageLHFPixels,
              "OI/OriginalHorizontal.ppm");
      boolean equality2 = compareImageData(imageLHFRedPixels,
              "OI/LOAD-HF-RED.ppm");
      boolean equality3 = compareImageData(imageLHFGreenPixels,
              "OI/LOAD-HF-GREEN.ppm");
      boolean equality4 = compareImageData(imageLHFBluePixels,
              "OI/LOAD-HF-BLUE.ppm");
      boolean equality5 = compareImageData(loadSameImageWithAnotherImgNamePixels,
              "OI/fox.ppm");
      boolean equality6 = compareImageData(newImgNameVFPixels,
              "OI/OriginalVertical.ppm");
      boolean equality7 = compareImageData(newImgNameVFBrPixels,
              "OI/LOAD-VF-B50.ppm");
      boolean equality8 = compareImageData(newImgNameVFBrDPixels,
              "OI/LOAD-VF-B50-D50.ppm");
      assertTrue(equality);
      assertTrue(equality1);
      assertTrue(equality2);
      assertTrue(equality3);
      assertTrue(equality4);
      assertTrue(equality5);
      assertTrue(equality6);
      assertTrue(equality7);
      assertTrue(equality8);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testCombinations2() {
    Reader in = new StringReader(load("res/fox.ppm", "test")
            + greyscale("red-component", "test", "testR")
            + brighten(50, "testR", "testBr")
            + save("res/LGSRedBr.ppm", "testBr")
            + horizontal("testR", "testR")//overWriting ImgName testR
            + run("test/saveOverWritten.txt")// saving through script
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/LGSRedBr.ppm",
              "OI/LOAD-GSRed-B50.ppm"));
      assertTrue(checkImageEqual("res/LGSRedHF.ppm",
              "OI/LOAD-GSRed-HF.ppm"));
    } catch (FileNotFoundException e) {
      fail();
    }


    try {
      int[][][] imageLoadPixels = testModel.getImagePixels("test");
      int[][][] imageOverWrittenPixels = testModel.getImagePixels("testR");
      int[][][] imageLRedCompBrPixels = testModel.getImagePixels("testBr");
      boolean equality = compareImageData(imageLoadPixels,
              "res/fox.ppm");
      boolean equality1 = compareImageData(imageLRedCompBrPixels,
              "OI/LOAD-GSRed-B50.ppm");
      boolean equality2 = compareImageData(imageOverWrittenPixels,
              "OI/LOAD-GSRed-HF.ppm");
      assertTrue(equality);
      assertTrue(equality1);
      assertTrue(equality2);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testOverwritingImageName() {
    Reader in = new StringReader(load("res/fox.ppm", "test")
            + greyscale("value-component", "test", "testVC")
    );

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imageLoadPixels = testModel.getImagePixels("test");
      int[][][] imageValueCompPixels = testModel.getImagePixels("testVC");
      boolean equality = compareImageData(imageLoadPixels,
              "res/fox.ppm");
      boolean equality1 = compareImageData(imageValueCompPixels,
              "OI/OriginalValueGreyScale.ppm");
      assertTrue(equality);
      assertTrue(equality1);
    } catch (IllegalArgumentException e) {
      fail();
    }

    //Overwriting testVC imageName
    in = new StringReader(load("res/fox.ppm", "test")
            + greyscale("value-component", "test", "testVC")
            + horizontal("testVC", "testVC")
    );
    controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    try {
      int[][][] imageLoadPixels = testModel.getImagePixels("test");
      int[][][] imageValueCompHFPixels = testModel.getImagePixels("testVC");
      boolean equality = compareImageData(imageLoadPixels,
              "res/fox.ppm");
      boolean equality1 = compareImageData(imageValueCompHFPixels,
              "OI/LOAD-GSValue-HF.ppm");
      assertTrue(equality);
      assertTrue(equality1);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testLoadingMultipleImagesAndOverwritingImageNames() {
    // The first load overwrites the second load,
    // while the third load creates different object for same second image.
    Reader in = new StringReader(load("res/fox.ppm", "test"));

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    int[][][] imageLoadFoxPixels = testModel.getImagePixels("test");

    in = new StringReader(load("res/fox.ppm", "test")
            + brighten(50, "test", "testBr")
            + load("res/snake.ppm", "test")//load on same ImgName diff image
            + vertical("test", "testV")
            + load("res/snake.ppm", "test1")//load on diff ImgName same image
    );
    controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    // testing imgName overridden command successfully.
    int[][][] imageLoadSnakePixels = testModel.getImagePixels("test");
    // "test" imgName gets overwritten by Snake.ppm from Fox.ppm
    // shows imgName object gets overridden when different ImageIOHandler is provided to it
    assertNotEquals(imageLoadFoxPixels, imageLoadSnakePixels);

    try {
      // testV representing vertical image
      int[][][] imageLoadSnakeVFPixels = testModel.getImagePixels("testV");
      // pixels of different imgName "test1" of same Snake image used to create imgName "test"
      int[][][] imageLoadNewSnakePixels = testModel.getImagePixels("test1");
      boolean equality = compareImageData(imageLoadSnakePixels,
              "OI/snake.ppm");
      boolean equality1 = compareImageData(imageLoadSnakeVFPixels,
              "OI/snakeVertical.ppm");
      // different imgName have same pixels for same image
      // shows same image is loaded with different imgName
      boolean equality2 = compareImageData(imageLoadNewSnakePixels,
              "OI/snake.ppm");
      assertTrue(equality);
      assertTrue(equality1);
      assertTrue(equality2);
    } catch (IllegalArgumentException e) {
      fail();
    }

  }

  @Test
  public void testRGBSplitBrightenCombine() {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Reader in = new StringReader(
            load("res/fox.ppm", "test")
                    + rgbSplit("test", "testR", "testG", "testB")
                    + brighten(50, "testG", "testGBright")
                    + rgbCombine("testCombined", "testR", "testGBright", "testB")
                    + save(tempFilePath, "testCombined")
    );
    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    String[] checkOutputMessages = {
        "load command successful",
        "rgb-split command successful",
        "brighten command successful",
        "rgb-combine command successful",
        "save command successful"
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));

    try {
      int[][][] imageCombinedPixels = testModel.getImagePixels("testCombined");
      boolean equality = compareImageData(imageCombinedPixels,
              "OI/foxGreenBrightenedCombined.ppm");
      assertTrue(equality);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testMultipleLoads() {
    List<String> testFileNamePath = new ArrayList<>(Arrays.asList(
            "load res/fox.ppm 1",
            "load res/blueGreyScale.ppm 2",
            "load res/increaseBrightnessImage.ppm 3",
            "load res/decreaseBrightnessImage.ppm 4",
            "load res/greenGreyScale.ppm 5",
            "load res/horizontalImage.ppm 6",
            "load res/verticalImage.ppm 7",
            "load res/intensityGreyScale.ppm 8",
            "load res/lumaGreyScale.ppm 9",
            "load res/redGreyScale.ppm 10",
            "load res/valueGreyScale.ppm 11",
            "load res/snake.ppm 12",
            "load res/snakeBlueScale.ppm 13",
            "load res/snakeBrighter.ppm 14",
            "load res/snakeDarken.ppm 15",
            "load res/snakeGreenScale.ppm 16",
            "load res/snakeHorizontal.ppm 17",
            "load res/snakeHorizontalVertical.ppm 18",
            "load res/snakeIntensityScale.ppm 19",
            "load res/snakeLumaScale.ppm 20",
            "load res/snakeRedScale.ppm 21",
            "load res/snakeValueScale.ppm 22",
            "load res/snakeVertical.ppm 23"
    ));
    StringBuilder commands = new StringBuilder();

    for (String execution : testFileNamePath) {
      commands.append(execution).append(" ");
    }
    Reader in = new StringReader(commands.toString());

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    for (String execution : testFileNamePath) {
      String[] split = execution.split(" ");
      int[][][] pixels = testModel.getImagePixels(split[2]);
      assertTrue(compareImageData(pixels, split[1]));
    }
  }

  @Test
  public void testRGBCombineWithDifferentDimensions() {
    Reader in = new StringReader(
            load("res/redGreyScale.ppm", "red")
                    + load("res/snakeBlueScale.ppm", "blue")
                    + load("res/greenGreyScale.ppm", "green")
                    + rgbCombine("RGB", "red", "blue", "green")
    );


    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    String[] checkOutputMessages = {
        "load command successful",
        "load command successful",
        "load command successful",
        "The specified images have different dimensions."
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));
  }

  @Test
  public void testCommandBeforeLoad() {

    Reader in = new StringReader(
            horizontal("image1", "image2")
                    + vertical("image2", "image3")
                    + brighten(100, "image3", "image4")
                    + rgbSplit("image5", "image1", "image2", "image3")
                    + rgbCombine("image9", "image6", "image7", "image8")
                    + greyscale("red-component", "image9", "image3")
    );


    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    String[] checkOutputMessages = {
        "Image name 'image1' does not exist.",
        "Image name 'image2' does not exist.",
        "Image name 'image3' does not exist.",
        "Image name 'image5' does not exist.",
        "Image name 'image6' does not exist.",
        "Image name 'image9' does not exist."
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));
  }

  @Test
  public void testGetImagePixels() {
    Reader in = new StringReader(
            load("res/fox.ppm", "fox")
    );

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(out.toString().contains("load command successful"));

    try {
      int[][][] imagePixels = testModel.getImagePixels("fox");
      boolean equality = compareImageData(imagePixels, "res/fox.ppm");
      assertTrue(equality);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testImageZeroNegativeWidthHeight() {

    Reader in;
    Controller controller;

    // Zero Width
    in = new StringReader(load("res/zeroWidthImage.ppm", "test"));
    controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(out.toString().contains("Image width cannot be 0 or negative."));

    // Negative Width
    in = new StringReader(load("res/negativeWidthImage.ppm", "test"));
    controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(out.toString().contains("Image width cannot be 0 or negative."));

    // Zero Height
    in = new StringReader(load("res/zeroHeightImage.ppm", "test"));
    controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(out.toString().contains("Image height cannot be 0 or negative."));

    // Negative Height
    in = new StringReader(load("res/negativeHeightImage.ppm", "test"));
    controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(out.toString().contains("Image height cannot be 0 or negative."));

  }

  @Test
  public void testImageWithMorePixels() {
    // A file with one extra pixel at the end of the PPM file has been given as input here
    Reader in = new StringReader(
            load("res/imageWithMorePixels.ppm", "fox")
    );

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(out.toString().contains("Image data contains more pixels than width or height."));
  }

  @Test
  public void testImageWithLessPixels() {
    // A file with one less pixel at the end of the PPM file has been given as input here
    Reader in = new StringReader(
            load("res/imageWithLessPixels.ppm", "fox")
    );

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(out.toString().contains("Image data contains less pixels than width or height."));
  }


}


