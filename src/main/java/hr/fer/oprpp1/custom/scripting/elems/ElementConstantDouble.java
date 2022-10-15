package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantDouble extends Element{
	
	private double value;
	
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}
	
	public ElementConstantDouble(Object value) {
		super();
		this.value = Double.parseDouble(value.toString());
	}

	@Override
	public String asText() {
		return Double.valueOf(value).toString();
	}
	

}
