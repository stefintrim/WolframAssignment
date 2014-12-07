/*
 *
 * GCD Student Number: 2866113
 * F / S Name: Stefan Compton
 * Email: stefancompton23@gmail.com
 * 
 * custom immutable type for passing interpreted answers 
 * consisting of a response code and a string answer
 *
 */
public class Answer {
	private final String answer;
	private final int responseType;
	
	/**
	 * class constructor for an Answer object
	 * 
	 * @param ans the answer / result to encode
	 * @param resp an integer encoding the type of response
	 */
	public Answer(String ans, int resp) {
		answer = ans;
		responseType = resp;

	}
	
	/**
	 * getter for the answer string
	 * @return answer string containing the Wolfram Alpha answer text
	 */
	public String getAnswer() {
		return answer;
	}
	
	/**
	 * getter for the response code
	 * @return response code encoding the type of response from Wolfram Alpha
	 */
	public int getResponseType() {
		return responseType;
	}
}
