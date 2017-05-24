package forex.validator;

public class Validator {
	public static boolean validate(String baseCurrency,String termCurrency,String amount) {
		StringBuffer error = new StringBuffer();
		if ( !baseCurrency.matches("[a-zA-Z]+") )
			error.append(" Base currency");
		if ( !termCurrency.matches("[a-zA-Z]+") )
			error.append(" Term currency");
		if ( !amount.matches("[0-9.]+")) 
			error.append(" Amount");
		
		if(error.length() > 0){
			//return "Wrong input for "+error.toString()+". Please enter again.";
			throw new InvalidInputException("Wrong input for "+error.toString()+". Please enter again.");
		}
		return true;
	}
}
