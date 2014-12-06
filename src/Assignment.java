import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * GCD Student Number: 2866113
 * F / S Name: Stefan Compton
 * Email: stefancompton23@gmail.com
 * 
 * Assignment class - the entry point for my Wolfram Alpha query program
 */
public class Assignment {
	// create an int for output line length
	static int lineLength = 60;
	
	// create some static strings for text output
	// create strings for the question and answer file names
	static String questionFileString = "questions.list";
	static String answerFileString = "answers.list";
	static String timeFileString = "times.list";
	
	// files to read and write
	static File questionFile = new File(questionFileString);
	static File answerFile = new File(answerFileString);
	static File timeFile = new File(timeFileString);
	
	// create some strings for displaying messages to user
	static String welcomePrompt = "Welcome to the Wolfram Alpha search program.\n\n"
			+ "Type any question and I will send it to the Wolfram Alpha answer engine.\n"
			+ "If I have already been asked the question, I will get the answer from my cache.\n\n"
			+ "Don't forget you can type quit or q to exit.\n"
			+ "Or *GUI to try the experimental GUI\n";
	
	static String userQuestionPrompt = "Enter your question or quit:";
	static String repeatQuestion = "From my cache:";
	static String goodbyeMessage = "Goodbye.";
	
	/**
	 * Helper method to decode XML output from Wolfram Alpha
	 * 
	 *  @param xml - a string of XML 
	 *  @return an arraylist of string arrays - the arraylist is of varable size and the arrays are of size
	 *  		2, containing the tag and associated data
	 *  
	 */
	public static ArrayList<String[]> decodeXML(String xml) {
		
		
	
		// create our output arraylist
		ArrayList<String[]> result = new ArrayList<String[]>();
		
		// remove newline chars
		xml = xml.replaceAll("\r\n|\r|\n", " ");
		
		while (xml.contains(">")) {
			// find our next tag + data to process
			// it starts at the index of the first opening angle bracket
			int start = xml.indexOf("<");
			
			// and ends on the character before the next opening angle bracket
			// add start + 1 because that's where we're starting from  
			int end = xml.substring(start + 1).indexOf("<") + start + 1;
			
			// if the second end angle bracket is not found we're done - we don't care about the last tag
			if (end <= start) break;
			
			
			// create a string with the next tag
			String data = xml.substring(start, end);
			
			
			// if it's an opening tag, get the tag name and data in an array and add to our result 
			 if (data.charAt(1) != '/') {
				// get the index of the closing angle bracket
				int endBracket = data.indexOf(">");
				
				// the tag name is from 1 to end bracket index
				String tagName = data.substring(1, endBracket);
				
				// the tag data is from the end bracket + 1 to the end of the string
				String tagData = data.substring(endBracket + 1);
				
				// add the data to the arraylist result
				result.add(new String[]{tagName, tagData});
			}
				
			// start the next iteration at the next tag
			xml = xml.substring(end);
			
		}
		
		// return our arraylist result
		return result;
		
	}
	
	/**
	 * helper method that checks whether a file exists and creates it if it doesn't
	 * 
	 * @param file the file object to be created
	 * 
	 */
	public static void createFile(File file) throws IOException {
		// if the file does not exist (using .isFile() because .exists() will return true for a directory) 
		if(!file.isFile()) {
			// create the file
			file.createNewFile();
		}
	}

	/**
	 * helper method to query Wolfram Alpha 
	 * @param question the question to pose to Wolfram Alpha
	 * @return a custom type "Answer" which holds the text answer
	 * and the type of the answer
	 * 
	 */
	public static Answer queryWA(String question) {
		// Create an instance of the Wolfram Alpha API - including your AppID key
		WolframAlpha WOLFRAM_ALPHA = new WolframAlpha("6RAGVP-P8JH6E3LYH");
				
		// create a string for returning the answer
		String answer = "";
		
		// create an integer to hold the response type
		// if we have "results" in there, use that (add 1000)
		// if we have "response" in there, that will have to do (add 100)
		// others to follow
		// if we have "noresults" call that out explicitly		
		int responseType = 0;
		
		// if the question has not been asked before, send a query and store the response
		String response = WOLFRAM_ALPHA.query(question);
		
		
		// process the response and get an araylist 
		ArrayList<String[]> arrayList = decodeXML(response);
		
		
		// check for tags - assuming only one type of response will be returned
		// <title>Result</title>
		// <title>Response</title>
		// <noresults>
		
		for (String[] array: arrayList) {
			// if "Result" in title tags is found
			if (array[0].equals("title") && array[1].equals("Result")) {
				// set the response type to 1000 so we can choose verbiage
				responseType = 1000;
				// set the answer string to the next arraylist array, position [1] (not the tag which is [0] in the array) 
				answer = arrayList.get(arrayList.indexOf(array) + 1)[1];
				break;
			// if "Response" in title tags is found 
			} else if (array[0].equals("title") && array[1].equals("Response")) {
				// set the response type to 100
				responseType = 100;
				// set the answer string to the next arraylist item, index 1
				answer = arrayList.get(arrayList.indexOf(array) + 1)[1];
				break;
			// if "noresults" tag is returned
			} else if (array[0].equals("noresults")) {
				responseType = -1;
				break;
			}
		}
		
		return new Answer(answer, responseType);
	}
	
