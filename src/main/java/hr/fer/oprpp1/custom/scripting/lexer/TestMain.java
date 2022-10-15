package hr.fer.oprpp1.custom.scripting.lexer;

public class TestMain {


		public static void main(String[] args) {
			String input = "This is sample text.\r\n"
					+ "{$ FOR i -1 1.20 \"pro\\\\b\\\"a\" $}\r\n"
					+ "This is {$= i $}"
					+ "{$END$}\r\n"
					+ "{$FOR i 0 10 2 $}\r\n"
					+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n"
					+ "{$END$}";
			
			ScriptingLexer lexer = new ScriptingLexer(input);
			
//			String name = "\"john\"";
			
			lexer.nextToken();
			
			lexer.nextToken();
			
			lexer.setState(LexerState.TAG);

			lexer.nextToken();

			lexer.nextToken();
			
			lexer.nextToken();
			
			lexer.nextToken();

			lexer.nextToken();

			lexer.nextToken();
			
			lexer.setState(LexerState.BASIC);
			
			lexer.nextToken();

			lexer.nextToken();
						
			lexer.setState(LexerState.TAG);
			
			lexer.nextToken();

			lexer.nextToken();

			lexer.nextToken();
			
			lexer.setState(LexerState.BASIC);
			
			lexer.nextToken();
			
			lexer.setState(LexerState.TAG);
			
			lexer.nextToken();

			lexer.nextToken();
			
			lexer.setState(LexerState.BASIC);











			

						
			
			
		}

	}
