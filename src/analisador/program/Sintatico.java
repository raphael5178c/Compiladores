package analisador.program;

import java.util.List;
import java.util.Stack;

import analisador.constants.LMSConstantTokens;
import analisador.domain.Token;
import analisador.parsers.LMSParserTable;
import analisador.util.ExceptionUtil;

public class Sintatico {
	

	public static Sintatico instance;
	private Stack<Integer> pilha;
	private static Token tokenAtual;

	public Sintatico() {
		pilha = new Stack<Integer>();
	}
	
	public static Sintatico getInstance() {
		return (instance == null) ? new Sintatico() : instance;
	}

	public boolean analiseSintatica(List<Token> listToken) throws Exception {
		if (listToken == null) {
			return false;
		}
		tokenAtual = null;
		pilha.clear();
		pilha.push(LMSConstantTokens.TOKEN_DOLLAR);
		pilha.push(LMSParserTable.getInstance().START_SYMBOL);

		int currentIndex = 0;
		while (pilha.size() > 0) {
			tokenAtual = (tokenAtual == null) ? listToken.get(currentIndex++) : tokenAtual;
			System.out.println(tokenAtual.getNome());
			int pilhaCodigo = pilha.pop();
			if(pilhaCodigo == LMSConstantTokens.TOKEN_EPSILON) {
				continue;
			}
			if (isTerminalState(pilhaCodigo)) {
				if(pilhaCodigo == tokenAtual.getCodigoParser() && !pilha.isEmpty()) {
					try {
						tokenAtual = listToken.get(currentIndex++);
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else {
					throw new Exception(ExceptionUtil.getSyntaticErrorException(tokenAtual));
				}
			} else if (isNotTerminalState(pilhaCodigo)) {
				int[] productionsList = findProduction(tokenAtual.getCodigoParser(), pilhaCodigo);
				if(productionsList != null) {
					for (int i = productionsList.length-1; i >= 0; i--) {
						pilha.push(productionsList[i]);
					}
				} else {
					throw new Exception(ExceptionUtil.getSyntaticErrorException(tokenAtual));
				}
			}
		}

		return true;
	}
	
	private int[] findProduction(int codigoToken, int codigoPilha) {
		int production = LMSParserTable.getInstance().LMS_PARSER_TABLE[codigoPilha - LMSParserTable.getInstance().FIRST_NON_TERMINAL][codigoToken - 1];
		return (production >= 0) ? LMSParserTable.getInstance().LMS_PRODUCTIONS[production] : null;
	}

	/* ########################## MÉTODOS ULTILITÁRIOS ########################## */

	public boolean isTerminalState(int currentState) {
		return (LMSParserTable.getInstance().FIRST_NON_TERMINAL > currentState);
	}

	public boolean isNotTerminalState(int currentState) {
		return (currentState >= LMSParserTable.getInstance().FIRST_NON_TERMINAL && currentState < LMSParserTable.getInstance().FIRST_SEMANTIC_ACTION);
	}

	public boolean isSemanticState(int currentState) {
		return (!isTerminalState(currentState) & !isNotTerminalState(currentState));
	}

}
