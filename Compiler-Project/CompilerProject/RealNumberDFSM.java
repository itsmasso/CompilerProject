package compiler_project;

import compiler_project.IdentifierDFSM.StateHandler;

//for more detailed comments, look at IdentifierDFSM.java as the code is very similar.
public class RealNumberDFSM {
	public enum StateHandler {
		Q1(false), Q2(false), Q3(false), Q4(true), Q5(false); // Initializing states and whether they are accepting or
																// not

		StateHandler digit;
		StateHandler period;

		private final boolean isAccepted; // this variable is constant as we declare whether the states are accepting or
											// not before hand

		// constructor for the isAccepted boolean.
		private StateHandler(boolean isAccepted) {
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

		// determines whether character is a digit or period. Transitions to the current
		// state's next state.
		private StateHandler Transition(char ch) {
			if (ch >= '0' && ch <= '9') {
				return this.digit;
			} else if (ch == '.') {
				return this.period;
			} else {
				return Q5;
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
