package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class ForLoopNode extends Node{
	
	ElementVariable variable;
	Element startExpression;
	Element endExpression;
	Element stepExpression;
	
	
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	public ForLoopNode(Object variable, Object startExpression, Object endExpression, Object stepExpression) {
		if(variable.getClass() != ElementVariable.class) {
			throw new SmartScriptParserException("Wrong variable type in for loop");
		}
		this.variable = (ElementVariable) variable;
		this.startExpression = (Element) startExpression;
		this.endExpression = (Element) endExpression;
		
		if(stepExpression == null) {
			this.stepExpression = new Element();
		}else {	
			this.stepExpression = (Element) stepExpression;	
		}
	}

	public ElementVariable getVariable() {
		return variable;
	}
	public Element getStartExpression() {
		return startExpression;
	}
	public Element getEndExpression() {
		return endExpression;
	}
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$");
		sb.append("FOR");
		sb.append(" " + variable.asText());
		sb.append(" " + startExpression.asText());
		sb.append(" " + endExpression.asText());
		sb.append(" " + stepExpression.asText());
		
		sb.append("$}");


		//Uzmi sve od prije
		sb.append(super.toString());
		
		sb.append("{$");
		sb.append("END");
		sb.append("$}");

		
		return sb.toString();
		
	}
	
	

}
