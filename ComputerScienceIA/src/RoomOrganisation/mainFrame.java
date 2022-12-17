package RoomOrganisation;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.ArrayList;

public class mainFrame {

	protected JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	
	private int[] bSpace = {0,0,0};
	private int[] gSpace = {0,0,0};
	
	protected boolean hasFile;
	protected boolean hasBoy;
	protected boolean hasGirl;

	
	//Start methods
	public int[] getBSpace(){
		return bSpace;
	}
	public int[] getGSpace(){
		return gSpace;
	}
	public ArrayList<String[]> getData(){
		return data;
	}
	
	
	private static boolean isInt(String s) {
		boolean intOrNot = true;
		try{
			Integer.parseInt(s);
		}
		catch (Exception e) {
			intOrNot = false;
		}
		return intOrNot;
	}
	
	private ArrayList<String[]> data = new ArrayList<String[]>();
	//Method to read file into ArrayList<String[]>
	private void populateData(File f){
		try {
			Scanner scan = new Scanner(f);
			scan.nextLine();
			while (scan.hasNext()) {
				String[] line = scan.nextLine().split(",");
				data.add(line);
			}
			scan.close();
		}
		catch(Exception e) {
			System.out.println("There has been an error.");
		}
	}
	

	public mainFrame() {
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 814, 502);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Select File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hasFile = false;
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(frame);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					populateData(file);
					hasFile = true;
				}
			}
		});
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 28));
		btnNewButton.setBounds(288, 76, 201, 59);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Please upload a file in csv format:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblNewLabel.setBounds(27, 28, 622, 27);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Types of rooms:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(27, 224, 129, 52);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("2        3        4");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel_2.setBounds(216, 230, 201, 32);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("Amount of rooms:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(27, 286, 159, 52);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		textField = new JTextField();
		textField.setBounds(190, 300, 70, 32);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(276, 300, 70, 32);
		frame.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(364, 300, 70, 32);
		frame.getContentPane().add(textField_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("2        3        4");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel_2_1.setBounds(542, 230, 201, 32);
		frame.getContentPane().add(lblNewLabel_2_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(518, 300, 70, 32);
		frame.getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(605, 300, 70, 32);
		frame.getContentPane().add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(693, 300, 70, 32);
		frame.getContentPane().add(textField_5);
		
		JLabel lblNewLabel_3 = new JLabel("Boys");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel_3.setBounds(283, 173, 63, 37);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Girls");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel_3_1.setBounds(605, 173, 63, 37);
		frame.getContentPane().add(lblNewLabel_3_1);
		
		
		//Takes in all input into array, validata that they are all integers, if not, re enter
		JButton btnSubmitAmounts = new JButton("Submit amounts");
		btnSubmitAmounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hasBoy = false;
				hasGirl = false;
				String[] testDataIn = {textField.getText(), textField_1.getText(), textField_2.getText(), textField_3.getText(), textField_4.getText(), textField_5.getText()};
				boolean allInt = true;
				for (int i = 0; i<testDataIn.length; i++) {
					if(!isInt(testDataIn[i])) {
						allInt = false;
					}
				}
				if(allInt) {
					btnSubmitAmounts.setEnabled(false);
					fillSpace(bSpace, testDataIn[0], testDataIn[1],testDataIn[2]);
					hasBoy = true;
					fillSpace(gSpace, testDataIn[3], testDataIn[4],testDataIn[5]);
					hasGirl = true;
				}
				else {
					error message = new error();
					message.frame.setVisible(true);
				}
				
			}
		});
		btnSubmitAmounts.setFont(new Font("Calibri", Font.PLAIN, 28));
		btnSubmitAmounts.setBounds(276, 369, 245, 59);
		frame.getContentPane().add(btnSubmitAmounts);
	}
	
	//Method to fill array
	private int[] fillSpace (int[] a, String s1, String s2, String s3) {
		String[] string = {s1,s2,s3};
		for (int i = 0; i<a.length; i++) {
			a[i] = Integer.parseInt(string[i]);
		}
		return a;
	}
}
