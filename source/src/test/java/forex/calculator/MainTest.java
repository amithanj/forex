package forex.calculator;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.api.mockito.PowerMockito;

import converter.CrossConverter;
import converter.DirectConverter;
import converter.InvertConverter;
import forex.calculator.Main;
import forex.domain.CurrencyExchange;
import forex.validator.InvalidInputException;
import handler.ConverterHandler;
import handler.CrossConverterHandler;
import handler.DirectConverterHandler;
import handler.InvertConverterHandler;

public class MainTest {

	String result = null;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	ConverterHandler handlerInvert = null;
	ConverterHandler handlerDirect = null;
	ConverterHandler handlerCross = null;
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(DirectConverter.class);
		PowerMockito.mockStatic(InvertConverter.class);
		PowerMockito.mockStatic(CrossConverter.class);
		
		Main.GENERAL_PROPERTIES = new Properties();
		Main.GENERAL_PROPERTIES.put("JPY_OUTPUT_FORMATTER","#");
		
		handlerDirect = mock(DirectConverterHandler.class);
		handlerInvert = mock(InvertConverterHandler.class);
		handlerCross = new CrossConverterHandler();
		
		handlerDirect.setNextConverterHandler(handlerInvert);
		handlerInvert.setFirstConverterHandler(handlerDirect);
		handlerInvert.setNextConverterHandler(handlerCross);
		handlerCross.setFirstConverterHandler(handlerDirect);
	}
	
	@Test
	public void testResultFormatJPY() {
		result="AUD 20 = JPY 2008";
		when(handlerDirect.handleRequest(new CurrencyExchange("AUD","USD",1.0))).thenReturn(new Double("0.8371"));
		when(handlerDirect.handleRequest(new CurrencyExchange("USD","JPY",1.0))).thenReturn(new Double("119.95"));
		assertEquals(result, Main.validateAndCalculate("AUD","JPY","20"));
	}
	
	@Test
	public void testResultFormatNonJPY() {
		result="AUD 20 = EUR 13.59";
		when(handlerDirect.handleRequest(new CurrencyExchange("AUD","USD",1.0))).thenReturn(new Double("0.8371"));
		when(handlerDirect.handleRequest(new CurrencyExchange("USD","EUR",1.0))).thenReturn(new Double("0.8120"));
		assertEquals(result, Main.validateAndCalculate("AUD","EUR","20"));
	}
	
	@Test
	public void testInvalidInput() {
		result="Wrong input for  Base currency. Please enter again.";
		exception.expect(InvalidInputException.class);
		exception.expectMessage(result);
		Main.validateAndCalculate("AU9","EUR","20");
		
		result="Wrong input for  Term currency. Please enter again.";
		exception.expectMessage(result);
		Main.validateAndCalculate("AUD","E$%","20");
		
		result="Wrong input for  Amount. Please enter again.";
		exception.expectMessage(result);
		Main.validateAndCalculate("AUD","EUR","jdfs");
	}
	
	@Test
	public void testWithSameBaseAndTerm() {
		result="AUD 20 = AUD 20.00";
		assertEquals(result, Main.validateAndCalculate("AUD","AUD","20"));
		
		result="JPY 20 = JPY 20";
		assertEquals(result, Main.validateAndCalculate("JPY","JPY","20"));
	}
	
	@Test
	public void testWithWrongBaseCurrency() {
		result="Unable to find rate for CHF/AUD";
		exception.expectMessage(result);
		Main.validateAndCalculate("CHF","AUD","20");
	}
	
	@Test
	public void testWithWrongTermCurrency() {
		result="Unable to find rate for AUD/CHF";
		exception.expectMessage(result);
		Main.validateAndCalculate("AUD","CHF","20");
	}
}
