import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import imgeditor.controller.GUIController;
import imgeditor.controller.GUIControllerImpl;
import imgeditor.model.Model;
import imgeditor.model.ReadOnlyModel;
import imgeditor.model.ReadOnlyModelImpl;
import imgeditor.view.GUIView;
import imgeditor.view.TextView;
import imgeditor.view.TextViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * JUnit test class for testing the mock model with the mock view via the controller.
 */
public class MockGuiViewToModelTest {

  int filterNumber = 0;
  int colorNumber = 0;

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

  /**
   * Mock GUI View class.
   */
  public class MockGUIView implements GUIView {

    private final StringBuilder mockLog;

    public MockGUIView(StringBuilder mockLog) {
      this.mockLog = mockLog;
    }

    @Override
    public void displayWarningPopup(String messageAfterExecution) {
      mockLog.append("Warning:" + messageAfterExecution);
    }

    @Override
    public String load() {
      return "res/fox.ppm";
    }

    @Override
    public String save() {
      return "res guiImage.ppm";
    }

    @Override
    public String displayDropdown(String command) {
      return "red-component";
    }

    @Override
    public String getBrightnessValue() {
      return "10";
    }

    @Override
    public String getValue() {
      return "1000";
    }

    @Override
    public String filter() {
      if (filterNumber == 0) {
        filterNumber++;
        return "blur";
      }
      return "sharpen";
    }

    @Override
    public String colorTransformation() {
      if (colorNumber == 0) {
        colorNumber++;
        return "sepia";
      }
      return "greyscale";
    }


    @Override
    public void addFeatures(GUIController features) {
      mockLog.append("Features added\n");
    }

    @Override
    public void displayImage(String imageNameToBeDisplayed) {
      mockLog.append(" DisplayImageName:" + imageNameToBeDisplayed);
    }

    @Override
    public void setExecutionMessage(String currentMessage) {
      mockLog.append(" Execution Message:" + currentMessage);
    }

    @Override
    public void displayHistogram(String imageName) {
      mockLog.append(" Display Histogram:" + imageName);
    }
  }

  public Model model;
  public ReadOnlyModel testModel;
  public TextView textView;
  public Appendable out;
  public StringBuilder mockLog;

  @Before
  public void setUp() {
    mockLog = new StringBuilder();
    model = new MockModel(mockLog);
    testModel = new ReadOnlyModelImpl();
    out = new StringBuilder();
    textView = new TextViewImpl(out);
  }

