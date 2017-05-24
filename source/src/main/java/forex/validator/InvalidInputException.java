package forex.validator;

public class InvalidInputException extends RuntimeException {

	String message=null;
	public InvalidInputException(){}
	
	public InvalidInputException(String message){
		super(message);
		this.message=message;
	}
	
	public String getMessage(){
		return message;
	}
}
