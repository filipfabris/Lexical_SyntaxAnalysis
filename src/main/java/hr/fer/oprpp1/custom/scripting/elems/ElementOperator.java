package hr.fer.oprpp1.custom.scripting.elems;

public class ElementOperator extends Element{
	
	private String symbol;
		
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	public ElementOperator(Object symbol) {
		super();
		this.symbol = symbol.toString();
	}


	@Override
	public String asText() {
		return symbol;
	}

}
