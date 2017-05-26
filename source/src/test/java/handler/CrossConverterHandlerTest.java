package handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import static org.mockito.Mockito.*;

import converter.CrossConverter;
import converter.DirectConverter;
import converter.InvertConverter;
import forex.calculator.Main;
import forex.domain.CurrencyExchange;
import utils.ConfigReader;
import utils.FormatterUtil;

public class CrossConverterHandlerTest {

	List<String> invertCurrencies = null;
	List<String> crossCurrencies = null;
	ConverterHandler handlerInvert = null;
	ConverterHandler handlerDirect = null;
	ConverterHandler handlerCross = null;
	
	ConfigReader testReader = null;
	CurrencyExchange currecy_found = null;
	Properties testForexes = null;
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(DirectConverter.class);
		PowerMockito.mockStatic(InvertConverter.class);
		PowerMockito.mockStatic(CrossConverter.class);
		Properties testForexes = mock(Properties.class);
		when(testForexes.get("USDJPY")).thenReturn("119.95");
		when(testForexes.get("GBPUSD")).thenReturn("1.5683");
		when(testForexes.get("USDCNY")).thenReturn("6.1715");
		when(testForexes.get("EURUSD")).thenReturn("1.2315");
		when(testForexes.get("EURNOK")).thenReturn("8.6651");
		when(testForexes.get("EURCZK")).thenReturn("27.6028");
		when(testForexes.get("EURDKK")).thenReturn("7.4405");
		
		Main.GENERAL_PROPERTIES = mock(Properties.class);//new Properties();
		when(Main.GENERAL_PROPERTIES.getProperty("PRIMARY_CROSS_CURRENCY")).thenReturn("USD");
		when(Main.GENERAL_PROPERTIES.getProperty("SECONDARY_CROSS_CURRENCY")).thenReturn("EUR");
		
		handlerDirect = mock(DirectConverterHandler.class);
		handlerInvert = mock(InvertConverterHandler.class);
		handlerCross = new CrossConverterHandler();
		
		handlerDirect.setNextConverterHandler(handlerInvert);
		handlerInvert.setFirstConverterHandler(handlerDirect);
		handlerInvert.setNextConverterHandler(handlerCross);
		handlerCross.setFirstConverterHandler(handlerDirect);
		
		List<String> invertCurrencies = new ArrayList<String>();
		invertCurrencies.add("JPYUSD");
		invertCurrencies.add("USDGBP");
		invertCurrencies.add("USDEUR");
		invertCurrencies.add("CZKEUR");
		invertCurrencies.add("CNYUSD");
		
		List<String> crossCurrencies = new ArrayList<String>();
		crossCurrencies.add("USDNOK");
		crossCurrencies.add("CNYNOK");
		crossCurrencies.add("CZKDKK");
		CrossConverter.initialize(crossCurrencies);
	}
	
	@Test
	public void testCrossWithEurCurrencies(){
		when(handlerDirect.handleRequest(new CurrencyExchange("CZK","EUR",1.0))).thenReturn(new Double("0.03622"));
		when(handlerDirect.handleRequest(new CurrencyExchange("EUR","DKK",1.0))).thenReturn(new Double("7.4405"));
		CurrencyExchange crossInvMode = new CurrencyExchange("CZK","DKK",12.91);
		assertEquals(new Double(3.48), FormatterUtil.round(handlerCross.handleRequest(crossInvMode)));
	}
	
	@Test
	public void testCrossWithUsdInvertedCurrencies(){
		when(handlerDirect.handleRequest(new CurrencyExchange("JPY","USD",1.0))).thenReturn(new Double("0.0083"));
		when(handlerDirect.handleRequest(new CurrencyExchange("USD","GBP",1.0))).thenReturn(new Double("0.6376"));
		CurrencyExchange crossInvMode = new CurrencyExchange("JPY","GBP",21.2);
		assertEquals(new Double(0.11), FormatterUtil.round(handlerCross.handleRequest(crossInvMode)));
	}
	
	@Test
	public void testCrossWithEurInvertedCurrencies(){
		when(handlerDirect.handleRequest(new CurrencyExchange("CNY","EUR",1.0))).thenReturn(new Double("0.1315"));
		when(handlerDirect.handleRequest(new CurrencyExchange("EUR","NOK",1.0))).thenReturn(new Double("8.6651"));
		CurrencyExchange crossInvMode = new CurrencyExchange("CNY","NOK",14.0);
		assertEquals(new Double(15.95), FormatterUtil.round(handlerCross.handleRequest(crossInvMode)));
	}
	
	@Test
	public void testWithUnknownBaseCurrency(){
		when(handlerDirect.handleRequest(new CurrencyExchange("CHF","USD",1.0))).thenReturn(null);
		when(handlerDirect.handleRequest(new CurrencyExchange("USD","EUR",1.0))).thenReturn(new Double("0.8120"));
		when(handlerDirect.handleRequest(new CurrencyExchange("EUR","NOK",1.0))).thenReturn(new Double("8.6651"));
		
		CurrencyExchange crossUnknownBase = new CurrencyExchange("CHF","NOK",14.0);
		assertEquals(null, handlerCross.handleRequest(crossUnknownBase));
	}
	
	@Test
	public void testWithUnknownTermCurrency(){
		CurrencyExchange crossUnknownBase = new CurrencyExchange("CNY","CHF",14.0);
		when(handlerDirect.handleRequest(new CurrencyExchange("CNY","USD",1.0))).thenReturn(null);
		when(handlerDirect.handleRequest(new CurrencyExchange("USD","CHF",1.0))).thenReturn(null);
		
		assertEquals(null, handlerCross.handleRequest(crossUnknownBase));
	}
}
