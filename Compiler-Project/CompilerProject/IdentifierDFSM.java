package compiler_project;

public class IdentifierDFSM {
	public enum StateHandler {
		// We used enums because enums allow for more readable/cleaner code and best
		// when you need a list of predefined constants.
		Q1(false), Q2(true), Q3(true), Q4(true), Q5(true), Q6(false); // Initializing states and whether they are
																		// accepting or not

		StateHandler letter;
		StateHandler digit;
		StateHandler underScore;

		private final boolean isAccepted; // this variable is constant as we declare whether the states are accepting or
											// not before hand

		// constructor for the isAccepted boolean.
		private StateHandler(boolean isAccepted) {
			this.isAccepted = isAccepted;
		}
		
		//transition table
		static {
			Q1.letter = Q2; Q1.digit = Q6; Q1.underScore = Q6;
			Q2.letter = Q3; Q2.digit = Q4; Q2.underScore = Q5;
			Q3.letter = Q3; Q3.digit = Q4; Q3.underScore = Q5;
			Q4.letter = Q3; Q4.digit = Q4; Q4.underScore = Q5;
			Q5.letter = Q3; Q5.digit = Q4; Q5.underScore = Q5;
			Q6.letter = Q6; Q6.digit = Q6; Q6.underScore = Q6;
		}
		
		//determines whether character is a letter, digit, or underscore. Returns an enum and refers to transition table to indicate which specific enum to return.
		//if no character meets conditions, then return state 'Q6' which according to our predefined enums, is not accepting.
		private StateHandler Transition(char ch) {
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
				return this.letter;
			} else if (ch >= '0' && ch <= '9') {
				return this.digit;
			} else if (ch == '_') {
				return this.underScore;
			} else {
				return Q6;
			}
		}

		//takes a string and iterates through each character. Each character will be put through the Transition method which will each change the current state to a new one according to the transition table.
		//By the time it reaches its last character, the last state it changes to will be used to determine whether this string is accepting or not. returns true or false.
		public boolean DFSMresult(String string) {
			StateHandler state = StateHandler.Q1;
			for (int i = 0; i < string.length(); i++) {
				state = state.Transition(string.charAt(i));
			}
			return state.isAccepted;
		}

	}

}
