package forex.domain;

/**
 * @author amithanadig
 * A value object containing information about input provided for currency conversion.
 * It contains base currency(source), target currency (term) and amount to be converted.
 * 
 */
public class CurrencyExchange {
	private String base;
	private String term;
	private Double amount;

	public CurrencyExchange(){}
	
	public CurrencyExchange(String base, String term, Double rate){
		this.base = base;
		this.term = term;
		this.amount = rate;
	}
	
	public String display(){
		return base+term;
	}
	
	public String toString(){
		return "source="+base+":target="+term+":rate="+amount;
	}
	
	public String getBase(){
		return base;
	}
	
	public String getTerm(){
		return term;
	}
	
	public Double getAmount(){
		return amount;
	}
}

