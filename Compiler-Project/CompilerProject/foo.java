public class foo {
    public void symbolExists(String symbolName){
		for (int i = 0; i < symbolTable.size(); i++){
			if(symbolName.equals(symbolTable.get(i).getName)){
				System.out.println("Error: redeclaration of variable " + symbolName);
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
        /*******************************************/
		gen_instr(out);
        /********************************************/
	}

    public void scanIDs() {
		productionRules += "<IDs> -> <Identifier> <IDs Prime>" + "\n";
		gen_instr(IN);
		scanidentifier();
		scanIDsPrime();
	}

	public void scanIDsPrime() {
		productionRules += "<IDs> -> , <IDS> | <Empty>" + "\n";
		if (ValidIndex() && listOfTokens.get(counter).getLexeme().equals(",")) {
			lexer(listOfTokens.get(counter));
			scanIDs();
		}else{
			productionRules += "<IDs Prime> -> <Empty>" + "\n";
		}
	}

	public void scanidentifier() {
		if(ValidIndex() && listOfTokens.get(counter).getToken() != TokenType.IDENTIFIER){
			System.out.println("syntax error: identifier expected.");
			System.exit(0);
		}
		else{
			gen_instr("POPM", get_address (listOfTokens.get(counter).getToken()));
			lexer(listOfTokens.get(counter));
		}
	}
}