	/**
	 * helper method to split a string into lines of a given length without breaking any words up
	 * 
	 * @param data the string to be split
	 * @param lineLength the length of the line
	 * @return the string broken with new line chars 
	 */
	public static String addLineBreaks(String data, int lineLength) {
		// create a result string 
		String result = "";
				
		// make sure the line length is sensible - if it is shorter than a word in "data" then 
		// we might get stuck in a loop
		assert(lineLength >= 20);
		
		// create an int for the current line length
		int currLine = 0;
		
		// loop through all words in the data string, split on a space
		for (String s: data.split(" ")) {
			// get the size of the current word - add 1 for a space
			int currWord = s.length() + 1;
			
			// if the word is going to take the current line overlength
			if (currWord + currLine > lineLength) {
				// start a new line with the word and setthe line length to the length of the word
				result += "\n" + s + " ";
				currLine = currWord;
			// if the word is not going to take the line overlength
			} else {
				// just add the current word and add the length to the current line length 
				result += s + " ";
				currLine += currWord;
			}
		}
		
		
		
		// return the result
		return result;
	}

	/**
	 * helper method to generate the prefix string the program will use to present a result
	 * based on the return from Wolfram Alpha 
	 * @param respCode an int representing various WA response types - 1000 = result, 100 = response etc
	 * @return string to be appended to an answer
	 */
	public static String generateResponse(int respCode) {
		// create a string for our reply
		String response;
		
		// switch on various values
		switch (respCode) {
		case 1000:
			response = "The answer is: ";
			break;
			
		case 100:
			response = "Wolfram Alpha responded with: ";
			break;
							
		case -1:
			response = "No response from Wolfram Alpha.";
			break;
		
		default:
		
			response = "I'm sorry, I couldn't interpret the answer.";
			break;
			
		}
		
		return response;
		
	}
	
	/**
	 * helper method to check if a question has been asked before
	 * @param question the question in question
	 * @return the line number for a question successfully found, or -1 if not found
	 * @throws FileNotFoundException
	 */
	public static int questionHasBeenAsked(String question) throws FileNotFoundException {
		// read in the file questions.list 
		Scanner questionScanner = new Scanner(questionFile);
		
		// create a boolean hasBeenAsked an and an integer line counter
		int counter = 0;
		boolean hasBeenAsked = false;
		
		// iterate through the file, line by line
		while (questionScanner.hasNextLine()) {
			// get the next line of the file
			String line = questionScanner.nextLine();

			// if the line is the same as the question asked by the user (ignoring case)
			if (line.equalsIgnoreCase(question)) {
				// mark hasBeenAsked as true and exit the while loop
				hasBeenAsked = true;
				break;
			} else {
				// otherwise increment counter - hence the first line is interpreted as line 0
				counter++;
			}
		}
		// close the scanner
		questionScanner.close();
		
		if (hasBeenAsked) return counter;
		else return -1;
		
	}
	
	/**
	 * static helper method that takes a line number in the result.list and returns the result at that line
	 * @param line the line number
	 * @return the result at that line composed of a string generated from the response type and the answer
	 * @throws FileNotFoundException
	 */
	public static String getCachedQuestion(int line) throws FileNotFoundException {
		// create a result string to return
		String result = "";
		
		// create a scanner to read the answer file
		Scanner answerScanner = new Scanner(answerFile);
		
		// consume the lines preceding the line we want
		for (int i = 0; i < line; i++) answerScanner.nextLine();
		
		// get the answer
		String answer = answerScanner.nextLine();
		
		// append response to result - making it clear that this question was asked before
		// the response type is encoded as 4 digits at the start of the line
		result += repeatQuestion + "\n" + generateResponse(Integer.parseInt(answer.substring(0, 4))) + "\n";
		
		// append the answer with linebreaks
		result += addLineBreaks(answer.substring(4), lineLength);
		
		// close the scanner
		answerScanner.close();
		
		// return the result
		return result;
		
	}

