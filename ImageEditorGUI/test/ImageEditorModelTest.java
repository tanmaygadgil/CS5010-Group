import org.junit.Before;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import imgeditor.model.ImageEditorModel;
import imgeditor.model.Model;
import imgeditor.model.ReadOnlyModel;
import imgeditor.model.ReadOnlyModelImpl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * JUnit test class for testing the model implementation.
 */
public class ImageEditorModelTest extends AbstractImageEditorTest {

  public Model model;
  public ReadOnlyModel testModel;

  @Before
  public void setup() {
    model = new ImageEditorModel();
    testModel = new ReadOnlyModelImpl();
  }


  @Test
  public void testLoad() throws IOException {

    InputStream ppmIn = new FileInputStream("res/fox.ppm");
    InputStream jpgIn = new FileInputStream("res/fox.jpg");
    InputStream pngIn = new FileInputStream("res/fox.png");
    InputStream bmpIn = new FileInputStream("res/fox.bmp");

    model.load(ppmIn, ".ppm", "foxP");
    model.load(jpgIn, ".jpg", "foxJ");
    model.load(pngIn, ".png", "foxPN");
    model.load(bmpIn, ".bmp", "foxB");

    assertTrue(compareIOPixels(testModel.getImagePixels("foxPN"), "res/fox.png"));
    assertTrue(compareIOPixels(testModel.getImagePixels("foxB"), "res/fox.bmp"));
    assertTrue(compareImageData(testModel.getImagePixels("foxP"), "res/fox.ppm"));
  }

  @Test(expected = FileNotFoundException.class)
  public void testLoadInvalidFilePath() throws IOException {
    new FileInputStream("res/invalidImage.ppm");
  }

  @Test(expected = FileNotFoundException.class)
  public void testLoadInvalidFileExtension() throws IOException {
    InputStream in = new FileInputStream("res/invalidImage.pm");
    model.load(in, ".ppm", "test");
  }

  @Test
  public void testSave() throws IOException {
    String tempFilePathPPM;
    String tempFilePathJPG;
    String tempFilePathBMP;
    String tempFilePathPNG;
    try {
      tempFilePathPPM = getTempFilePath(".ppm");
      tempFilePathJPG = getTempFilePath(".jpg");
      tempFilePathBMP = getTempFilePath(".bmp");
      tempFilePathPNG = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // from ppm
    InputStream in = new FileInputStream("res/fox.ppm");
    OutputStream outppm = new BufferedOutputStream(new FileOutputStream(tempFilePathPPM));
    OutputStream outjpg = new BufferedOutputStream(new FileOutputStream(tempFilePathJPG));
    OutputStream outpng = new BufferedOutputStream(new FileOutputStream(tempFilePathBMP));
    OutputStream outbmp = new BufferedOutputStream(new FileOutputStream(tempFilePathPNG));

    model.load(in, ".ppm", "fox");
    model.save(outppm, ".ppm", "fox");
    model.save(outjpg, ".jpg", "fox");
    model.save(outpng, ".png", "fox");
    model.save(outbmp, ".bmp", "fox");
    assertTrue(checkImageEqual(tempFilePathPPM, "res/fox.ppm"));
    assertTrue(compareIOFiles(tempFilePathBMP, "res/fox.bmp"));
    assertTrue(compareIOFiles(tempFilePathPNG, "res/fox.png"));

    //from bmp
    in = new FileInputStream("res/fox.bmp");
    outppm = new BufferedOutputStream(new FileOutputStream(tempFilePathPPM));
    outjpg = new BufferedOutputStream(new FileOutputStream(tempFilePathJPG));
    outpng = new BufferedOutputStream(new FileOutputStream(tempFilePathBMP));
    outbmp = new BufferedOutputStream(new FileOutputStream(tempFilePathPNG));
    model.load(in, ".bmp", "fox");
    model.save(outppm, ".ppm", "fox");
    model.save(outjpg, ".jpg", "fox");
    model.save(outpng, ".png", "fox");
    model.save(outbmp, ".bmp", "fox");
    assertTrue(checkImageEqual(tempFilePathPPM, "res/fox.ppm"));
    assertTrue(compareIOFiles(tempFilePathBMP, "res/fox.bmp"));
    assertTrue(compareIOFiles(tempFilePathPNG, "res/fox.png"));

    //from png
    in = new FileInputStream("res/fox.png");
    outppm = new BufferedOutputStream(new FileOutputStream(tempFilePathPPM));
    outjpg = new BufferedOutputStream(new FileOutputStream(tempFilePathJPG));
    outpng = new BufferedOutputStream(new FileOutputStream(tempFilePathBMP));
    outbmp = new BufferedOutputStream(new FileOutputStream(tempFilePathPNG));
    model.load(in, ".png", "fox");
    model.save(outppm, ".ppm", "fox");
    model.save(outjpg, ".jpg", "fox");
    model.save(outpng, ".png", "fox");
    model.save(outbmp, ".bmp", "fox");
    assertTrue(checkImageEqual(tempFilePathPPM, "res/fox.ppm"));
    assertTrue(compareIOFiles(tempFilePathBMP, "res/fox.bmp"));
    assertTrue(compareIOFiles(tempFilePathPNG, "res/fox.png"));

  }

  @Test
  public void testBlur() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    InputStream in = new FileInputStream("res/fox.png");

    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".png", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("foxB");
    model.operate("blur", imgName, destImgName, "blur");
    model.save(out, ".bmp", "foxB");

    assertTrue(compareIOFiles(tempFilePath, "bmpScripts/Blur.bmp"));

    assertTrue(compareIOPixels(testModel.getImagePixels("foxB"), "bmpScripts/Blur.bmp"));
  }

