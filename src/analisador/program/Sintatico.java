package analisador.program;

import java.util.ArrayList;

import analisador.domain.Token;
import analisador.parsers.LMSParserTable;

public class Sintatico  {
	
	public static Sintatico instance;
	
	public static Sintatico getInstance() {
		return (instance == null) ? new Sintatico() : instance;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean analiseSintatica(ArrayList<Token> listToken) {
		if(listToken == null) return false;
		ArrayList<Token> pilhaToken = (ArrayList) listToken.clone();
		
		int currentIndex = 0;
		do {
			Token tokenTopo = pilhaToken.get(currentIndex);
			if(isTerminalState(tokenTopo.getCodigoParser()) || tokenTopo.getNome().equalsIgnoreCase("$")) {
				
			} else {
				
			}
			currentIndex++;
			
		} while (pilhaToken == null || pilhaToken.size() < 1);
		
		return true;
	}
	
	
	/* ########################## MÉTODOS ULTILITÁRIOS ########################## */
	
	public boolean isTerminalState(int currentState) {
		return (LMSParserTable.getInstance().FIRST_NON_TERMINAL > currentState);
	}
	
	public boolean isNotTerminalState(int currentState) {
		return (!isTerminalState(currentState) && LMSParserTable.getInstance().FIRST_SEMANTIC_ACTION > currentState);
	}
	
	public boolean isSemanticState(int currentState) {
		return (!isTerminalState(currentState) & !isNotTerminalState(currentState));
	}
	

}
