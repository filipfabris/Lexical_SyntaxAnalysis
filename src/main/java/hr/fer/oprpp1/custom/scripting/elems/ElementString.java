package hr.fer.oprpp1.custom.scripting.elems;

public class ElementString extends Element{
	
	private String value;
	
	public ElementString(String value) {
		super();
		this.value = value;
	}

	public ElementString(Object value) {
		super();
		this.value = value.toString();
	}


	@Override
	public String asText() {
		
		String edditedText = value.replaceAll("\\\\", "\\\\\\\\");
		
		edditedText = edditedText.replaceAll("\\{", "\\\\\\{");
		
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\"");
		
		String temp = edditedText.substring(1, edditedText.length()-1);
		
		temp = temp.replace("\"", "\\\"");
		
		sb.append(temp);
		
		sb.append("\"");
		
		return sb.toString();
	}

}
