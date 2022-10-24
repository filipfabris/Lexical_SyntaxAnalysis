package hr.fer.oprpp1.custom.scripting.parser;

public class SmartScriptParserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	SmartScriptParserException(){
		super();
	}
	
	public SmartScriptParserException(String message){
		super(message);
	}

}
