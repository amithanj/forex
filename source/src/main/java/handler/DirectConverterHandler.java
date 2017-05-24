package handler;

import converter.DirectConverter;
import forex.domain.CurrencyExchange;

/**
 * @author amithanadig
 * 
 * DirectConverterHandler is the first one to handle currency conversion using direct lookup from forex.properties.
 * The currency pairs and their exchange value is read through DirectConverter. 
 * If the currency pair is not found, then the responsibility of conversion is delegated to next ConverterHandler.
 *
 */
public class DirectConverterHandler implements ConverterHandler{
	ConverterHandler nextHandler;
	
	public void setNextConverterHandler(ConverterHandler handler){
		nextHandler = handler;
	}

	@Override
	public Double handleRequest(CurrencyExchange currencyExchanger) {
		Double rate = DirectConverter.getValue(currencyExchanger.display());
		if (rate != null){
			return (rate * currencyExchanger.getAmount() );
		}
		else{
			return nextHandler.handleRequest(currencyExchanger);
		}
	}

}
