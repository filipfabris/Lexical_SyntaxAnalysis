package hr.fer.oprpp1.custom.scripting.elems;

public class ElementFunction extends Element{
	
	private String name;
	
	
	
	public ElementFunction(String name) {
		super();
		this.name = name;
	}
	
	public ElementFunction(Object name) {
		super();
		this.name = name.toString();
	}



	@Override
	public String asText() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("@");
		sb.append(name);
		return sb.toString();
	}

}
