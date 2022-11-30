package hr.fer.oprpp1.custom.scripting.elems;

public class ElementVariable extends Element{
	
	private String name;


	public ElementVariable(String name) {
		super();
		this.name = name;
	}
	
	public ElementVariable(Object name) {
		super();
		this.name = name.toString();
	}


	@Override
	public String asText() {
		return name;
	}
	
	

}
