package handler;

import converter.InvertConverter;
import forex.CurrencyExchange;

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
	ConverterHandler next = null;
	ConverterHandler first = null;
	
	@Override
	public void setNext(ConverterHandler next) {
		this.next = next;
	}

	@Override
	public Double handleRequest(CurrencyExchange cur) {
		if (InvertConverter.exists(cur.display())){
			Double firstConv = first.handleRequest(new CurrencyExchange(cur.getTarget(),cur.getSource(),1.0));
			return ((1/firstConv) * cur.getInput() );
		}
		else{
			return next.handleRequest(cur);
		}
	}
	public void setFirst(ConverterHandler first) {
		this.first = first;
	}

}
