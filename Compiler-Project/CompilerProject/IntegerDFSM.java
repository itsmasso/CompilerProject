package compiler_project;

//for more detailed comments, look at IdentifierDFSM.java as the code is very similar.
public class IntegerDFSM {
	public enum StateHandler {
		Q1(false), Q2(true), Q3(false); // Initializing states and whether they are accepting or not

		StateHandler digit;

		private final boolean isAccepted; // this variable is constant as we declare whether the states are accepting or
											// not before hand

		// constructor for the isAccepted boolean.
		private StateHandler(boolean isAccepted) {
			this.isAccepted = isAccepted;
		}

		// transition table
		static {
			Q1.digit = Q2;
			Q2.digit = Q2;
			Q3.digit = Q3;
		}

		// determines whether character is a digit or not. Transitions to the current
		// state's next state.
		private StateHandler Transition(char ch) {
			if (ch >= '0' && ch <= '9') {
				return this.digit;
			} else {
				return Q3;
			}
		}

		// method that iterates through the transition table
		public boolean DFSMresult(String string) {
			StateHandler state = StateHandler.Q1;
			for (int i = 0; i < string.length(); i++) {
				state = state.Transition(string.charAt(i));
			}
			return state.isAccepted;
		}
	}
}
