package handler;

import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import converter.DirectConverter;
import forex.CurrencyExchange;
import utils.ConfigReader;

import static org.junit.Assert.*;


public class DirectConverterHandlerTest {
	
	List<String> testCurrencies = null;
	DirectConverterHandler handler = null;
	ConfigReader testReader = null;
	CurrencyExchange currecy_found = null;
	Properties testForexes = null;
	
	@Before
	public void setUp() throws Exception {
		handler = new DirectConverterHandler();
		Properties testForexes = new Properties();
		testForexes.put("CADUSD", 0.8711);
		testForexes.put("GBPINR", 86.12);
		DirectConverter.initialize(testForexes);
	}

	@Test
	public void test() {
		currecy_found = new CurrencyExchange("CAD","USD",22.0);
		assertEquals(new Double(19.1642), handler.handleRequest(currecy_found));
	}
}
