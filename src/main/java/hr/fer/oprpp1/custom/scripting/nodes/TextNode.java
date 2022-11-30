package hr.fer.oprpp1.custom.scripting.nodes;

public class TextNode extends Node{
	
	private String text;
	

	public TextNode(String text) {
		super();
		this.text = text;
	}


	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		
		String edditedText = text.replaceAll("\\\\", "\\\\\\\\");
		
		edditedText = edditedText.replaceAll("\\{", "\\\\\\{");
		
		edditedText = edditedText.replaceAll("\"", "\\\\\"");
		
		
		return edditedText;
	}
	
	

}
