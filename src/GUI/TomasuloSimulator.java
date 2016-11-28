package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import memoryHierarchy.Cache;
import memoryHierarchy.Word;
import memoryHierarchy.WritingPolicy;
import tomasulo.Simulator;

public class TomasuloSimulator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// global variables
	private File programFile;
	private int superscalarity;
	private int loadFUs;
	private int storeFUs;
	private int multiplyFUs;
	private int addFUs;
	private int subFUs;
	private int addiFUs;
	private int nandFUs;
	private int jmpFUs;
	private int breqFUs;
	private int jalFUs;
	private int retFUs;
	private int loadLat;
	private int storeLat;
	private int multiplyLat;
	private int addLat;
	private int subLat;
	private int addiLat;
	private int nandLat;
	private int jmpLat;
	private int breqLat;
	private int jalLat;
	private int retLat;
	private int ROBSize;
	private int iCacheLevels;
	private int dCacheLevels;
	private int memoryAccessTime;
	private int instructionBuffer;
	
	// output global variables	
	private double IPC;
	private int totalExecutionTime;
	public double[] iCacheHitRatio;
	public double[] dCacheHitRatio;
	public double iAMAT;
	public double dAMAT;
	public double branchMisprediction;
	
	// panelOne components
	private JPanel panelOne;
	private JPanel panelTwo;
	private JPanel panelThree;

	private JButton chooseFile;
	private JLabel chooseFileLabel;

	// private JLabel programOriginLabel;
	// private JTextField programOriginText;

	private JLabel mWayLabel;
	private JTextField mWay;

	private JLabel numberOfRSLabel;

	private JLabel ROBSizeLabel;
	private JTextField ROBSizeText;

	private JLabel iCacheNumberLabel;
	private JTextField iCacheNumberText;
	private JLabel dCacheNumberLabel;
	private JTextField dCacheNumberText;

	private JLabel error;

	private JButton proceed;

	public TomasuloSimulator() {
		super();
		this.setSize(800, 600);
		this.setVisible(true);
		this.setLayout(null);
		this.setTitle("Tomasulo Simulator");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		launchPanelOne();

	}

	public void launchPanelOne() {
		if(panelThree != null)
			this.remove(panelThree);
		panelOne = new JPanel();
		panelOne.setBackground(Color.black);
		panelOne.setLayout(null);
		panelOne.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		panelOne.setBounds(0, 0, 800, 578);

		chooseFile = new JButton("Choose File");
		chooseFile.setBounds(20, 70, 100, 35);
		chooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				programFile = chooser.getSelectedFile();
				if (programFile != null) {
					String filename = programFile.getAbsolutePath();
					chooseFileLabel.setText(filename);
				}
			}
		});
		;
		chooseFileLabel = new JLabel("No file attached");
		chooseFileLabel.setBounds(20, 20, 500, 35);
		chooseFileLabel.setForeground(Color.white);
		
		mWayLabel = new JLabel("Specify the superscalarity");
		mWayLabel.setForeground(Color.white);
		mWayLabel.setBounds(20, 120, 250, 35);

		mWay = new JTextField("n");
		mWay.setBounds(200, 120, 40, 35);

		numberOfRSLabel = new JLabel(
				"Specify the number of reservation stations for each class of instructions:");
		numberOfRSLabel.setBounds(20, 160, 500, 35);
		numberOfRSLabel.setForeground(Color.white);

//		JTextField addFUsField = new JTextField("add");
		JTextField addFUsField = new JTextField("1");
		addFUsField.setBounds(20, 195, 40, 35);
//		JTextField multiplyFUsField = new JTextField("mult");
		JTextField multiplyFUsField = new JTextField("1");
		multiplyFUsField.setBounds(70, 195, 40, 35);
//		JTextField loadFUsField = new JTextField("load");
		JTextField loadFUsField = new JTextField("1");
		loadFUsField.setBounds(120, 195, 40, 35);
