package hr.fer.oprpp1.hw02.prob1.demo;

import hr.fer.oprpp1.hw02.prob1.*;

public class LexerMain {
	
	public static void main(String[] args) {
//		String ulaz = "Ovo je 123ica, ab57.\nKraj";
		String ulaz = "Janko 3! Jasmina 5; -24";
		
		Lexer lexer = new Lexer(ulaz);

		while(true) {
			
			lexer.nextToken();
			
			
			Token out = lexer.getToken();
			System.out.println(out);
			
			if(lexer.getToken().getType() == TokenType.EOF) {
				break;
			}
		}
	}

}
