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
	
	public String getBase(){
		return base;
	}
	
	public String getTerm(){
		return term;
	}
	
	public Double getAmount(){
		return amount;
	}
	
	public boolean equals(Object param) {
	    if(param == null)
	    {
	        return false;
	    }
	    if (param == this)
	    {
	        return true;
	    }
	    if (getClass() != param.getClass())
	    {
	        return false;
	    }
	     
	    CurrencyExchange toCompare = (CurrencyExchange) param;
	    return (this.getBase().equals(toCompare.getBase()) && this.getTerm().equals(toCompare.getTerm()) && this.getAmount().equals(toCompare.getAmount()) );
	     
	}
	
	public int hashCode(){
	    return (int) base.hashCode() *
	                 term.hashCode() *
	                 amount.hashCode();
	}
	
	public String toString(){
		return "source="+base+":target="+term+":rate="+amount;
	}
}

