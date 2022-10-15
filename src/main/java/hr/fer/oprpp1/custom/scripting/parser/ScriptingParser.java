package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.LexerState;
import hr.fer.oprpp1.custom.scripting.lexer.ScriptingLexer;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.*;

public class ScriptingParser {

	private Token currentToken;
	private ScriptingLexer lexer;
	private DocumentNode mainNode;
	private ObjectStack stack;

	ScriptingParser(ScriptingLexer lexer) {
		this.lexer = lexer;
		this.mainNode = new DocumentNode();
		this.stack = new ObjectStack();

		stack.push(mainNode);
		this.parse();
		
		String val = "23";
	}

	private void parse() {

		// Prvi token inicijalizacija da vidimo gdje cemo pocet
		lexer.nextToken();
		currentToken = lexer.getToken();

		while (true) {
			if (isTokenOfType(TokenType.EOF)) {
				break; // kraj
			}

			else if (isTokenOfType(TokenType.BASIC_TEXT)) {
				this.parseText();

			}

			else if (isTokenOfType(TokenType.TAG_START)) {
				this.lexer.setState(LexerState.TAG);

				lexer.nextToken(); // Da vidimo je li FOR,= ili END
				currentToken = lexer.getToken();

				if (isTokenOfType(TokenType.FOR)) {
					this.parseFor();
				}

				else if (isTokenOfType(TokenType.ECHO)) {
					this.parseEcho();
				}

				else if (isTokenOfType(TokenType.END)) {
					this.parseEnd();
				}

				// Moralo je doci do Tag_END-a
				if (isTokenOfType(TokenType.TAG_END) == false) {
					throw new RuntimeException("Wrong parsing");
				}
				this.lexer.setState(LexerState.BASIC);
			}

			// Novi consumer je spreman
			lexer.nextToken();
			currentToken = lexer.getToken();
		}

	}

	private void parseText() {
		Node currentMain = (Node) stack.peek();
		TextNode node = new TextNode((String) currentToken.getValue());
		
		currentMain.addChildNode(node);
		return;

	}

	private void parseFor() {
		Node currentMain = (Node) stack.peek();
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		ForLoopNode node;
		
		while(true) {
			//Da vidimo sta smo dobili
			lexer.nextToken();
			Token currentToken = lexer.getToken();
			
			//Gotovi
			if (isTokenOfType(TokenType.TAG_END)) {
				break;
			}
			
			//Varijabla
			else if(isTokenOfType(TokenType.VARIABLE)) {
				ElementVariable variable = new ElementVariable(currentToken.getValue());
				array.add(variable);
			}
			
			//Broj DOUBLE
			else if(isTokenOfType(TokenType.DOUBLE)) {
				Element variable = new ElementConstantDouble(currentToken.getValue());
				array.add(variable);
				
			}
			//Broj INTEGER
			else if(isTokenOfType(TokenType.INTEGER)) {
				Element variable = new ElementConstantInteger(currentToken.getValue());
				array.add(variable);
			}
			
			//String
			else if(isTokenOfType(TokenType.STRING)) {
				Element variable = new ElementString(currentToken.getValue());
				array.add(variable);
			}
				
			//Error
			else {
				throw new RuntimeException("Wrong ECHO variable input");
			}
			
		}
		
		if(array.size() < 3 || array.size() > 4) {
			throw new RuntimeException("Too many or too few variables");
		}
		
		if(array.size() == 3) {
			node = new ForLoopNode(array.get(0), array.get(1), array.get(2), null);
		}else {
			node = new ForLoopNode(array.get(0), array.get(1), array.get(2), array.get(3));
		}
		
		currentMain.addChildNode(node);
		stack.push(node);
				
		return;
	}

	private void parseEcho() {
		Node currentMain = (Node) stack.peek();
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		EchoNode node;
		
		while(true) {
			//Da vidimo sta smo dobili
			lexer.nextToken();
			Token currentToken = lexer.getToken();
			
			//Gotovi
			if (isTokenOfType(TokenType.TAG_END)) {
				break;
			}
			
			//Varijabla
			else if(isTokenOfType(TokenType.VARIABLE)) {
				ElementVariable variable = new ElementVariable(currentToken.getValue());
				array.add(variable);
			}
			
			//Broj DOUBLE
			else if(isTokenOfType(TokenType.DOUBLE)) {
				Element variable = new ElementConstantDouble(currentToken.getValue());
				array.add(variable);
				
			}
			//Broj INTEGER
			else if(isTokenOfType(TokenType.INTEGER)) {
				Element variable = new ElementConstantInteger(currentToken.getValue());
				array.add(variable);
			}
			
			//String
			else if(isTokenOfType(TokenType.STRING)) {
				Element variable = new ElementString(currentToken.getValue());
				array.add(variable);
			}
			
			else if(isTokenOfType(TokenType.OPERATOR)) {
				Element variable = new ElementOperator(currentToken.getValue());
				array.add(variable);
			}
			
			else if(isTokenOfType(TokenType.FUNCTION)) {
				Element variable = new ElementFunction(currentToken.getValue());
				array.add(variable);
			}
			
			//Error
			else {
				throw new RuntimeException("Wrong FOR variable input");
			}
			
		}
		
		node = new EchoNode(array.toArray());
		
		currentMain.addChildNode(node);
		

	}

	private void parseEnd() {
		stack.pop();
		lexer.nextToken();
		currentToken = lexer.getToken();
	}

	private boolean isTokenOfType(TokenType type) {
		return lexer.getToken().getType().equals(type);
	}

	public DocumentNode getDocumentNode() {
		return mainNode;
	}
}
