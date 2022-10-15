package hr.fer.oprpp1.hw02.prob1;

public class Lexer {

	private char[] data; // ulazni tekst
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState state;

	// konstruktor prima ulazni tekst koji se tokenizira
	public Lexer(String text) {

		if (text == null) {
			throw new NullPointerException();
		}

		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.token = null;
		this.state = LexerState.BASIC;
	}

	public void setState(LexerState newstate) {
		if (newstate == null) {
			throw new NullPointerException("State can not be null");
		}
		this.state = newstate;
	}

	// generira i vraća sljedeći token
	// baca LexerException ako dođe do pogreške
	public Token nextToken() {

		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("No more tokens");
		}

		// Skip blanks
		this.skipBlanks();

		// EOF
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (state.equals(LexerState.BASIC)) {

			// Symbol
			if (String.valueOf(data[currentIndex]).matches("[!.,;?#-]")) {
				Character value = Character.valueOf(data[currentIndex]);
				currentIndex++;
				token = new Token(TokenType.SYMBOL, value);

				return token;
			}

			// Number
			if (Character.isDigit(data[currentIndex])) {
				StringBuilder sb = new StringBuilder();

				while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
					sb.append(data[currentIndex]);
					currentIndex++;
				}

				Long value;
				try {
					value = Long.parseLong(sb.toString());
				} catch (NumberFormatException e) {
					throw new LexerException("Long number is out of range");
				}

				token = new Token(TokenType.NUMBER, value);
				return token;

			}

			// Word
			if (Character.isLetter(data[currentIndex]) || String.valueOf(data[currentIndex]).matches("[\\\\]")) {
				StringBuilder sb = new StringBuilder();
				boolean special = false;

				while (currentIndex < data.length) {

					if (String.valueOf(data[currentIndex]).matches("[\\\\]") && special == false) {
						special = true;
						currentIndex++;

						if (currentIndex < data.length && (Character.isDigit(data[currentIndex])
								|| String.valueOf(data[currentIndex]).matches("[\\\\]"))) {
							continue; // There is more space and next character that is digit or /
						} else {
							throw new LexerException("InvalidEscapeEnding");
						}

					} else if (special == true) {
						sb.append(data[currentIndex]);
						special = false;
						currentIndex++;
					} else if (Character.isLetter(data[currentIndex])) {
						sb.append(data[currentIndex]);
						currentIndex++;
					} else {
						break;
					}
				}

				token = new Token(TokenType.WORD, sb.toString());
				return token;

			}
		}else if(state.equals(LexerState.EXTENDED)) {
				StringBuilder sb = new StringBuilder();
				
				if(data[currentIndex] == '#') {
					
					Character value = Character.valueOf(data[currentIndex]);
					currentIndex++;
					
					token = new Token(TokenType.SYMBOL, value);
					return token;
				}

				while (currentIndex < data.length && data[currentIndex] != '#' && Character.isWhitespace(data[currentIndex]) == false) {
					sb.append(data[currentIndex]);
					currentIndex++;
				}

				token = new Token(TokenType.WORD, sb.toString());
				return token;

			}

		// Unknown character type
		throw new LexerException("Error during lexical analysic");
	}

	// vraća zadnji generirani token; može se pozivati
	// više puta; ne pokreće generiranje sljedećeg tokena
	public Token getToken() {
		return token;
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
	

}
