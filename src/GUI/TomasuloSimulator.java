package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class TomasuloSimulator extends JFrame {
	
	private JPanel panelOne;
	private JPanel panelTwo;
	
	private JButton chooseFile;
	private JLabel chooseFileLabel;
	
	private JLabel mWayLabel;
	private JTextField mWay;
	
	private JTextField instructionBuffer;
	
	private JLabel numberOfRSLabel;
	private JTextField loadFUs;
	private JTextField multiplyFUs;
	private JTextField addFUs;
	
	private JLabel ROBSizeLabel;
	private JTextField ROBSizeText;
	
	public TomasuloSimulator(){
		super();
		this.setSize(800,600);
		this.setVisible(true);
		this.setLayout(null);
		this.setTitle("Tomasulo Simulator");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		panelOne=new JPanel();
		panelOne.setBackground(Color.black);
		panelOne.setLayout(null);
		panelOne.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
		panelOne.setBounds(0, 0, 800, 578);
		
		chooseFile = new JButton("Choose File");
		chooseFile.setBounds(10,70,100,35);
		chooseFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				String filename = file.getAbsolutePath();
				chooseFileLabel.setText(filename);
			}
		});;
		chooseFileLabel = new JLabel("No file attached");
		chooseFileLabel.setBounds(10,20,500,35);
		chooseFileLabel.setForeground(Color.white);
		
		mWayLabel = new JLabel("Specify the superscalarity");
		mWayLabel.setForeground(Color.white);
		mWayLabel.setBounds(10, 120, 250, 35);
		
		mWay = new JTextField("  N");
		mWay.setBounds(200, 120, 40, 35);
		
		numberOfRSLabel = new JLabel("Specify the number of reservation stations for each class of instructions:");
		numberOfRSLabel.setBounds(10, 160, 500, 35);
		numberOfRSLabel.setForeground(Color.white);
		
		addFUs = new JTextField("Add");
		addFUs.setBounds(10, 195, 40, 35);
		
		multiplyFUs = new JTextField("Mult");
		multiplyFUs.setBounds(70, 195, 40, 35);
		
		loadFUs = new JTextField("load");
		loadFUs.setBounds(140, 195, 40, 35);
		
		ROBSizeLabel=new JLabel("Specify the size of the reoder buffer:");
		ROBSizeLabel.setBounds(10, 235, 500, 35);
		ROBSizeLabel.setForeground(Color.white);
		
		ROBSizeText=new JTextField("  N");
		ROBSizeText.setBounds(10, 260, 40, 35);
		
		panelOne.add(chooseFile);
		panelOne.add(chooseFileLabel);
		panelOne.add(mWayLabel);
		panelOne.add(mWay);
		panelOne.add(numberOfRSLabel);
		panelOne.add(addFUs);
		panelOne.add(multiplyFUs);
		panelOne.add(loadFUs);
		panelOne.add(ROBSizeLabel);
		panelOne.add(ROBSizeText);
		
		this.add(panelOne);
		this.validate();
		this.repaint();
	}
	
	public void initializeComponents(){
		
	}
	
	public static void main(String[] args) {
		TomasuloSimulator window = new TomasuloSimulator();
	}
}
