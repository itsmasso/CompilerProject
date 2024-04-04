package lexical_analyzer;

import java.util.ArrayList;
import lexical_analyzer.Tokenizer.Token;
import lexical_analyzer.Tokenizer.TokenType;

class SyntaxAnalyzer {

	Tokenizer tokenizer = new Tokenizer();
	
	private int counter = 0;
	private ArrayList<Token> listOfTokens = new ArrayList<Token>();
	public boolean printRules = true;
	private String productionRules = "";
	
	public SyntaxAnalyzer(ArrayList<Token> listOfTokens) {
		this.listOfTokens = listOfTokens;

	}

	public void lexer(Token t) {
		tokenizer.printToken(t); //prints token out
		printRules();
		if(counter < listOfTokens.size()) {
			counter++;
		}
	}
	
	private boolean ValidIndex() {
		if(counter < listOfTokens.size()) {
			return true;
		}
		return false;
	}
	
	private void printRules() {
		System.out.println(productionRules);
		productionRules = "";
	}
	
	public void Rat23S() {
		productionRules += "<Rat23S> -> <Opt Function Definitions> # <Opt Declaration List> # <Statement List>" + "\n";
		optFunctionDefinitions();
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("#"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: # expected");
			System.exit(0);
		}
		optDeclarationList();
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("#"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: # expected");
			System.exit(0);
		}
		statementList();
		
		if (ValidIndex() && counter != listOfTokens.size()) { // if !end of file
			System.out.println("syntax error");
			System.exit(0);
		}
		
	}

