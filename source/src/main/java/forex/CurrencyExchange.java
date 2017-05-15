package forex;

/**
 * @author amithanadig
 * A value object containing information about input provided for currency conversion.
 * It contains base currency(source), target currency (term) and amount to be converted.
 * 
 */
public class CurrencyExchange {
	private String source;
	private String target;
	private Double input;

	public CurrencyExchange(){}
	
	public CurrencyExchange(String source, String target, Double rate){
		this.source = source;
		this.target = target;
		this.input = rate;
	}
	
	public String display(){
		return source+target;
	}
	
	public String toString(){
		return "source="+source+":target="+target+":rate="+input;
	}
	
	public String getSource(){
		return source;
	}
	
	public String getTarget(){
		return target;
	}
	
	public Double getInput(){
		return input;
	}
}

