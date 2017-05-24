
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
	static Set<String> EUR_CROSS_CURRENCY_SET;
	ConverterHandler firstHandler = null;
	public static void initialize(List<String> invertableCurrencies){
		if(EUR_CROSS_CURRENCY_SET == null){
			EUR_CROSS_CURRENCY_SET = new HashSet<String>();
		}
		EUR_CROSS_CURRENCY_SET.addAll(invertableCurrencies);
	}
	
	
	public static boolean exists(String currencyPair){
		return EUR_CROSS_CURRENCY_SET.contains(currencyPair);
	}
}
