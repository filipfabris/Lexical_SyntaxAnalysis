package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class ScriptingLexer {

	private char[] data;
	private int currentIndex;
	private Token currentToken;
	private LexerState state;

	private static String TAG_START = "{$";
	private static String TAG_END = "$}";
	private static ArrayIndexedCollection operators;

	static {
		operators = new ArrayIndexedCollection();
		operators.add("*");
		operators.add("+");
		operators.add("-");
		operators.add("/");
	}

	public ScriptingLexer(String input) {
		this.data = input.toCharArray();
		this.currentIndex = 0;
		this.currentToken = null;
		this.state = LexerState.BASIC; // Default
	}

	public Token getToken() {
		return currentToken;
	}

	public void setState(LexerState state) {
		this.state = state;
	}

	public void nextToken() {

		if (currentToken != null && currentToken.getType() == TokenType.EOF) {
			throw new LexerException("No more tokens");
		}

		if (currentIndex >= data.length) {
			currentToken = new Token(TokenType.EOF, null);
			return;
		}

		if (state == LexerState.BASIC) {
			if (checkStartTag()) {
				currentToken = new Token(TokenType.TAG_START, null);
				currentIndex += 2;
				return;
			}

			this.tokenText();
			return;

		} else if (state == LexerState.TAG) {
			if (checkEndTag()) {
				currentToken = new Token(TokenType.TAG_END, null);
				currentIndex += 2;
				return;
			}

			this.tokenTag();
			return;
		}

		throw new RuntimeException("Unexpected State");
	}

	private void tokenText() {

		StringBuilder sb = new StringBuilder();
		
		
		boolean special = false;
		while (currentIndex < data.length) {

			if (String.valueOf(data[currentIndex]).matches("[\\\\]") && special == false) {
				special = true;
				currentIndex++;

				if (currentIndex < data.length && (data[currentIndex] == '{')
						|| String.valueOf(data[currentIndex]).matches("[\\\\]")) {
					continue; // Je li dopusten escape
				} else {
					throw new LexerException("InvalidEscapeEnding");
				}

			} else if (special == true) { // Specijalni karakteri
				sb.append(data[currentIndex]);
				special = false;
				currentIndex++;
			}else if (checkStartTag() == true) {
				break; // Pronadjen drugi navodnil
			}else {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
		}

		currentToken = new Token(TokenType.BASIC_TEXT, sb.toString());
		return;

	}

	private void tokenTag() {

		// Preskoci bijeline
		skipBlanks();

		String check = "" + data[currentIndex] + data[currentIndex + 1] + data[currentIndex + 2];

		// Je li FOR
		if (check.equalsIgnoreCase("FOR")) {
			currentToken = new Token(TokenType.FOR, null);
			currentIndex += 3;
			return;
		}

		// Je li END
		if (check.equalsIgnoreCase("END")) {
			currentToken = new Token(TokenType.END, null);
			currentIndex += 3;
			return;
		}

		// Je li jednako
		check = String.valueOf(data[currentIndex]);
		if (check.equals("=")) {
			currentToken = new Token(TokenType.ECHO, null);
			currentIndex += 1;
			return;
		}

		// Je li decimal broj
		if (Character.isDigit(data[currentIndex])
				|| (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1]))) {
			StringBuilder sb = new StringBuilder();

			if (data[currentIndex] == '-') {
				sb.append(data[currentIndex]);
				currentIndex++;
			}

			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}

			// Ako je decimalni
			if (data[currentIndex] == '.') {
				sb.append(data[currentIndex]);
				currentIndex++;

				// I jos uzmi drugi dio

				while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
					sb.append(data[currentIndex]);
					currentIndex++;
				}
				Double value = Double.parseDouble(sb.toString());
				currentToken = new Token(TokenType.DOUBLE, value);
				return;
			}

			Integer value;
			try {
				value = Integer.parseInt(sb.toString());
			} catch (NumberFormatException e) {
				throw new LexerException("Long number is out of range");
			}

			currentToken = new Token(TokenType.INTEGER, value);
			return;

		}

		// Je li operator
		if (operators.contains(String.valueOf(data[currentIndex]))) {
			Character value = Character.valueOf(data[currentIndex]);
			currentIndex++;
			currentToken = new Token(TokenType.OPERATOR, value);
			return;
		}

		// Moze biti varijabla
		if (Character.isLetter(data[currentIndex])) {
			StringBuilder sb = new StringBuilder();

			// Prvo je moralo biti slovo zbog ifa prije tako da je sigurno varijabla
			while (currentIndex < data.length) {
				if (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
						|| data[currentIndex] == '-') {
					sb.append(data[currentIndex]);
					currentIndex++;
				}
				break;
			}

			currentToken = new Token(TokenType.VARIABLE, sb.toString());
			return;
		}

		// String vrijednost
		if (data[currentIndex] == '"') {
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex]);
			currentIndex++;
			boolean special = false;

			while (currentIndex < data.length) {

				if (String.valueOf(data[currentIndex]).matches("[\\\\]") && special == false) {
					special = true;
					currentIndex++;

					if (currentIndex < data.length && (data[currentIndex] == '"')
							|| String.valueOf(data[currentIndex]).matches("[\\\\]")) {
						continue; // Je li dopusten escape
					} else {
						throw new LexerException("InvalidEscapeEnding");
					}

				} else if (special == true) { // Specijalni karakteri
					sb.append(data[currentIndex]);
					special = false;
					currentIndex++;
				} else if (data[currentIndex] == '"') {
					sb.append(data[currentIndex]);
					currentIndex++;
					break; // Pronadjen drugi navodnil
				}else {
					sb.append(data[currentIndex]);
					currentIndex++;
				}
			}

			currentToken = new Token(TokenType.STRING, sb.toString());
			return;

		}

		// Moze biti funkcija
		if (data[currentIndex] == '@') {
			currentIndex++; // preskoci @
			StringBuilder sb = new StringBuilder();
			while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			currentToken = new Token(TokenType.FUNCTION, sb.toString());
			return;
		}

		throw new RuntimeException("Lexical error");
	}

	private void skipBlanks() {
		while (currentIndex < data.length) {
			if (data[currentIndex] == '\r' || data[currentIndex] == '\n' || data[currentIndex] == '\t'
					|| data[currentIndex] == ' ') {
				this.currentIndex++;
			} else {
				break;
			}
		}
	}

	private boolean checkStartTag() {

		if (currentIndex + 1 < data.length) {

			String temp = "" + data[currentIndex] + data[currentIndex + 1];

			return temp.equals(TAG_START);
		}

		return false;
//		throw new RuntimeException("checkStartTag out of bounds");

	}

	private boolean checkEndTag() {
		skipBlanks();
		if (currentIndex + 1 < data.length) {
			String temp = "" + data[currentIndex] + data[currentIndex + 1];

			return temp.equals(TAG_END);

		}

		return false;
//		throw new RuntimeException("checkEndTag out of bounds");

	}

	private boolean isBlank() {
		if (data[currentIndex] == '\r' || data[currentIndex] == '\n' || data[currentIndex] == '\t'
				|| data[currentIndex] == ' ') {
			return true;
		}

		return false;
	}

	private boolean allowedString() {
		if (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| String.valueOf(data[currentIndex]).matches("[!.,;?#-]")) {
			return true;
		}
		return false;
	}

}
