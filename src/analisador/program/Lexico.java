package analisador.program;

import java.util.ArrayList;
import java.util.List;

import analisador.app.Main;
import analisador.constants.LMSConstantTokens;
import analisador.domain.PalavraReservada;
import analisador.domain.Token;
import analisador.util.CharUtil;

public class Lexico {

	public static Lexico instance;

	public static Lexico getInstance() {
		return (instance == null) ? new Lexico() : instance;
	}

	private int linha;

	private int estadoInicial(char c) {
		if (CharUtil.isLetter(c)) {
			return 1;
		}
		if (c == '"') {
			return 3;
		}
		if (c == '<') {
			return 5;
		}
		if (c == '>') {
			return 6;
		}
		if ((c == '-') || (c == '=') || (c == '+') || (c == '*') || (c == ')') || (c == '[') || (c == ']') || (c == ',')
				|| (c == ';') || (c == '$')) {
			return 7;
		}
		if (c == '.') {
			return 8;
		}
		if (c == ':') {
			return 9;
		}
		if (c == '/') {
			return 10;
		}
		if (c == '(') {
			return 15;
		}
		if (CharUtil.isDigit(c)) {
			return 14;
		}
		if (c == ' ' || c == '\t') {
			return 0;
		}
		if ((c == '\n') || (c == '\r')) {
			return 0;
		}
		return -1;
	}

	private int estado1(char c) {
		return (((CharUtil.isLetter(c)) || (CharUtil.isDigit(c)))) ? 1 : 0;
	}

	private int estado3(char c) {
		return (c == '"') ? 4 : 3;
	}

	private int estado4(char c) {
		return 0;
	}

	private int estado5(char c) {
		return ((c == '=') || (c == '>')) ? 7 : 0;
	}

	private int estado6(char c) {
		return (c == '=') ? 7 : 0;
	}

	private int estado7(char c) {
		return 0;
	}

	private int estado8(char c) {
		return (c == '.') ? 7 : 0;
	}

	private int estado9(char c) {
		return estado6(c);
	}

	private int estado10(char c) {
		return (c == '*') ? 11 : 0;
	}

	private int estado11(char c) {
		return (c == '*') ? 12 : 11;
	}

	private int estado12(char c) {
		if (c == '*') {
			return 12;
		}
		if (c == ')') {
			return 13;
		}
		if (c == '$') {
			return -1;
		}
		return 11;
	}

	private int estado13(char c) {
		return 0;
	}

	private int estado14(char c) {
		if (CharUtil.isDigit(c)) {
			return 14;
		}
		if (estadoInicial(c) < 0) {
			return -1;
		}
		return 0;
	}

	private int estado15(char c) {
		return 0;
	}

