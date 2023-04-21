import org.junit.Before;
import org.junit.Test;

import imgeditor.controller.GUIController;
import imgeditor.controller.GUIControllerImpl;
import imgeditor.model.ImageEditorModel;
import imgeditor.model.Model;
import imgeditor.model.ReadOnlyModel;
import imgeditor.model.ReadOnlyModelImpl;
import imgeditor.view.GUIView;
import imgeditor.view.TextView;
import imgeditor.view.TextViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * JUnit test class for the GUIViewImpl class. Contains mock tests for the GUIView.
 */
public class GUIViewImplMockTest {

  /**
   * Mock GUIView class.
   */
  public static class MockGUIView implements GUIView {

    int brighten = 0;

    int value = 0;

    private final StringBuilder mockLog;

    public MockGUIView(StringBuilder mockLog) {
      this.mockLog = mockLog;
    }

    @Override
    public void displayWarningPopup(String messageAfterExecution) {
      mockLog.append("Warning:").append(messageAfterExecution);
    }

    @Override
    public String load() {
      mockLog.append("Load Your File");
      return null;
    }

    @Override
    public String save() {
      mockLog.append("Save Your File");
      return null;
    }

    @Override
    public String displayDropdown(String command) {
      mockLog.append("The command passed is: ").append(command);
      return null;
    }

    @Override
    public String getBrightnessValue() {
      if (brighten == 0) {
        mockLog.append("Adjust Brightness By mockLog");
        brighten++;
        return null;
      }
      return "";
    }

    @Override
    public String getValue() {
      if (value == 0) {
        mockLog.append("Set Seeds By mockLog");
        value++;
        return null;
      }
      return "";
    }

    @Override
    public String filter() {
      mockLog.append("Filter By mockLog");
      return null;
    }

    @Override
    public String colorTransformation() {
      mockLog.append("Color Transformation By mockLog");
      return null;
    }


    @Override
    public void addFeatures(GUIController features) {
      mockLog.append("\nFeatures Added");
    }

    @Override
    public void displayImage(String imageNameToBeDisplayed) {
      mockLog.append("ImageName:").append(imageNameToBeDisplayed);
    }

    @Override
    public void setExecutionMessage(String currentMessage) {
      mockLog.append(" Execution Message:").append(currentMessage);
    }

    @Override
    public void displayHistogram(String imageName) {
      mockLog.append(" Histogram ImageName:").append(imageName);
    }
  }

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
  public void testMock() {

    StringBuilder mockLog = new StringBuilder();
    String mockLogExpected = "";
    GUIView jFrameView = new MockGUIView(mockLog);
    GUIController controller = new GUIControllerImpl(model, jFrameView);

    // test load without parameter
    try {
      controller.load();
      mockLogExpected =
          "Load Your File" + " Execution Message:User Cancelled Operation. Display Not Changed.";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test save without parameter
    try {
      controller.save();
      mockLogExpected = mockLogExpected + "Save Your File"
          + " Execution Message:User Cancelled Operation. Display Not Changed.";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (NullPointerException e) {
      fail();
    }

    // test greyscale
    try {
      controller.greyscale();
      mockLogExpected = mockLogExpected + "The command passed is: greyscale"
          + " Execution Message:User Cancelled Operation. Display Not Changed.";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (NullPointerException e) {
      fail();
    }

    // test rgb-split
    try {
      controller.rgbSplit();
      mockLogExpected = mockLogExpected + "The command passed is: rgb-split"
          + " Execution Message:User Cancelled Operation. Display Not Changed.";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (NullPointerException e) {
      fail();
    }

    // test brighten
    try {
      controller.adjustBrightness();
      mockLogExpected = mockLogExpected + "Adjust Brightness By mockLog"
          + " Execution Message:User Cancelled Operation. Display Not Changed.";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (NullPointerException e) {
      fail();
    }

    // test filter
    try {
      controller.filter();
      mockLogExpected = mockLogExpected + "Filter By mockLog"
          + " Execution Message:User Cancelled Operation. Display Not Changed.";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (Exception e) {
      fail();
    }

    // test color transformation
    try {
      controller.colorTransformation();
      mockLogExpected = mockLogExpected + "Color Transformation By mockLog"
          + " Execution Message:User Cancelled Operation. Display Not Changed.";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (NullPointerException e) {
      fail();
    }

    // test setView i.e. addFeatures
    try {
      controller.setView();
      mockLogExpected = mockLogExpected + "\nFeatures Added";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (NullPointerException e) {
      fail();
    }

    // test displayWarningPopup
    try {
      controller.adjustBrightness();
      mockLogExpected = mockLogExpected + "Warning:Brightness value must be an integer "
          + "Execution Message:Operation failed. Brightness value must be an integer";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (NullPointerException e) {
      fail();
    }

    try {
      controller.mosaic();
      mockLogExpected = mockLogExpected
          + "Set Seeds By mockLog Execution Message:User Cancelled Operation. Display Not Changed.";
      assertEquals(mockLogExpected, mockLog.toString());
    } catch (NullPointerException e) {
      fail();
    }

  }


}
