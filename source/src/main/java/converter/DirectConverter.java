package converter;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import java.util.HashMap;

/**
 * @author amithanadig
 * This class initializes all the currency information which provide direct conversion facility.
 * It loads the Properties object containing name/value pair provided in forex.properties file
 * This information is used for lookup forex rate.
 *
 */
public class DirectConverter{
	public static Map<String,String> FOREX_MAP;

	public static void initialize(Properties properties){
		if(FOREX_MAP == null){
			FOREX_MAP = new HashMap<String,String>();
		}
		FOREX_MAP.putAll(properties.entrySet().stream().collect(
				Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
		
	}
	public static Double getValue(String currencies){
		String 	rate = FOREX_MAP.get(currencies);		
		return  rate!=null?new Double(rate):null;
	}
}
