import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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


  public UITrialFrame() {
    setTitle("Image App");
    setSize(800, 800);
    setLayout(new BorderLayout());

    JPanel controlPanel = new JPanel();
    controlPanel.setBackground(Color.RED);
    controlPanel.setSize(50, 50);
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

    JButton loadButton = new JButton("load");
    loadButton.setActionCommand("load");
    controlPanel.add(loadButton);

    JButton saveButton = new JButton("save");
    saveButton.setActionCommand("save");
    controlPanel.add(saveButton);

    JButton greyButton = new JButton("grey");
    greyButton.setActionCommand("grey");
    controlPanel.add(greyButton);

    JButton sepiaButton = new JButton("sepia");
    sepiaButton.setActionCommand("sepia");
    controlPanel.add(sepiaButton);

    add(controlPanel, BorderLayout.WEST);

    ImagePanel imagePanel =  new ImagePanel();



    imagePanel.readAndLoad("src/Koala.jpg");


    JScrollPane imageScroll = new JScrollPane(imagePanel);
    imageScroll.setSize(new Dimension(500,getHeight()));
    imageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    imageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//    imageScroll.setPreferredSize(new Dimension(100, 600));
    add(imageScroll, BorderLayout.CENTER);
//    imagePanel.add(imageScroll);

    HistogramPanel histogramPanel = new HistogramPanel();
    histogramPanel.setSize(300,800);
    histogramPanel.readAndLoad("src/Koala.jpg");

    add(histogramPanel, BorderLayout.EAST);


//    controlPanel.setLayout(FlowLayout);

  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }
}
