package hr.fer.oprpp1.custom.scripting.parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.lexer.ScriptingLexer;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

public class ParserDemo {

	public static void main(String[] args) throws IOException {
		String filepath = "C:\\Users\\filip\\Desktop\\Java\\hw02-0036532834\\src\\main\\resources\\extra\\primjer9.txt";
		
		String docBody = new String(
				Files.readAllBytes(Paths.get(filepath)),
				StandardCharsets.UTF_8
				);
		
		System.out.println(docBody);
		
		ScriptingLexer lexer = new ScriptingLexer(docBody);
		ScriptingParser parser = new ScriptingParser(lexer);
		//Output
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		
		ScriptingLexer lexer2 = new ScriptingLexer(originalDocumentBody);
		ScriptingParser parser2 = new ScriptingParser(lexer2);
		DocumentNode document2 = parser2.getDocumentNode();
		
		boolean same = document.equals(document2);
		
		System.out.println(same);
		
		
		
	}
	
	

}