  @Test
  public void testSharpen() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    InputStream in = new FileInputStream("res/fox.bmp");

    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".bmp", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("foxB");
    model.operate("sharpen", imgName, destImgName, "sharpen");
    model.save(out, ".png", "foxB");

    assertTrue(compareIOFiles(tempFilePath, "pngScripts/Sharpen.png"));

    assertTrue(compareIOPixels(testModel.getImagePixels("foxB"), "pngScripts/Sharpen.png"));
  }

  @Test
  public void testSepia() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    InputStream in = new FileInputStream("res/fox.bmp");

    model.load(in, ".bmp", "fox");

    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("foxB");
    model.operate("sepia", imgName, destImgName, "sepia");

    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.save(out, ".ppm", "foxB");

    assertTrue(checkImageEqual(tempFilePath, "ppmScripts/Sepia.ppm"));

    assertTrue(compareImageData(testModel.getImagePixels("foxB"), "ppmScripts/Sepia.ppm"));
  }

  @Test
  public void testDither() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    InputStream in = new FileInputStream("res/fox.ppm");

    model.load(in, ".ppm", "fox");

    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("foxB");
    model.operate("dither", imgName, destImgName, "");

    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.save(out, ".bmp", "foxB");

    assertTrue(compareIOFiles(tempFilePath, "bmpScripts/Dither.bmp"));

    assertTrue(compareIOPixels(testModel.getImagePixels("foxB"), "bmpScripts/Dither.bmp"));
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
    InputStream in = new FileInputStream("res/fox.bmp");

    model.load(in, ".bmp", "fox");

    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("foxB");
    destImgName.add("foxB1");
    destImgName.add("foxB2");
    model.operate("rgb-split", imgName, destImgName, "");

    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath1));
    OutputStream out1 = new BufferedOutputStream(new FileOutputStream(tempFilePath2));
    OutputStream out2 = new BufferedOutputStream(new FileOutputStream(tempFilePath3));

    model.save(out, ".png", "foxB");
    model.save(out1, ".png", "foxB1");
    model.save(out2, ".png", "foxB2");

    assertTrue(compareIOFiles(tempFilePath1, "pngScripts/RedSplit.png"));
    assertTrue(compareIOFiles(tempFilePath2, "pngScripts/GreenSplit.png"));
    assertTrue(compareIOFiles(tempFilePath3, "pngScripts/BlueSplit.png"));

    assertTrue(compareIOPixels(testModel.getImagePixels("foxB"), "pngScripts/RedSplit.png"));

    assertTrue(compareIOPixels(testModel.getImagePixels("foxB1"), "pngScripts/GreenSplit.png"));

    assertTrue(compareIOPixels(testModel.getImagePixels("foxB2"), "pngScripts/BlueSplit.png"));
  }

  @Test
  public void testRGBCombine() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    InputStream in = new FileInputStream("res/fox.jpg");
    model.load(in, ".jpg", "fox");

    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("foxB");
    destImgName.add("foxB1");
    destImgName.add("foxB2");

    model.operate("rgb-split", imgName, destImgName, "");

    imgName.clear();
    imgName.add("foxB");
    imgName.add("foxB1");
    imgName.add("foxB2");
    destImgName.clear();
    destImgName.add("foxC");

    model.operate("rgb-combine", imgName, destImgName, "");

    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.save(out, ".png", "foxC");

    assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRGBCombinePNG.png"));

    //pixels testing
    int[][][] imageColor = testModel.getImagePixels("foxC");
    assertTrue(compareIOPixels(imageColor, "OI/OriginalRGBCombinePNG.png"));
  }

  @Test
  public void testBrighten() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    InputStream in = new FileInputStream("res/fox.bmp");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".bmp", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-bright");
    model.operate("brighten", imgName, destImgName, "50");
    model.save(out, ".ppm", "fox-bright");

    try {
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalIncreasedBrightness.ppm"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testDarken() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    InputStream in = new FileInputStream("res/fox.bmp");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".bmp", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-dark");
    model.operate("brighten", imgName, destImgName, "-100");
    model.save(out, ".png", "fox-dark");

    try {
      assertTrue(compareIOFiles(tempFilePath, "OI/OriginalDarkened.png"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }


  @Test
  public void testGreyscaleValueComponent() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".jpg");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    InputStream in = new FileInputStream("res/fox.jpg");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));
    model.load(in, ".jpg", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-grey-value");
    model.operate("greyscale", imgName, destImgName, "value-component");
    model.save(out, ".jpg", "fox-grey-value");

    try {
      assertTrue(compareIOFiles(tempFilePath, "OI/OriginalValueGS.jpg"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testGreyscaleRedComponent() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    InputStream in = new FileInputStream("res/fox.png");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".png", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-grey-red");
    model.operate("greyscale", imgName, destImgName, "red-component");
    model.save(out, ".png", "fox-grey-red");

    try {
      assertTrue(compareIOFiles(tempFilePath, "OI/OriginalRedGS.png"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testGreyscaleGreenComponent() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".bmp");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    InputStream in = new FileInputStream("res/fox.bmp");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".bmp", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-grey-green");
    model.operate("greyscale", imgName, destImgName, "green-component");
    model.save(out, ".bmp", "fox-grey-green");

    try {
      assertTrue(compareIOFiles(tempFilePath, "OI/OriginalGreenGS.bmp"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testGreyscaleBlueComponent() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    InputStream in = new FileInputStream("res/fox.ppm");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".ppm", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-grey-blue");
    model.operate("greyscale", imgName, destImgName, "blue-component");
    model.save(out, ".png", "fox-grey-blue");

    try {
      assertTrue(compareIOFiles(tempFilePath, "OI/OriginalBlueGS.png"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testGreyscaleIntensityComponent() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    InputStream in = new FileInputStream("res/fox.bmp");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".bmp", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-grey-intensity");
    model.operate("greyscale", imgName, destImgName, "intensity-component");
    model.save(out, ".png", "fox-grey-intensity");

    try {
      assertTrue(compareIOFiles(tempFilePath, "OI/OriginalIntensityGS.png"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testGreyscaleLumaComponent() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    InputStream in = new FileInputStream("res/fox.png");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".png", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-grey-luma");
    model.operate("greyscale", imgName, destImgName, "luma-component");
    model.save(out, ".ppm", "fox-grey-luma");

    try {
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalLumaGreyScale.ppm"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }


  @Test
  public void testHorizontalFlip() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    InputStream in = new FileInputStream("res/fox.ppm");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".ppm", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-hf");
    model.operate("horizontal-flip", imgName, destImgName, "");
    model.save(out, ".png", "fox-hf");

    try {
      assertTrue(compareIOFiles(tempFilePath, "OI/OriginalHorizontal.png"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testVerticalFlip() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    InputStream in = new FileInputStream("res/fox.png");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".png", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-vf");
    model.operate("vertical-flip", imgName, destImgName, "");
    model.save(out, ".ppm", "fox-vf");

    try {
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalVertical.ppm"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testMosaic() throws IOException {
    String tempFilePath;
    try {
      tempFilePath = getTempFilePath(".ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    InputStream in = new FileInputStream("res/fox.png");
    OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

    model.load(in, ".png", "fox");
    List<String> imgName = new ArrayList<>();
    imgName.add("fox");
    List<String> destImgName = new ArrayList<>();
    destImgName.add("fox-mosaic");
    model.operate("mosaic", imgName, destImgName, "0", "four-corners");
    model.save(out, ".ppm", "fox-mosaic");

    try {
      assertTrue(checkImageEqual(tempFilePath, "OI/OriginalMosaicFourCorners.ppm"));
    } catch (IllegalArgumentException e) {
      fail();
    }
  }
}