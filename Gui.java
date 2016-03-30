package penguins;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;


public class Gui implements ActionListener {
	private JFrame frame;
	private JSpinner jspWeight;
	private JComboBox cboSpecies;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private ButtonGroup btngroup;
	private JTextField txtLat;
	private JTextField txtLong;
	private JTextField txtTheme;
	private JLabel lblInvalidTheme;
	private JLabel lblInvalidLat;
	private JLabel lblInvalidLong;
	private JLabel lblSexError;
	private JLabel lblTheme;
	
	private JTextPane textPane;
	private JButton btnValidate;
	private JButton btnExport;
	private JButton btnAnother;
	
	private static final Controller controller = new Controller();
	private JPanel pnlReport;
	private JPanel pnlLog;
	private JTextField txtOutputPath;
	private JTextField txtInputPath;
	
	//a special variable for the input dialog whether it completed
	boolean result;
	
	public Gui() {
		initialize();
		//controller = new Controller();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui mygui = new Gui();
					mygui.initialize();
					//set visible makes the form components visible and active
					mygui.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 515, 427);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		pnlLog = new JPanel();
		pnlLog.setBounds(0, 0, 499, 368);
		frame.getContentPane().add(pnlLog);
		pnlLog.setLayout(null);
		
		pnlLog.setVisible(true);
		
		
		pnlReport = new JPanel();
		pnlReport.setBounds(0, 0, 499, 368);
		frame.getContentPane().add(pnlReport);
		pnlReport.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmReportForm = new JMenuItem("Report Form");
		mntmReportForm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mntmReportForm.getText().equals("Report Form")){
					pnlLog.setVisible(false);
					pnlReport.setVisible(true);
					
					txtOutputPath.setText(controller.getBusiness().getoutputPath());
					txtInputPath.setText(controller.getBusiness().getinputPath());
					mntmReportForm.setText("Log Form");
				}
				else if (mntmReportForm.getText().equals("Log Form")){
					pnlLog.setVisible(true);
					pnlReport.setVisible(false);
					mntmReportForm.setText("Report Form");
				}
			}
		});
		mnFile.add(mntmReportForm);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenuItem mntmOutputFile = new JMenuItem("Set Output File...");
		mntmOutputFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//handle file input entering
				String messageName = "Please pick/create the directory and file to write to: ";
				
				//validate filepath first
				inputLoop(messageName, true);
				
				//pnlLog.setVisible(false);
				//pnlReport.setVisible(true);
			}
		});
		mnSettings.add(mntmOutputFile);
		
		JMenuItem mntmInputFile = new JMenuItem("Set Input File...");
		mntmInputFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//handle file input entering
				String messageName = "Please pick the file to import: ";
				
				//validate filepath first
				inputLoop(messageName, false);
				
				try {
					textPane.setText(controller.getBusiness().readFile(controller.getBusiness().getinputPath()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnSettings.add(mntmInputFile);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnAbout.add(mntmAbout);
		
		loadLog();
		//frame.getContentPane().setLayout(groupLayout);
		loadReport();
		//frame.setVisible(true);
	}
	//creates the dialog to have user specify a valid file path for either read/write
	private boolean inputLoop(String msg, boolean write){ //boolean true = write/ false = read
		String jopFilePath = JOptionPane.showInputDialog(msg);
		
		result = true;
		
		if (jopFilePath == "" | jopFilePath == null){
			result = false;
			System.out.println("returning false");
			return result; //updating file path abandoned
		}
		if (!write){
			while (controller.validateFilePath(jopFilePath) == 0){
				inputLoop("File does not exist. Please try again", write);
				return result;
			}
		}
		while (controller.validateFilePath(jopFilePath) == 1){ //1 = invalid file (not end in .txt)
			inputLoop("Must be a valid text file. Please try again", write);
			return result;
		}
		while (controller.validateFilePath(jopFilePath) == 2){
			inputLoop("That was not a valid filepath. Please try again", write);
			return result;
		}
		if (write){
			while (controller.validateFilePath(jopFilePath) == 3){
				int dlgResult = JOptionPane.showConfirmDialog(null, "File already exists. Okay to Overwrite?");
				if (dlgResult == 0){
					//System.out.println("Result was Yes");
					controller.getBusiness().setoutputPath(jopFilePath);
					return result;
				}
				
				else if (dlgResult == 1){
					System.out.println("Result was No");
					inputLoop("Please Enter the filepath to read/write from: ", write);
					return result;
				}
				else if (dlgResult == 2){
					System.out.println("Result was Cancel");
				}
				
			}
			result = true;
			return result;
		}
		//finally, adjust paths.
		if (write){
			controller.getBusiness().setoutputPath(jopFilePath);
			txtOutputPath.setText(jopFilePath);
		}
		else {
			controller.getBusiness().setinputPath(jopFilePath);
			txtInputPath.setText(jopFilePath);
		}
		System.out.println("returning true");
		return result;
	}
	//load the report panel with all the proper button elements
	private void loadReport(){
		textPane = new JTextPane();
		textPane.setFont(new Font("Arial", Font.PLAIN, 9));
		textPane.setEditable(false);
		textPane.setBounds(10, 11, 462, 265);
		pnlReport.add(textPane);
		
		JButton btnReadFile = new JButton("Read File");
		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("read file clicked");
				try {
					if (controller.validateFilePath(controller.getBusiness().getinputPath()) != 3){
						if (inputLoop("Please specify a file to read from: ", false) == true){
							textPane.setText(controller.getBusiness().readFile(txtInputPath.getText()));
						}
					};
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReadFile.setFont(new Font("Arial", Font.PLAIN, 9));
		btnReadFile.setBounds(219, 334, 89, 23);
		pnlReport.add(btnReadFile);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(472, 11, 17, 265);
		pnlReport.add(scrollBar);
		
		txtOutputPath = new JTextField();
		txtOutputPath.setFont(new Font("Arial", Font.PLAIN, 9));
		txtOutputPath.setBounds(76, 281, 232, 20);
		pnlReport.add(txtOutputPath);
		txtOutputPath.setColumns(10);
		txtOutputPath.setEnabled(false);
		
		JLabel lblOutputPath = new JLabel("Output Path:");
		lblOutputPath.setFont(new Font("Arial", Font.PLAIN, 9));
		lblOutputPath.setBounds(10, 284, 56, 14);
		pnlReport.add(lblOutputPath);
		
		JLabel lblInputPath = new JLabel("Input Path:");
		lblInputPath.setFont(new Font("Arial", Font.PLAIN, 9));
		lblInputPath.setBounds(10, 309, 56, 14);
		pnlReport.add(lblInputPath);
		
		txtInputPath = new JTextField();
		txtInputPath.setText((String) null);
		txtInputPath.setFont(new Font("Arial", Font.PLAIN, 9));
		txtInputPath.setColumns(10);
		txtInputPath.setBounds(76, 305, 232, 20);
		pnlReport.add(txtInputPath);
		txtInputPath.setEnabled(false);
		
		txtOutputPath.setText(controller.getBusiness().getoutputPath());
		txtInputPath.setText(controller.getBusiness().getinputPath());
		
		pnlReport.setVisible(false);
	}
	//Load the pnLog panel with the form elements (some shit happens when you adjust gui in design view to elements
	private void loadLog(){
		
		btngroup = new ButtonGroup();
		cboSpecies = new JComboBox();
		cboSpecies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cboSpecies.getSelectedItem().equals("Penguin")){
					lblTheme.setText("Blood Pressure");
				}
				if (cboSpecies.getSelectedItem().equals("Sea Lion")){
					lblTheme.setText("# of Spots");
				}
				if (cboSpecies.getSelectedItem().equals("Walrus")){
					lblTheme.setText("Dental Health");
				}
			}
		});
		cboSpecies.setModel(new DefaultComboBoxModel(new String[] {"Penguin", "Sea Lion", "Walrus"}));
		cboSpecies.setBounds(22, 30, 129, 20);
		pnlLog.add(cboSpecies);
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setFont(new Font("Arial", Font.PLAIN, 10));
		rdbtnMale.setBounds(32, 56, 109, 23);
		pnlLog.add(rdbtnMale);
		btngroup.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setFont(new Font("Arial", Font.PLAIN, 10));
		rdbtnFemale.setBounds(32, 81, 109, 23);
		pnlLog.add(rdbtnFemale);
		btngroup.add(rdbtnFemale);
		
		jspWeight = new JSpinner();
		jspWeight.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		jspWeight.setBounds(32, 111, 47, 20);
		pnlLog.add(jspWeight);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Arial", Font.PLAIN, 10));
		lblWeight.setBounds(105, 114, 46, 14);
		pnlLog.add(lblWeight);
		
		txtLat = new JTextField();
		txtLat.setBounds(32, 149, 86, 20);
		pnlLog.add(txtLat);
		txtLat.setColumns(10);
		
		txtLong = new JTextField();
		txtLong.setColumns(10);
		txtLong.setBounds(32, 180, 86, 20);
		pnlLog.add(txtLong);
		
		JLabel lblLat = new JLabel("Lat");
		lblLat.setFont(new Font("Arial", Font.PLAIN, 10));
		lblLat.setBounds(128, 152, 46, 14);
		pnlLog.add(lblLat);
		
		JLabel lblLong = new JLabel("Long");
		lblLong.setFont(new Font("Arial", Font.PLAIN, 10));
		lblLong.setBounds(128, 183, 46, 14);
		pnlLog.add(lblLong);
		
		txtTheme = new JTextField();
		txtTheme.setBounds(32, 222, 86, 20);
		pnlLog.add(txtTheme);
		txtTheme.setColumns(10);
		
		lblTheme = new JLabel("Blood Pressure");
		lblTheme.setFont(new Font("Arial", Font.PLAIN, 10));
		lblTheme.setBounds(128, 225, 86, 14);
		pnlLog.add(lblTheme);
		
		btnValidate = new JButton("Validate");
		btnValidate.setFont(new Font("Arial", Font.PLAIN, 10));
		btnValidate.setActionCommand("validate");
		btnValidate.addActionListener(this);
		btnValidate.setBounds(22, 334, 89, 23);
		pnlLog.add(btnValidate);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setActionCommand("clear");
		btnClear.addActionListener(this);
		btnClear.setFont(new Font("Arial", Font.PLAIN, 10));
		btnClear.setBounds(219, 29, 89, 23);
		pnlLog.add(btnClear);
		
		btnAnother = new JButton("Another");
		btnAnother.setActionCommand("another");
		btnAnother.addActionListener(this);
		btnAnother.setFont(new Font("Arial", Font.PLAIN, 10));
		btnAnother.setBounds(120, 334, 89, 23);
		pnlLog.add(btnAnother);
		btnAnother.setEnabled(false);
		
		btnExport = new JButton("Export");
		btnExport.setBounds(219, 334, 89, 23);
		btnExport.setActionCommand("export");
		btnExport.setFont(new Font("Arial", Font.PLAIN, 10));
		pnlLog.add(btnExport);
		btnExport.setEnabled(false);
		btnExport.addActionListener(this);
		
		lblInvalidTheme = new JLabel("Invalid");
		lblInvalidTheme.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInvalidTheme.setForeground(Color.RED);
		lblInvalidTheme.setBounds(224, 225, 46, 14);
		pnlLog.add(lblInvalidTheme);
		lblInvalidTheme.setVisible(false);
		
		lblInvalidLong = new JLabel("Invalid");
		lblInvalidLong.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInvalidLong.setForeground(Color.RED);
		lblInvalidLong.setBounds(224, 183, 46, 14);
		pnlLog.add(lblInvalidLong);
		lblInvalidLong.setVisible(false);
		
		lblInvalidLat = new JLabel("Invalid");
		lblInvalidLat.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInvalidLat.setForeground(Color.RED);
		lblInvalidLat.setBounds(224, 152, 46, 14);
		pnlLog.add(lblInvalidLat);
		lblInvalidLat.setVisible(false);
		
		lblSexError = new JLabel("Pick Male or Female");
		lblSexError.setForeground(Color.RED);
		lblSexError.setVisible(false);
		lblSexError.setFont(new Font("Arial", Font.PLAIN, 10));
		lblSexError.setBounds(147, 61, 123, 14);
		pnlLog.add(lblSexError);
		
	}
	
	/**
	 * Handles all form events
	 * Different buttons each send their own respective action events
	 */
	@Override
	  public void actionPerformed(ActionEvent evt) {

		String cmd = evt.getActionCommand();
		//Validate Button Click
		if (cmd == "validate"){
			validateForm();
		}
		else if (cmd == "export"){
			controller.getBusiness().listAnimals();
			String file = controller.getBusiness().getoutputPath();
			try {
				controller.getBusiness().writeFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (cmd == "import"){
			String file = controller.getBusiness().getinputPath();
			try {
				controller.getBusiness().readFile(file);
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

		else if (cmd == "another"){
			addAnother();
		}
		else if (cmd == "clear"){
			clearForm();
		}
	}
	
	private void validateForm(){
		boolean validTheme = controller.validateTheme(txtTheme.getText(), cboSpecies.getSelectedItem().toString());
		//System.out.println(validTheme);
		if (validTheme){
			lblInvalidTheme.setVisible(false);
		}
		else {
			lblInvalidTheme.setVisible(true);
		}
		
		boolean validLat = controller.validateLatLong("lat", txtLat.getText());
		if (validLat){
			lblInvalidLat.setVisible(false);	
		}
		else { lblInvalidLat.setVisible(true);}
		
		boolean validLon = controller.validateLatLong("lon", txtLong.getText());
		if (validLon){
			lblInvalidLong.setVisible(false);	
		}
		else { lblInvalidLong.setVisible(true);}
		
		boolean validSex = true;
		if (getSelectedButtonText(btngroup) == 'x'){
			lblSexError.setVisible(true);
			validSex = false;
		}
		else {
			lblSexError.setVisible(false);
			validSex = true;
		}
		
		
		//if all are true, let export button be enabled
		if (validTheme && validLat && validLon && validSex){
			btnExport.setEnabled(true);
			btnAnother.setEnabled(true);
			
			cboSpecies.setEnabled(false);
			rdbtnMale.setEnabled(false);
			rdbtnFemale.setEnabled(false);
			jspWeight.setEnabled(false);
			txtLat.setEnabled(false);
			txtLong.setEnabled(false);
			txtTheme.setEnabled(false);
			
			
		}
	}
	
	private void addAnother(){
		//System.out.println("rdbtnMale text = " + rdbtnMale.getText());
		char sex = getSelectedButtonText(btngroup);
		double coord[][] = {{Double.parseDouble(txtLat.getText()),Double.parseDouble(txtLong.getText())}};
		
		controller.getBusiness().addAnimal(cboSpecies.getSelectedItem().toString(), sex, 
				(float) Float.parseFloat(jspWeight.getValue().toString()), coord, txtTheme.getText());
		clearForm();
		btnAnother.setEnabled(false);
		
		cboSpecies.setEnabled(true);
		rdbtnMale.setEnabled(true);
		rdbtnFemale.setEnabled(true);
		jspWeight.setEnabled(true);
		txtLat.setEnabled(true);
		txtLong.setEnabled(true);
		txtTheme.setEnabled(true);
		
	}
	
	private void clearForm(){
		cboSpecies.setSelectedIndex(0);
		rdbtnMale.setSelected(true);
		rdbtnFemale.setSelected(false);
		jspWeight.setValue(0);
		txtLat.setText("");
		txtLong.setText("");
		txtTheme.setText("");
		
		btnAnother.setEnabled(false);
	}
	private char getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            
            if (button.isSelected()) {
            	//System.out.println("button is selected");
                return button.getText().toLowerCase().charAt(0);
            }
        }

        return 'x';
    }
}
