package analisador.program;

import java.util.List;
import java.util.Stack;

import analisador.constants.LMSConstantTokens;
import analisador.domain.Token;
import analisador.hipotetica.Hipotetica;
import analisador.parsers.LMSParserTable;
import analisador.util.ConsoleUtil;
import analisador.util.ExceptionUtil;

public class Sintatico {
	

	public static Sintatico instance;
	private Stack<Integer> pilha;
	private static Token twoTokenBeforeAtual; 
	private static Token previousToken;
	private static Token tokenAtual;

	public Sintatico() {
		pilha = new Stack<Integer>();
	}
	
	public static Sintatico getInstance() {
		return (instance == null) ? new Sintatico() : instance;
	}

	public boolean analiseSintatica(List<Token> listToken, boolean goSemantica) throws Exception {
		if (listToken == null) {
			return false;
		}
		beginVariables();
		int currentIndex = 0;
		while (pilha.size() > 0) {
			tokenAtual = (tokenAtual == null) ? listToken.get(currentIndex++) : tokenAtual;
			int pilhaCodigo = pilha.pop();
			if(isValorPilhaInicioOuFimArquivo(pilhaCodigo)) {
				continue;
			}
			if (isTerminalState(pilhaCodigo)) {
				currentIndex = verificaTokenTerminal(listToken, currentIndex, pilhaCodigo);
			} else if (isNotTerminalState(pilhaCodigo)) {
				verificaTokenNaoTerminal(pilhaCodigo);
			} else if (isSemanticState(pilhaCodigo) && goSemantica) {
				Semantico.gerenciaAcoesSemanticas(pilhaCodigo, previousToken, twoTokenBeforeAtual);
			}
		}
		if(goSemantica) {
			ConsoleUtil.getInstance().setTxtInfoConsole("Iniciando a analise semantica do código de fonte...");
			Hipotetica.Interpreta(Semantico.areaInstrucoes, Semantico.areaLiterais);
			ConsoleUtil.getInstance().setTxtInfoConsole("Finalizada a analise semantica do código de fonte...");
		}
		return true;
	}

	private void beginVariables() {
		tokenAtual = null;
		pilha.clear();
		pilha.push(LMSConstantTokens.TOKEN_DOLLAR);
		pilha.push(LMSParserTable.getInstance().START_SYMBOL);
	}

	private void verificaTokenNaoTerminal(int pilhaCodigo) throws Exception {
		int[] productionsList = findProduction(tokenAtual.getCodigoParser(), pilhaCodigo);
		if(productionsList != null) {
			for (int i = productionsList.length-1; i >= 0; i--) {
				pilha.push(productionsList[i]);
			}
		} else {
			throw new Exception(ExceptionUtil.getSyntaticErrorException(tokenAtual));
		}
	}

	private int verificaTokenTerminal(List<Token> listToken, int currentIndex, int pilhaCodigo) throws Exception {
		if(pilhaCodigo == tokenAtual.getCodigoParser() && !pilha.isEmpty()) {
			try {
				try {
					twoTokenBeforeAtual = listToken.get(currentIndex-2);
				} catch (Exception e) { /* ignora exceção */ }
				previousToken = tokenAtual;
				tokenAtual = listToken.get(currentIndex++);
			} catch (Exception e) { /* ignora exceção */ }
		} else {
			throw new Exception(ExceptionUtil.getSyntaticErrorException(tokenAtual));
		}
		return currentIndex;
	}
	
	private int[] findProduction(int codigoToken, int codigoPilha) {
		int production = LMSParserTable.getInstance().LMS_PARSER_TABLE[codigoPilha - LMSParserTable.getInstance().FIRST_NON_TERMINAL][codigoToken - 1];
		return (production >= 0) ? LMSParserTable.getInstance().LMS_PRODUCTIONS[production] : null;
	}

	
	/* ########################## MÉTODOS ULTILITÁRIOS ########################## */

	public boolean isTerminalState(int currentState) {
		return (currentState < LMSParserTable.getInstance().FIRST_NON_TERMINAL);
	}

	public boolean isNotTerminalState(int currentState) {
		return (currentState >= LMSParserTable.getInstance().FIRST_NON_TERMINAL && currentState < LMSParserTable.getInstance().FIRST_SEMANTIC_ACTION);
	}

	public boolean isSemanticState(int currentState) {
		return currentState >= LMSParserTable.getInstance().FIRST_SEMANTIC_ACTION;
	}
	
	private boolean isValorPilhaInicioOuFimArquivo(int pilhaCodigo) {
		return (pilhaCodigo == LMSConstantTokens.TOKEN_EPSILON || pilhaCodigo == LMSConstantTokens.TOKEN_DOLLAR);
	}

}
