package imgeditor.view;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import imgeditor.controller.GUIController;
import imgeditor.model.ReadOnlyModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * This class represents an implementation of the GUIView. It contains methods for setting up the
 * GUI frames and panels, along with methods for displaying dialogs and menus for each operation
 * along with displaying the image and histogram.
 */
public class GUIViewImpl extends JFrame implements GUIView {

  private final ReadOnlyModel rModel;
  private final JPanel outerPanel;
  private ImageIcon imageIcon;
  private JLabel imageLabel;
  private JButton loadButton;
  private JButton saveButton;
  private JButton ditherButton;
  private JButton filterButton;
  private JButton brightenButton;
  private JButton colorTransformationButton;
  private JButton rgbSplitButton;
  private JButton rgbCombineButton;
  private JButton horizontalFlipButton;
  private JButton verticalFlipButton;
  private JButton greyscaleButton;
  private JButton mosaicButton;
  private JPanel innerNorthPanel;
  private JPanel imagePanel;
  private JPanel executionMessagePanel;
  private JLabel executionMessageLabel;

  /**
   * Construct a GUIView object.
   *
   * @param caption the title of the outermost frame window
   * @param rModel  the ReadOnlyModel object used to retrieve a copy of the image pixel array
   */
  public GUIViewImpl(String caption, ReadOnlyModel rModel) {

    super(caption);
    this.rModel = rModel;

    // Set size and location
    setSize(1000, 750);
    setLocation(200, 100);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Outer JPanel with BoxLayout
    outerPanel = new JPanel();
    outerPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    // Setup all panels
    setupHistogramPanel(gbc);
    setupImagePanel(gbc);
    setupExecutionMessagePanel(gbc);
    setupButtonPanel(gbc);

    // Set content pane and visible
    this.add(outerPanel);
    setVisible(true);
  }

  private void setupHistogramPanel(GridBagConstraints gbc) {
    innerNorthPanel = new JPanel();
    innerNorthPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    innerNorthPanel.setLayout(new BoxLayout(innerNorthPanel, BoxLayout.PAGE_AXIS));

    innerNorthPanel.add(new JPanel());

    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1;
    gbc.weighty = 0.45;

    outerPanel.add(innerNorthPanel, gbc);
  }

