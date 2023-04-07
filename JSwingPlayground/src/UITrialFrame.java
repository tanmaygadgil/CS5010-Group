import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UITrialFrame extends JFrame implements ActionListener, ItemListener, ListSelectionListener{

  //Buttons
  private JPanel controlPanel;
  private GridBagConstraints gbc;

  public UITrialFrame() {
    setTitle("Image App");
    setSize(800, 800);
    setLayout(new BorderLayout());

    controlPanel = new JPanel();
    controlPanel.setBackground(Color.RED);
    controlPanel.setSize(100, 50);
    controlPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();



    JComboBox filterComboBox = new JComboBox<>(new String[]{"Load", "Save", "Sepia", "Flip", "Brighten"});
    filterComboBox.addActionListener(this);
    filterComboBox.setActionCommand("operations");
    JButton applyButton = new JButton("Apply");

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10,10,10,10);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    controlPanel.add(filterComboBox, gbc);


    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1;
    controlPanel.add(applyButton, gbc);
//    JButton loadButton = new JButton("load");
//    loadButton.setActionCommand("load");
//
//    controlPanel.add(loadButton);
//
//    JButton saveButton = new JButton("save");
//    saveButton.setActionCommand("save");
//    controlPanel.add(saveButton);
//
//    JButton greyButton = new JButton("grey");
//    greyButton.setActionCommand("grey");
//    controlPanel.add(greyButton);
//
//    JButton sepiaButton = new JButton("sepia");
//    sepiaButton.setActionCommand("sepia");
//    controlPanel.add(sepiaButton);

    add(controlPanel, BorderLayout.WEST);


//    imagePanel.add(imageScroll);

    HistogramPanel histogramPanel = new HistogramPanel();
    histogramPanel.setSize(150,800);
    histogramPanel.readAndLoad("src/Jellyfish.jpg");

    add(histogramPanel, BorderLayout.EAST);

    ImagePanel imagePanel =  new ImagePanel();
    imagePanel.readAndLoad("src/Jellyfish.jpg");
    JScrollPane imageScroll = new JScrollPane(imagePanel);
    imageScroll.setSize(new Dimension(500,getHeight()));
    imageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    imageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    imageScroll.setSize(new Dimension(550, 800));
    add(imageScroll, BorderLayout.CENTER);

//    controlPanel.setLayout(FlowLayout);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "operations":
        if (e.getSource() instanceof JComboBox) {
          JComboBox<String> box = (JComboBox<String>) e.getSource();
          if (box.getSelectedItem().equals("Brighten")) {
            JTextArea textArea = new JTextArea(1, 20);
            textArea.setBorder(BorderFactory.createTitledBorder("Regular text area"));
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.weightx = 1;
            controlPanel.add(textArea);
          }
        }
    }
  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }
}