	/**
	 * static helper method to calculate how long ago a timstamp is from now
	 * @param timeStamp the timestamp to check
	 * @return a string with useful information like "yesterday" or "3 hours ago"
	 */
	public static String getTimeDifference(long timeStamp) {
		// create a result string
		String result;
		
		// get the tiome now and the diff between now and the timestamp = the age of the entry
		long now = System.currentTimeMillis();
		long age = now - timeStamp;
		
		
		// some calcs from milliseconds to ..
		long second = 1000;
		long minute = 60 * second;
		long hour = 60 * minute;
		long day = 24 * hour;
		long year = 365 * day;
		long today = (now % day); // number of milliseconds so far today
		
		if (age > year) {
			result = "over a year ago.";
			// this checks to see if the result is from before yesterday
			// now % day is the number of millis so far today
		} else if (age > day + today) {
			// add a day because this translates 1 day plus the time today ago as one day ago and it should be 2
			result = ((age + day - today)) / day + " days ago.";
		} else if (age > today) {
			result = "yesterday.";
		} else if (age > hour) {
			if (age / hour == 1) result = "1 hour ago.";
			else result = age / hour + " hours ago.";
		} else if (age > minute) {
			if (age / minute == 1) result = "1 minute ago.";
			else result = age / minute + " minutes ago.";
		} else {
			if (age / second == 1) result = "1 second ago.";
			else result = age / second + " seconds ago.";
		}
		
		return result;
	}
	
	/**
	 * helper method to delete a line from all 3 files - question, answer and timestamp files
	 * @param line the number of the line to delete - starting at 0 for first line
	 * @throws IOException
	 */
	public static void deleteLine(int line) throws IOException {
		// create 3 temp files
		
		File tempQFile = new File("tempQFile");
		File tempAFile = new File("tempAFile");
		File tempTFile = new File("tempTFile");
		
		// create printwriters
		
		PrintWriter TQOutput = new PrintWriter(new BufferedWriter(new FileWriter("tempQFile", true)));
		PrintWriter TAOutput = new PrintWriter(new BufferedWriter(new FileWriter("tempAFile", true)));
		PrintWriter TTOutput = new PrintWriter(new BufferedWriter(new FileWriter("tempTFile", true))); 
		
		// create 3 scanners
		
		Scanner Qin = new Scanner(questionFile);
		Scanner Ain = new Scanner(answerFile);
		Scanner Tin = new Scanner(timeFile);

		int i = 0; // counter for our while loop - when we get to the right line we won't copy it to the temp file
		
		// loop through each file and copy all lines to the relevant temp file except the line we want to delete
		while (Qin.hasNextLine()) {
			String Qtemp = Qin.nextLine();
			String Atemp = Ain.nextLine();
			String Ttemp = Tin.nextLine();
			
			if (i != line) {
				TQOutput.println(Qtemp);
				TAOutput.println(Atemp);
				TTOutput.println(Ttemp);
				
			}
			i++;
		}
		
		// close scanners
		Qin.close();
		Ain.close();
		Tin.close();
		
		// delete the original files
		questionFile.delete();
		answerFile.delete();
		timeFile.delete();
		
		TQOutput.close();
		TAOutput.close();
		TTOutput.close();
		
		
		// rename the temp files to the proper file names
		tempQFile.renameTo(questionFile);
		tempAFile.renameTo(answerFile);
		tempTFile.renameTo(timeFile);
		
		
		

		
	}

	/**
	 * static helper to get a timestamp at a given line number
	 * @param line the line number starting at 0
	 * @return a timestamp of type long because it won't fit into an int
	 * @throws IOException
	 */
	public static long getTime(int line) throws IOException {
		// create a scanner
		Scanner timeScanner = new Scanner(timeFile);
		
		// consume the lines preceding the line we want
		for (int i = 0; i < line; i++) timeScanner.nextLine();
		
		// get the timestamp we'll parse the int in two parts to get a long
		// the timestamp is going to be 13 chars - it will increase to 14 chars
		// in about 280 years 
		String timeString = timeScanner.nextLine();
		
		// close the scanner
		timeScanner.close();
		
		// return the time(long)
		return Long.parseLong(timeString);
	}

