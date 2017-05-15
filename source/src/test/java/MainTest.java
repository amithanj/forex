import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MainTest {

	String result = null;
	@Before
	public void setUp() throws Exception {
		Main.initialize();
		
	}
	
	@Test
	public void testResultFormatJPY() {
		result="AUD 20 = JPY 2008";
		assertEquals(result, Main.validateAndCalculate("AUD","JPY","20"));
	}
	
	@Test
	public void testResultFormatNonJPY() {
		result="AUD 20 = EUR 13.59";
		assertEquals(result, Main.validateAndCalculate("AUD","EUR","20"));
	}
	
	@Test
	public void testInvalidInput() {
		result="Wrong input for  Base currency. Please enter again.";
		assertEquals(result, Main.validateAndCalculate("AU9","EUR","20"));
		
		result="Wrong input for  Term currency. Please enter again.";
		assertEquals(result, Main.validateAndCalculate("AUD","E$%","20"));
		
		result="Wrong input for  Amount. Please enter again.";
		assertEquals(result, Main.validateAndCalculate("AUD","EUR","jdfs"));
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
		assertEquals(result, Main.validateAndCalculate("CHF","AUD","20"));
	}
	
	@Test
	public void testWithWrongTermCurrency() {
		result="Unable to find rate for AUD/CHF";
		assertEquals(result, Main.validateAndCalculate("AUD","CHF","20"));
	}

}