	public void optFunctionDefinitions() {
		productionRules += "<Opt Function Definitions> -> <Function Definitions> | <Empty>" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("function"))
			functionDefinitions();
		else
			productionRules += "<Opt Function Definitions> -> <Empty>" + "\n";
	}
	
	public void functionDefinitions() {
		productionRules += "<Function Definitions> -> <Function> <Function Definitions Prime>" + "\n";
		function();
		functionDefinitionsPrime();

	}

	public void functionDefinitionsPrime() {
		productionRules += "<Function Definitions Prime> -> <Function Definitions> | <Empty>" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("function"))
			functionDefinitions();
		else
			productionRules += "<Function Definitions Prime> -> <Empty>" + "\n";
	}
	
	public void function() {
		productionRules += "<Function> -> function <Identifier> ( <Opt Parameter List> ) <Opt Declaration List> <Body>" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("function"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: function keyword expected.");
			System.exit(0);
		}
		identifier();
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("("))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: ( expected.");
			System.exit(0);
		}
	    optParameterList();
	    if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals(")"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: ) expected.");
			System.exit(0);
		}
	    optDeclarationList();
	    body();
	}
	
	public void optParameterList() {
		productionRules += "<Opt Parameter List> -> <Parameter List> | <Empty>" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getToken() == TokenType.IDENTIFIER)
			parameterList();
		else
			productionRules += "<Opt Parameter List> -> <Empty>" + "\n";
	}
	
	public void parameterList() {
		productionRules += "<Parameter List> -> <Parameter> <Parameter List Prime>" + "\n";
		parameter();
		parameterListPrime();
	}

	public void parameterListPrime() {
		productionRules += "<Parameter List Prime> -> , <Parameter List> | <Empty>" + "\n";
		if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals(",")) {
			lexer(listOfTokens.get(counter));
			parameterList();
		}else {
			productionRules += "<Parameter List Prime> -> <Empty>" + "\n";
		}
	}

	public void parameter() {
		productionRules += "<Parameter> ::= <IDs > <Qualifier>" + "\n";
		IDs();
	    Qualifier();
	}
	
	public void Qualifier() {
		productionRules += "<Qualifier> -> int | bool | real" + "\n";
		if(ValidIndex() && (listOfTokens.get(counter).getLexeme().equals("int") || listOfTokens.get(counter).getLexeme().equals("bool") || listOfTokens.get(counter).getLexeme().equals("real") ||
				listOfTokens.get(counter).getLexeme().equals("Int")))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: int, bool, or real expected.");
			System.exit(0);
		}
	}
	
	public void body() {
		productionRules += "<Body> -> { < Statement List> }" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("{"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: { expected.");
			System.exit(0);
		}
		statementList();
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("}"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: } expected.");
			System.exit(0);
		}
	}
	
	public void optDeclarationList() {
		productionRules += "<Opt Declaration List> -> <Declaration List> | <Empty>" + "\n";
		if(ValidIndex() && (listOfTokens.get(counter).getLexeme().equals("int") || listOfTokens.get(counter).getLexeme().equals("bool") || 
				listOfTokens.get(counter).getLexeme().equals("real") || listOfTokens.get(counter).getLexeme().equals("Int")))
			declarationList();
		else
			productionRules += "<Opt Declaration List> -> <Empty>" + "\n";
	}

	public void declarationList() {
		productionRules += "<Declaration List> -> <Declaration> ; <Declaration List Prime>" + "\n";
		declaration();
		if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals(";"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.print("syntax error: ; expected");
			System.exit(0);
		}
		declarationListPrime();
	}

	public void declarationListPrime() {
		productionRules += "<Declaration List Prime> -> <Declaration List> | <Empty>" + "\n";
		if(ValidIndex() && (listOfTokens.get(counter).getLexeme().equals("int") || listOfTokens.get(counter).getLexeme().equals("bool") || 
				listOfTokens.get(counter).getLexeme().equals("real") || listOfTokens.get(counter).getLexeme().equals("Int")))
			declarationList();
		else
			productionRules += "<Declaration List Prime> -> <Empty>" + "\n";
	}

	public void declaration() {
		productionRules += "<Declaration> -> <Qualifier > <IDs>" + "\n";
		Qualifier();
	    IDs();
	}
	
	public void IDs() {
		productionRules += "<IDs> -> <Identifier> <IDs Prime>" + "\n";
		identifier();
		IDsPrime();
	}

	public void IDsPrime() {
		productionRules += "<IDs> -> , <IDS> | <Empty>" + "\n";
		if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals(",")) {
			lexer(listOfTokens.get(counter));
			IDs();
		}else{
			productionRules += "<IDs Prime> -> <Empty>" + "\n";
		}
	}
	
	public void statementList() {
		productionRules += "<Statement List> -> <Statement> <Statement List Prime>" + "\n";
		statement();
		statementListPrime();
	}

	public void statementListPrime() {
		productionRules += "<Statement List Prime> -> <Statement List> | <Empty>" + "\n";
		if(ValidIndex() && (listOfTokens.get(counter).getLexeme().equals("{") || listOfTokens.get(counter).getToken() == TokenType.IDENTIFIER || 
				listOfTokens.get(counter).getLexeme().equals("if") || listOfTokens.get(counter).getLexeme().equals("return") ||
				listOfTokens.get(counter).getLexeme().equals("put") || listOfTokens.get(counter).getLexeme().equals("get") || 
				listOfTokens.get(counter).getLexeme().equals("while"))) 
			statementList();
		else
			productionRules += "<Statement List Prime> -> <Empty>" + "\n";
	}
	
	public void statement() {
		productionRules += "<Statement> -> <Compound> | <Assign> | <If> | <Return> | <Print> | <Scan> | <While>" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("{"))
			compound();
		else if(ValidIndex() && listOfTokens.get(counter).getToken() == TokenType.IDENTIFIER)
			assign();
		else if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("if"))
			If();
		else if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("return"))
			Return();
		else if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("put"))
			Print();
		else if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("get"))
			Scan();
		else if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("while"))
			While();
		else{
			System.out.println("syntax error: No valid statement");
			System.exit(0);
		}
	}

	public void compound() {
		productionRules += "<Compound> -> { <Statement List> }" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("{"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: { expected.");
			System.exit(0);
		}
		statementList();
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("}"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: } expected.");
			System.exit(0);
		}
	}
	
	public void assign() {
		productionRules += "<Assign> -> <Identifier> = <Expression> ;" + "\n";
		identifier();
	     if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("=")){
			Token save = listOfTokens.get(counter);
	       lexer(listOfTokens.get(counter));
		 }
	     else {
	    	System.out.println("syntax error: = expected.");
	    	System.exit(0);
	     }
	     expression();
		 gen_instr (POPM, get_address(save) );
	     if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals(";"))
				lexer(listOfTokens.get(counter));
			else {
				System.out.print("syntax error: ; expected");
				System.exit(0);
			}
	}

	public void If() {
		productionRules += "<If> -> if (<condition>) <Statement> <If Prime>" + "\n";
		if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals("if"))
			lexer(listOfTokens.get(counter));
		else {
	    	System.out.println("syntax error: if expected.");
	    	System.exit(0);
		}
		if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals("("))
			lexer(listOfTokens.get(counter));
		else {
	    	System.out.println("syntax error: ( expected.");
	    	System.exit(0);
		}
		condition();
		if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals(")"))
			lexer(listOfTokens.get(counter));
		else {
	    	System.out.println("syntax error: ) expected.");
	    	System.exit(0);
		}
		statement();
		back_patch(instr_address);
		IfPrime();
	}

	public void IfPrime() {
		productionRules += "<If Prime> -> fi | else <statement> fi" + "\n";
		if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals("fi")) {
			lexer(listOfTokens.get(counter));
		} else if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals("else")) {
			lexer(listOfTokens.get(counter));
			statement();
			if (ValidIndex() && !listOfTokens.get(counter).getLexeme().equals("fi")) {
				System.out.println("syntax error: fi expected.");
				System.exit(0);
			} else {
				lexer(listOfTokens.get(counter));
			}
		}
	}


	public void Return() {
		productionRules += "<Return> -> return <Return Prime>" + "\n";
		if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals("return"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: return expected.");
			System.exit(0);
		}
		ReturnPrime();
	}
	public void ReturnPrime() {
		productionRules += "<Return Prime> -> ; | <Expression> ;" + "\n";
		if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals(";")) {
			lexer(listOfTokens.get(counter));
		}else {
			expression();
			if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals(";"))
				lexer(listOfTokens.get(counter));
			else {
				System.out.println("syntax error: ; expected.");
				System.exit(0);
			}
		}
		
	}
	public void Print() {
		productionRules += "<Print> -> put ( <Expression>);" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("put"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: put keyword expected.");
			System.exit(0);
		}
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("("))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: ( expected.");
			System.exit(0);
		}
		expression();
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals(")"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: ) expected.");
			System.exit(0);
		}
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals(";"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: ; expected.");
			System.exit(0);
		}
	}

	public void Scan() {
		productionRules += "<Scan> -> get ( <IDs> );" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("get"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: get keyword expected.");
			System.exit(0);
		}
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("("))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: ( expected.");
			System.exit(0);
		}
		IDs();
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals(")"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: ) expected.");
			System.exit(0);
		}
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals(";"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: ; expected.");
			System.exit(0);
		}
	}

	public void While() {
		productionRules += "<While> -> while ( <Condition> ) <Statement> endwhile" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("while")){
			addr = instr_address;
			gen_instr(“LABEL”, nil);
			lexer(listOfTokens.get(counter));
		}
		else {
			System.out.println("syntax error: while keyword expected.");
			System.exit(0);
		}
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("("))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: ( expected.");
			System.exit(0);
		}
		condition();
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals(")")){
			gen_instr(JMP, addr);
			back_patch (instr_address);
			lexer(listOfTokens.get(counter));
		}
		else {
			System.out.println("syntax error: ) expected.");
			System.exit(0);
		}
		statement();
		if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("endwhile"))
			lexer(listOfTokens.get(counter));
		else {
			System.out.println("syntax error: endwhile keyword expected.");
			System.exit(0);
		}
	}

	public void condition() {
		productionRules += "<Condition> -> <Expression> <Relop> <Expression>" + "\n";
		expression();
		relop();
		expression();
	}

	public void expression() {
		productionRules += "<Expression> -> <Term> <Expression Prime>" + "\n";
		term();
		expressionPrime();
	}
	public void expressionPrime() {
		productionRules += "<Expression Prime> -> + <Term> <Expression Prime> | - <Term> <Expression Prime> | <Empty>" + "\n";
		if(ValidIndex() && (listOfTokens.get(counter).getLexeme().equals("+") || listOfTokens.get(counter).getLexeme().equals("-"))) {
			lexer(listOfTokens.get(counter));
			term();
			gen_instr (ADD, nil);
			expressionPrime();
		}else {
			productionRules += "<Expression Prime> -> <Empty>" + "\n";
		}
	}

	public void relop() {
		productionRules += "<Relop> -> == | != | > | < | <= | =>" + "\n";
		if(ValidIndex() && (listOfTokens.get(counter).getLexeme().equals("=="))) {
			gen_instr (EQU, nil);
			push_jumpstack (instr_address);
			gen_instr (JMPZ, nil);
			lexer(listOfTokens.get(counter));
		}
		else if (ValidIndex() &&listOfTokens.get(counter).getLexeme().equals("!=")){
			gen_instr (NEQ, nil);
			push_jumpstack (instr_address);
			gen_instr (JMPZ, nil);
			lexer(listOfTokens.get(counter));
		}
		else if (ValidIndex() &&listOfTokens.get(counter).getLexeme().equals(">")){
			gen_instr (GRT, nil);
			push_jumpstack (instr_address);
			gen_instr (JMPZ, nil);
			lexer(listOfTokens.get(counter));
		}
		else if (ValidIndex() &&listOfTokens.get(counter).getLexeme().equals("<")){
			gen_instr (LES, nil);
			push_jumpstack (instr_address);
			gen_instr (JMPZ, nil);
			lexer(listOfTokens.get(counter));
		}
		else if (ValidIndex() &&listOfTokens.get(counter).getLexeme().equals("<=")){
			gen_instr (LEQ, nil);
			push_jumpstack (instr_address);
			gen_instr (JMPZ, nil);
			lexer(listOfTokens.get(counter));
		}
		else if (ValidIndex() &&listOfTokens.get(counter).getLexeme().equals("=>")){
			gen_instr (GEQ, nil);
			push_jumpstack (instr_address);
			gen_instr (JMPZ, nil);
			lexer(listOfTokens.get(counter));
		}
		
		else {
			System.out.println("syntax error: ==, !=, >, <, <=, or => expected.");
			System.exit(0);
		}
	}

	public void term() {
		productionRules += "<Term> -> <Factor> <Term Prime>" + "\n";
		factor();
		termPrime();
	}
	public void termPrime() {
		productionRules += "<Term Prime> -> * <Factor> <Term Prime> | / <Factor> <Term Prime> | <Empty>" + "\n";
		if(ValidIndex() && (listOfTokens.get(counter).getLexeme().equals("*") || listOfTokens.get(counter).getLexeme().equals("/"))) {
			lexer(listOfTokens.get(counter));
			factor();
			gen_instr(MUL, nil);
			termPrime();
		}else {
			productionRules += "<Term Prime> -> <Empty>" + "\n";
		}
	}
	public void primary() {
		productionRules += "<Primary> -> <Identifier> | <Integer> | <Identifier> ( <IDs> ) | ( <Expression> ) | <Real> | true | false" + "\n";
		if(ValidIndex() && listOfTokens.get(counter).getToken() == TokenType.IDENTIFIER) {
			identifier();
			if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("(")) {
				lexer(listOfTokens.get(counter));
				IDs();
				if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals(")"))
					lexer(listOfTokens.get(counter));
				else {
					System.out.println("syntax error: ) expected.");
					System.exit(0);
				}
			}
		}else if(ValidIndex() && listOfTokens.get(counter).getToken() == TokenType.INTEGER) {
			integer();
		}else if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals("(")) {
			lexer(listOfTokens.get(counter));
			expression();
			if(ValidIndex() && listOfTokens.get(counter).getLexeme().equals(")"))
				lexer(listOfTokens.get(counter));
			else
				System.out.println("syntax error: ) expected.");
		}else if(ValidIndex() && listOfTokens.get(counter).getToken() == TokenType.REAL) {
			real();
		}else if(ValidIndex() && (listOfTokens.get(counter).getLexeme().equals("true") || listOfTokens.get(counter).getLexeme().equals("false"))) {
			lexer(listOfTokens.get(counter));
		}else {
			System.out.println("syntax error: true or false expected.");
			System.exit(0);
		}
	}

	public void factor() {
		productionRules += "<Factor> -> - <Primary> | <Primary>" + "\n";
		if(ValidIndex() && !listOfTokens.get(counter).getLexeme().equals("-")) {
			primary();
		}else {
			lexer(listOfTokens.get(counter));
			primary();
		}
	}

	public void identifier() {
		if(ValidIndex() && listOfTokens.get(counter).getToken() != TokenType.IDENTIFIER){
			System.out.println("syntax error: identifier expected.");
			System.exit(0);
		}
		else{
			gen_instr(PUSHM, get_address (token));
			lexer(listOfTokens.get(counter));
		}
	}
	
	public void integer() {
		if(ValidIndex() && listOfTokens.get(counter).getToken() != TokenType.INTEGER){
			System.out.println("syntax error: integer expected.");
			System.exit(0);
		}
		else
			lexer(listOfTokens.get(counter));
	}
	
	public void real() {
		if(ValidIndex() && listOfTokens.get(counter).getToken() != TokenType.REAL){
			System.out.println("syntax error: real expected.");
			System.exit(0);
		}
		else
			lexer(listOfTokens.get(counter));
	}
	
	public static void main(String[] args) {
		Lexer lexer = new Lexer();
		ArrayList<Token> tokensList = lexer.CreateTokens();
		SyntaxAnalyzer SA = new SyntaxAnalyzer(tokensList);
		SA.Rat23S();
		
	}

}
