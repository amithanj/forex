package converter;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import converter.DirectConverter;
import utils.ConfigReader;

public class DirectConverterTest {

	Properties testCurrencies = null;
	ConfigReader testReader = null;
	Properties currencies = null;
	@Before
	public void setUp() throws Exception {
		Properties testForexes = new Properties();
		testForexes.put("EURUSD", 1.2323);
		testForexes.put("GBPINR", 86.12);
		DirectConverter.initialize(testForexes);
	}
	
	@Test
	public void testGetExchangeRate() {
		
		assertEquals(new Double(1.2323), DirectConverter.getValue("EURUSD"));		
		assertNull(DirectConverter.getValue("EURJPY"));
	}

}
