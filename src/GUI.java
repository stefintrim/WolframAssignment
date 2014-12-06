import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
		
		// input box event handler for pressing enter
		// calls the same code as clicking search
		// uses an anonymous inner class
		inputBox.addActionListener(new ActionListener() {
			

			// needs a try-catch because buttonhandler calls methods in assignment
			// that manipulate files and scanner and such
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonHandler();
				} catch (FileNotFoundException err) {
					System.out.println(err);
				} catch (IOException IO) {
					System.out.println(IO);
				}
			}
		});
		
		// code for handling focus events in the text box
		// adds and removes the text Enter your question here...
		inputBox.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				inputBox.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				
				
			}
			
		});
		
		// handler for clicking search
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonHandler();
				} catch (FileNotFoundException err) {
					System.out.println(err);
				} catch (IOException IO) {
					System.out.println(IO);
				}
			}
			
		});
		
		// search button properties
		searchButton.setText("Search");
		searchButton.setLocation(360, 700);
		searchButton.setPreferredSize(new Dimension(120, 40));
		searchButton.setVisible(true);
		
		// add input box and search to a panel
		searchPanel.add(inputBox);
		searchPanel.add(searchButton);
		
		// set up a panel for output
		resultPanel = new JPanel();
		
		// set up the output box - a text area because it can be multi-line
		outputField = new JTextArea(20, 20);
		outputField.setEditable(false);
		outputField.setPreferredSize(new Dimension(400, 600));
		outputField.setText("Welcome to the Wolfram Alpha search app GUI.\n\n Enter your question below and click Search.");
		outputField.setAlignmentY(0);
		outputField.setLineWrap(true);
		outputField.setWrapStyleWord(true);
		outputField.setFont(new Font("Arial", Font.PLAIN, 26));
		outputField.setRows(20);
		
		// add the output to a panel
		resultPanel.add(outputField);
		
		// position the panels
		frame.add(searchPanel, BorderLayout.SOUTH);
		frame.add(resultPanel, BorderLayout.NORTH);
		// set the frame visible and on top
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.setAlwaysOnTop(false);
	}
	
	
	
	
	
	public void buttonHandler() throws IOException {
		// handler for when the search button is clicked
		// or enter is pressed 
		
		// store the contents of the text box
		String userInput = inputBox.getText();
		
		// remove leading and trailing spaces 
		userInput = userInput.trim(); 
		
		// check if search was hit (or enter pressed) without a question
		if (!userInput.equals("") && !userInput.equals("Enter your question here...")) {
			
			// ensure question mark is present at the end of the question for consistency
			if (!userInput.endsWith("?")) userInput += "?";
			
			// check has the question been asked
			int counter = Assignment.questionHasBeenAsked(userInput);
			
			// if asked before
			if (counter > -1) {
				// get the timestamp from file
				long timeStamp = Assignment.getTime(counter);
				
				// get the difference between now and the timestamp
				// in String form and easily digested (yesteday, 3 days ago, 2 hours ago etc)
				String timeDiff = Assignment.getTimeDifference(timeStamp);
				
				// 
				
				
				
				// offer cached response (popup) - returns 0 for yes
				int choice = JOptionPane.showConfirmDialog(frame, "I have a cached answer from " + timeDiff + " Would you like to see it?", "Use Cached Response?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				
				if (choice != 0) {
					// if the user wants to search again
					// delete the cached answer in all 3 files 
					Assignment.deleteLine(counter);
					
					// set counter to -1 so that we search again and repopulate cache files
					counter = -1;
				}
				
			}
			
			if (counter > -1) {
				// get cached response
				outputField.setText(Assignment.getCachedQuestion(counter));
				
				
			} else {
			
			
				// query WA
				answer = Assignment.queryWA(inputBox.getText());
				
				// create a reply to be displayed to the user
				String response = "";
				response += Assignment.generateResponse(answer.getResponseType());
				response += answer.getAnswer(); 
				outputField.setText(response);
				
				// write the files
				Assignment.writeToFiles(userInput, answer);
				
				// clear the search from the text box
				inputBox.setText("");
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}
