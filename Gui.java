package penguins;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
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
import javax.swing.Timer;

import java.awt.SystemColor;

import javax.swing.border.CompoundBorder;
import javax.swing.SwingConstants;

public class Gui implements ActionListener {
	private JFrame frmPenguinTrackingApp;
	private JSpinner jspWeight;
	private JComboBox<String> cboSpecies;
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
	private JButton btnAddCoords;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnBack;
	private JLabel lblTimerMsg;
	
	private static final Controller controller = new Controller();
	private JPanel pnlMain;
	private JPanel pnlReport;
	private JPanel pnlLog;
	private JLabel lblInputPath;
	
	private Timer timer;
	private final int DELAY = 60;
	private int tick = 0;
	
	private Gps formCoords;
	//a special variable for the input dialog whether it completed
	boolean result;
	private JTextPane tpnCoords;
	private JButton btnViewFile;
	private JLabel lblNewLabel;
	private JLabel lblTracking;
	private JLabel lblNewLabel_2;
	private JLabel lblViewImportedText;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui mygui = new Gui();
					mygui.initialize();
					//set visible makes the form components visible and active
					mygui.frmPenguinTrackingApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Gui() {
		initialize();
	}
	
	private void initialize() {
		formCoords = new Gps(); //create Gps object to save coordinates
		frmPenguinTrackingApp = new JFrame();
		frmPenguinTrackingApp.setTitle("Penguin Tracking App");
		frmPenguinTrackingApp.setBounds(100, 100, 420, 519);
		frmPenguinTrackingApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPenguinTrackingApp.getContentPane().setLayout(null);
		btngroup = new ButtonGroup();
		
		pnlMain = new JPanel();
		pnlMain.setBounds(0, 0, 410, 460);
		frmPenguinTrackingApp.getContentPane().add(pnlMain);
		pnlMain.setLayout(null);
		
		JLabel lblMap = new JLabel();
		lblMap.setIcon(new ImageIcon(Gui.class.getResource("/map.png")));
		lblMap.setBounds(10, 155, 390, 305);
		pnlMain.add(lblMap);
		
		JButton btnAddAnimal = new JButton("Add Animal");
		btnAddAnimal.setToolTipText("Add a tracker for a new animal");
		btnAddAnimal.setForeground(new Color(0, 0, 128));
		btnAddAnimal.setBackground(SystemColor.activeCaption);
		btnAddAnimal.setFont(new Font("Arial", Font.PLAIN, 9));
		btnAddAnimal.setBounds(10, 11, 89, 23);
		btnAddAnimal.setActionCommand("addanimal");
		btnAddAnimal.addActionListener(this);
		
		pnlMain.add(btnAddAnimal);
		
		JButton btnSaveFile = new JButton("Save to File");
		btnSaveFile.setToolTipText("Save your animals and their coordinates to a textfile");
		btnSaveFile.setForeground(new Color(0, 0, 128));
		btnSaveFile.setBackground(SystemColor.activeCaption);
		btnSaveFile.setFont(new Font("Arial", Font.PLAIN, 9));
		btnSaveFile.setBounds(10, 45, 89, 23);
		btnSaveFile.setActionCommand("savefile");
		btnSaveFile.addActionListener(this);
		pnlMain.add(btnSaveFile);
		
		btnViewFile = new JButton("View File");
		btnViewFile.setToolTipText("View an existing animal text file");
		btnViewFile.setForeground(new Color(0, 0, 128));
		btnViewFile.setBackground(SystemColor.activeCaption);
		btnViewFile.setFont(new Font("Arial", Font.PLAIN, 9));
		btnViewFile.setBounds(10, 79, 89, 23);
		btnViewFile.setActionCommand("viewfile");
		btnViewFile.addActionListener(this);
		pnlMain.add(btnViewFile);
		
		lblNewLabel = new JLabel("<html>Antarctic Animal <br/>\r\nTracking App</html>");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(109, 14, 279, 99);
		pnlMain.add(lblNewLabel);
		
		lblTracking = new JLabel("Not Tracking any Species Yet");
		lblTracking.setForeground(new Color(0, 0, 128));
		lblTracking.setBounds(10, 124, 186, 23);
		pnlMain.add(lblTracking);
		
		lblTimerMsg = new JLabel("timer msg");
		lblTimerMsg.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimerMsg.setForeground(Color.RED);
		lblTimerMsg.setBounds(239, 130, 149, 14);
		pnlMain.add(lblTimerMsg);
		lblTimerMsg.setVisible(false);
		
		pnlLog = new JPanel();
		pnlLog.setBounds(0, 0, 410, 460);
		frmPenguinTrackingApp.getContentPane().add(pnlLog);
		pnlLog.setLayout(null);
		
		pnlLog.setVisible(false);
		
		cboSpecies = new JComboBox<String>();
		cboSpecies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cboSpecies.getSelectedIndex() != -1){
					if (cboSpecies.getSelectedItem().equals("Penguin")){
						lblTheme.setText("Blood Pressure");
					}
					if (cboSpecies.getSelectedItem().equals("Sea Lion")){
						lblTheme.setText("# of Spots");
					}
					if (cboSpecies.getSelectedItem().equals("Walrus")){
						lblTheme.setText("Dental Health");
					}
					//else {System.out.println(cboSpecies.getSelectedItem());}
				}
			}
		});
		cboSpecies.setModel(new DefaultComboBoxModel<String>(new String[] {"Penguin", "Sea Lion", "Walrus"}));
		cboSpecies.setBounds(22, 64, 129, 20);
		pnlLog.add(cboSpecies);
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setFont(new Font("Arial", Font.PLAIN, 10));
		rdbtnMale.setBounds(32, 90, 109, 23);
		pnlLog.add(rdbtnMale);
		btngroup.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setFont(new Font("Arial", Font.PLAIN, 10));
		rdbtnFemale.setBounds(32, 115, 109, 23);
		pnlLog.add(rdbtnFemale);
		btngroup.add(rdbtnFemale);
		
		jspWeight = new JSpinner();
		jspWeight.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		jspWeight.setBounds(32, 145, 47, 20);
		pnlLog.add(jspWeight);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Arial", Font.PLAIN, 10));
		lblWeight.setBounds(105, 148, 46, 14);
		pnlLog.add(lblWeight);
		
		
		txtTheme = new JTextField();
		txtTheme.setBounds(32, 176, 86, 20);
		pnlLog.add(txtTheme);
		txtTheme.setColumns(10);
		
		tpnCoords = new JTextPane();
		tpnCoords.setBorder(new CompoundBorder());
		tpnCoords.setEnabled(false);
		tpnCoords.setEditable(false);
		tpnCoords.setFont(new Font("Arial", Font.PLAIN, 9));
		tpnCoords.setBounds(22, 207, 295, 110);
		pnlLog.add(tpnCoords);
		
		lblTheme = new JLabel("Blood Pressure");
		lblTheme.setFont(new Font("Arial", Font.PLAIN, 10));
		lblTheme.setBounds(128, 179, 86, 14);
		pnlLog.add(lblTheme);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setActionCommand("clear");
		btnClear.addActionListener(this);
		btnClear.setFont(new Font("Arial", Font.PLAIN, 10));
		btnClear.setBounds(22, 393, 89, 23);
		pnlLog.add(btnClear);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand("cancel");
		btnCancel.addActionListener(this);
		btnCancel.setFont(new Font("Arial", Font.PLAIN, 10));
		btnCancel.setBounds(22, 426, 89, 23);
		pnlLog.add(btnCancel);
		
		
		btnSave = new JButton("Save");
		btnSave.setBounds(306, 426, 89, 23);
		btnSave.setActionCommand("savelog");
		btnSave.setFont(new Font("Arial", Font.PLAIN, 10));
		pnlLog.add(btnSave);
		btnSave.addActionListener(this);
		
		lblInvalidTheme = new JLabel("Invalid");
		lblInvalidTheme.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInvalidTheme.setForeground(Color.RED);
		lblInvalidTheme.setBounds(224, 179, 46, 14);
		pnlLog.add(lblInvalidTheme);
		lblInvalidTheme.setVisible(false);
		
		lblSexError = new JLabel("Pick Male or Female");
		lblSexError.setForeground(Color.RED);
		lblSexError.setVisible(false);
		lblSexError.setFont(new Font("Arial", Font.PLAIN, 10));
		lblSexError.setBounds(215, 94, 123, 14);
		pnlLog.add(lblSexError);
		
		txtLat = new JTextField();
		txtLat.setBounds(22, 327, 86, 20);
		pnlLog.add(txtLat);
		txtLat.setColumns(10);
		
		txtLong = new JTextField();
		txtLong.setBounds(128, 327, 86, 20);
		pnlLog.add(txtLong);
		txtLong.setColumns(10);
		
		JLabel lblLong = new JLabel("Long");
		lblLong.setBounds(128, 349, 24, 13);
		pnlLog.add(lblLong);
		lblLong.setFont(new Font("Arial", Font.PLAIN, 10));
		
		JLabel lblLat = new JLabel("Lat");
		lblLat.setBounds(25, 351, 24, 13);
		pnlLog.add(lblLat);
		lblLat.setFont(new Font("Arial", Font.PLAIN, 10));
		
		lblInvalidLong = new JLabel("Invalid");
		lblInvalidLong.setBounds(172, 348, 46, 14);
		pnlLog.add(lblInvalidLong);
		lblInvalidLong.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInvalidLong.setForeground(Color.RED);
		
		lblInvalidLat = new JLabel("Invalid");
		lblInvalidLat.setBounds(70, 349, 46, 14);
		pnlLog.add(lblInvalidLat);
		lblInvalidLat.setFont(new Font("Arial", Font.PLAIN, 10));
		lblInvalidLat.setForeground(Color.RED);
		
		btnAddCoords = new JButton("Add Coords");
		btnAddCoords.setBounds(228, 328, 89, 23);
		pnlLog.add(btnAddCoords);
		btnAddCoords.setActionCommand("addcoords");
		btnAddCoords.setFont(new Font("Arial", Font.PLAIN, 10));
		
		lblNewLabel_2 = new JLabel("Track New Animal");
		lblNewLabel_2.setForeground(new Color(0, 0, 139));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel_2.setBounds(22, 11, 234, 42);
		pnlLog.add(lblNewLabel_2);
		
		
		pnlReport = new JPanel();
		pnlReport.setBounds(0, 0, 410, 460);
		frmPenguinTrackingApp.getContentPane().add(pnlReport);
		pnlReport.setLayout(null);
		pnlReport.setVisible(false);
		textPane = new JTextPane();
		textPane.setFont(new Font("Arial", Font.PLAIN, 9));
		textPane.setEditable(false);
		textPane.setBounds(10, 103, 360, 265);
		pnlReport.add(textPane);
		
		JButton btnReadFile = new JButton("Read File");
		btnReadFile.setActionCommand("readfile");
		btnReadFile.addActionListener(this);
		btnReadFile.setFont(new Font("Arial", Font.PLAIN, 9));
		btnReadFile.setBounds(299, 426, 89, 23);
		pnlReport.add(btnReadFile);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(372, 103, 17, 265);
		pnlReport.add(scrollBar);
		
		JLabel lblInput = new JLabel("Input File Path:");
		lblInput.setFont(new Font("Arial", Font.PLAIN, 9));
		lblInput.setBounds(10, 383, 77, 14);
		pnlReport.add(lblInput);
		
		lblInputPath = new JLabel();
		lblInputPath.setText((String) null);
		lblInputPath.setFont(new Font("Arial", Font.PLAIN, 9));
		lblInputPath.setBounds(121, 379, 232, 20);
		pnlReport.add(lblInputPath);
		lblInputPath.setEnabled(false);
		lblInputPath.setText(controller.getBusiness().getinputPath());
		
		lblViewImportedText = new JLabel("View Report From File");
		lblViewImportedText.setForeground(new Color(0, 0, 128));
		lblViewImportedText.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblViewImportedText.setBounds(10, 11, 360, 51);
		pnlReport.add(lblViewImportedText);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pnlMain.setVisible(true);
				pnlLog.setVisible(false);
				pnlReport.setVisible(false);
			}
		});
		btnBack.setToolTipText("Return to Main Panel");
		btnBack.setBounds(10, 425, 89, 23);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 9));
		pnlReport.add(btnBack);
		pnlReport.setVisible(false);
		
		btnAddCoords.addActionListener(this);
		lblInvalidLat.setVisible(false);
		lblInvalidLong.setVisible(false);
		
		JMenuBar menuBar = new JMenuBar();
		frmPenguinTrackingApp.setJMenuBar(menuBar);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				About myabout = new About();
				myabout.setVisible(true);
			}
		});
		mnAbout.add(mntmAbout);
		loadReport();

	}
	
	//creates the dialog to have user specify a valid file path for either read/write
	private boolean inputLoop(String msg, boolean write){ //write should return 0, 2 is cancel
		String jopFilePath = JOptionPane.showInputDialog(msg);
		
		result = true;
		
		if (jopFilePath == "" | jopFilePath == null){
			result = false;
			System.out.println("cancel pressed. returning false");
			return result; //updating file path abandoned
		}
		if (!write){
			while (controller.validateFilePath(jopFilePath) == 0){
				result = inputLoop("File does not exist. Please try again", write);
				return result;
			}
		}
		while (controller.validateFilePath(jopFilePath) == 1){ //1 = invalid file (not end in .txt)
			result = inputLoop("Must be a valid text file. Please try again", write);
			return result;
		}
		while (controller.validateFilePath(jopFilePath) == 2){
			result = inputLoop("That was not a valid filepath. Please try again", write);
			return result;
		}
		if (write){
			while (controller.validateFilePath(jopFilePath) == 3){
				int dlgResult = JOptionPane.showConfirmDialog(null, "File already exists. Okay to Overwrite?");
				if (dlgResult == 0){
					System.out.println("Result was Yes");
					result = true;
					controller.getBusiness().setoutputPath(jopFilePath);
					return result;
				}
				
				else if (dlgResult == 1){
					System.out.println("Result was No");
					result = inputLoop("Please Enter the filepath to read/write from: ", write);
					return result;	
				}
				else if (dlgResult == 2){
					System.out.println("Result was cancel");
					result = false;
					return result;
					}
				}
			}
			
		//finally, adjust paths.
		if (write){
			controller.getBusiness().setoutputPath(jopFilePath);
		}
		else {
			controller.getBusiness().setinputPath(jopFilePath);
			lblInputPath.setText(jopFilePath);
		}
		System.out.println("returning true");
		return result;
	}
	
	//load the report panel with all the proper button elements
	private void loadReport(){
		pnlReport.setVisible(false);
	}

	
	/**
	 * Handles all form events
	 * Different buttons each send their own respective action events
	 */
	@Override
	  public void actionPerformed(ActionEvent evt) {

		String cmd = evt.getActionCommand();
		
		//pnlLog Save Button Click
		if (cmd == "savelog"){
			//validate form data
			if (validate()){
				//save form data to object
				System.out.println("coordlist size: " + formCoords.coordList.size());
				saveAnimal();
				//clear form
				clearForm();
				formCoords.clearCoords();
				//go back to main form
				pnlReport.setVisible(false);
				pnlLog.setVisible(false);
				pnlMain.setVisible(true);
				
				lblTracking.setText("Tracking " + controller.getBusiness().getAnimals().size() + " animal(s) so far");
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
		//pnlLog Form Add Coords button click
		else if (cmd == "addcoords"){
			if (validateCoords()){
				String c = txtLat.getText() + " " + txtLong.getText();
				tpnCoords.setText(tpnCoords.getText() + c + "\n");
				txtLat.setText("");
				txtLong.setText("");
				
				formCoords.addCoords(c);
			}
		}
		//pnlLog Clear Form button click
		else if (cmd == "clear"){
			clearForm();
		}
		//pnlMain Add Animal Button click
		else if(cmd == "addanimal"){
			pnlMain.setVisible(false);
			pnlReport.setVisible(false);
			pnlLog.setVisible(true);
		}
		//pnlMain Save to File button click
		else if(cmd == "savefile"){
			controller.getBusiness().listAnimals(); //TODO: if animals is 0 msg error.
			if (controller.getBusiness().getAnimals().isEmpty()){
				JOptionPane.showMessageDialog(this.pnlMain, "Whoops. You have no records to export. "
						+ "Create some data and try again.", "Error: no data", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (inputLoop("Please Specify a directory path and file name (ie. c:/temp/output.txt)", true)){
				try {
					System.out.println(controller.getBusiness().getoutputPath());
					controller.getBusiness().writeFile(controller.getBusiness().getoutputPath());
					System.out.println("done exporting file");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			
			timer = new Timer(DELAY, new TimerHandler());
			timer.start();
		}
		//pnlReport Read File button click
		else if(cmd == "readfile"){
			System.out.println("read file clicked");
			try {
		
				if (inputLoop("File: " + controller.getBusiness().getinputPath() +
						" does not exist.\n" +
						"Please specify a file to read from: ", false) == true)
				{
					textPane.setText(controller.getBusiness().readFile(lblInputPath.getText()));
				}
				
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//pnlMain View File button click
		else if(cmd == "viewfile"){
			pnlMain.setVisible(false);
			pnlLog.setVisible(false);
			pnlReport.setVisible(true);
		}
		//pnlLog Cancel button click
		else if (cmd == "cancel"){
			clearForm();
			pnlLog.setVisible(false);
			pnlReport.setVisible(false);
			pnlMain.setVisible(true);
		}
	}
	

	//inner class
		public class TimerHandler implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				doTick();
				
			}
			
		}
	
	private void doTick(){
		tick += 1;
		//System.out.println("tick=" + tick);
		if (tick < 10){
			lblTimerMsg.setText("Saving to File");
		}
		else if (tick < 20){
			lblTimerMsg.setText("Saving to File.");
		}
		else if (tick < 30){
			lblTimerMsg.setText("Saving to File..");
		}
		else if (tick < 40){
			lblTimerMsg.setText("Saving to File...");
		}
		else if (tick < 50){
			lblTimerMsg.setText("File saved Successfully!");
		}
		else {
			lblTimerMsg.setText("");
			timer.restart();
			timer.stop();
			
		}
			
		
		return;
		
	}
	
	//validate main log panel
	private boolean validate(){
		boolean validTheme = controller.validateTheme(txtTheme.getText(), cboSpecies.getSelectedItem().toString());
		
		if (validTheme){
			lblInvalidTheme.setVisible(false);
		}
		else {
			lblInvalidTheme.setVisible(true);
		}
				
		boolean validSex = true;
		if (getSelectedButtonText(btngroup) == 'x'){
			lblSexError.setVisible(true);
			validSex = false;
		}
		else {
			lblSexError.setVisible(false);
			validSex = true;
		}
		
		
		if (validTheme && validSex){
			return true;
		}
		else {return false;}
	}
	//validate coordinates (called by add coords button)
	private boolean validateCoords(){
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
		
		if (validLat && validLon){
			return true;
		}
		else {return false;}
		
	}
	
	//save animal form data to the list of animal objects in business class (called by Save button)
	//call addAnimal
	private void saveAnimal(){
		
		char sex = getSelectedButtonText(btngroup);
		System.out.println("coordlist size: " + formCoords.coordList.size());
		controller.getBusiness().addAnimal(cboSpecies.getSelectedItem().toString(), sex, 
				(float) Float.parseFloat(jspWeight.getValue().toString()), formCoords, txtTheme.getText());
	}
	
	//clear values in form to blank
	private void clearForm(){
		cboSpecies.setSelectedIndex(-1);
		rdbtnMale.setSelected(true);
		rdbtnFemale.setSelected(false);
		jspWeight.setValue(0);
		txtLat.setText("");
		txtLong.setText("");
		txtTheme.setText("");
		tpnCoords.setText("");
		formCoords.clearCoords();

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
