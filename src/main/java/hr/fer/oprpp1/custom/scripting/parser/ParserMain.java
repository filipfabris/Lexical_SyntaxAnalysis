package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.ScriptingLexer;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

public class ParserMain {
	
	public static void main(String[] args) {
		String input = "This is sample text.\r\n"
				+ "{$ FOR i 1 10 1 $}\r\n"
				+ "This is {$= i $}-th time this message is generated.\r\n"
				+ "{$END$}\r\n"
				+ "{$FOR i 0 10 2 $}\r\n"
				+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}";
		
		ScriptingLexer lexer = new ScriptingLexer(input);
		
		ScriptingParser parser = new ScriptingParser(lexer);
		
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = document.toString();
		
		System.out.println(originalDocumentBody);
		
		ScriptingLexer lexer2 = new ScriptingLexer(originalDocumentBody);
		ScriptingParser parser2 = new ScriptingParser(lexer2);
		DocumentNode document2 = parser2.getDocumentNode();
		
		System.out.println("\n");
		
		System.out.println(document2);
		
		boolean same = document.equals(document2);
		
		System.out.println(same);
		
		
		
	}

}
