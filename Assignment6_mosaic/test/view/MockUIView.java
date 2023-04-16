package view;

import controller.Features;

/**
 * A mock model for the GUI used for testing.
 */
public class MockUIView implements IGUIView {

  @Override
  public void addFeatures(Features features) {
    //Left blank for the mock
  }

  @Override
  public String getInput() {
    return null;
  }

  @Override
  public void renderOutput(String inputString) {
    //Left blank for the mock
  }

  @Override
  public void reset() {
    //Left blank for the mock
  }
}
