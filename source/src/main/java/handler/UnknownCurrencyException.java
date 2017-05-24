package handler;

public class UnknownCurrencyException extends RuntimeException {
	String message=null;
	public UnknownCurrencyException(){}
	
	public UnknownCurrencyException(String message){
		super(message);
		this.message=message;
	}
	
	public String getMessage(){
		return message;
	}
}
