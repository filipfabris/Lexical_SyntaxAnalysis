package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		lexer.nextToken();	
		assertNotNull(lexer.getToken(), "Token was expected but null was returned.");
		
	}
	
	@Test
	public void testNullInput() {
		assertThrows(LexerException.class, () -> new SmartScriptLexer(null));
	}
	
	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		 lexer.nextToken();
	
		assertEquals(TokenType.EOF, lexer.getToken().getType(), "Empty input must generate only EOF token.");
	}
	
	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		 lexer.nextToken();

		
		Token token = lexer.getToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}
	
	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testForNode() {
		// Lets check for several numbers...
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i 1 10 1 $}\\r\\n");
		
		
		lexer.nextToken();	
		checkToken(lexer.getToken(), new Token(TokenType.TAG_START, null));
		
		lexer.setState(LexerState.TAG);
		lexer.nextToken();	
		checkToken(lexer.getToken(), new Token(TokenType.FOR, null));
		
		lexer.nextToken();	
		checkToken(lexer.getToken(), new Token(TokenType.VARIABLE, "i"));
		
		lexer.nextToken();	
		checkToken(lexer.getToken(), new Token(TokenType.INTEGER, 1));
		
		lexer.nextToken();	
		checkToken(lexer.getToken(), new Token(TokenType.INTEGER, 10));
		
		lexer.nextToken();	
		checkToken(lexer.getToken(), new Token(TokenType.INTEGER, 1));
		
		lexer.nextToken();	
		checkToken(lexer.getToken(), new Token(TokenType.TAG_END, null));
	}
	
	@Test
	public void testTextNode() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.\r\n");

		Token correctData[] = {
			new Token(TokenType.BASIC_TEXT, "This is sample text.\r\n"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testFunctionNode() {
		SmartScriptLexer lexer = new SmartScriptLexer(" @funkcija");
		
		lexer.setState(LexerState.TAG);
		
		lexer.nextToken();	
		checkToken(lexer.getToken(), new Token(TokenType.FUNCTION, "funkcija"));	
	}
	
	@Test
	public void testStringNode() {
		SmartScriptLexer lexer = new SmartScriptLexer("\"string\"");
		
		lexer.setState(LexerState.TAG);
		
		lexer.nextToken();
		checkToken(lexer.getToken(), new Token(TokenType.STRING, "\"string\""));	
	}
	

	private void checkTokenStream(SmartScriptLexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			
			
			
			lexer.nextToken();
			Token actual = lexer.getToken();
			
			
			String msg = "Checking token "+counter + ":";
			
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			
			counter++;
		}
	}

	
	
	private void checkToken(Token actual, Token expected) {
		String msg = "Token are not equal.";
		assertEquals(expected.getType(), actual.getType(), msg);
		assertEquals(expected.getValue(), actual.getValue(), msg);
}

	

}
