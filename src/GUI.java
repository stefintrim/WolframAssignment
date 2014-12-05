import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GUI {

	JFrame frame;
	JButton searchButton;
	JTextField inputBox;
	Answer answer;
	JPanel searchPanel, resultPanel;
	JTextArea outputField;
	
	public GUI() {
		// set up frame
		JFrame frame = new JFrame();
		// set the close behaviour
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		// set the title
		frame.setTitle("Wolfram Alpha Search GUI"); 
		frame.setSize(480,800);  
		// center the frame
		frame.setLocationRelativeTo(null);
		
		
		// set up a panel for search button and input
		searchPanel = new JPanel(new GridLayout(1, 4));
		searchButton = new JButton();
		inputBox = new JTextField();
		
		inputBox.setPreferredSize(new Dimension(360, 40));
		inputBox.setText("Enter your question here...");
		
		inputBox.addActionListener(new ActionListener() {

			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				buttonHandler();
				
			}
			
		});
		
		inputBox.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				inputBox.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				inputBox.setText("Enter your question here...");
				
			}
			
		});
		
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonHandler();
			}
			
		});
		
		searchButton.setText("Search");
		searchButton.setLocation(360, 700);
		searchButton.setPreferredSize(new Dimension(120, 40));
		searchButton.setVisible(true);
		
		searchPanel.add(inputBox);
		searchPanel.add(searchButton);
		
		// set up a panel for output
		resultPanel = new JPanel();
		outputField = new JTextArea(20, 20);
		
		outputField.setEditable(false);
		
		outputField.setPreferredSize(new Dimension(400, 600));
		outputField.setText("Answer here...");
		outputField.setAlignmentY(0);
		outputField.setLineWrap(true);
		outputField.setWrapStyleWord(true);
		outputField.setFont(new Font("Arial", Font.PLAIN, 26));
		outputField.setRows(20);
		
		
		resultPanel.add(outputField);
		
		frame.add(searchPanel, BorderLayout.SOUTH);
		frame.add(resultPanel, BorderLayout.NORTH);
		// set the frame visible
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
	}
	
	
	
	
	
	public void buttonHandler() {
		if (!inputBox.getText().equals(""))
		answer = Assignment.queryWA(inputBox.getText());
		String response = "";
		response += answer.getAnswer(); 
		response = String.format("%40s", response);
		
		outputField.setText(response);
		inputBox.setText("");
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}
