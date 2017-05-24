package converter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author amithanadig
 * This class initializes the information about currency pairs which require direct currency exchanges
 * to be inverted for conversion.
 * Such currency pairs are read from invertibleCurrencies.properties and used during conversion.
 */
public class InvertConverter {
	static Set<String> FOREX_SET = null;

	public static void initialize(List<String> invertableCurrencies){
		if(FOREX_SET == null){
			FOREX_SET = new HashSet<String>();
		}
		FOREX_SET.addAll(invertableCurrencies);
	}
	
	public static boolean exists(String currencyPair){
		return FOREX_SET.contains(currencyPair);
	}
	
}
