package compiler_project;

import java.util.ArrayList;
import java.util.StringTokenizer;

import compiler_project.Tokenizer.Token;

// creating a token class that includes its token type and the lexeme
public class Tokenizer {
	public static enum TokenType {
		IDENTIFIER, REAL, INTEGER, KEYWORD, OPERATOR, SEPERATOR, INVALID;
	}

	public static class Token {
		private TokenType token;
		private String lexeme;

		public Token(TokenType token, String lexeme) {
			this.token = token;
			this.lexeme = lexeme;
		}

		public TokenType getToken() {
			return token;
		}

		public String getLexeme() {
			return lexeme;
		}
	}

	// this while loop iterates through each character in the string and finishes
	// when it reaches the length of the string
	public ArrayList<String> Tokenize(String input) {

		ArrayList<String> tokens = new ArrayList<String>();
		ArrayList<String> finalTokens = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(input, " \n\t");
		boolean insideComment = false;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			tokens.add(token);

		}
		for (String str : tokens) {

			StringTokenizer st2 = new StringTokenizer(str, "+-*/#{}();,[**]", true);

			while (st2.hasMoreTokens()) {
				String token2 = st2.nextToken();
				if (token2.equals("[")) {
					insideComment = true;
				} else if (token2.equals("]")) {
					insideComment = false;
				} else if (!insideComment) {
					finalTokens.add(token2);
				}
			}

		}

		return finalTokens;
	}
	
	public void printToken(Token token) {
		System.out.format("%-10s %-5s", "Token: " + token.getToken(), "Lexeme: " + token.getLexeme() + "\n");
	}
	
}
