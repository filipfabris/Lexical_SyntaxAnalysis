package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantInteger extends Element{
	
	private int value;

	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}
	
	public ElementConstantInteger(Object value) {
		super();
		this.value = Integer.parseInt(value.toString());
	}
	
	
	@Override
	public String asText() {
		return Integer.valueOf(value).toString();
	}

	
	

}
