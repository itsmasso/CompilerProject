package lexical_analyzer;

import lexical_analyzer.IdentifierDFSM.StateHandler;

public class RealNumberDFSM {
	
	//test
	/*
	public static void main(String[] args) {
		String string = "6.5";
		StateHandler dfa = StateHandler.Q1;
		System.out.print(dfa.DFSMresult(string));
	}
	*/	
	
	//for comments on what these methods do, refer to the 'IdentifierDFSM' class. The structure of this code is extremely similar as the only changes are to creating new constants.
	public enum StateHandler{
		Q1(false), Q2(false), Q3(false), Q4(true), Q5(false); 
		
		StateHandler digit;
		StateHandler period;
		
		private final boolean isAccepted;
		
		
		private StateHandler(boolean isAccepted){
			this.isAccepted = isAccepted;
		}
		
		//transition table
		static {
			Q1.digit = Q2; Q1.period = Q5;
			Q2.digit = Q2; Q2.period = Q3;
			Q3.digit = Q4; Q3.period = Q5;
			Q4.digit = Q4; Q4.period = Q5;
			Q5.digit = Q5; Q5.period = Q5;
		}
		
		
		private StateHandler Transition(char ch) {
			if(ch >= '0' && ch <= '9') {
				return this.digit;
			}else if(ch == '.') {
				return this.period;
			}else {
				return Q5;
			}
		}
		
		
		public boolean DFSMresult(String string) {
			StateHandler state = StateHandler.Q1;
			for(int i = 0; i < string.length(); i++) {
				state = state.Transition(string.charAt(i));
			}
			return state.isAccepted;
		}
	}
}
