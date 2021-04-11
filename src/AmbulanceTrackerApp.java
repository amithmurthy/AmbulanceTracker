import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class AmbulanceTrackerApp {
	static JPanel panel;
	static JFrame window;
	static JTable jt;
	static JFrame patientFrame;
	static JFrame ambulanceFrame;
	public static void AmbulanceTrackerApp(){
		window = new JFrame("Ambulance Tracking System");
		panel = new JPanel();
		panel.setLayout(null);
		//panel.setLayout(new FlowLayout());
		JLabel label = new JLabel("Ambulance Tracking System");
		label.setBounds(60,10,200,20);
		//window.pack();
		window.setSize(300,300);
		JButton patients = new JButton("Patients");
		panel.add(label);
		panel.add(patients);
		patients.setBounds(7,40,270,60);
		JButton ambulance = new JButton("Ambulance");
		panel.add(ambulance);
		ambulance.setBounds(7,110,270,60);
		JButton exit = new JButton("Exit");
		panel.add(exit);
		exit.setBounds(7,180,270,60);
		window.add(panel);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		//Add event listener to exit button 
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				exitButtonAction(e);
			}
		});
		patients.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					patientsPanel(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		ambulance.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ambulancePanel(e);
			}
		});
	}
	public static void patientsPanel(ActionEvent e) throws IOException{
		patientFrame = new JFrame();
		patientFrame.setSize(400, 300);
		JPanel patientPanel = new JPanel();
		patientPanel.setLayout(null);
		patientFrame.add(patientPanel);
		JButton back = new JButton("Back");
		JButton add = new JButton("Add New");
		patientPanel.add(back);
		patientPanel.add(add);
		back.setBounds(30,200,80,50);
		add.setBounds(150,200,100,50);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent b){
				patientFrame.setVisible(false);
				window.setVisible(true);
			}
		});
		patientFrame.setVisible(true);
		window.setVisible(false);
		JLabel pLabel = new JLabel("Patients");
		patientPanel.add(pLabel);
		pLabel.setBounds(10,10,50,50);
		String[] colheads = {"ID","Location","Status","Ambulance"};
		Object[][] data_1 = CSVreader.table();
		System.out.println(data_1);
		jt = new JTable(data_1,colheads);
		jt.setBounds(10,60,200,90);
		jt.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				patientMouse(e);
			}
		});
		
		patientPanel.add(jt);
		JScrollPane scrollPane = new JScrollPane(jt);
		patientPanel.add(scrollPane);
		scrollPane.setBounds(10,60,350,70);
		jt.setPreferredScrollableViewportSize(new Dimension(500,50));
		jt.setFillsViewportHeight(true);
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				patientFrame.setVisible(false);
				addNewFrame(e);
			}
		});
	}
	
	private static void addNewFrame(ActionEvent e){
		JFrame addFrame = new JFrame();
		addFrame.setSize(400,400);
		JPanel addPanel = new JPanel();
		addPanel.setLayout(null);
		addFrame.add(addPanel);
		addFrame.setVisible(true);
		String[] new_data = CSVreader.data();
		JLabel label = new JLabel("Patient: " + Integer.toString(new_data.length));
		addPanel.add(label);
		label.setBounds(150,10,80,50);
		JButton save = new JButton("save");
		JButton cancel = new JButton("cancel");
		save.setBounds(50,280,100,50);
		cancel.setBounds(170,280,100,50);
		addPanel.add(save);
		addPanel.add(cancel);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addPatient.save(e);
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addFrame.setVisible(false);
				patientFrame.setVisible(true);
			}
		});
		//TextFields and Labels
		JTextField idField = new JTextField(Integer.toString(new_data.length));
		addPanel.add(idField);
		idField.setBounds(100, 50, 200, 40);
		JLabel id = new JLabel("ID:");
		id.setBounds(10,45,50,50);
		addPanel.add(id);
		JLabel location = new JLabel("Location:");
		addPanel.add(location);
		location.setBounds(10,100,90,50);
		JTextField locationField1 = new JTextField();
		addPanel.add(locationField1);
		locationField1.setBounds(100,110,100,40);
		JTextField locationField2 = new JTextField();
		locationField2.setBounds(220, 110, 100,40);
		addPanel.add(locationField2);
		JLabel status = new JLabel("Status:");
		addPanel.add(status);
		status.setBounds(10,160,50,50);
		JLabel ambulance = new JLabel("Ambulance:");
		addPanel.add(ambulance);
		ambulance.setBounds(10,220,80,50);
		//Creating ComboBox
		String[] items = {"Transporting","Assigned","Pending","Completed"};
		JComboBox box = new JComboBox(items);
		addPanel.add(box);
		box.setBounds(100,160,200,40);
		//Ambulance ComboBox
		JComboBox abox = new JComboBox(AmbulanceReader.row());
		addPanel.add(abox);
		abox.setBounds(100,220,200,40);
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int xloc = Integer.parseInt(locationField1.getText());
				int yloc = Integer.parseInt(locationField2.getText());
				if(xloc < 0 || yloc > 100){
					JOptionPane.showMessageDialog(null, "Enter numbers between 0 and 100", "Enter numbers between 0 and 100", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}
	
	private static void ambulancePanel(ActionEvent e){
		ambulanceFrame = new JFrame();
		ambulanceFrame.setSize(400, 300);
		JPanel ambulancePanel = new JPanel();
		ambulancePanel.setLayout(null);
		ambulanceFrame.add(ambulancePanel);
		JButton back = new JButton("Back");
		JButton add = new JButton("Add New");
		ambulancePanel.add(back);
		ambulancePanel.add(add);
		back.setBounds(30,200,80,50);
		add.setBounds(150,200,100,50);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent b){
				ambulanceFrame.setVisible(false);
				window.setVisible(true);
			}
		});
		ambulanceFrame.setVisible(true);
		window.setVisible(false);
		JLabel pLabel = new JLabel("Ambulance");
		ambulancePanel.add(pLabel);
		pLabel.setBounds(10,10,90,50);
		String[] colheads = {"ID","Location","Status","Ambulance"};
		Object[][] data_1 = AmbulanceReader.table();
		System.out.println(data_1);
		jt = new JTable(data_1,colheads);
		jt.setBounds(10,60,200,90);
		ambulancePanel.add(jt);
		JScrollPane scrollPane = new JScrollPane(jt);
		ambulancePanel.add(scrollPane);
		scrollPane.setBounds(10,60,350,70);
		jt.setPreferredScrollableViewportSize(new Dimension(500,50));
		jt.setFillsViewportHeight(true);
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ambulanceFrame.setVisible(false);
				newAmbulance(e);
			}
		});
        //Mouse Event
		jt.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				ambulanceMouse(e);
			}
		});
		
	}
	
	public static void newAmbulance(ActionEvent e){
		JFrame addFrame = new JFrame();
		addFrame.setSize(400,400);
		JPanel addPanel = new JPanel();
		addPanel.setLayout(null);
		addFrame.add(addPanel);
		addFrame.setVisible(true);
		JLabel label = new JLabel("Ambulance: ");
		addPanel.add(label);
		label.setBounds(150,10,90,50);
		JButton save = new JButton("save");
		JButton cancel = new JButton("cancel");
		save.setBounds(50,280,100,50);
		cancel.setBounds(170,280,100,50);
		addPanel.add(save);
		addPanel.add(cancel);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addPatient.save(e);
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addFrame.setVisible(false);
				ambulanceFrame.setVisible(true);
			}
		});
		//TextFields and Labels
		JTextField idField = new JTextField();
		addPanel.add(idField);
		idField.setBounds(100, 50, 200, 40);
		JLabel id = new JLabel("Ambulance ID:");
		id.setBounds(10,45,90,50);
		addPanel.add(id);
		JLabel location = new JLabel("Location:");
		addPanel.add(location);
		location.setBounds(10,100,90,50);
		JTextField locationField1 = new JTextField();
		addPanel.add(locationField1);
		locationField1.setBounds(100,110,100,40);
		JTextField locationField2 = new JTextField();
		locationField2.setBounds(220, 110, 100,40);
		addPanel.add(locationField2);
		JLabel status = new JLabel("Status:");
		addPanel.add(status);
		status.setBounds(10,160,50,50);
		JLabel ambulance = new JLabel("Patient:");
		addPanel.add(ambulance);
		ambulance.setBounds(10,220,80,50);
		//Creating ComboBox
		String[] items = {"At Station", "Responding","At Station","At Scene","Transporting","At Destination","Returnig"};
		JComboBox box = new JComboBox(items);
		addPanel.add(box);
		box.setBounds(100,160,200,40);
		//Ambulance ComboBox
		JComboBox abox = new JComboBox(CSVreader.data());
		addPanel.add(abox);
		abox.setBounds(100,220,200,40);
		
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int xloc = Integer.parseInt(locationField1.getText());
				int yloc = Integer.parseInt(locationField2.getText());
				if(xloc < 0 || yloc > 100){
					JOptionPane.showMessageDialog(null, "Enter numbers between 0 and 100", "Enter numbers between 0 and 100", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}
	
	public static void ambulanceMouse(MouseEvent e){
		
		ambulanceFrame.setVisible(false);
		JFrame addFrame = new JFrame();
		addFrame.setSize(400,400);
		JPanel addPanel = new JPanel();
		addPanel.setLayout(null);
		addFrame.add(addPanel);
		addFrame.setVisible(true);
		String[] new_data = CSVreader.data();
		JLabel label = new JLabel("Ambulances: ");
		addPanel.add(label);
		label.setBounds(150,10,50,50);
		JButton save = new JButton("save");
		JButton cancel = new JButton("cancel");
		save.setBounds(50,280,100,50);
		cancel.setBounds(170,280,100,50);
		addPanel.add(save);
		addPanel.add(cancel);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addPatient.save(e);
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addFrame.setVisible(false);
				ambulanceFrame.setVisible(true);
			}
		});
		//TextFields and Labels
		JTextField idField = new JTextField();
		addPanel.add(idField);
		idField.setBounds(100, 50, 200, 40);
		JLabel id = new JLabel("Ambulance ID:");
		id.setBounds(10,45,90,50);
		addPanel.add(id);
		JLabel location = new JLabel("Location:");
		addPanel.add(location);
		location.setBounds(10,100,90,50);
		JTextField locationField1 = new JTextField();
		addPanel.add(locationField1);
		locationField1.setBounds(100,110,100,40);
		JTextField locationField2 = new JTextField();
		locationField2.setBounds(220, 110, 100,40);
		addPanel.add(locationField2);
		JLabel status = new JLabel("Status:");
		addPanel.add(status);
		status.setBounds(10,160,50,50);
		JLabel ambulance = new JLabel("Patient:");
		addPanel.add(ambulance);
		ambulance.setBounds(10,220,80,50);
		//Creating ComboBox
		String[] items = {"At Station", "Responding","At Station","At Scene","Transporting","At Destination","Returnig"};
		JComboBox box = new JComboBox(items);
		addPanel.add(box);
		box.setBounds(100,160,200,40);
		//Ambulance ComboBox
		JComboBox abox = new JComboBox(CSVreader.data());
		addPanel.add(abox);
		abox.setBounds(100,220,200,40);
	}
	
	public static void patientMouse(MouseEvent e){
		JFrame addFrame = new JFrame();
		addFrame.setSize(400,400);
		JPanel addPanel = new JPanel();
		addPanel.setLayout(null);
		addFrame.add(addPanel);
		addFrame.setVisible(true);
		String[] new_data = CSVreader.data();
		JLabel label = new JLabel("Patient: ");
		addPanel.add(label);
		label.setBounds(150,10,80,50);
		JButton save = new JButton("save");
		JButton cancel = new JButton("cancel");
		save.setBounds(50,280,100,50);
		cancel.setBounds(170,280,100,50);
		addPanel.add(save);
		addPanel.add(cancel);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addPatient.save(e);
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addFrame.setVisible(false);
				patientFrame.setVisible(true);
			}
		});
		//TextFields and Labels
		JTextField idField = new JTextField();
		addPanel.add(idField);
		idField.setBounds(100, 50, 200, 40);
		JLabel id = new JLabel("ID:");
		id.setBounds(10,45,50,50);
		addPanel.add(id);
		JLabel location = new JLabel("Location:");
		addPanel.add(location);
		location.setBounds(10,100,90,50);
		JTextField locationField1 = new JTextField();
		addPanel.add(locationField1);
		locationField1.setBounds(100,110,100,40);
		JTextField locationField2 = new JTextField();
		locationField2.setBounds(220, 110, 100,40);
		addPanel.add(locationField2);
		JLabel status = new JLabel("Status:");
		addPanel.add(status);
		status.setBounds(10,160,50,50);
		JLabel ambulance = new JLabel("Ambulance:");
		addPanel.add(ambulance);
		ambulance.setBounds(10,220,80,50);
		//Creating ComboBox
		String[] items = {"Transporting","Assigned","Pending","Completed"};
		JComboBox box = new JComboBox(items);
		addPanel.add(box);
		box.setBounds(100,160,200,40);
		//Ambulance ComboBox
		JComboBox abox = new JComboBox(AmbulanceReader.row());
		addPanel.add(abox);
		abox.setBounds(100,220,200,40);

	}
	
	private static void exitButtonAction(ActionEvent e){
		System.exit(0);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		AmbulanceTrackerApp();
	}
}