//		JTextField storeFUsField = new JTextField("store");
		JTextField storeFUsField = new JTextField("1");
		storeFUsField.setBounds(170, 195, 40, 35);
//		JTextField jmpFUsField = new JTextField("jmp");
		JTextField jmpFUsField = new JTextField("1");
		jmpFUsField.setBounds(220, 195, 40, 35);
//		JTextField breqFUsField = new JTextField("breq");
		JTextField breqFUsField = new JTextField("1");
		breqFUsField.setBounds(270, 195, 40, 35);
//		JTextField jalFUsField = new JTextField("jal");
		JTextField jalFUsField = new JTextField("1");
		jalFUsField.setBounds(320, 195, 40, 35);
//		JTextField retFUsField = new JTextField("ret");
		JTextField retFUsField = new JTextField("1");
		retFUsField.setBounds(370, 195, 40, 35);
//		JTextField subFUsField = new JTextField("sub");
		JTextField subFUsField = new JTextField("1");
		subFUsField.setBounds(420, 195, 40, 35);
//		JTextField nandFUsField = new JTextField("nand");
		JTextField nandFUsField = new JTextField("1");
		nandFUsField.setBounds(470, 195, 40, 35);
//		JTextField addiFUsField = new JTextField("addi");
		JTextField addiFUsField = new JTextField("1");
		addiFUsField.setBounds(520, 195, 40, 35);

		JLabel latenciesLabel = new JLabel(
				"Specify the latency for each class of instructions:");
		latenciesLabel.setBounds(20, 225, 500, 35);
		latenciesLabel.setForeground(Color.white);

//		JTextField addLatField = new JTextField("add");
		JTextField addLatField = new JTextField("10");
		addLatField.setBounds(20, 260, 40, 35);
//		JTextField multiplyLatField = new JTextField("mult");
		JTextField multiplyLatField = new JTextField("10");
		multiplyLatField.setBounds(70, 260, 40, 35);
//		JTextField loadLatField = new JTextField("load");
		JTextField loadLatField = new JTextField("10");
		loadLatField.setBounds(120, 260, 40, 35);
//		JTextField storeLatField = new JTextField("store");
		JTextField storeLatField = new JTextField("10");
		storeLatField.setBounds(170, 260, 40, 35);
//		JTextField jmpLatField = new JTextField("jmp");
		JTextField jmpLatField = new JTextField("10");
		jmpLatField.setBounds(220, 260, 40, 35);
//		JTextField breqLatField = new JTextField("breq");
		JTextField breqLatField = new JTextField("10");
		breqLatField.setBounds(270, 260, 40, 35);
//		JTextField jalLatField = new JTextField("jal");
		JTextField jalLatField = new JTextField("10");
		jalLatField.setBounds(320, 260, 40, 35);
//		JTextField retLatField = new JTextField("ret");
		JTextField retLatField = new JTextField("10");
		retLatField.setBounds(370, 260, 40, 35);
//		JTextField subLatField = new JTextField("sub");
		JTextField subLatField = new JTextField("10");
		subLatField.setBounds(420, 260, 40, 35);
//		JTextField nandLatField = new JTextField("nand");
		JTextField nandLatField = new JTextField("10");
		nandLatField.setBounds(470, 260, 40, 35);
