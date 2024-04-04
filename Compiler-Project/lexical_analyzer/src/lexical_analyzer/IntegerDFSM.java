package lexical_analyzer;

public class IntegerDFSM {
		//test
		/*
		public static void main(String[] args) {
			String string = "6";
			StateHandler dfa = StateHandler.Q1;
			System.out.print(dfa.DFSMresult(string));
		}
		*/	
//for comments on what these methods do, refer to the 'IdentifierDFSM' class. The structure of this code is extremely similar as the only changes are to creating new constants.
		public enum StateHandler{
			Q1(false), Q2(true), Q3(false); 
			
			StateHandler digit;
			
			private final boolean isAccepted; 
			
		
			private StateHandler(boolean isAccepted){
				this.isAccepted = isAccepted;
			}
			
			//transition table
			static {
				Q1.digit = Q2; 
				Q2.digit = Q2; 
				Q3.digit = Q3;
			}
			
			
			private StateHandler Transition(char ch) {
				if(ch >= '0' && ch <= '9') {
					return this.digit;
				}else {
					return Q3;
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
