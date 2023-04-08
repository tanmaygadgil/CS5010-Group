package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class HistogramPanel extends JPanel {

  private int[][][] image;

  private ChartPanel intensityChart;
  private ChartPanel redChart;
  private ChartPanel greenChart;
  private ChartPanel blueChart;
  private XYDataset intensity;
  private XYDataset red;
  private XYDataset green;
  private XYDataset blue;

  public HistogramPanel() {
    setLayout(new GridLayout(4, 1));
    this.image = null;
  }

  public void setImage(int[][][] image) {
    this.remove(this.intensityChart);
    this.remove(this.redChart);
    this.remove(this.greenChart);
    this.remove(this.blueChart);

    this.image = image;

    this.intensity = generateDataset(getIntensityHistorgramValues());
    this.red = generateDataset(getRedHistorgramValues());
    this.green = generateDataset(getgreenHistorgramValues());
    this.blue = generateDataset(getBlueHistorgramValues());

    this.intensityChart = new ChartPanel(createChart("Intensity Plot", "Pixel", "Norm Count",
        this.intensity));
    this.redChart = new ChartPanel(createChart("Intensity Plot", "Pixel", "Norm Count",
        this.red));
    this.greenChart = new ChartPanel(createChart("Intensity Plot", "Pixel", "Norm Count",
        this.green));
    this.blueChart = new ChartPanel(createChart("Intensity Plot", "Pixel", "Norm Count",
        this.blue));

    add(this.intensityChart);
    add(this.redChart);
    add(this.greenChart);
    add(this.blueChart);

    this.revalidate();
    this.repaint();
  }

  public void readAndLoad(String ImagePath) {
    BufferedImage img;
    try {
      img = ImageIO.read(new FileInputStream(ImagePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.image = convertToRgb(img);

    this.intensity = generateDataset(getIntensityHistorgramValues());
    this.red = generateDataset(getRedHistorgramValues());
    this.green = generateDataset(getgreenHistorgramValues());
    this.blue = generateDataset(getBlueHistorgramValues());

    this.intensityChart = new ChartPanel(createChart("Intensity Plot", "Pixel", "Norm Count",
        this.intensity));
    this.redChart = new ChartPanel(createChart("Intensity Plot", "Pixel", "Norm Count",
        this.red));
    this.greenChart = new ChartPanel(createChart("Intensity Plot", "Pixel", "Norm Count",
        this.green));
    this.blueChart = new ChartPanel(createChart("Intensity Plot", "Pixel", "Norm Count",
        this.blue));

    add(this.intensityChart);
    add(this.redChart);
    add(this.greenChart);
    add(this.blueChart);

    setPreferredSize(new Dimension(300, 800));
    revalidate();
    repaint();
  }

  private JFreeChart createChart(String title, String XAxisLabel, String yAxisLabel,
      XYDataset dataset) {
    JFreeChart chart = ChartFactory.createXYLineChart(
        title, XAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL,
        true, true, false
    );

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

  public float[] getRedHistorgramValues() {
    float[] hist = new float[256];
    float maxval = 0;
    for (int i = 0; i < this.image[0].length; i++) {
      for (int j = 0; j < this.image[0][0].length; j++) {
        int val = this.image[0][i][j];
        hist[val] += 1;
        maxval = Math.max(hist[val], maxval);

      }
    }
    //Normalize

    for (int i = 0; i < hist.length; i ++){
      hist[i] /= (float)maxval;
    }

    return hist;
  }

  public float[] getgreenHistorgramValues() {
    float[] hist = new float[256];
    float maxval = 0;
    for (int i = 0; i < this.image[0].length; i++) {
      for (int j = 0; j < this.image[0][0].length; j++) {
        int val = this.image[1][i][j];
        hist[val] += 1;
        maxval = Math.max(hist[val], maxval);

      }

    }
    //Normalize

    for (int i = 0; i < hist.length; i ++){
      hist[i] /= (float)maxval;
    }

    return hist;
  }

  public float[] getBlueHistorgramValues() {
    float[] hist = new float[256];
    float maxval = 0;
    for (int i = 0; i < this.image[0].length; i++) {
      for (int j = 0; j < this.image[0][0].length; j++) {
        int val = this.image[2][i][j];
        hist[val] += 1;
        maxval = Math.max(hist[val], maxval);

      }

    }
    //Normalize
    for (int i = 0; i < hist.length; i ++){
      hist[i] /= (float)maxval;
    }
    return hist;
  }

  public float[] getIntensityHistorgramValues() {
    float[] hist = new float[256];
    float maxval = 0;
    for (int i = 0; i < this.image[0].length; i++) {
      for (int j = 0; j < this.image[0][0].length; j++) {
        int val = (this.image[0][i][j] + this.image[1][i][j] + this.image[2][i][j]) / 3;
        hist[val] += 1;
        maxval = Math.max(hist[val], maxval);

      }

    }
    //Normalize
    for (int i = 0; i < hist.length; i ++){
      hist[i] /= (float)maxval;
    }
    return hist;
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

