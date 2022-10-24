package hr.fer.oprpp1.custom.scripting.parser.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

	public static void main(String[] args) throws IOException {
		
		String filepath = "C:\\Users\\filip\\Desktop\\Java\\hw02-0036532834\\src\\main\\resources\\extra\\primjer1.txt";
		String docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);

//		System.out.println(docBody);
		SmartScriptParser parser;

		try {
			parser = new SmartScriptParser(docBody);
			DocumentNode document = parser.getDocumentNode(); // Output
			String originalDocumentBody = document.toString();


			SmartScriptLexer lexer2 = new SmartScriptLexer(originalDocumentBody);
			SmartScriptParser parser2 = new SmartScriptParser(lexer2);
			DocumentNode document2 = parser2.getDocumentNode();
			

			boolean same = document.equals(document2);
			System.out.println(same);

		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			e.printStackTrace();
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			e.printStackTrace();
			System.exit(-1);
		}

	}
	

}
