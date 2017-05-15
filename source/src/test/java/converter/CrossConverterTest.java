package converter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import utils.ConfigReader;

public class CrossConverterTest {

	List<String> testCurrencies = null;
	ConfigReader testReader = null;
	@Before
	public void setUp() throws Exception {
		List<String> crossCurrencies = new ArrayList<String>();
		crossCurrencies.add("INRJPY");
		crossCurrencies.add("USDCZK");
		InvertConverter.initialize(crossCurrencies);
	}
	
	@Test
	public void testGetExchangeRate() {
		assertTrue(InvertConverter.exists("INRJPY"));		
		assertFalse(InvertConverter.exists("EURGBP"));
	}

}
