package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.oprpp1.custom.scripting.elems.Element;

public class EchoNode extends Node{
	
	Element[] elements;
	

	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}


	public EchoNode(Object[] array) {
		super();
		this.elements = Arrays.copyOf(array, array.length, Element[].class);
	}


	public Element[] getElements() {
		return elements;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$");
		sb.append("=");
		
		for(int i = 0; i<elements.length; i++) {
			sb.append(" " + elements[i].asText());
		}
		
		sb.append("$}");
		
		return sb.toString();
	}
	
	

}
