package view;

import controller.Features;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.ViewModel;

public class GUIView extends JFrame implements IGUIView {

  //Buttons
  private JPanel controlPanel;
  private GridBagConstraints gbc;
  private JPanel filterComboBoxPanel;
  private JComboBox<String> filterComboBox;
  private JButton applyButton;
  private JButton fileOpenButton;
  private ImagePanel imagePanel;
  private ViewModel viewModel;
  private HistogramPanel histogramPanel;

  public GUIView(ViewModel viewModel) {
    this.viewModel = viewModel;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setTitle("Image App");
    //setSize(800, 800);
    setPreferredSize(new Dimension(800, 800));
    setLayout(new BorderLayout());

    controlPanel = new JPanel();
    //controlPanel.setBackground(Color.RED);
    controlPanel.setSize(100, 50);
    controlPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    //load a file
    fileOpenButton = new JButton("Open a file");
    controlPanel.add(fileOpenButton);

    //add the combobox panel
    filterComboBoxPanel = new JPanel();
    filterComboBoxPanel.setBorder(BorderFactory.createTitledBorder("Select an operation:"));
    filterComboBoxPanel.setLayout(new BoxLayout(filterComboBoxPanel, BoxLayout.PAGE_AXIS));
    controlPanel.add(filterComboBoxPanel);

    //create and set the display for the combobox
    JLabel comboboxDisplay = new JLabel("Options");
    filterComboBoxPanel.add(comboboxDisplay);

    //list operations
    filterComboBox = new JComboBox<String>();
    filterComboBox.setActionCommand("Operations");

    //create the apply button
    applyButton = new JButton("Apply");
    applyButton.setActionCommand("Apply");
    controlPanel.add(applyButton);

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

    add(controlPanel, BorderLayout.WEST);
    histogramPanel = new HistogramPanel();
    histogramPanel.setSize(150, 800);

    add(histogramPanel, BorderLayout.EAST);

    imagePanel = new ImagePanel();
    imagePanel.setSize(new Dimension(400, 800));
    JScrollPane imageScroll = new JScrollPane(imagePanel);
    imageScroll.setSize(new Dimension(500, getHeight()));
    imageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    imageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    imageScroll.setSize(new Dimension(550, 800));
    add(imageScroll, BorderLayout.CENTER);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    //add the options for the combobox
    String[] options = {"horizontal-flip", "vertical-flip", "greyscale", "gaussian-blur", "dither",
        "sepia", "sharpen"};
    for (String str : options) {
      filterComboBox.addItem(str);
    }
    filterComboBoxPanel.add(filterComboBox);

    //sends the current text in the combobox to the controller
    applyButton.addActionListener(evt -> {
      try {
        features.callCommand(filterComboBox.getSelectedItem().toString());
        imagePanel.setImage(viewModel.getImage("image"));
        histogramPanel.setImage(viewModel.getImage("image"));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    fileOpenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final JFileChooser fChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg",
            "gif");
        fChooser.setFileFilter(filter);
        int retValue = fChooser.showOpenDialog(GUIView.this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          File f = fChooser.getSelectedFile();
          try {
            features.callLoad(f.getAbsolutePath());
            imagePanel.readAndLoad(f.getAbsolutePath());
            histogramPanel.readAndLoad(f.getAbsolutePath());
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        }
      }

    });

  }

  @Override
  public String getInput() {
    return null;
  }

  @Override
  public void renderOutput(String inputString) {

  }


}
