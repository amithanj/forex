package converter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class CrossConverterTest {

	List<String> crossCurrencies = null;
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(InvertConverter.class);
		crossCurrencies = new ArrayList<String>();
		crossCurrencies.add("INRJPY");
		crossCurrencies.add("USDCZK");
	}
	
	@Test
	public void testGetExchangeRate() {
		InvertConverter.initialize(crossCurrencies);
		assertTrue(InvertConverter.exists("INRJPY"));		
		assertFalse(InvertConverter.exists("EURGBP"));
	}

}