	public List<Token> analisar(String sequencia) throws Exception {
		try {
			this.linha = 0;
			List<Token> returnList = new ArrayList<Token>();

			int estadoAtual = 0;
			StringBuilder sequenciaAtual = null;

			int sequenciaLength = sequencia.length();
			for (int i = 0; i <= sequenciaLength; i++) {
				char charAtual = i == sequenciaLength ? '$' : sequencia.charAt(i);
				contarLinha(charAtual);

				switch (estadoAtual) {
				case 0:
					sequenciaAtual = new StringBuilder();
					estadoAtual = estadoInicial(charAtual);
					break;
				case 1:
					estadoAtual = estado1(charAtual);
					String palavra = sequenciaAtual.toString().toUpperCase();
					if (estadoAtual == 0) {
						if (PalavraReservada.PALAVRAS_RESERVADAS.containsKey(palavra.toUpperCase())) {
							returnList.add(new Token(
									((Integer) PalavraReservada.PALAVRAS_RESERVADAS.get(palavra.toUpperCase()))
											.intValue(),
									palavra, "Palavra Reservada", getCodigoParser(palavra.toUpperCase()), this.linha));
						} else if (sequenciaAtual.toString().length() <= 30) {
							returnList.add(
									new Token(19, palavra, "ID", LMSConstantTokens.TOKEN_IDENTIFICADOR, this.linha));
						} else {
							throw new Exception("ID não pode conter mais de 30 caracteres! na Linha: " + this.linha);
						}
						i--;
					} else if (estadoAtual < 0) {
						throw new Exception("Caracter não reconhecido! na Linha: " + this.linha);
					}
					break;
				case 3:
					estadoAtual = estado3(charAtual);
					break;
				case 4:
					estadoAtual = estado4(charAtual);
					if (estadoAtual == 0) {
						if (sequenciaAtual.toString().length() < 255) {
							returnList.add(new Token(21, sequenciaAtual.toString(), "LIT",
									LMSConstantTokens.TOKEN_LITERAL, this.linha));
						} else {
							throw new Exception("ILEGAL, valor fora da escala! na Linha: " + this.linha);
						}
						i--;
					}
					break;
				case 5:
					estadoAtual = estado5(charAtual);
					if (estadoAtual == 0) {
						returnList.add(new Token(9, sequenciaAtual.toString(), "Sinal de Menor",
								LMSConstantTokens.TOKEN_COMPARACAO_MENOR, this.linha));
						i--;
					}
					break;
				case 6:
					estadoAtual = estado6(charAtual);
					if (estadoAtual == 0) {
						returnList.add(new Token(7, sequenciaAtual.toString(), "Sinal de Maior",
								LMSConstantTokens.TOKEN_COMPARACAO_MAIOR, this.linha));
						i--;
					}
					break;
				case 7:
					estadoAtual = estado7(charAtual);
					if (sequenciaAtual.toString().equals("-")) {
						returnList.add(new Token(3, sequenciaAtual.toString(), "Sinal de Subtração",
								LMSConstantTokens.TOKEN_OPERACAO_SUBTRAI, this.linha));
					} else if (sequenciaAtual.toString().equals("=")) {
						returnList.add(new Token(6, sequenciaAtual.toString(), "Sinal de Igualdade",
								LMSConstantTokens.TOKEN_COMPARACAO_IGUAL, this.linha));
					} else if (sequenciaAtual.toString().equals("<>")) {
						returnList.add(new Token(11, sequenciaAtual.toString(), "Sinal de Diferente",
								LMSConstantTokens.TOKEN_COMPARACAO_DIFERENTE, this.linha));
					} else if (sequenciaAtual.toString().equals(">=")) {
						returnList.add(new Token(8, sequenciaAtual.toString(), "Sinal de Maior Igual",
								LMSConstantTokens.TOKEN_COMPARACAO_MAIOR_IGUAL, this.linha));
					} else if (sequenciaAtual.toString().equals("<=")) {
						returnList.add(new Token(10, sequenciaAtual.toString(), "Sinal de Menor Igual",
								LMSConstantTokens.TOKEN_COMPARACAO_MENOR_IGUAL, this.linha));
					} else if (sequenciaAtual.toString().equals("+")) {
						returnList.add(new Token(2, sequenciaAtual.toString(), "Sinal de Adição",
								LMSConstantTokens.TOKEN_OPERACAO_ADICIONA, this.linha));
					} else if (sequenciaAtual.toString().equals("*")) {
						returnList.add(new Token(4, sequenciaAtual.toString(), "Sinal de Multiplicação",
								LMSConstantTokens.TOKEN_OPERACAO_MULTIPLICA, this.linha));
					} else if (sequenciaAtual.toString().equals(")")) {
						returnList.add(new Token(18, sequenciaAtual.toString(), "Fechamento de parênteses",
								LMSConstantTokens.TOKEN_FECHA_PARENTESES, this.linha));
					} else if (sequenciaAtual.toString().equals(",")) {
						returnList.add(new Token(15, sequenciaAtual.toString(), "Vírgula",
								LMSConstantTokens.TOKEN_VIRGULA, this.linha));
					} else if (sequenciaAtual.toString().equals(";")) {
						returnList.add(new Token(14, sequenciaAtual.toString(), "Ponto e vírgula",
								LMSConstantTokens.TOKEN_PONTO_VIRGULA, this.linha));
					} else if (sequenciaAtual.toString().equals(":=")) {
						returnList.add(new Token(12, sequenciaAtual.toString(), "Sinal de atribuição",
								LMSConstantTokens.TOKEN_ATRIBUICAO, this.linha));
					} else if (sequenciaAtual.toString().equals("$")) {
						returnList.add(new Token(1, sequenciaAtual.toString(), "Fim de Arquivo",
								LMSConstantTokens.TOKEN_DOLLAR, this.linha));
						i = sequenciaLength;
						break;
					}
					i--;
					break;
				case 8:
					estadoAtual = estado8(charAtual);
					if (estadoAtual == 0) {
						returnList.add(new Token(16, sequenciaAtual.toString(), "Ponto final",
								LMSConstantTokens.TOKEN_PONTO, this.linha));
						i--;
					}
					break;
				case 9:
					estadoAtual = estado9(charAtual);
					if (estadoAtual == 0) {
						returnList.add(new Token(13, sequenciaAtual.toString(), "Dois pontos",
								LMSConstantTokens.TOKEN_DOIS_PONTOS, this.linha));
						i--;
					}
					break;
				case 10:
					estadoAtual = estado15(charAtual);
					if (estadoAtual == 0) {
						returnList.add(new Token(5, sequenciaAtual.toString(), "Sinal de divisão",
								LMSConstantTokens.TOKEN_OPERACAO_DIVIDE, this.linha));
						i--;
					}
					break;
				case 11:
					estadoAtual = estado11(charAtual);
					if (estadoAtual < 0) {
						throw new Exception("ID deve terminar com letra ou número! na Linha: " + this.linha);
					}
					break;
				case 12:
					estadoAtual = estado12(charAtual);
					break;
				case 13:
					estadoAtual = estado13(charAtual);
					if (estadoAtual == 0) {
						i--;
					}
					break;
				case 14:
					estadoAtual = estado14(charAtual);
					if (estadoAtual <= 0) {
						if (estadoAtual == 0) {
							i--;
						}
						try {
							int numInteiro = Integer.parseInt(sequenciaAtual.toString());
							if ((numInteiro > 32767)) {
								throw new Exception("ILEGAL, valor fora da escala! na Linha: " + this.linha);
							} else {
								returnList.add(new Token(20, String.valueOf(numInteiro), "INTEIRO",
										LMSConstantTokens.TOKEN_NUMERAL, this.linha));
							}
						} catch (Exception e) {
							if (e.getMessage().indexOf("ILEGAL, valor fora da escala! na Linha: ") >= 0) {
								throw e;
							}
							throw new Exception(
									"ILEGAL, não aceita ponto decimal nem outros caracteres! na Linha: " + this.linha);
						}
					}
					break;
				case 15:
					estadoAtual = estado10(charAtual);
					if (estadoAtual == 0) {
						returnList.add(new Token(17, sequenciaAtual.toString(), "Abertura de Parênteses",
								LMSConstantTokens.TOKEN_ABRE_PARENTESES, this.linha));
						i--;
					}
					break;
				}
				if (estadoAtual >= 0) {
					sequenciaAtual.append(charAtual);
				} else {
					estadoAtual = 0;
					throw new Exception("Atenção! token inválido! na Linha: " + this.linha);
				}
			}
			return returnList;
		} catch (Exception ex) {
			throw ex;
		}
	}

	private int getCodigoParser(String key) {
		for (PalavraReservada palavraReservada : Main.palavraReservadaList) {
			if (palavraReservada.getPalavra().equalsIgnoreCase(key)) {
				return palavraReservada.getIdGalls();
			}
		}
		return 0;
	}

	private void contarLinha(char charAtual) {
		if (charAtual != '\n')
			return;
		this.linha += 1;
	}

}
