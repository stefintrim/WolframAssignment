/**
 * custom immutable type for passing interpreted answers 
 * consisting of a response code and a string
 * 
 * param String answer
 * param int responseType
 * 
 * @author Stefan
 *
 */
public class Answer {
	private final String answer;
	private final int responseType;
	
	public Answer(String ans, int resp) {
		answer = ans;
		responseType = resp;

	}
	
	public String getAnswer() {
		return answer;
	}
	
	public int getResponseType() {
		return responseType;
	}
}
