package RoomOrganisation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class error {

	JFrame frame;

	private String message;
	
	public error() {
		initialize();

	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea txtrErrorPleaseOnly = new JTextArea();
		txtrErrorPleaseOnly.setFont(new Font("Monospaced", Font.PLAIN, 18));
		txtrErrorPleaseOnly.setText("Error, please only  enter integers");
		txtrErrorPleaseOnly.setEditable(false);
		txtrErrorPleaseOnly.setBounds(108, 51, 226, 117);
		frame.getContentPane().add(txtrErrorPleaseOnly);
		txtrErrorPleaseOnly.setLineWrap(true);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(168, 192, 105, 30);
		frame.getContentPane().add(btnNewButton);
		
	}
}
