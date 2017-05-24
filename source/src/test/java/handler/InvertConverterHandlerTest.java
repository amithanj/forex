package handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import converter.DirectConverter;
import converter.InvertConverter;
import forex.domain.CurrencyExchange;
import utils.ConfigReader;
import utils.FormatterUtil;

public class InvertConverterHandlerTest {

	List<String> testCurrencies = null;
	ConverterHandler handlerInvert = null;
	ConverterHandler handlerDirect = null;
	
	ConfigReader testReader = null;
	CurrencyExchange currecy_found = null;
	Properties testForexes = null;
	
	@Before
	public void setUp() throws Exception {
		Properties testForexes = new Properties();
		testForexes.put("USDJPY", 119.95);
		testForexes.put("NZDUSD", 0.7750);
		DirectConverter.initialize(testForexes);
		handlerDirect = new DirectConverterHandler();
		handlerInvert = new InvertConverterHandler();
		handlerDirect.setNextConverterHandler(handlerInvert);
		handlerInvert.setFirstConverterHandler(handlerDirect);
		List<String> testCurrencies = new ArrayList<String>();
		testCurrencies.add("JPYUSD");
		testCurrencies.add("USDNZD");
		InvertConverter.initialize(testCurrencies);
	}

	@Test
	public void test() {
		currecy_found = new CurrencyExchange("JPY","USD",22.0);
		assertEquals(new Double(0.18), FormatterUtil.round(handlerInvert.handleRequest(currecy_found)));
	}

}
