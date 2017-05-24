package handler;

import converter.InvertConverter;
import forex.domain.CurrencyExchange;

/**
 * @author amithanadig
 *
 * InvertConverterHandler is the second one to handle currency conversion.
 * Currency pairs with exchange rates obtained by direct lookup from forex.properties are inverted to get the exchange rates.
 * 
 * This handler looks up at the list of such invertable currency pairs from invertibleCurrencies.properties.
 * If the currency pair is found in the list, 
 * then the control is passed to DirectConverterHandler to return the exchange rates. 
 * This rate is inverted to get the conversion rate. 
 * 
 * If the currency pair is not found in invertibleCurrencies.properties, then the responsibility of conversion is delegated to next ConverterHandler.
 *
 */
public class InvertConverterHandler implements ConverterHandler {
	ConverterHandler nextHandler = null;
	ConverterHandler firstHandler = null;
	
	@Override
	public void setNextConverterHandler(ConverterHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	@Override
	public Double handleRequest(CurrencyExchange currencyExchange) {
		if (InvertConverter.exists(currencyExchange.display())){
			Double conversionRate = firstHandler.handleRequest(new CurrencyExchange(currencyExchange.getTerm(),currencyExchange.getBase(),1.0));
			return ((1/conversionRate) * currencyExchange.getAmount() );
		}
		else{
			return nextHandler.handleRequest(currencyExchange);
		}
	}
	public void setFirstConverterHandler(ConverterHandler firstHandler) {
		this.firstHandler = firstHandler;
	}

}