  @Test
  public void testMock() {
    GUIView jFrameView = new MockGUIView(mockLog);
    GUIController controller = new GUIControllerImpl(model, jFrameView);
    String mockLogExpected = "";

    //test add features of void and setView of controller
    try {
      controller.setView();
      mockLogExpected = "Features added\n";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test load
    try {
      controller.load();
      InputStream input;
      try {
        input = new FileInputStream("res/fox.ppm");
      } catch (IOException e) {
        throw new FileNotFoundException("File Not Found");
      }
      mockLogExpected = mockLogExpected + "InputStream:" + new String(input.readAllBytes())
        + " ImageFormat:.ppm" + " ImageName:guiImage Execution Message:Image Loaded "
        + "Successfully DisplayImageName:guiImage Display Histogram:guiImage";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test horizontal flip
    try {
      controller.horizontalFlip();
      mockLogExpected = mockLogExpected + " OperationName:horizontal-flip ImageName:[guiImage] "
        + "DestImageName:[guiImage] commandArgs:[] DisplayImageName:guiImage Display "
        + "Histogram:guiImage Execution Message:Horizontal Flip successful";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test vertical flip
    try {
      controller.verticalFlip();
      mockLogExpected = mockLogExpected + " OperationName:vertical-flip ImageName:[guiImage] "
        + "DestImageName:[guiImage] commandArgs:[] "
        + "DisplayImageName:guiImage Display "
        + "Histogram:guiImage Execution Message:Vertical Flip successful";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test brighten
    try {
      controller.adjustBrightness();
      mockLogExpected = mockLogExpected + " OperationName:brighten ImageName:[guiImage] "
        + "DestImageName:[guiImage] commandArgs:[10] "
        + "DisplayImageName:guiImage Display "
        + "Histogram:guiImage Execution Message:Brightness Adjusted Successfully";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test greyscale
    try {
      controller.greyscale();
      mockLogExpected = mockLogExpected + " OperationName:greyscale ImageName:[guiImage] "
        + "DestImageName:[guiImage] commandArgs:[red-component] "
        + "DisplayImageName:guiImage Display "
        + "Histogram:guiImage Execution Message:Greyscale successful";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test blur
    try {
      controller.filter();
      mockLogExpected = mockLogExpected + " OperationName:blur ImageName:[guiImage] "
        + "DestImageName:[guiImage] "
        + "commandArgs:[blur] "
        + "DisplayImageName:guiImage Display "
        + "Histogram:guiImage Execution Message:Filter applied successfully";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test sharpen
    try {
      controller.filter();
      mockLogExpected = mockLogExpected + " OperationName:sharpen ImageName:[guiImage] "
        + "DestImageName:[guiImage] "
        + "commandArgs:[sharpen] "
        + "DisplayImageName:guiImage Display "
        + "Histogram:guiImage Execution Message:Filter applied successfully";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test sepia
    try {
      controller.colorTransformation();
      mockLogExpected = mockLogExpected + " OperationName:sepia ImageName:[guiImage] "
        + "DestImageName:[guiImage] "
        + "commandArgs:[sepia] "
        + "DisplayImageName:guiImage Display "
        + "Histogram:guiImage Execution Message:Color transformation applied successfully";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test color transform greyscale
    try {
      controller.colorTransformation();
      mockLogExpected = mockLogExpected + " OperationName:greyscale ImageName:[guiImage] "
        + "DestImageName:[guiImage] "
        + "commandArgs:[luma-component] "
        + "DisplayImageName:guiImage Display "
        + "Histogram:guiImage Execution Message:Color transformation applied successfully";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test dither
    try {
      controller.dither();
      mockLogExpected = mockLogExpected + " OperationName:dither ImageName:[guiImage] "
        + "DestImageName:[guiImage] "
        + "commandArgs:[] "
        + "DisplayImageName:guiImage Display "
        + "Histogram:guiImage Execution Message:Dithering successful";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test rgb-split
    try {
      controller.rgbSplit();
      mockLogExpected = mockLogExpected + " OperationName:rgb-split ImageName:[guiImage] "
        + "DestImageName:[red-component, green-component, blue-component] "
        + "commandArgs:[red-component] ImageFormat:.ppm "
        + "ImageName:red-component"
        + " Execution Message:Image Saved Successfully DisplayImageName:red-component "
        + "Display Histogram:red-component "
        + "ImageFormat:.ppm ImageName:green-component"
        + " Execution Message:Image Saved Successfully DisplayImageName:green-component "
        + "Display Histogram:green-component "
        + "ImageFormat:.ppm ImageName:blue-component"
        + " Execution Message:Image Saved Successfully DisplayImageName:blue-component "
        + "Display Histogram:blue-component Execution Message:RGB Split successful "
        + "DisplayImageName:red-component Display Histogram:red-component";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test rgb-combine
    try {
      controller.rgbCombine();
      for (int i = 0; i < 3; i++) {
        InputStream input;
        try {
          input = new FileInputStream("res/fox.ppm");
        } catch (IOException e) {
          throw new IOException("File Not Found\n");
        }
        mockLogExpected = mockLogExpected + "InputStream:" + new String(input.readAllBytes())
          + " ImageFormat:.ppm" + " ImageName:Component" + (i + 1)
          + " Execution Message:Image Loaded Successfully "
          + "DisplayImageName:Component" + (i + 1) + " Display Histogram:Component" + (i + 1)
          + "";
      }
      mockLogExpected = mockLogExpected + " OperationName:rgb-combine ImageName:[Component1, "
        + "Component2, Component3] "
        + "DestImageName:[red-component] "
        + "commandArgs:[] Execution Message:RGB Combine successful "
        + "DisplayImageName:red-component Display Histogram:red-component";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

  }


}
