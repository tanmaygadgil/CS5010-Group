package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * A panel used to render the histograms of the images displayed.
 */
public class HistogramPanel extends JPanel {

  private ChartPanel intensityChart;
  private ChartPanel redChart;
  private ChartPanel greenChart;
  private ChartPanel blueChart;


  public HistogramPanel() {
    setLayout(new GridLayout(4, 1));
    setPreferredSize(new Dimension(300, 800));
  }

  /**
   * Resets the histogram panel.
   */
  public void reset() {
    try {
      this.remove(this.intensityChart);
      this.remove(this.redChart);
      this.remove(this.greenChart);
      this.remove(this.blueChart);
    } catch (Exception e) {
      System.out.println("no chart loaded");
    }
  }

  /**
   * Used to set the histogram values.
   *
   * @param histVals the histogram values as counts.
   */
  public void setImage(float[][] histVals) {
    XYDataset intensity;
    XYDataset red;
    XYDataset green;
    XYDataset blue;
    reset();

    intensity = generateDataset(histVals[0]);
    red = generateDataset(histVals[1]);
    green = generateDataset(histVals[2]);
    blue = generateDataset(histVals[3]);

    this.intensityChart = new ChartPanel(
        createChart("Intensity Plot", "Pixel", "Norm Count", intensity));
    this.redChart = new ChartPanel(createChart("Red Plot", "Pixel", "Norm Count", red));
    this.greenChart = new ChartPanel(createChart("Green Plot", "Pixel", "Norm Count", green));
    this.blueChart = new ChartPanel(createChart("Blue Plot", "Pixel", "Norm Count", blue));

    add(this.intensityChart);
    add(this.redChart);
    add(this.greenChart);
    add(this.blueChart);

    this.revalidate();
    this.repaint();
  }

  private JFreeChart createChart(String title, String xAxisLabel, String yAxisLabel,
      XYDataset dataset) {
    JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, dataset,
        PlotOrientation.VERTICAL, true, true, false);

    return chart;

  }

  private int[][][] convertToRgb(BufferedImage img) {
    int height = img.getHeight();
    int width = img.getWidth();
    int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
    int[][][] image = new int[3][height][width];
    for (int i = 0; i < height * width; i++) {
      int pixel = pixels[i];
      int x = i % width;
      int y = i / width;
      image[0][y][x] = (pixel >> 16) & 0xff; // red
      image[1][y][x] = (pixel >> 8) & 0xff;  // green
      image[2][y][x] = pixel & 0xff;          // blue
    }

    return image;

  }

  private XYDataset generateDataset(float[] hist) {
    XYSeriesCollection dataset = new XYSeriesCollection();
    XYSeries series1 = new XYSeries("Data");

    for (int i = 0; i < hist.length; i++) {
      series1.add(i, hist[i]);
    }
    dataset.addSeries(series1);

    return dataset;
  }


}

