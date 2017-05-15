package utils;

import java.text.DecimalFormat;

public class Util {

	public static String validate(String baseCurrency,String termCurrency,String amount){
		StringBuffer error = new StringBuffer();
		if ( !baseCurrency.matches("[a-zA-Z]+") )
			error.append(" Base currency");
		if ( !termCurrency.matches("[a-zA-Z]+") )
			error.append(" Term currency");
		if ( !amount.matches("[0-9.]+")) 
			error.append(" Amount");
		
		if(error.length() > 0){
			return "Wrong input for "+error.toString()+". Please enter again.";
		}
		return null;
	}
	
	public static String format(Double amount, String currency){
		String format = currency.equalsIgnoreCase("JPY")?"#":"#.00";
		DecimalFormat df = new DecimalFormat(format); 
		return df.format(amount);
	}
	
	public static Double round(Double input){
		return input!=null ? Math.round(input * 100D) / 100D : null;
	}
}
