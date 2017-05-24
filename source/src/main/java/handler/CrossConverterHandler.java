package handler;

import converter.CrossConverter;
import forex.calculator.Main;
import forex.domain.CurrencyExchange;

/**
 * @author amithanadig
 * This is the last ConverterHandler. 
 * This uses currency pair list from crossCurrencies.properties. These pairs use "EUR" as the cross currency.
 * If the pairs which are not listed in the list use "USD".
 * 
 *  Currency pair is formed by pairing base currency and cross currency. 
 *  For example - 
 *  For CZKDKK, EUR is cross currency.
 *  CZKEUR is formed and exchange rate is obtained. Then,
 *  EURDKK pair is formed and exchange rate is formed. 
 *  To get the exchange rate, the control is transferred to
 *  first ConverterHandler i.e DirectConverterHandler. It loops through ConverterHandlers from there till the values is obtained.
 *  
 *  
 */
public class CrossConverterHandler implements ConverterHandler {
	ConverterHandler firstHandler = null;
	String primaryCrossCurrency = Main.GENERAL_PROPERTIES.getProperty("PRIMARY_CROSS_CURRENCY");
	String secondaryCrossCurrency = Main.GENERAL_PROPERTIES.getProperty("SECONDARY_CROSS_CURRENCY");
	
	@Override
	public void setNextConverterHandler(ConverterHandler nextHandler) {
	}

	@Override
	public Double handleRequest(CurrencyExchange currencyExchange) {
		String crossCurrency = primaryCrossCurrency;
		if (CrossConverter.exists(currencyExchange.display())){//cross currency is EUR
			crossCurrency = secondaryCrossCurrency;
		}
			
		String currencyPair = currencyExchange.getBase()+crossCurrency;
		
		if (currencyExchange.getTerm().equals(primaryCrossCurrency) && !crossCurrency.equals(secondaryCrossCurrency)){
			return null;
		}
		if(crossCurrency.equals(primaryCrossCurrency) && currencyExchange.getBase().equals(crossCurrency)){
			return null;	
		}
		Double initialConversionRate = firstHandler.handleRequest(new CurrencyExchange(currencyExchange.getBase(),crossCurrency,1.0));
		
		currencyPair = crossCurrency+currencyExchange.getTerm();
		
		Double finalConversionRate = firstHandler.handleRequest(new CurrencyExchange(crossCurrency,currencyExchange.getTerm(),1.0));
		
		if (initialConversionRate == null || finalConversionRate == null){
			return null;
		}else{
			Double rate = (initialConversionRate * finalConversionRate) * currencyExchange.getAmount();
			return rate;
		}
	}
	public void setFirstConverterHandler(ConverterHandler first) {
		// TODO Auto-generated method stub
		this.firstHandler = first;
	}
}
