package handler;

import converter.DirectConverter;
import forex.CurrencyExchange;

/**
 * @author amithanadig
 * 
 * DirectConverterHandler is the first one to handle currency conversion using direct lookup from forex.properties.
 * The currency pairs and their exchange value is read through DirectConverter. 
 * If the currency pair is not found, then the responsibility of conversion is delegated to next ConverterHandler.
 *
 */
public class DirectConverterHandler implements ConverterHandler{
	ConverterHandler next;
	
	public void setNext(ConverterHandler handler){
		next = handler;
	}

	@Override
	public Double handleRequest(CurrencyExchange cur) {
		Double rate = DirectConverter.getValue(cur.display());
		if (rate != null){
			return (rate * cur.getInput() );
		}
		else{
			return next.handleRequest(cur);
		}
	}

}
