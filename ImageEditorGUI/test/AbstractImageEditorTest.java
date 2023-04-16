import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class is an abstract test class for the other test classes.
 * It contains helper methods used and shared by the other classes.
 * This class helps in reading a file, comparing pixel arrays and comparing two image files.
 */
public abstract class AbstractImageEditorTest {

  /**
   * Read a PPM file from a given file path.
   *
   * @param filePath path to read the file from
   * @return StringBuilder with the raw PPM file contents
   */

  protected StringBuilder readPPMFile(String filePath) {
    File file = new File(filePath);
    Scanner fileScanner = null;
    try {
      fileScanner = new Scanner(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      fail();
    }

    StringBuilder fileBuilder = new StringBuilder();

    while (fileScanner.hasNext()) {
      String fileString = fileScanner.next();
      if (fileString.charAt(0) != '#') {
        fileBuilder.append(fileString).append(System.lineSeparator());
      } else if (fileScanner.hasNextLine()) {
        fileScanner.nextLine();
      } else {
        break;
      }
    }
    return fileBuilder;
  }

  /**
   * Checks if two images are equal, given their file paths.
   *
   * @param img1FilePath path to the first image file
   * @param img2FilePath path to the second image file
   * @return true if both the images are equal, false otherwise
   */
  protected boolean checkImageEqual(String img1FilePath, String img2FilePath) {

    StringBuilder originalFileBuilder = readPPMFile(img1FilePath);
    StringBuilder processedFileBuilder = readPPMFile(img2FilePath);

    return originalFileBuilder.toString().equals(processedFileBuilder.toString());
  }

  /**
   * Checks if the pixels of one image are equal to another image, given the first image's pixels
   * and the second image's file path. This method loads the second image and compares the pixels.
   *
   * @param imagePixels the pixel array of the first image
   * @param filePath    path to the second image file
   * @return true if the pixels of both images are equal, false otherwise
   */

  protected boolean compareImageData(int[][][] imagePixels, String filePath) {
    StringBuilder originalFile = readPPMFile(filePath);
    StringBuilder originalFilePixels = new StringBuilder();
    StringBuilder imagePixelsString = new StringBuilder();
    StringBuilder imageData = new StringBuilder();

    Scanner sc = null;
    try {
      sc = new Scanner(originalFile.toString());
      for (int i = 0; i < 4; i++) {
        imageData.append(sc.nextLine()).append(System.lineSeparator());
      }
    } catch (Exception e) {
      fail();
    }

    while (sc.hasNext()) {
      String contents = sc.next();
      originalFilePixels.append(contents).append(System.lineSeparator());
    }

    String[] imageDataCompare = imageData.toString().split("\n");

    assertEquals(imageDataCompare[1], String.valueOf(imagePixels.length));
    assertEquals(imageDataCompare[2], String.valueOf(imagePixels[0].length));

    for (int i = 0; i < imagePixels[0].length; i++) {
      for (int j = 0; j < imagePixels.length; j++) {
        for (int k = 0; k < imagePixels[0][0].length; k++) {
          if (imagePixels[j][i][k] > Integer.parseInt(imageDataCompare[3])) {
            fail("Pixel value greater than Max Value");
          }
          imagePixelsString.append(imagePixels[j][i][k]).append("\n");
        }
      }
    }

    return (imagePixelsString.toString().equals(originalFilePixels.toString()));
  }

  protected boolean compareIOPixels(int[][][] pixels, String filepath) throws IOException {
    BufferedImage image = ImageIO.read(new FileInputStream(filepath));

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        Color color = new Color(image.getRGB(x, y));
        if (pixels[x][y][0] != color.getRed()) {
          return false;
        }
        if (pixels[x][y][1] != color.getGreen()) {
          return false;
        }
        if (pixels[x][y][2] != color.getBlue()) {
          return false;
        }
      }
    }
    return true;
  }

  protected boolean compareIOFiles(String savedFile, String originalFile) throws IOException {
    BufferedImage savedImage = ImageIO.read(new FileInputStream(savedFile));
    BufferedImage originalImage = ImageIO.read(new FileInputStream(originalFile));

    for (int y = 0; y < originalImage.getHeight(); y++) {
      for (int x = 0; x < originalImage.getWidth(); x++) {
        Color originalColor = new Color(originalImage.getRGB(x, y));
        Color savedColor = new Color(savedImage.getRGB(x, y));
        if (originalColor.getRed() != savedColor.getRed()) {
          return false;
        }
        if (originalColor.getGreen() != savedColor.getGreen()) {
          return false;
        }
        if (originalColor.getBlue() != savedColor.getBlue()) {
          return false;
        }
      }
    }
    return true;
  }

  protected String getTempFilePath(String fileExtension) throws IOException {
    File resDir = new File("res/");
    File tempFile = File.createTempFile("tempImage-", fileExtension, resDir);

    // Ensures the file is deleted once the unit test has completed
    tempFile.deleteOnExit();

    return tempFile.getPath();
  }

  protected int countSubstringOccurrences(String str, String substr) {
    int count = 0;
    int searchIndex = 0;
    while (searchIndex != -1) {
      searchIndex = str.indexOf(substr, searchIndex);
      if (searchIndex != -1) {
        count += 1;
        searchIndex += 1;
      }
    }
    return count;
  }

  protected boolean containsAll(String s, String[] checkStrs) {
    for (String checkStr : checkStrs) {
      if (!s.contains(checkStr)) {
        return false;
      }
    }
    return true;
  }

  /*
  The helper methods below create the command string for ease of use during testing.
   */

  protected String load(String imgPath, String imgName) {
    return String.format(" load %s %s\n", imgPath, imgName);
  }

  protected String save(String imgPath, String imgName) {
    return String.format(" save %s %s\n", imgPath, imgName);
  }

  protected String brighten(int adjustBrightness, String imgName, String destImgName) {
    return String.format(" brighten %d %s %s\n", adjustBrightness, imgName, destImgName);
  }

  protected String greyscale(String component, String imgName, String destImgName) {
    return String.format(" greyscale %s %s %s\n", component, imgName, destImgName);
  }

  protected String rgbSplit(String imgName, String destRed, String destGreen, String destBlue) {
    return String.format(" rgb-split %s %s %s %s\n", imgName, destRed, destGreen, destBlue);
  }

  protected String rgbCombine(String destImgName, String destRed,
                              String destGreen, String destBlue) {
    return String.format(" rgb-combine %s %s %s %s\n", destImgName, destRed, destGreen, destBlue);
  }

  protected String horizontal(String imgName, String destImgName) {
    return String.format(" horizontal-flip %s %s\n", imgName, destImgName);
  }

  protected String vertical(String imgName, String destImgName) {
    return String.format(" vertical-flip %s %s\n", imgName, destImgName);
  }

  protected String blur(String imgName, String destImgName) {
    return String.format(" blur %s %s\n", imgName, destImgName);
  }

  protected String sharpen(String imgName, String destImgName) {
    return String.format(" sharpen %s %s\n", imgName, destImgName);
  }

  protected String sepia(String imgName, String destImgName) {
    return String.format(" sepia %s %s\n", imgName, destImgName);
  }

  protected String dither(String imgName, String destImgName) {
    return String.format(" dither %s %s\n", imgName, destImgName);
  }

  protected String run(String scriptPath) {
    return String.format(" run %s\n", scriptPath);
  }


}
