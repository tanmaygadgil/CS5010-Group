package view;

import controller.Features;

public interface IGUIView extends View {

  void addFeatures(Features features);

  void reset();

}
