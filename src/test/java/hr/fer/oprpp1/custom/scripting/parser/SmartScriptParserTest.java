package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

class SmartScriptParserTest {

	@Test
	void testDoc1() {
		String docBody = this.loader("test01.txt");
		
		boolean output = this.parserEquals(docBody);
		
		assertEquals(true, output, "One Text node");
	}
	
	@Test
	void testDoc2() {
		String docBody = this.loader("test02.txt");
		
		boolean output = this.parserEquals(docBody);
		
		assertEquals(true, output, "Empty text");
	}
	
	@Test
	void testDoc3() {
		String docBody = this.loader("test03.txt");
		
		boolean output = this.parserEquals(docBody);
		
		assertEquals(true, output, "Text node with escape sign");
	}
	
//	@Tag("Illegal escape")
	@Test
	void testDoc4() {
		String docBody = this.loader("test04.txt");
				
		assertThrows(SmartScriptParserException.class, () -> this.parserEquals(docBody), "Text node with illegal escape");
	}
	
//	@Tag("Illegal escape inside string sequence")
	@Test
	void testDoc5() {
		String docBody = this.loader("test05.txt");
				
		assertThrows(SmartScriptParserException.class, () -> this.parserEquals(docBody), "Text node with illegal escape");
	}
	
	@Test
	void testDoc6() {
		String docBody = this.loader("test06.txt");
		
		boolean output = this.parserEquals(docBody);
		
		assertEquals(true, output, "Text node, Exho node");
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		
		assertEquals(3, document.numberOfChildren(), "Text node, Exho node, Text node(\n)");
		
		assertEquals(EchoNode.class, document.getChild(1).getClass(), "Echo node");
	}
	
	@Test
	void testDoc7() {
		String docBody = this.loader("test07.txt");
		
		boolean output = this.parserEquals(docBody);
		
		assertEquals(true, output, "Text node, Exho node");
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		
		assertEquals(2, document.numberOfChildren(), "Text node, Exho node");
		
		assertEquals(EchoNode.class, document.getChild(1).getClass(), "Echo node");
		
		assertThrows(IndexOutOfBoundsException.class,() -> document.getChild(2).getClass(), "Echo node");

	}
	
	@Test
	void testDoc8() {
		String docBody = this.loader("test08.txt");
				
		assertThrows(SmartScriptParserException.class, () -> this.parserEquals(docBody), "Echo node with illegal escape inside its string element");
	}
	
	@Test
	void testDoc9() {
		String docBody = this.loader("test09.txt");
				
		assertThrows(SmartScriptParserException.class, () -> this.parserEquals(docBody), "Echo node with illegal escape inside its string element");
	}
	
	@Test
	void testDoc10() {
		String docBody = this.loader("test10.txt");
		
		boolean output = this.parserEquals(docBody);
		
		assertEquals(true, output, "Text node, For node, Text node, For node");
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		
		assertEquals(4, document.numberOfChildren(), "Text node, Exho node");
		
		assertEquals(ForLoopNode.class, document.getChild(1).getClass(), "For node");
		
		assertEquals(3, document.getChild(1).numberOfChildren(), "For node children");
		
		assertEquals(3, document.getChild(1).numberOfChildren(), "For node children");
		
		assertEquals(TextNode.class, document.getChild(1).getChild(0).getClass(), "Text node inside For node");
		
		assertThrows(IndexOutOfBoundsException.class,() -> document.getChild(0).getChild(0), "There are no elements inside text node");
		
		assertThrows(IndexOutOfBoundsException.class,() -> document.getChild(4).getClass(), "There is no 5th child inside Document node");

	}
	
	@Test
	void testDoc11() {
		String docBody = this.loader("test11.txt");
		
		boolean output = this.parserEquals(docBody);
		
		assertEquals(true, output, "For Node");
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		
		assertEquals(1, document.numberOfChildren(), "For node");
				
		assertEquals(1, document.getChild(0).numberOfChildren(), "Text node(\r\n)");
		
		assertEquals(1, document.getChild(0).numberOfChildren(), "Text node(\r\n)");
		
		assertEquals(ElementConstantInteger.class, ((ForLoopNode) document.getChild(0)).getStartExpression().getClass(), "-1");
		assertEquals(ElementVariable.class, ((ForLoopNode) document.getChild(0)).getVariable().getClass(), "sco_re");
		assertEquals(ElementConstantInteger.class, ((ForLoopNode) document.getChild(0)).getEndExpression().getClass(), "1");

	}
	
	@Test
	void testDoc12() {
		String docBody = this.loader("test12.txt");
		
		boolean output = this.parserEquals(docBody);
		
		assertEquals(true, output, "For Node");
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		
		assertEquals(1, document.numberOfChildren(), "For node");
		
		assertEquals(1, document.getChild(0).numberOfChildren(), "Text node(\r\n)");
				
		assertEquals(ElementConstantDouble.class, ((ForLoopNode) document.getChild(0)).getStartExpression().getClass(), "-1");
		assertEquals(ElementVariable.class, ((ForLoopNode) document.getChild(0)).getVariable().getClass(), "sco_re");
		assertEquals(ElementConstantInteger.class, ((ForLoopNode) document.getChild(0)).getEndExpression().getClass(), "1");
	}
	
	@Test
	void testDoc13() {
		String docBody = this.loader("test13.txt");
		
		assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(docBody), "Integer on position for variable inside for loop");
	}
	
	@Test
	void testDoc14() {
		String docBody = this.loader("test14.txt");
		
		assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(docBody), "* is not a variable inside for loop");
	}
	
	@Test
	void testDoc15() {
		String docBody = this.loader("test15.txt");
		
		assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(docBody), "@sin function element");
	}
	
	@Test
	void testDoc16() {
		String docBody = this.loader("test16.txt");
		
		assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(docBody), "too many arguments");
	}
	
	@Test
	void testDoc17() {
		String docBody = this.loader("test17.txt");
		
		assertThrows(SmartScriptParserException.class, () ->  new SmartScriptParser(docBody), "too many arguments");
	}
	
	@Test
	void testDoc18() {
		String docBody = this.loader("test18.txt");
		
		boolean output = this.parserEquals(docBody);
		
		assertEquals(true, output, "For Node");
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		
		assertEquals(1, document.numberOfChildren(), "For node");
				
		assertEquals(ElementConstantInteger.class, ((ForLoopNode) document.getChild(0)).getStartExpression().getClass(), "1");
		assertEquals(ElementVariable.class, ((ForLoopNode) document.getChild(0)).getVariable().getClass(), "year");
		assertEquals(ElementVariable.class, ((ForLoopNode) document.getChild(0)).getEndExpression().getClass(), "last_year");
		
		assertEquals(Element.class, ((ForLoopNode) document.getChild(0)).getStepExpression().getClass(), "");

	}
	
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
	
	private boolean parserEquals(String docBody) {
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode(); // Output
		String originalDocumentBody = document.toString();

		SmartScriptLexer lexer2 = new SmartScriptLexer(originalDocumentBody);
		SmartScriptParser parser2 = new SmartScriptParser(lexer2);
		DocumentNode document2 = parser2.getDocumentNode();

		return document.equals(document2);
		
	}

}
