package analisador.program;

import java.util.ArrayList;
import java.util.List;

import analisador.domain.Token;
import analisador.domain.Util;

public class Lexico {

	private int linha;

	private int estadoInicial(char c) {
		if (isLetter(c)) {
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
		if (isDigit(c)) {
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
		if ((isLetter(c)) || (isDigit(c))) {
			return 1;
		}
		return 0;
	}

	private int estado3(char c) {
		if (c == '"') {
			return 4;
		}
		return 3;
	}

	private int estado4(char c) {
		return 0;
	}

	private int estado5(char c) {
		if ((c == '=') || (c == '>')) {
			return 7;
		}
		return 0;
	}

	private int estado6(char c) {
		if (c == '=') {
			return 7;
		}
		return 0;
	}

	private int estado7(char c) {
		return 0;
	}

	private int estado8(char c) {
		if (c == '.') {
			return 7;
		}
		return 0;
	}

	private int estado9(char c) {
		if (c == '=') {
			return 7;
		}
		return 0;
	}

	private int estado10(char c) {
		if (c == '*') {
			return 11;
		}
		return 0;
	}

	private int estado11(char c) {
		if (c == '*') {
			return 12;
		}
		return 11;
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
		if (isDigit(c)) {
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

	private boolean isDigit(char c) {
		return Character.isDigit(c);
	}

	private boolean isLetter(char c) {
		return Character.isLetter(c);
	}
	
	public List<Token> analisar(String sequencia) {
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
					if (Util.PALAVRAS_RESERVADAS.containsKey(palavra.toUpperCase())) {
						returnList.add(new Token(((Integer) Util.PALAVRAS_RESERVADAS.get(palavra.toUpperCase())).intValue(), palavra, "Palavra Reservada"));
					} else if (sequenciaAtual.toString().length() <= 30) {
						returnList.add(new Token(19, palavra, "ID"));
					} else {
						returnList.add(new Token(0, palavra, "ID não pode conter mais de 30 caracteres! Linha: " + this.linha));
					}
					i--;
				} else if (estadoAtual < 0) {
					returnList.add(new Token(0, palavra, "Caractere não reconhecido! Linha: " + this.linha));
					return returnList;
				}
				break;
			case 3:
				estadoAtual = estado3(charAtual);
				break;
			case 4:
				estadoAtual = estado4(charAtual);
				if (estadoAtual == 0) {
					if (sequenciaAtual.toString().length() < 255) {
						returnList.add(new Token(21, sequenciaAtual.toString(), "LIT"));
					} else {
						returnList.add(new Token(0, sequenciaAtual.toString(), "ILEGAL, valor fora da escala! Linha:" + this.linha));
					}
					i--;
				}
				break;
			case 5:
				estadoAtual = estado5(charAtual);
				if (estadoAtual == 0) {
					returnList.add(new Token(9, sequenciaAtual.toString(), "Sinal de Menor"));
					i--;
				}
				break;
			case 6:
				estadoAtual = estado6(charAtual);
				if (estadoAtual == 0) {
					returnList.add(new Token(7, sequenciaAtual.toString(), "Sinal de Maior"));
					i--;
				}
				break;
			case 7:
				estadoAtual = estado7(charAtual);
				if (sequenciaAtual.toString().equals("-")) {
					returnList.add(new Token(3, sequenciaAtual.toString(), "Sinal de Subtração"));
				} else if (sequenciaAtual.toString().equals("=")) {
					returnList.add(new Token(6, sequenciaAtual.toString(), "Sinal de Igualdade"));
				} else if (sequenciaAtual.toString().equals("<>")) {
					returnList.add(new Token(11, sequenciaAtual.toString(), "Sinal de Diferente"));
				} else if (sequenciaAtual.toString().equals(">=")) {
					returnList.add(new Token(8, sequenciaAtual.toString(), "Sinal de Maior Igual"));
				} else if (sequenciaAtual.toString().equals("<=")) {
					returnList.add(new Token(10, sequenciaAtual.toString(), "Sinal de Menor Igual"));
				} else if (sequenciaAtual.toString().equals("+")) {
					returnList.add(new Token(2, sequenciaAtual.toString(), "Sinal de Adição"));
				} else if (sequenciaAtual.toString().equals("*")) {
					returnList.add(new Token(4, sequenciaAtual.toString(), "Sinal de Multiplicação"));
				} else if (sequenciaAtual.toString().equals(")")) {
					returnList.add(new Token(18, sequenciaAtual.toString(), "Fechamento de parênteses"));
				} else if (sequenciaAtual.toString().equals(",")) {
					returnList.add(new Token(15, sequenciaAtual.toString(), "Vírgula"));
				} else if (sequenciaAtual.toString().equals(";")) {
					returnList.add(new Token(14, sequenciaAtual.toString(), "Ponto e vírgula"));
				} else if (sequenciaAtual.toString().equals(":=")) {
					returnList.add(new Token(12, sequenciaAtual.toString(), "Sinal de atribuição"));
				} else if (sequenciaAtual.toString().equals("$")) {
					returnList.add(new Token(1, sequenciaAtual.toString(), "Fim de Arquivo"));
					i = sequenciaLength;
					break;
				}
				i--;
				break;
			case 8:
				estadoAtual = estado8(charAtual);
				if (estadoAtual == 0) {
					returnList.add(new Token(16, sequenciaAtual.toString(), "Ponto final"));
					i--;
				}
				break;
			case 9:
				estadoAtual = estado9(charAtual);
				if (estadoAtual == 0) {
					returnList.add(new Token(13, sequenciaAtual.toString(), "Dois pontos"));
					i--;
				}
				break;
			case 10:
				estadoAtual = estado15(charAtual);
				if (estadoAtual == 0) {
					returnList.add(new Token(5, sequenciaAtual.toString(), "Sinal de divisão"));
					i--;
				}
				break;
			case 11:
				estadoAtual = estado11(charAtual);
				if (estadoAtual < 0) {
					returnList.add(new Token(0, sequenciaAtual.toString().replaceAll("\n", "/n").replaceAll("\t", "/t"),
							"ID deve terminar com letra ou número! Linha: " + this.linha));
					return returnList;
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
							returnList.add(new Token(0, sequenciaAtual.toString(),
									"ILEGAL, valor fora da escala! Linha: " + this.linha));
						} else {
							returnList.add(new Token(20, String.valueOf(numInteiro), "INTEIRO"));
						}
					} catch (Exception e) {
						returnList.add(new Token(0, sequenciaAtual.toString(),
								"ILEGAL, não aceita ponto decimal nem outros caracteres! Linha: " + this.linha));
					}
				}
				break;
			case 15:
				estadoAtual = estado10(charAtual);
				if (estadoAtual == 0) {
					returnList.add(new Token(17, sequenciaAtual.toString(), "Abertura de Parênteses"));
					i--;
				}
				break;
			}
			if (estadoAtual >= 0) {
				sequenciaAtual.append(charAtual);
			} else {
				returnList.add(new Token(0, String.valueOf(charAtual), "Atenção! token inválido! Linha: " + this.linha));
				estadoAtual = 0;
			}
		}
		return returnList;
	}

	private void contarLinha(char charAtual) {
		if (charAtual == '\n') {
			this.linha += 1;
		}
	}

}
