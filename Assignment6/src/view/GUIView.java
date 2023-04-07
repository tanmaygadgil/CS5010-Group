package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUIView extends JFrame implements ActionListener {

  //Buttons
  private JPanel controlPanel;
  private GridBagConstraints gbc;

  public GUIView() {
    setTitle("Image App");
    setSize(800, 800);
    setLayout(new BorderLayout());

    controlPanel = new JPanel();
    controlPanel.setBackground(Color.RED);
    controlPanel.setSize(100, 50);
    controlPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    JComboBox filterComboBox = new JComboBox<>(
        new String[]{"Load", "Save", "Sepia", "Flip", "Brighten"});
    filterComboBox.addActionListener(this);
    filterComboBox.setActionCommand("operations");
    JButton applyButton = new JButton("Apply");

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    controlPanel.add(filterComboBox, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1;
    controlPanel.add(applyButton, gbc);

    /*
    add(controlPanel, BorderLayout.WEST);
    HistogramPanel histogramPanel = new HistogramPanel();
    histogramPanel.setSize(150, 800);
    histogramPanel.readAndLoad("src/Jellyfish.jpg");

    add(histogramPanel, BorderLayout.EAST);

    ImagePanel imagePanel = new ImagePanel();
    imagePanel.readAndLoad("src/Jellyfish.jpg");
    JScrollPane imageScroll = new JScrollPane(imagePanel);
    imageScroll.setSize(new Dimension(500, getHeight()));
    imageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    imageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    imageScroll.setSize(new Dimension(550, 800));
    add(imageScroll, BorderLayout.CENTER);
     */
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}