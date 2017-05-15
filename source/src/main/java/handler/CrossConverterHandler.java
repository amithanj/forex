package handler;

import converter.CrossConverter;
import forex.CurrencyExchange;

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
 *  EURDKK pair is formed and exhange rate is formed. 
 *  To get the exchange rate, the control is transferred to
 *  first ConverterHandler i.e DirectConverterHandler. It loops through ConverterHandlers from there till the values is obtained.
 *  
 *  
 */
public class CrossConverterHandler implements ConverterHandler {
	ConverterHandler first = null;
	@Override
	public void setNext(ConverterHandler next) {
	}

	@Override
	public Double handleRequest(CurrencyExchange cur) {
		String crossCur = "USD";
		if (CrossConverter.exists(cur.display())){//cross currency is EUR
			crossCur = "EUR";
		}
			
		String currencyPair = cur.getSource()+crossCur;
		
		if (cur.getTarget().equals("USD") && !crossCur.equals("EUR")){
			return null;
		}
		if(crossCur.equals("USD") && cur.getSource().equals(crossCur)){
			return null;	
		}
		Double firstConv = first.handleRequest(new CurrencyExchange(cur.getSource(),crossCur,1.0));
		
		currencyPair = crossCur+cur.getTarget();
		
		Double secConv = first.handleRequest(new CurrencyExchange(crossCur,cur.getTarget(),1.0));
		
		if (firstConv == null || secConv == null){
			return null;
		}else{
			Double rate = (firstConv * secConv) * cur.getInput();
			return rate;
		}
	}
	public void setFirst(ConverterHandler first) {
		// TODO Auto-generated method stub
		this.first = first;
	}
}
