package forex.calculator;

import java.io.BufferedInputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

import converter.CrossConverter;
import converter.DirectConverter;
import converter.InvertConverter;
import forex.domain.CurrencyExchange;
import forex.validator.InvalidInputException;
import forex.validator.Validator;
import handler.ConverterHandler;
import handler.CrossConverterHandler;
import handler.DirectConverterHandler;
import handler.InvertConverterHandler;
import handler.UnknownCurrencyException;
import utils.ConfigReader;
import utils.FormatterUtil;

public class Main {
	
	public static Properties GENERAL_PROPERTIES = null;
	
	public static void main(String input[]){
		Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        String line = null;
		while ( !((line = scanner.nextLine()).equals("q")) ) {
			process(line);
        }
		scanner.close();
	}
	
	public static void process(String input){
		StringTokenizer parser = new StringTokenizer(input, " ");
		int numberOfParams = parser.countTokens();
	
		if (numberOfParams != 4){
			System.out.println("Please provide all the arguments for input line ");
			System.out.println("Usage: <ccy1> <amount> in <ccy2>");
		} else {
			String baseCurrency = parser.nextToken();
			String amount = parser.nextToken();
			parser.nextToken();
			String termCurrency = parser.nextToken();
			try {
				System.out.println(validateAndCalculate(baseCurrency,termCurrency,amount));
			}catch(InvalidInputException | UnknownCurrencyException exception){
				System.out.println(exception.getMessage());
			}
		}
	}
	
	public static String validateAndCalculate(final String baseCurrency, final String termCurrency,final String amount){
		boolean inputValidated=false;
		inputValidated = Validator.validate(baseCurrency,termCurrency,amount);
		
		String result=null;
		if (inputValidated) {
			if (baseCurrency.equalsIgnoreCase(termCurrency)){
				result = formResult(baseCurrency,termCurrency,amount, new Double(amount));
			} else { 
				ConverterHandler directHandler = initialize();
				CurrencyExchange exchangeInput = new CurrencyExchange(baseCurrency, termCurrency, new Double(amount));
				
				Double convertedAmount = directHandler.handleRequest(exchangeInput);
				if (convertedAmount == null){
					throw new UnknownCurrencyException("Unable to find rate for "+baseCurrency+"/"+termCurrency);
				} else {
					result = formResult(baseCurrency,termCurrency,amount, convertedAmount);
				}
			}
		}
		return result;
	}
	
	/**
	 * This method sets the context for currency exchange.
	 * Properties files containing currency information are loaded.
	 * A chain of ConverterHandlers are formed. 
	 *  
	 * @return
	 */
	public static ConverterHandler initialize() {		
		DirectConverter.initialize(ConfigReader.readProperties("config/forex.properties"));
		InvertConverter.initialize(ConfigReader.readCurrencies("config/invertibleCurrencies.properties"));
		CrossConverter.initialize(ConfigReader.readCurrencies("config/crossCurrencies.properties"));
		GENERAL_PROPERTIES = ConfigReader.readProperties("config/general.properties");
		ConverterHandler directHandler = new DirectConverterHandler();
		ConverterHandler invertHandler = new InvertConverterHandler();
		ConverterHandler crossHandler = new CrossConverterHandler();
		directHandler.setNextConverterHandler(invertHandler);
		invertHandler.setFirstConverterHandler(directHandler);
		invertHandler.setNextConverterHandler(crossHandler);
		crossHandler.setFirstConverterHandler(directHandler);
		
		return directHandler;
	}
	
	private static String formResult(String base,String term,String amount, Double exchangedAmount){
		return base+" "+amount+" = "+term+" "+FormatterUtil.format(exchangedAmount,term);

	}
}