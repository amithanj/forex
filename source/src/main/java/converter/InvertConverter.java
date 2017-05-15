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
	static Set<String> forexes = null;

	public static void initialize(List<String> invertableCurrencies){
		if(forexes == null){
			forexes = new HashSet<String>();
		}
		forexes.addAll(invertableCurrencies);
	}
	
	public static boolean exists(String currencyPair){
		return forexes.contains(currencyPair);
	}
	
}
