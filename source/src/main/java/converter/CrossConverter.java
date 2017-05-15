
package converter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import handler.ConverterHandler;

/**
 * @author amithanadig
 * This class sets the cross currency conversion context.
 * It loads the currency pairs(Currency1Currency2) that require EUR as cross currency.
 *
 */
public class CrossConverter {
	static Set<String> EUR_CROSS_CURRENCY;
	ConverterHandler first = null;
	public static void initialize(List<String> invertableCurrencies){
		if(EUR_CROSS_CURRENCY == null){
			EUR_CROSS_CURRENCY = new HashSet<String>();
		}
		EUR_CROSS_CURRENCY.addAll(invertableCurrencies);
	}
	
	
	public static boolean exists(String currencyPair){
		return EUR_CROSS_CURRENCY.contains(currencyPair);
	}
}
