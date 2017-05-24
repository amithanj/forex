package utils;

import java.text.DecimalFormat;

import forex.calculator.Main;

/**
 * @author amithanadig
 * This utility class is responsible for formatting monetary values.
 * 
 */
public class FormatterUtil {
	
	public static String format(Double amount, String currency){
		String format = (String) Main.GENERAL_PROPERTIES.get(currency+"_OUTPUT_FORMATTER");
		if(format == null){
			format = "#.00";
		}
		DecimalFormat df = new DecimalFormat(format); 
		return df.format(amount);
	}
	
	public static Double round(Double input){
		return input!=null ? Math.round(input * 100D) / 100D : null;
	}
}
