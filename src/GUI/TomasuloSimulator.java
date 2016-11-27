package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class TomasuloSimulator extends JFrame {
	
	// global variables
	private File programFile;
	private int superscalarity;
	private int loadFUs;
	private int multiplyFUs;
	private int addFUs;
	private int ROBSize;
	private int iCacheLevels;
	private int dCacheLevels;
	
	// panelOne components 
	private JPanel panelOne;
	private JPanel panelTwo;
	
	private JButton chooseFile;
	private JLabel chooseFileLabel;
	
	private JLabel programOriginLabel;
	private JTextField programOriginText;
	
	private JLabel mWayLabel;
	private JTextField mWay;
		
	private JLabel numberOfRSLabel;
	private JTextField loadFUsField;
	private JTextField multiplyFUsField;
	private JTextField addFUsField;
	
	private JLabel ROBSizeLabel;
	private JTextField ROBSizeText;
	
	private JLabel iCacheNumberLabel;
	private JTextField iCacheNumberText;
	private JLabel dCacheNumberLabel;
	private JTextField dCacheNumberText;
	
	private JLabel error;
	
	private JButton proceed;
	
	public TomasuloSimulator(){
		super();
		this.setSize(800,600);
		this.setVisible(true);
		this.setLayout(null);
		this.setTitle("Tomasulo Simulator");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		launchPanelTwo();
		
	}
	
	public void launchPanelOne(){
		panelOne=new JPanel();
		panelOne.setBackground(Color.black);
		panelOne.setLayout(null);
		panelOne.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
		panelOne.setBounds(0, 0, 800, 578);
		
		chooseFile = new JButton("Choose File");
		chooseFile.setBounds(20,70,100,35);
		chooseFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				programFile = chooser.getSelectedFile();
				String filename = programFile.getAbsolutePath();
				chooseFileLabel.setText(filename);
			}
		});;
		chooseFileLabel = new JLabel("No file attached");
		chooseFileLabel.setBounds(20,20,500,35);
		chooseFileLabel.setForeground(Color.white);
		
		mWayLabel = new JLabel("Specify the superscalarity");
		mWayLabel.setForeground(Color.white);
		mWayLabel.setBounds(20, 120, 250, 35);
		
		mWay = new JTextField("n");
		mWay.setBounds(200, 120, 40, 35);
		
		numberOfRSLabel = new JLabel("Specify the number of reservation stations for each class of instructions:");
		numberOfRSLabel.setBounds(20, 160, 500, 35);
		numberOfRSLabel.setForeground(Color.white);
		
		addFUsField = new JTextField("Add");
		addFUsField.setBounds(20, 195, 40, 35);
		
		multiplyFUsField = new JTextField("Mult");
		multiplyFUsField.setBounds(70, 195, 40, 35);
		
		loadFUsField = new JTextField("load");
		loadFUsField.setBounds(120, 195, 40, 35);
		
		ROBSizeLabel=new JLabel("Specify the size of the reoder buffer:");
		ROBSizeLabel.setBounds(20, 235, 500, 35);
		ROBSizeLabel.setForeground(Color.white);
		
		ROBSizeText=new JTextField("n");
		ROBSizeText.setBounds(20, 265, 40, 35);
		
		iCacheNumberLabel = new JLabel("Number of i-cache levels");
		iCacheNumberLabel.setBounds(20, 310, 300, 35);
		iCacheNumberLabel.setForeground(Color.white);
		iCacheNumberText = new JTextField(iCacheLevels);
		iCacheNumberText.setBounds(20, 340, 40, 35);
		
		dCacheNumberLabel = new JLabel("Number of d-cache levels");
		dCacheNumberLabel.setBounds(20, 390, 300, 35);
		dCacheNumberLabel.setForeground(Color.white);
		dCacheNumberText = new JTextField(dCacheLevels);
		dCacheNumberText.setBounds(20, 420, 40, 35);
		
		error = new JLabel("An error occured, please make sure you entered the settings correctly");
		error.setForeground(Color.red);
		error.setBounds(20, 530, 500, 35);
		error.setVisible(false);
		
		proceed = new JButton("proceed");
		proceed.setBounds(350, 500, 100, 35);
		proceed.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(programFile == null){
					error.setVisible(true);
				}
				try{
					superscalarity = Integer.parseInt(mWay.getText());
					loadFUs = Integer.parseInt(loadFUsField.getText());
					multiplyFUs = Integer.parseInt(multiplyFUsField.getText());
					addFUs = Integer.parseInt(addFUsField.getText());
					ROBSize = Integer.parseInt(ROBSizeText.getText());
					try {
						iCacheLevels = Integer.parseInt(iCacheNumberText.getText());
					} catch (Exception e2) {
						iCacheLevels = 1;
					}
					try {
						dCacheLevels = Integer.parseInt(dCacheNumberText.getText());
					} catch (Exception e2) {
						dCacheLevels = 1;
					}
					launchPanelTwo();
				}
				catch(Exception ex){
					error.setVisible(true);
				}
			}
		});
		
		
		panelOne.add(chooseFile);
		panelOne.add(chooseFileLabel);
		panelOne.add(mWayLabel);
		panelOne.add(mWay);
		panelOne.add(numberOfRSLabel);
		panelOne.add(addFUsField);
		panelOne.add(multiplyFUsField);
		panelOne.add(loadFUsField);
		panelOne.add(ROBSizeLabel);
		panelOne.add(ROBSizeText);
		panelOne.add(iCacheNumberLabel);
		panelOne.add(iCacheNumberText);
		panelOne.add(dCacheNumberLabel);
		panelOne.add(dCacheNumberText);
		panelOne.add(proceed);
		panelOne.add(error);
		
		this.add(panelOne);
		this.validate();
		this.repaint();
	}
	
	public void launchPanelTwo(){
		//this.remove(panelOne);
		
		panelTwo=new JPanel();
		panelTwo.setBackground(Color.black);
		panelTwo.setLayout(null);
		panelTwo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
		panelTwo.setBounds(0, 0, 800, 578);
		
		JLabel iCacheLabel = new JLabel("I-Cache geometry");
		iCacheLabel.setForeground(Color.white);
		iCacheLabel.setBounds(20, 10, 150, 35);
		
		JLabel iLineSizeLabel = new JLabel("Line Size");
		iLineSizeLabel.setForeground(Color.white);
		iLineSizeLabel.setBounds(170, 10, 100, 35);
		
		JTextField iLineSizeText = new JTextField();
		iLineSizeText.setBounds(260, 10, 100, 35);
		
		JLabel si = new JLabel("S");
		si.setForeground(Color.white);
		si.setBounds(30, 50, 35, 35);
		
		int [] iS = new int[iCacheLevels];
		
		
		panelTwo.add(iCacheLabel);
		panelTwo.add(iLineSizeLabel);
		panelTwo.add(iLineSizeText);
		panelTwo.add(si);
		
		this.add(panelTwo);
		
		this.revalidate();
		this.repaint();

	}
	
	
	
	public static void main(String[] args) {
		TomasuloSimulator window = new TomasuloSimulator();
	}
}
