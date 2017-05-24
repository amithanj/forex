package handler;

import forex.domain.CurrencyExchange;

/**
 * @author amithanadig
 * An interface to provide an outline on the currency conversion process.
 * Implementors of this interface are responsible for converting the currency.
 * DirectConverterHandler is the first one to handle currency conversion using direct lookup from forex.properties.
 * If it is not found, 
 * then next handler InvertConverterHandler tries to do the conversion. 
 * This is done by finding the currency pair in the invertibleCurrencies.properties.
 * If it is found,
 * then the exchange process is delegated to first ConverterHandler i.e DirectConverterHandler.
 * If it is not found,
 * then the conversion is delegated to next ConverterHandler i.e CrossConverterHandler.
 * This ConverterHandler moves the control to first Handler to get the currency conversion information.
 *
 */
public interface ConverterHandler{
	public void setNextConverterHandler(ConverterHandler nextHandler);
	public Double handleRequest(CurrencyExchange currencyExchange) throws UnknownCurrencyException;
	public default void setFirstConverterHandler(ConverterHandler handler){}
}
