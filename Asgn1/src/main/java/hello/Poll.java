
package hello;




public class Poll {

	String id;
	String question;
	String started_at;
	String expired_at;
	String [] choice = new String[2];
	int [] result= new int[2];
	
	
	public Poll(){
		super();
	}
	
	public Poll(String id, String question ,String started_at, String expired_at,String[]choice ){
		
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getStarted_at() {
		return started_at;
	}
	public void setStarted_at(String started_at) {
		this.started_at = started_at;
	}
	public String getExpired_at() {
		return expired_at;
	}
	public void setExpired_at(String expired_at) {
		this.expired_at = expired_at;
	}
	public String[] getChoice() {
		return choice;
	}
	public void setChoice(String[] choice) {
		this.choice = choice;
	}
	public int[] getResult() {
		return result;
	}
	public void setResult(int[] result) {
		this.result = result;
	}
	
	
	

   
	
}