  private void setupImagePanel(GridBagConstraints gbc) {
    imagePanel = new JPanel();
    imagePanel.setLayout(new BorderLayout());
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));

    imageIcon = new ImageIcon();
    imageLabel = new JLabel(imageIcon);

    imagePanel.add(imageLabel, BorderLayout.CENTER);

    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1;
    gbc.weighty = 0.35;

    gbc.gridy = 1; // Set the grid row to 1
    outerPanel.add(imagePanel, gbc);
  }

  private void setupExecutionMessagePanel(GridBagConstraints gbc) {
    executionMessagePanel = new JPanel();
    executionMessageLabel = new JLabel("Execution Not Started Yet");

    executionMessagePanel.add(executionMessageLabel);

    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1;
    gbc.weighty = 0.05;

    gbc.gridy = 2; // Set the grid row to 1
    outerPanel.add(executionMessagePanel, gbc);

  }

  private void setupButtonPanel(GridBagConstraints gbc) {

    JPanel buttonPanel = new JPanel(new FlowLayout());

    // Load Button
    loadButton = new JButton("Load");
    loadButton.setActionCommand("load");
    buttonPanel.add(loadButton);

    // Save Button
    saveButton = new JButton("Save");
    saveButton.setActionCommand("save");
    buttonPanel.add(saveButton);

    // brighten Button
    brightenButton = new JButton("Adjust Brightness");
    brightenButton.setActionCommand("brighten");
    buttonPanel.add(brightenButton);

    // dither Button
    ditherButton = new JButton("Dither");
    ditherButton.setActionCommand("dither");
    buttonPanel.add(ditherButton);

    // greyscale Button
    greyscaleButton = new JButton("Greyscale");
    greyscaleButton.setActionCommand("greyscale");
    buttonPanel.add(greyscaleButton);

    // Horizontal flip Button
    horizontalFlipButton = new JButton("Horizontal Flip");
    horizontalFlipButton.setActionCommand("horizontal-flip");
    buttonPanel.add(horizontalFlipButton);

    // Vertical flip Button
    verticalFlipButton = new JButton("Vertical Flip");
    verticalFlipButton.setActionCommand("vertical-flip");
    buttonPanel.add(verticalFlipButton);

    // RGB Combine Button
    rgbCombineButton = new JButton("RGB Combine");
    rgbCombineButton.setActionCommand("rgb-combine");
    buttonPanel.add(rgbCombineButton);

    // RGB Split Button
    rgbSplitButton = new JButton("RGB Split");
    rgbSplitButton.setActionCommand("rgb-split");
    buttonPanel.add(rgbSplitButton);

    // Sepia Button
    colorTransformationButton = new JButton("Apply Color Transformation");
    colorTransformationButton.setActionCommand("color transformation");
    buttonPanel.add(colorTransformationButton);

    // Filter Button
    filterButton = new JButton("Apply Filter");
    filterButton.setActionCommand("filter");
    buttonPanel.add(filterButton);

    //Mosaic Button
    mosaicButton = new JButton("Mosaic");
    mosaicButton.setActionCommand("mosaic");
    buttonPanel.add(mosaicButton);

    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1;
    gbc.weighty = 0.1;

    gbc.gridy = 3; // Set the grid row to 2
    outerPanel.add(buttonPanel, gbc);

    setButtonsVisibility(false);
  }

  private void setButtonsVisibility(boolean isVisible) {
    JButton[] buttons = {saveButton, brightenButton, filterButton, ditherButton, greyscaleButton,
        horizontalFlipButton, verticalFlipButton, rgbCombineButton, rgbSplitButton,
        colorTransformationButton, mosaicButton};

    for (JButton button : buttons) {
      button.setEnabled(isVisible);
    }
  }

  @Override
  public void displayWarningPopup(String messageAfterExecution) {
    JOptionPane.showMessageDialog(outerPanel, messageAfterExecution, "Alert Message",
        JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public String displayDropdown(String command) {

    // Greyscale and RGB Split components
    String[] components = null;
    String labelMessage = null;
    if (command.equals("greyscale")) {
      components = new String[]{"red-component", "green-component", "blue-component",
          "value-component", "intensity-component", "luma-component"};
      labelMessage = "Select a component";
    } else if (command.equals("rgb-split")) {
      components = new String[]{"red-component", "green-component", "blue-component"};
      labelMessage = "Select which component do you want to display";
    }

    Object selectionObject = JOptionPane.showInputDialog(outerPanel, labelMessage, "Menu",
        JOptionPane.PLAIN_MESSAGE, null, components, components[0]);
    if (selectionObject == null) {
      return null;
    }
    return selectionObject.toString();
  }

  @Override
  public String getBrightnessValue() {
    return JOptionPane.showInputDialog(outerPanel, "Enter brightness value:",
        "Adjust Brightness By", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public String getValue() {
    return JOptionPane.showInputDialog(outerPanel, "Enter amount of seeds: ", "Amount Of Seeds",
        JOptionPane.PLAIN_MESSAGE);
  }

  private void setFileFilter(JFileChooser fileChooser) {
    fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
    FileNameExtensionFilter filter;
    String[] fileTypes = {".ppm", ".bmp", ".jpg", ".png"};
    String[] fileExtensions = {"ppm", "bmp", "jpg", "png"};
    for (int i = 0; i < fileTypes.length; i++) {
      filter = new FileNameExtensionFilter(fileTypes[i], fileExtensions[i]);
      fileChooser.setFileFilter(filter);
    }
  }

  @Override
  public String load() {
    JFileChooser fileChooser = new JFileChooser();
    setFileFilter(fileChooser);
    int result = fileChooser.showOpenDialog(outerPanel);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    return null;
  }

  @Override
  public String save() {
    JFileChooser fileChooser = new JFileChooser();
    setFileFilter(fileChooser);
    int result = fileChooser.showSaveDialog(outerPanel);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().getAbsolutePath() + fileChooser.getFileFilter()
          .getDescription();
    }
    return null;
  }

  @Override
  public String filter() {
    String[] filters = new String[]{"blur", "sharpen"};
    String labelMessage = "Select a filter";
    Object selectionObject = JOptionPane.showInputDialog(outerPanel, labelMessage, "Filter",
        JOptionPane.PLAIN_MESSAGE, null, filters, filters[0]);
    if (selectionObject == null) {
      return null;
    }
    return selectionObject.toString();
  }

  @Override
  public String colorTransformation() {
    String[] colorTransform = new String[]{"sepia", "greyscale"};
    String labelMessage = "Select a color transformation technique";
    Object selectionObject = JOptionPane.showInputDialog(outerPanel, labelMessage,
        "Color Transformation", JOptionPane.PLAIN_MESSAGE, null, colorTransform, colorTransform[0]);
    if (selectionObject == null) {
      return null;
    }
    return selectionObject.toString();
  }

  @Override
  public void addFeatures(GUIController features) {
    loadButton.addActionListener(evt -> features.load());
    saveButton.addActionListener(evt -> features.save());
    brightenButton.addActionListener(evt -> features.adjustBrightness());
    ditherButton.addActionListener(evt -> features.dither());
    filterButton.addActionListener(evt -> features.filter());
    colorTransformationButton.addActionListener(evt -> features.colorTransformation());
    greyscaleButton.addActionListener(evt -> features.greyscale());
    rgbSplitButton.addActionListener(evt -> features.rgbSplit());
    rgbCombineButton.addActionListener(evt -> features.rgbCombine());
    horizontalFlipButton.addActionListener(evt -> features.horizontalFlip());
    verticalFlipButton.addActionListener(evt -> features.verticalFlip());
    mosaicButton.addActionListener(evt -> features.mosaic());
  }

  @Override
  public void displayImage(String imageNameToBeDisplayed) {
    // Create a new frame to display the image

    int[][][] pixels = rModel.getImagePixels(imageNameToBeDisplayed);

    BufferedImage image = new BufferedImage(pixels.length, pixels[0].length,
        BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < pixels[0].length; i++) {
      for (int j = 0; j < pixels.length; j++) {
        int red = pixels[j][i][0];
        int green = pixels[j][i][1];
        int blue = pixels[j][i][2];
        Color color = new Color(red, green, blue);
        image.setRGB(j, i, color.getRGB());
      }
      setButtonsVisibility(true);
    }

    // Create an ImageIcon with the generated image
    imageIcon.setImage(image);
    imageLabel.setIcon(imageIcon);
    JScrollPane scrollImage = new JScrollPane(imageLabel);
    imagePanel.removeAll();
    imagePanel.add(scrollImage);

    // From controller
    imagePanel.repaint();
    executionMessagePanel.repaint();
    outerPanel.revalidate();
  }

  @Override
  public void setExecutionMessage(String currentMessage) {
    executionMessageLabel.setText(currentMessage);
  }

  @Override
  public void displayHistogram(String imageName) {
    JFreeChart chart = drawHistogram(imageName);
    ChartPanel histogramPanel = new ChartPanel(chart);

    innerNorthPanel.removeAll();
    innerNorthPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    innerNorthPanel.add(histogramPanel, BorderLayout.CENTER);
    innerNorthPanel.revalidate();
    innerNorthPanel.repaint();
    outerPanel.revalidate();
    outerPanel.repaint();
  }

  private JFreeChart drawHistogram(String imageName) {
    int[][][] pixels = rModel.getImagePixels(imageName);
    int[] firstChannel = new int[256];
    int[] secondChannel = new int[256];
    int[] thirdChannel = new int[256];
    int[] fourthChannel = new int[256];

    // Loop through each pixel and get the red, green, and blue values
    for (int i = 0; i < pixels[0].length; i++) {
      for (int j = 0; j < pixels.length; j++) {
        int firstComponent = pixels[j][i][0];
        int secondComponent = pixels[j][i][1];
        int thirdComponent = pixels[j][i][2];
        int fourthComponent = (firstComponent + secondComponent + thirdComponent) / 3;

        // Increment the corresponding histogram bin
        firstChannel[firstComponent]++;
        secondChannel[secondComponent]++;
        thirdChannel[thirdComponent]++;
        fourthChannel[fourthComponent]++;
      }
    }

    // If the intensity and channel values are the same, it means the image is greyscale
    // since all pixel values are the same
    boolean isGreyscale = Arrays.equals(fourthChannel, firstChannel);

    int[][] histogramData = new int[][]{firstChannel, secondChannel, thirdChannel, fourthChannel};
    return getHistogramChart(histogramData, isGreyscale);
  }

  private static JFreeChart getHistogramChart(int[][] histogramData, boolean isGreyscale) {
    String xAxisLabel = "Pixel Value";
    String yAxisLabel = "Frequency";
    PlotOrientation orientation = PlotOrientation.VERTICAL;

    XYSeriesCollection dataset = new XYSeriesCollection();

    String channelName = null;
    XYSeries series;

    // RGB and Intensity Channels
    for (int i = 0; i < histogramData.length; i++) {
      int[] channel = histogramData[i];

      switch (i) {
        case 0:
          channelName = "Red";
          break;
        case 1:
          channelName = "Green";
          break;
        case 2:
          channelName = "Blue";
          break;
        case 3:
          channelName = "Intensity";
          break;
        default:
          break;
      }

      series = new XYSeries(channelName);

      for (int j = 0; j < channel.length; j++) {
        series.add(j, channel[j]);
      }

      dataset.addSeries(series);

    }
    JFreeChart chart = ChartFactory.createXYLineChart("", xAxisLabel, yAxisLabel, dataset,
        orientation, true, false, false);
    chart.setBackgroundPaint(Color.WHITE);

    XYPlot plot = chart.getXYPlot();
    plot.setBackgroundPaint(Color.WHITE);

    NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
    xAxis.setTickUnit(new NumberTickUnit(15));
    xAxis.setTickMarksVisible(true);

    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

    if (isGreyscale) {
      renderer.setSeriesPaint(0, Color.GRAY);
      renderer.setSeriesPaint(1, Color.GRAY);
      renderer.setSeriesPaint(2, Color.GRAY);
      renderer.setSeriesPaint(3, Color.GRAY);
    } else {
      renderer.setSeriesPaint(0, Color.RED);
      renderer.setSeriesPaint(1, Color.GREEN);
      renderer.setSeriesPaint(2, Color.BLUE);
      renderer.setSeriesPaint(3, Color.GRAY);
    }

    renderer.setSeriesShapesVisible(0, false);
    renderer.setSeriesShapesVisible(1, false);
    renderer.setSeriesShapesVisible(2, false);
    renderer.setSeriesShapesVisible(3, false);

    plot.setRenderer(renderer);

    return chart;
  }
}

