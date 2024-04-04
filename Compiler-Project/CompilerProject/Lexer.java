package compiler_project;
import java.util.ArrayList;
import java.util.Formatter;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.util.StringTokenizer;

import compiler_project.Tokenizer.Token;
import compiler_project.Tokenizer.TokenType;
public class Lexer{

	// initializing the FSM objects for identifier, realNumber and integer
	IdentifierDFSM.StateHandler id = IdentifierDFSM.StateHandler.Q1;
	RealNumberDFSM.StateHandler realNum = RealNumberDFSM.StateHandler.Q1;
	IntegerDFSM.StateHandler integer = IntegerDFSM.StateHandler.Q1;
	Tokenizer tokenizer = new Tokenizer();
	FileReader fileReader = new FileReader();
	
	public ArrayList<Token> GetTokenTypes(String input){
		ArrayList<Token> tokenTypes = new ArrayList<Token>();
		ArrayList<String> tokenStrings = new ArrayList<String>();
		tokenStrings = tokenizer.Tokenize(input);
		for(String str : tokenStrings) {
			Token newToken = new Token(DetermineTokenType(str), str);
			tokenTypes.add(newToken);
		}
		return tokenTypes;
	}
	// this method utilizes the FSM's to determine the token type and gives it the
	// enum label. We don't account FSM's for keywords, ops, and seperators because
	// it is not needed. We simply can check for specific words or symbols
	// this will check through each if statement and only one will return true
	public TokenType DetermineTokenType(String string) {
		if (id.DFSMresult(string)) {
			return (string.equals("for") || string.equals("if") || string.equals("while") || string.equals("else")
					|| string.equals("endwhile") || string.equals("true") || string.equals("false")
					|| string.equals("int") || string.equals("float") || string.equals("string")
					|| string.equals("function") || string.equals("return") || string.equals("break")
					|| string.equals("bool") || string.equals("put") || string.equals("get") || string.equals("real")
					|| string.equals("Int") || string.equals("fi"))
							? TokenType.KEYWORD
							: TokenType.IDENTIFIER;
		} else if (realNum.DFSMresult(string)) {
			return TokenType.REAL;
		} else if (integer.DFSMresult(string)) {
			return TokenType.INTEGER;
		} else if (string.equals("<") || string.equals(">") || string.equals("<=") || string.equals(">=")
				|| string.equals("+") || string.equals("-") || string.equals("*") || string.equals("/")
				|| string.equals("%") || string.equals("=") || string.equals("==") || string.equals("+=")
				|| string.equals("!=") || string.equals("+-") || string.equals("=>")) {
			return TokenType.OPERATOR;
		} else if (string.equals("#") || string.equals("{") || string.equals("}") || string.equals(";")
				|| string.equals(",") || string.equals("(") || string.equals(")")) {
			return TokenType.SEPERATOR;
		} else {
			return TokenType.INVALID;
		}
	}
	
	public ArrayList<Token> CreateTokens(){
		ArrayList<Token> listofTokens = new ArrayList<Token>();
		listofTokens = GetTokenTypes(fileReader.FileReader());
		return listofTokens;
	}
	

/*
	public static void main(String[] args) {

		Lexer lexer = new Lexer();
		ArrayList<Token> listofTokens = new ArrayList<Token>();
		listofTokens = lexer.CreateTokens();
		
		System.out.format("%-10s %-5s", "Lexeme","Token" + "\n");
		for(Token token : listofTokens) {
			System.out.format("%-10s %-5s", token.getLexeme(), token.getToken() + "\n");
		}
	
	}
*/
}