	/**
	 * Static helper method to write an entry to the files
	 * @param input the question posed to WA
	 * @param answer an answer object encoding the reply and the type of that reply
	 * @throws IOException
	 */
	public static void writeToFiles(String input, Answer answer) throws IOException {
		// create printwriter objects to write to the output files
		PrintWriter questionOutput = new PrintWriter(new BufferedWriter(new FileWriter(questionFileString, true)));
		PrintWriter answerOutput = new PrintWriter(new BufferedWriter(new FileWriter(answerFileString, true)));
		PrintWriter timeOutput = new PrintWriter(new BufferedWriter(new FileWriter(timeFileString, true))); 
		
		// write the question to the question file
		// write the answer to the answer file with a response type prefix
		// write a timestamp to the time file
		questionOutput.println(input);
		answerOutput.println(String.format("%04d", answer.getResponseType()) + answer.getAnswer());
		timeOutput.println(System.currentTimeMillis());
		
		// close the PrintWriter objects
		questionOutput.close();
		answerOutput.close();
		timeOutput.close();
	}
	
	/**
	 * The text based version of my Wolfram Alpha query program
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// check questions.list and answers.list exist and make them if they don't
		
		// create a question and an answer files
		// also a file containing timestamps in order to check for stale results
		createFile(questionFile);
		createFile(answerFile);
		createFile(timeFile);
		
		
		// create a scanner object for user input
		Scanner typedInput = new Scanner(System.in);
		
		
		// get user input
		System.out.println(welcomePrompt);
		System.out.println(userQuestionPrompt);
		String userInput;
		do {
			userInput = typedInput.nextLine();
		} while (userInput.trim().equals(""));
		
		
		// create a String for the answer
		String answer = "";
		
		
		// loop until the user types quit or q
		while (!userInput.equalsIgnoreCase("quit") && !userInput.equalsIgnoreCase("q") && !userInput.equalsIgnoreCase("*GUI")) {
			
			
			// remove leading and trailing spaces 
			userInput = userInput.trim(); 
			
			// ensure question mark is present at the end of the question for consistency
			if (!userInput.endsWith("?")) userInput += "?";
			
			// check has the question been asked
			int counter = questionHasBeenAsked(userInput);
			
			// if the counter is a line number = question has been asked before
			if (counter > -1) {
				// get the timeStamp the question was asked before
				long timeStamp = getTime(counter);
				
				// get the difference between now and the timestamp
				// in String form and easily digested (yesteday, 3 days ago, 2 hours ago etc)
				String timeDiff = getTimeDifference(timeStamp);
				
				
				System.out.println("That has been searched before. I have a result from " + timeDiff);
				System.out.println("Would you like me to search again, or give you the cached result?");
				System.out.println("Type 'a' to search again");
				String cacheReply = typedInput.nextLine();



				
				// present the user with the option of reasking the question
				if (cacheReply.equalsIgnoreCase("A") || cacheReply.equalsIgnoreCase("'a'")) {
					// delete the lines in all 3 files
					deleteLine(counter);
					// set counter back to -1 so the question is asked again
					counter = -1;
				} 
				
			}
			
			if (counter > -1) {
				
				System.out.println(getCachedQuestion(counter));
				
				
			// otherwise query WA	
			} else {
				
				// get a custom Answer object with the query results
				Answer answerObject = queryWA(userInput);
				
				// get the answer string using the getter
				answer = answerObject.getAnswer();
				
				if (answerObject.getResponseType() > 0) {
					
					// create a string for the response based on WA response type
					String response = generateResponse(answerObject.getResponseType());
					
					
					// reply with the answer
					System.out.println(response);
					System.out.println(addLineBreaks(answer, lineLength));
					
					
					// write question, answer and timestamp to file
					writeToFiles(userInput, answerObject);
					
				}
			}
			
			// prompt the user again and get input			
			System.out.println(userQuestionPrompt);
			
			do {
				userInput = typedInput.nextLine();
			} while (userInput.trim().equals(""));
			
		}
		
		// close off open user input scanner
		typedInput.close();

		if (userInput.equalsIgnoreCase("*GUI")) {
			// start GUI if user typed *GUI
			System.out.println("Opening GUI in another window...");
			new GUI();
		} else {
			// print out goodbye message to the console
			System.out.println(goodbyeMessage);
		}
	}
}
