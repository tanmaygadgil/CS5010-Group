import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import imgeditor.controller.Controller;
import imgeditor.controller.ImageEditorController;
import imgeditor.model.Model;
import imgeditor.model.ImageEditorModel;
import imgeditor.model.ReadOnlyModel;
import imgeditor.model.ReadOnlyModelImpl;
import imgeditor.view.TextViewImpl;
import imgeditor.view.TextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * JUnit test class for testing the execution of commands in a file.
 * This class contains nearly the same tests as the DirectInputTest class,
 * but has the commands stored in a text file so the input to the program can be given
 * through a script file.
 */
public class RunFromScriptTest extends AbstractImageEditorTest {

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
  public void testImageLoad() {
    Reader in = new StringReader("run test/loadFox.txt");
    
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
    Reader in = new StringReader("run test/testImageSave.txt");
    
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
      boolean equality = checkImageEqual("res/savedFox.ppm", "res/fox.ppm");
      assertTrue(equality);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testSameImageOverwrite() {
    Reader in = new StringReader("run test/testSameImageOverwrite.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    assertTrue(checkImageEqual("res/test.ppm", "res/savedFile.ppm"));

    try {
      int[][][] imagePixels = testModel.getImagePixels("testfox");
      assertTrue(compareImageData(imagePixels, "res/test.ppm"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testHorizontalFlipFile() {
    Reader in = new StringReader("run test/horizontal-flip-commands.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/foxFlippedHorizontallyThroughScript.ppm",
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
  public void testVerticalFlipFile() {
    Reader in = new StringReader("run test/vertical-flip-commands.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/foxFlippedVerticallyThroughScript.ppm",
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
  public void testBrightenFile() {
    Reader in = new StringReader("run test/BrightenAndDarken.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/increaseBrightnessThroughScript.ppm",
              "OI/OriginalIncreasedBrightness.ppm"));

      assertTrue(checkImageEqual("res/decreaseBrightnessThroughScript.ppm",
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
    Reader in = new StringReader("run test/greyScale.txt");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/redGreyScaleThroughScript.ppm",
              "OI/OriginalRedGreyScale.ppm"));

      assertTrue(checkImageEqual("res/greenGreyScaleThroughScript.ppm",
              "OI/OriginalGreenGreyScale.ppm"));

      assertTrue(checkImageEqual("res/blueGreyScaleThroughScript.ppm",
              "OI/OriginalBlueGreyScale.ppm"));

      assertTrue(checkImageEqual("res/valueGreyScaleThroughScript.ppm",
              "OI/OriginalValueGreyScale.ppm"));

      assertTrue(checkImageEqual("res/intensityGreyScaleThroughScript.ppm",
              "OI/OriginalIntensityGreyScale.ppm"));

      assertTrue(checkImageEqual("res/lumaGreyScaleThroughScript.ppm",
              "OI/OriginalLumaGreyScale.ppm"));

      assertTrue(checkImageEqual("res/WithoutImageNameGreyScaleThroughScript.ppm",
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
      int[][][] imageWithoutImageNamePixels = testModel.getImagePixels("testW");
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
      boolean withoutImageNameEquality = compareImageData(imageWithoutImageNamePixels,
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
  public void testTypoGreyscaleComponentCommand() {
    Reader in = new StringReader("run test/testTypoInGreyscaleComponent.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    String[] checkOutputMessages = {
        "load command successful",
        "Image name 'value-compnent' does not exist.",
        "Image name 'value' does not exist."
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));


  }

  @Test
  public void testRGBSplitCommands() {
    Reader in = new StringReader("run test/RGBSplit.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/RedSplitThroughScript.ppm",
              "OI/OriginalRedGreyScale.ppm"));
      assertTrue(checkImageEqual("res/GreenSplitThroughScript.ppm",
              "OI/OriginalGreenGreyScale.ppm"));
      assertTrue(checkImageEqual("res/BlueSplitThroughScript.ppm",
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
    Reader in = new StringReader("run test/RGBCombine.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/RedCombineThroughScript.ppm",
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

  @Test
  public void testCombinations1() {
    Reader in = new StringReader("run test/testCombinations1.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(checkImageEqual("res/LHSRed.ppm",
              "OI/LOAD-HF-RED.ppm"));
      assertTrue(checkImageEqual("res/LHSGreen.ppm",
              "OI/LOAD-HF-GREEN.ppm"));
      assertTrue(checkImageEqual("res/LHSBlue.ppm",
              "OI/LOAD-HF-BLUE.ppm"));
      assertTrue(checkImageEqual("res/LVB.ppm",
              "OI/LOAD-VF-B50.ppm"));
      assertTrue(checkImageEqual("res/LVBD.ppm",
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
    Reader in = new StringReader("run test/testCombinations2.txt");
    
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
  public void testTypoLoad() {
    Reader in = new StringReader("run test/loadTypoThroughScript.txt");
    
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
    Reader in = new StringReader("run test/saveTypoThroughScript.txt");
    
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
    Reader in = new StringReader("run test/testTypoGreyscaleThroughScript.txt");
    
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

    String[] invalidGreyscaleCommands = {"run test/testTypoGreyscaleArgument.txt"};

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
    Reader in = new StringReader("run test/testTypoBrighten.txt");
    
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
    Reader in = new StringReader("run test/testTypoHorizontalFlip.txt");
    
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
    Reader in = new StringReader("run test/testTypoVerticalFlip.txt");
    
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
    Reader in = new StringReader("run test/testTypoRGBSplit.txt");
    
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
    Reader in = new StringReader("run test/testTypoRGBCombine.txt");
    
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
    Reader in = new StringReader("run test/testSaveDestinationFolderNotExists.txt");
    
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
    Reader in = new StringReader("run test/testSaveInvalidFileType.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Unsupported file format.\n"));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testEmptyCommandFile() {
    Reader in = new StringReader("run test/noCommands.txt");

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
    Reader in = new StringReader("run test/testWhitespaceCommand.txt");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().isEmpty());
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testCommandWithDifferentCase() {
    Reader in = new StringReader("run test/testCommandWithDifferentCase.txt");

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
    Reader in = new StringReader("run test/testCommandWithComments.txt");

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
  public void testWrongCommandsInFile() {
    Reader in = new StringReader("run test/invalidCmds.txt");

    Controller controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
      assertTrue(out.toString().contains("Command 'loadnew' not found."));
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testWrongFileExtensionLoad() {
    Reader in = new StringReader("run test/testWrongFileExtensionLoad.txt");

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
    Reader in = new StringReader("run test/testWrongFileExtensionSave.txt");

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
    Reader in = new StringReader("run test/testWrongImageName.txt");
    
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
        "Image name 'foxABC' does not exist.",
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));
  }

  @Test
  public void testBrightenValueNotAnInteger() {

    String[] invalidBrightenCommands = {"run test/testBrightenValueNotAnInteger.txt"};

    for (String invalidBrightenCommand : invalidBrightenCommands) {
      Reader in = new StringReader(invalidBrightenCommand);
      
      Controller controller = new ImageEditorController(in, model, textView);

      try {
        controller.execute();
        assertTrue(out.toString().contains("Brightness value must be an integer."));
      } catch (FileNotFoundException e) {
        fail();
      }
    }
  }

  @Test
  public void testBrightnessMaxValue() {
    Reader in = new StringReader("run test/testBrightnessMaxValue.txt");
    
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
    Reader in = new StringReader("run test/testBrightnessMinValue.txt");
    
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
  public void testSpacesAfterCommand() {
    Reader in = new StringReader("run test/testSpacesAfterCommand.txt");
    
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
  public void loadingMultipleImagesAndOverWritingImageNames() {
    // first load overWrites second load while third load creates different object for same
    // second image.
    Reader in = new StringReader("run test/loadingMultipleImagesAndOverWritingImageNames1.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    int[][][] imageLoadFoxPixels = testModel.getImagePixels("test");


    in = new StringReader("run test/loadingMultipleImagesAndOverWritingImageNames2.txt");
    controller = new ImageEditorController(in, model, textView);

    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    int[][][] imageLoadSnakePixels = testModel.getImagePixels("test");
    // "test" imgName gets overWritten by Snake.ppm from Fox.ppm
    assertNotEquals(imageLoadFoxPixels, imageLoadSnakePixels);

    try {
      // test imgName overloaded with snake.ppm
      int[][][] imageLoadSnakeVFPixels = testModel.getImagePixels("testV");
      //pixels of different imgName of same Snake image
      int[][][] imageLoadNewSnakePixels = testModel.getImagePixels("test1");
      boolean equality = compareImageData(imageLoadSnakePixels,
              "OI/snake.ppm");
      boolean equality1 = compareImageData(imageLoadSnakeVFPixels,
              "OI/snakeVertical.ppm");
      // different imgName have same pixels for same image
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
  public void testFileMoreThanMaximum() {
    Reader in = new StringReader("run test/FileMoreThanMaximum.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertEquals(out.toString(), "Pixel value greater than maximum");
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

    Reader in = new StringReader("run test/testMultipleLoads.txt");
    
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
            "run test/testRGBCombineWithDifferentDimensions.txt"
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
    Reader in = new StringReader("run test/testCommandBeforeLoad.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }

    String[] checkOutputMessages = {
        "Image name 'image1' does not exist.",
        "Image name 'image1' does not exist.",
        "Image name 'image3' does not exist.",
        "Image name 'image1' does not exist.",
        "Image name 'image6' does not exist.",
        "Image name 'image9' does not exist."
    };
    assertTrue(containsAll(out.toString(), checkOutputMessages));

  }

  @Test
  public void testFileNotHavingP3() {
    Reader in = new StringReader("run test/FileNotHavingP3.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(out.toString().contains("Invalid PPM file: plain RAW file should begin with P3"));
  }

  @Test
  public void testImageZeroNegativeWidthHeight() {
    Reader in = new StringReader("run test/testImageZeroNegativeWidthHeight.txt");
    
    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(out.toString().contains("Image width cannot be 0 or negative."));
  }

  @Test
  public void testImageWithMorePixels() {
    Reader in = new StringReader("run test/testImageWithMorePixels.txt");
    

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
    Reader in = new StringReader("run test/testImageWithLessPixels.txt");
    

    Controller controller = new ImageEditorController(in, model, textView);
    try {
      controller.execute();
    } catch (FileNotFoundException e) {
      fail();
    }
    assertTrue(out.toString().contains("Image data contains less pixels than width or height."));
  }

}