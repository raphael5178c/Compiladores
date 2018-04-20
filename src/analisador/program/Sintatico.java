package analisador.program;

import java.util.List;
import java.util.Stack;

import analisador.constants.LMSConstantTokens;
import analisador.domain.Token;
import analisador.parsers.LMSParserTable;

public class Sintatico {
	

	public static Sintatico instance;
	private Stack<Integer> pilha;

	public Sintatico() {
		pilha = new Stack<Integer>();
	}
	
	public static Sintatico getInstance() {
		return (instance == null) ? new Sintatico() : instance;
	}

	public boolean analiseSintatica(List<Token> listToken) {
		if (listToken == null) {
			return false;
		}
		pilha.clear();
		pilha.push(LMSConstantTokens.TOKEN_DOLLAR);
		pilha.push(LMSParserTable.getInstance().START_SYMBOL);

		int currentIndex = 0;
		while (pilha.size() > 0) {
			Token tokenTopo = listToken.get(currentIndex++);
			System.out.println(tokenTopo.getNome());
			int pilhaCodigo = pilha.pop();
			if (isTerminalState(pilhaCodigo)) {
				//--
			} else if (isNotTerminalState(pilhaCodigo)) {
				int[] a = findProduction(tokenTopo.getCodigoParser(), pilhaCodigo);
				for (int i = a.length-1; i >= 0; i--) {
					pilha.push(a[i]);
					
				}
			}
		}

		return true;
	}
	
	private int[] findProduction(int codigoToken, int codigoPilha) {
		int p = LMSParserTable.getInstance().LMS_PARSER_TABLE[codigoPilha - LMSParserTable.getInstance().FIRST_NON_TERMINAL][codigoToken - 1];
		if (p >= 0) {
			return LMSParserTable.getInstance().LMS_PRODUCTIONS[p];
		}
		return null;
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
