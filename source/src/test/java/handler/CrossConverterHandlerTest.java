package handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import converter.CrossConverter;
import converter.DirectConverter;
import converter.InvertConverter;
import forex.CurrencyExchange;
import utils.ConfigReader;
import utils.Util;

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
		Properties testForexes = new Properties();
		testForexes.put("USDJPY", 119.95);
		testForexes.put("GBPUSD", 1.5683);
		testForexes.put("USDCNY", 6.1715);
		testForexes.put("EURUSD", 1.2315);
		testForexes.put("EURNOK", 8.6651);
		testForexes.put("EURCZK",27.6028);
		testForexes.put("EURDKK",7.4405);
		DirectConverter.initialize(testForexes);
		handlerDirect = new DirectConverterHandler();
		handlerInvert = new InvertConverterHandler();
		handlerCross = new CrossConverterHandler();
		handlerDirect.setNext(handlerInvert);
		handlerInvert.setFirst(handlerDirect);
		handlerInvert.setNext(handlerCross);
		handlerCross.setFirst(handlerDirect);
		
		List<String> invertCurrencies = new ArrayList<String>();
		invertCurrencies.add("JPYUSD");
		invertCurrencies.add("USDGBP");
		invertCurrencies.add("USDEUR");
		invertCurrencies.add("CZKEUR");
		invertCurrencies.add("CNYUSD");
		InvertConverter.initialize(invertCurrencies);
		
		List<String> crossCurrencies = new ArrayList<String>();
		crossCurrencies.add("USDNOK");
		crossCurrencies.add("CNYNOK");
		crossCurrencies.add("CZKDKK");
		CrossConverter.initialize(crossCurrencies);
	}
	
	@Test
	public void testCrossWithEurCurrencies(){
		CurrencyExchange crossInvMode = new CurrencyExchange("CZK","DKK",12.91);
		assertEquals(new Double(3.48), Util.round(handlerCross.handleRequest(crossInvMode)));
	}
	
	@Test
	public void testCrossWithUsdInvertedCurrencies(){
		CurrencyExchange crossInvMode = new CurrencyExchange("JPY","GBP",21.2);
		assertEquals(new Double(0.11), Util.round(handlerCross.handleRequest(crossInvMode)));
	}
	
	@Test
	public void testCrossWithEurInvertedCurrencies(){
		CurrencyExchange crossInvMode = new CurrencyExchange("CNY","NOK",14.0);
		assertEquals(new Double(15.96), Util.round(handlerCross.handleRequest(crossInvMode)));
	}
	
	@Test
	public void testWithUnknownBaseCurrency(){
		CurrencyExchange crossUnknownBase = new CurrencyExchange("CHF","NOK",14.0);
		assertEquals(null, handlerCross.handleRequest(crossUnknownBase));
	}
	
	@Test
	public void testWithUnknownTermCurrency(){
		CurrencyExchange crossUnknownBase = new CurrencyExchange("CNY","CHF",14.0);
		assertEquals(null, handlerCross.handleRequest(crossUnknownBase));
	}
}
