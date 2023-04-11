package controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Scanner;
import model.MockModel;
import model.MockModelImpl;
import model.ModelV2;
import model.ModelV2Impl;
import model.ViewModel;
import model.ViewModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.GUIView;
import view.IGUIView;
import view.MockScriptView;
import view.MockUIView;
import view.View;

public class UIControllerTest {

  ModelV2 model;
  MockModel mockModel;
  ViewModel vm;
  IGUIView view;
  IGUIView mockView;
  Controller controller;

  @Before
  public void setUp() {
    mockModel = new MockModelImpl();
    mockView = new MockUIView();
  }

  @Test
  public void testSave() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callSave("filePath.jpg", "imageName");
    assertEquals(mockModel.getLog(), "In function save with arguments imageName, jpg\n");
  }

  @Test
  public void testLoad() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callLoad("filePath.jpg", "imageName");
    assertEquals(mockModel.getLog(), "In function load with arguments imageName, jpg\n");
  }

  @Test
  public void testDarken() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callModel(new String[]{"darken", "10", "source", "dest"});
    assertEquals(mockModel.getLog(), "In function darken with arguments 10, source, dest\n");
  }

  @Test
  public void testBrighten() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callModel(new String[]{"brighten", "10", "source", "dest"});
    assertEquals(mockModel.getLog(), "In function brighten with arguments 10, source, dest\n");
  }

  @Test
  public void testHorizontalFlip() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callModel(new String[]{"horizontal-flip", "source", "dest"});
    assertEquals(mockModel.getLog(), "In function flip with arguments HORIZONTAL, source, dest\n");
  }

  @Test
  public void testVerticalFlip() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callModel(new String[]{"vertical-flip", "source", "dest"});
    assertEquals(mockModel.getLog(), "In function flip with arguments VERTICAL, source, dest\n");
  }

  @Test
  public void testRGBSplit() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callModel(new String[]{"rgb-split", "source", "red", "green", "blue"});
    assertEquals(mockModel.getLog(),
        "In function rgbSplit with arguments source, red, green, blue\n");
  }

  @Test
  public void testRGBCombine() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callModel(new String[]{"rgb-combine", "dest", "red", "green", "blue"});
    assertEquals(mockModel.getLog(),
        "In function rgbCombine with arguments dest, red, green, blue\n");
  }


  @Test
  public void testGaussian() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callModel(new String[]{"gaussian-blur", "source", "dest"});
    assertEquals(mockModel.getLog(),
        "In function callFilter with arguments GaussianBlur, source, dest\n");
  }

  @Test
  public void testSharpen() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callModel(new String[]{"sharpen", "source", "dest"});
    assertEquals(mockModel.getLog(),
        "In function callFilter with arguments Sharpening, source, dest\n");
  }

  @Test
  public void testGreyscaleTransform() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callModel(new String[]{"greyscale", "source", "dest"});
    System.out.println(mockModel.getLog());
    assertEquals(mockModel.getLog(),
        "In function callTransform with arguments Greyscale Transform, source, dest\n");
  }

  @Test
  public void testDither() throws IOException {
    UIController controller = new UIController(mockModel, mockView);
    controller.callModel(new String[]{"dither", "source", "dest"});
    assertEquals(mockModel.getLog(),
        "In function callTransform with arguments Greyscale Transform, source, dest\n"
            + "In function callOperation with arguments DitherOperation, dest, dest\n");
  }

}