//		JTextField addiLatField = new JTextField("addi");
		JTextField addiLatField = new JTextField("10");
		addiLatField.setBounds(520, 260, 40, 35);

		ROBSizeLabel = new JLabel("Specify the size of the reoder buffer:");
		ROBSizeLabel.setBounds(20, 295, 500, 35);
		ROBSizeLabel.setForeground(Color.white);

		ROBSizeText = new JTextField("n");
		ROBSizeText.setBounds(20, 325, 40, 35);

		iCacheNumberLabel = new JLabel("Number of i-cache levels");
		iCacheNumberLabel.setBounds(20, 360, 300, 35);
		iCacheNumberLabel.setForeground(Color.white);
		iCacheNumberText = new JTextField(iCacheLevels);
		iCacheNumberText.setBounds(20, 400, 40, 35);

		dCacheNumberLabel = new JLabel("Number of d-cache levels");
		dCacheNumberLabel.setBounds(20, 440, 300, 35);
		dCacheNumberLabel.setForeground(Color.white);
		dCacheNumberText = new JTextField(dCacheLevels);
		dCacheNumberText.setBounds(20, 470, 40, 35);

		JLabel accessTimeLabel = new JLabel("Memory Access Time");
		accessTimeLabel.setBounds(300, 360, 200, 35);
		accessTimeLabel.setForeground(Color.white);

		JTextField accessTime = new JTextField();
		accessTime.setBounds(300, 400, 35, 35);

		JLabel instructionBufferLabel = new JLabel("Instruction Buffer Size");
		instructionBufferLabel.setBounds(500, 360, 200, 35);
		instructionBufferLabel.setForeground(Color.white);

		JTextField instructionBufferSize = new JTextField();
		instructionBufferSize.setBounds(500, 400, 35, 35);

		//Testing
		ROBSizeText.setText("10");
		mWay.setText("4");
		iCacheNumberText.setText("3");
		dCacheNumberText.setText("3");
		accessTime.setText("50");
		instructionBufferSize.setText("10");
		
		error = new JLabel(
				"An error occured, please make sure you entered the settings correctly");
		error.setForeground(Color.red);
		error.setBounds(20, 530, 500, 35);
		error.setVisible(false);

		proceed = new JButton("proceed");
		proceed.setBounds(350, 500, 100, 35);
		proceed.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (programFile == null) {
					error.setVisible(true);
				} 
				else
					
				try {
					superscalarity = Integer.parseInt(mWay.getText());
					loadFUs = Integer.parseInt(loadFUsField.getText());
					multiplyFUs = Integer.parseInt(multiplyFUsField.getText());
					addFUs = Integer.parseInt(addFUsField.getText());
					storeFUs = Integer.parseInt(storeFUsField.getText());
					jmpFUs = Integer.parseInt(jmpFUsField.getText());
					breqFUs = Integer.parseInt(breqFUsField.getText());
					jalFUs = Integer.parseInt(jalFUsField.getText());
					retFUs = Integer.parseInt(retFUsField.getText());
					subFUs = Integer.parseInt(subFUsField.getText());
					nandFUs = Integer.parseInt(nandFUsField.getText());
					addiFUs = Integer.parseInt(addiFUsField.getText());

					loadLat = Integer.parseInt(loadLatField.getText());
					multiplyLat = Integer.parseInt(multiplyLatField.getText());
					addLat = Integer.parseInt(addLatField.getText());
					storeLat = Integer.parseInt(storeLatField.getText());
					jmpLat = Integer.parseInt(jmpLatField.getText());
					breqLat = Integer.parseInt(breqLatField.getText());
					jalLat = Integer.parseInt(jalLatField.getText());
					retLat = Integer.parseInt(retLatField.getText());
					subLat = Integer.parseInt(subLatField.getText());
					nandLat = Integer.parseInt(nandLatField.getText());
					addiLat = Integer.parseInt(addiLatField.getText());
					memoryAccessTime = Integer.parseInt(accessTime.getText());
					instructionBuffer = Integer.parseInt(instructionBufferSize
							.getText());
					ROBSize = Integer.parseInt(ROBSizeText.getText());
					try {
						iCacheLevels = Integer.parseInt(iCacheNumberText
								.getText());
					} catch (Exception e2) {
						iCacheLevels = 1;
					}
					try {
						dCacheLevels = Integer.parseInt(dCacheNumberText
								.getText());
					} catch (Exception e2) {
						dCacheLevels = 1;
					}
					launchPanelTwo();
				} catch (Exception ex) {
					ex.printStackTrace();
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
		panelOne.add(storeFUsField);
		panelOne.add(jmpFUsField);
		panelOne.add(breqFUsField);
		panelOne.add(jalFUsField);
		panelOne.add(retFUsField);
		panelOne.add(subFUsField);
		panelOne.add(nandFUsField);
		panelOne.add(addiFUsField);

		panelOne.add(latenciesLabel);
		panelOne.add(addLatField);
		panelOne.add(multiplyLatField);
		panelOne.add(loadLatField);
		panelOne.add(storeLatField);
		panelOne.add(jmpLatField);
		panelOne.add(breqLatField);
		panelOne.add(jalLatField);
		panelOne.add(retLatField);
		panelOne.add(subLatField);
		panelOne.add(nandLatField);
		panelOne.add(addiLatField);

		panelOne.add(ROBSizeLabel);
		panelOne.add(ROBSizeText);
		panelOne.add(iCacheNumberLabel);
		panelOne.add(iCacheNumberText);
		panelOne.add(dCacheNumberLabel);
		panelOne.add(dCacheNumberText);
		panelOne.add(accessTimeLabel);
		panelOne.add(accessTime);
		panelOne.add(instructionBufferLabel);
		panelOne.add(instructionBufferSize);
		panelOne.add(proceed);
		panelOne.add(error);

		this.add(panelOne);
		this.validate();
		this.repaint();
	}

	public void launchPanelTwo() {
		this.remove(panelOne);

		// iCacheLevels = 2;
		// dCacheLevels = 2;

		panelTwo = new JPanel();
		panelTwo.setBackground(Color.black);
		panelTwo.setLayout(null);
		panelTwo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		panelTwo.setBounds(0, 0, 800, 578);

		JLabel iCacheLabel = new JLabel("I-Cache geometry");
		iCacheLabel.setForeground(Color.white);
		iCacheLabel.setBounds(20, 10, 150, 35);

		JLabel iLineSizeLabel = new JLabel("Line Size in words");
		iLineSizeLabel.setForeground(Color.white);
		iLineSizeLabel.setBounds(170, 10, 120, 35);

		JTextField iLineSizeText = new JTextField();
		iLineSizeText.setBounds(300, 10, 60, 35);

		JLabel siLabel = new JLabel("Size in Words");
		siLabel.setForeground(Color.white);
		siLabel.setBounds(80, 50, 100, 35);

		JLabel miLabel = new JLabel("Associativity");
		miLabel.setForeground(Color.white);
		miLabel.setBounds(200, 50, 100, 35);

		JLabel wiLabel = new JLabel("Write Policy, 0=WT, 1=WB");
		wiLabel.setForeground(Color.white);
		wiLabel.setBounds(320, 50, 200, 35);

		JLabel aiLabel = new JLabel("Access Time");
		aiLabel.setForeground(Color.white);
		aiLabel.setBounds(520, 50, 200, 35);

		JTextField[] si = new JTextField[iCacheLevels];
		JTextField[] mi = new JTextField[iCacheLevels];
		JTextField[] wi = new JTextField[iCacheLevels];
		JTextField[] ai = new JTextField[iCacheLevels];

		for (int i = 0; i < iCacheLevels; i++) {
			JLabel li = new JLabel("L" + (i + 1));
			li.setBounds(20, 80 + (i * 30), 35, 35);
			li.setForeground(Color.white);
			panelTwo.add(li);
			si[i] = new JTextField();
			si[i].setText("64");
			si[i].setBounds(80, 80 + (i * 30), 35, 35);
			panelTwo.add(si[i]);
			mi[i] = new JTextField();
			mi[i].setText("1");
			mi[i].setBounds(200, 80 + (i * 30), 35, 35);
			panelTwo.add(mi[i]);
			wi[i] = new JTextField();
			wi[i].setText("0");
			wi[i].setBounds(320, 80 + (i * 30), 35, 35);
			panelTwo.add(wi[i]);
			ai[i] = new JTextField();
			ai[i].setText("10");
			ai[i].setBounds(520, 80 + (i * 30), 35, 35);
			panelTwo.add(ai[i]);
		}

		JLabel dCacheLabel = new JLabel("D-Cache geometry");
		dCacheLabel.setForeground(Color.white);
		dCacheLabel.setBounds(20, 260, 150, 35);

		JLabel dLineSizeLabel = new JLabel("Line Size in words");
		dLineSizeLabel.setForeground(Color.white);
		dLineSizeLabel.setBounds(170, 260, 120, 35);

		JTextField dLineSizeText = new JTextField();
		dLineSizeText.setBounds(300, 260, 60, 35);

		JLabel sdLabel = new JLabel("Size in Words");
		sdLabel.setForeground(Color.white);
		sdLabel.setBounds(80, 300, 100, 35);

		JLabel mdLabel = new JLabel("Associativity");
		mdLabel.setForeground(Color.white);
		mdLabel.setBounds(200, 300, 100, 35);

		JLabel wdLabel = new JLabel("Write Policy, 0=WT, 1=WB");
		wdLabel.setForeground(Color.white);
		wdLabel.setBounds(320, 300, 200, 35);

		JLabel adLabel = new JLabel("Access Time");
		adLabel.setForeground(Color.white);
		adLabel.setBounds(520, 300, 200, 35);

		JTextField[] sd = new JTextField[dCacheLevels];
		JTextField[] md = new JTextField[dCacheLevels];
		JTextField[] wd = new JTextField[dCacheLevels];
		JTextField[] ad = new JTextField[dCacheLevels];

		for (int i = 0; i < dCacheLevels; i++) {
			JLabel ld = new JLabel("L" + (i + 1));
			ld.setBounds(20, 330 + (i * 30), 35, 35);
			ld.setForeground(Color.white);
			panelTwo.add(ld);
			sd[i] = new JTextField();
			sd[i].setText("64");
			sd[i].setBounds(80, 330 + (i * 30), 35, 35);
			panelTwo.add(sd[i]);
			md[i] = new JTextField();
			md[i].setText("1");
			md[i].setBounds(200, 330 + (i * 30), 35, 35);
			panelTwo.add(md[i]);
			wd[i] = new JTextField();
			wd[i].setText("0");
			wd[i].setBounds(320, 330 + (i * 30), 35, 35);
			panelTwo.add(wd[i]);
			ad[i] = new JTextField();
			ad[i].setText("10");
			ad[i].setBounds(520, 330 + (i * 30), 35, 35);
			panelTwo.add(ad[i]);
		}
		
		//Testing
		dLineSizeText.setText("2");
		iLineSizeText.setText("2");
		
		JButton start = new JButton("Start");
		start.setBounds(350, 500, 100, 35);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Cache[] iCache = new Cache[iCacheLevels];
				Cache[] dCache = new Cache[dCacheLevels];
				try {
					int iLineSize = Integer.parseInt(iLineSizeText.getText());
					int dLineSize = Integer.parseInt(dLineSizeText.getText());
					for (int i = 0; i < iCacheLevels; i++) {
						WritingPolicy p = WritingPolicy.WRITE_BACK;
						if (Integer.parseInt(wi[i].getText()) == 0)
							p = WritingPolicy.WRITE_THROUGH;
						iCache[i] = new Cache(
								Integer.parseInt(si[i].getText()), iLineSize,
								Integer.parseInt(mi[i].getText()), p, Integer
										.parseInt(ai[i].getText()));
					}
					for (int i = 0; i < dCacheLevels; i++) {
						WritingPolicy p = WritingPolicy.WRITE_BACK;
						if (Integer.parseInt(wd[i].getText()) == 0)
							p = WritingPolicy.WRITE_THROUGH;
						dCache[i] = new Cache(
								Integer.parseInt(sd[i].getText()), dLineSize,
								Integer.parseInt(md[i].getText()), p, Integer
										.parseInt(ad[i].getText()));
					}
					startSimulator(iCache, dCache);

					// System.out.println("Line Size for I = "+iLineSize);
					// System.out.println("Line Size for D = "+dLineSize);
					// System.out.println("");
					// for(int i = 0; i < iCacheLevels; i++)
					// System.out.println(siValues[i]);
					// for(int i = 0; i < iCacheLevels; i++)
					// System.out.println(miValues[i]);
					// for(int i = 0; i < dCacheLevels; i++)
					// System.out.println(sdValues[i]);
					// for(int i = 0; i < dCacheLevels; i++)
					// System.out.println(mdValues[i]);
				} catch (Exception e1) {
					error.setVisible(true);
					e1.printStackTrace();
				}
			}
		});

		error = new JLabel("An error occured, please make sure you entered the settings correctly");
		error.setForeground(Color.red);
		error.setBounds(20, 530, 500, 35);
		error.setVisible(false);

		panelTwo.add(iCacheLabel);
		panelTwo.add(iLineSizeLabel);
		panelTwo.add(iLineSizeText);
		panelTwo.add(siLabel);
		panelTwo.add(miLabel);
		panelTwo.add(wiLabel);
		panelTwo.add(aiLabel);

		panelTwo.add(dCacheLabel);
		panelTwo.add(dLineSizeLabel);
		panelTwo.add(dLineSizeText);
		panelTwo.add(sdLabel);
		panelTwo.add(mdLabel);
		panelTwo.add(wdLabel);
		panelTwo.add(adLabel);
		panelTwo.add(start);
		panelTwo.add(error);

		this.add(panelTwo);

		this.revalidate();
		this.repaint();

	}
	
	public void launchPanelThree(){
		this.remove(panelTwo);
		
//		iCacheLevels = 3;
//		dCacheLevels = 2;
		
		panelThree = new JPanel();
		panelThree.setBackground(Color.black);
		panelThree.setLayout(null);
		panelThree.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		panelThree.setBounds(0, 0, 800, 578);
		
		JLabel totalTimeLabel = new JLabel("Total execution time in terms of cycles: ");
		totalTimeLabel.setForeground(Color.white);
		totalTimeLabel.setBounds(20,20,300,35);
		
		JLabel totalTime = new JLabel(""+this.totalExecutionTime);
		totalTime.setForeground(Color.white);
		totalTime.setBounds(300,20,90,35);
		
		JLabel ipcLabel = new JLabel("IPC: ");
		ipcLabel.setForeground(Color.white);
		ipcLabel.setBounds(400, 20, 50, 35);
		
		JLabel ipcValue = new JLabel(""+this.IPC);
		ipcValue.setForeground(Color.white);
		ipcValue.setBounds(450, 20, 100, 35);
		
		JLabel amatLabelI = new JLabel("AMAT for I-Memory: ");
		amatLabelI.setForeground(Color.white);
		amatLabelI.setBounds(400, 150, 150, 35);
		
		JLabel amatValueI = new JLabel(""+this.iAMAT);
		amatValueI.setForeground(Color.white);
		amatValueI.setBounds(550, 150, 100, 35);
		
		JLabel amatLabelD = new JLabel("AMAT for D-Memory: ");
		amatLabelD.setForeground(Color.white);
		amatLabelD.setBounds(400, 200, 150, 35);
		
		JLabel amatValueD = new JLabel(""+this.dAMAT);
		amatValueD.setForeground(Color.white);
		amatValueD.setBounds(550, 200, 100, 35);
		
		JLabel branchMispredictionLabel = new JLabel("Branch misprediction: ");
		branchMispredictionLabel.setForeground(Color.white);
		branchMispredictionLabel.setBounds(400, 250, 200, 35);
		
		JLabel branchMispredictionValue = new JLabel(""+ this.branchMisprediction);
		branchMispredictionValue.setForeground(Color.white);
		branchMispredictionValue.setBounds(550, 250, 100, 35);
		
		JLabel hitRatioI = new JLabel("Hit ratio for I-Cache:");
		hitRatioI.setForeground(Color.white);
		hitRatioI.setBounds(20,60,250,35);
		
		JLabel [] hitRatioLabelsI = new JLabel[iCacheLevels];
		JLabel [] hitRatioValuesI = new JLabel[iCacheLevels];
		
		for(int i = 0; i < iCacheLevels; i++){
			hitRatioLabelsI[i] = new JLabel("L"+(i+1));
			hitRatioLabelsI[i].setForeground(Color.white);
			hitRatioLabelsI[i].setBounds(20, 100+(i*30), 100, 35);
			panelThree.add(hitRatioLabelsI[i]);
			
			hitRatioValuesI[i] = new JLabel(""+this.iCacheHitRatio[i]);
			hitRatioValuesI[i].setForeground(Color.white);
			hitRatioValuesI[i].setBounds(100, 100+(i*30), 100, 35);
			panelThree.add(hitRatioValuesI[i]);
		}
		
		JLabel hitRatioD = new JLabel("Hit ratio for D-Cache:");
		hitRatioD.setForeground(Color.white);
		hitRatioD.setBounds(20,300,250,35);
		
		JLabel [] hitRatioLabelsD = new JLabel[dCacheLevels];
		JLabel [] hitRatioValuesD = new JLabel[dCacheLevels];
		
		for(int i = 0; i < dCacheLevels; i++){
			hitRatioLabelsD[i] = new JLabel("L"+(i+1));
			hitRatioLabelsD[i].setForeground(Color.white);
			hitRatioLabelsD[i].setBounds(20, 340+(i*30), 100, 35);
			panelThree.add(hitRatioLabelsD[i]);
			
			hitRatioValuesD[i] = new JLabel(""+this.dCacheHitRatio[i]);
			hitRatioValuesD[i].setForeground(Color.white);
			hitRatioValuesD[i].setBounds(100, 340+(i*30), 100, 35);
			panelThree.add(hitRatioValuesD[i]);
		}
		
		JButton startOver = new JButton("Start Over");
		startOver.setBounds(350, 500, 100, 35);
		startOver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Start Over
				launchPanelOne();
			}
		});
		
		panelThree.add(totalTimeLabel);
		panelThree.add(totalTime);
		panelThree.add(ipcLabel);
		panelThree.add(ipcValue);
		panelThree.add(amatLabelI);
		panelThree.add(amatLabelD);
		panelThree.add(amatValueI);
		panelThree.add(amatValueD);
		panelThree.add(branchMispredictionLabel);
		panelThree.add(branchMispredictionValue);
		panelThree.add(hitRatioI);
		panelThree.add(hitRatioD);
		panelThree.add(startOver);
		
		this.add(panelThree);
		this.revalidate();
		this.repaint();
	}


	public void startSimulator(Cache[] iCaches, Cache[] dCaches)
			throws IOException {
		Simulator s = new Simulator(loadFUs, loadLat, storeFUs, storeLat,
				jmpFUs, jmpLat, breqFUs, breqLat, jalFUs, jalLat, retFUs,
				retLat, addFUs, addLat, subFUs, subLat, addiFUs, addiLat,
				nandFUs, nandLat, multiplyFUs, multiplyLat, iCaches, dCaches,
				memoryAccessTime, ROBSize, superscalarity, instructionBuffer);

		BufferedReader br = new BufferedReader(new FileReader(programFile));
		String line = "";
		int i = 0;
		while ((line = br.readLine()) != null) {
			Simulator.instructionMemory.write(new Word(line), i);
			i++;
		}
		br.close();
		s.run();
		System.out.println("IPC : " + Simulator.IPC);
		this.IPC = Simulator.IPC;
		this.totalExecutionTime = Simulator.totalExecutionTime-1;
		this.iCacheHitRatio = Simulator.iCacheHitRatio;
		this.dCacheHitRatio = Simulator.dCacheHitRatio;
		this.iAMAT = Simulator.iAMAT;
		this.dAMAT = Simulator.dAMAT;
		this.branchMisprediction = Simulator.MissPredictionsRatio;
		launchPanelThree();
	}

	public static void main(String[] args) {
		new TomasuloSimulator();
	}
}
