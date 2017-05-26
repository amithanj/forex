package converter;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import converter.DirectConverter;

@RunWith(PowerMockRunner.class)
public class DirectConverterTest {

	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(DirectConverter.class);
		
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
