import converter.CrossConverter;
import converter.DirectConverter;
import converter.InvertConverter;
import forex.CurrencyExchange;
import handler.ConverterHandler;
import handler.CrossConverterHandler;
import handler.DirectConverterHandler;
import handler.InvertConverterHandler;
import utils.ConfigReader;
import utils.Util;

public class Main {

	public static void main(String input[]){
		process(input);		
	}
	
	public static void process(String[] input){
		if (input.length != 4){
			System.out.println("Please provide all the arguments");
			System.out.println("Usage: <ccy1> <amount> in <ccy2>");
			System.exit(0);
		} else {
			String baseCurrency = input[0];
			String amount = input[1];
			String termCurrency = input[3];
			
			System.out.println( validateAndCalculate(baseCurrency,termCurrency,amount) );
		}
	}
	
	public static String validateAndCalculate(final String baseCurrency, final String termCurrency,final String amount){
		String message = Util.validate(baseCurrency,termCurrency,amount);
		if (message == null) {
			if (baseCurrency.equalsIgnoreCase(termCurrency)){
				message = baseCurrency+" "+amount+" = "+termCurrency+" "+Util.format(new Double(amount),termCurrency);
			} else { 
				ConverterHandler directHandler = initialize();
				CurrencyExchange exchangeInput = new CurrencyExchange(baseCurrency, termCurrency, new Double(amount));
				
				Double result = directHandler.handleRequest(exchangeInput);
				if (result == null){
					message = "Unable to find rate for "+baseCurrency+"/"+termCurrency;
				} else {
					message = baseCurrency+" "+amount+" = "+termCurrency+" "+Util.format(result,termCurrency);
				}
			}
		}
		return message;
	}
	
	/**
	 * This method sets the context for currency exchange.
	 * Properties files containing currency information are loaded.
	 * A chain of ConverterHandlers are formed. 
	 *  
	 * @return
	 */
	public static ConverterHandler initialize() {		
		DirectConverter.initialize(ConfigReader.readForex("config/forex.properties"));
		InvertConverter.initialize(ConfigReader.readCurrencies("config/invertibleCurrencies.properties"));
		CrossConverter.initialize(ConfigReader.readCurrencies("config/crossCurrencies.properties"));
		ConverterHandler handler1 = new DirectConverterHandler();
		ConverterHandler handler2 = new InvertConverterHandler();
		ConverterHandler handler3 = new CrossConverterHandler();
		handler1.setNext(handler2);
		handler2.setFirst(handler1);
		handler2.setNext(handler3);
		handler3.setFirst(handler1);
		
		return handler1;
	}
}