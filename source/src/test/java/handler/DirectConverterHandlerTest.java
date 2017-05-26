package handler;

import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import forex.domain.CurrencyExchange;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class DirectConverterHandlerTest {
	List<String> testCurrencies = null;
	DirectConverterHandler dirctHandler = null;
	InvertConverterHandler invertHandler = null;
	Properties testForexes = null;
	
	@Before
	public void setUp() throws Exception {
		testForexes = mock(Properties.class);
		dirctHandler = new DirectConverterHandler();
		invertHandler = mock(InvertConverterHandler.class);
		dirctHandler.setNextConverterHandler(invertHandler);
		when(testForexes.get("CADUSD")).thenReturn("0.8711");
		when(testForexes.get("GBPINR")).thenReturn("86.12");
	}

	@Test
	public void testDirectConversion() {
		assertEquals(new Double(19.1642), dirctHandler.handleRequest(new CurrencyExchange("CAD","USD",22.0)));
	}
	
	
}
