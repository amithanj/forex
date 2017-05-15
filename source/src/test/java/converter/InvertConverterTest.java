package converter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import utils.ConfigReader;

public class InvertConverterTest {

	List<String> testCurrencies = null;
	ConfigReader testReader = null;
	@Before
	public void setUp() throws Exception {
		List<String> invertableCurrencies = new ArrayList<String>();
		invertableCurrencies.add("USDINR");
		invertableCurrencies.add("USDJPY");
		InvertConverter.initialize(invertableCurrencies);
	}
	
	@Test
	public void testGetExchangeRate() {
		
		assertTrue(InvertConverter.exists("USDINR"));		
		assertFalse(InvertConverter.exists("EURGBP"));
	}

}
