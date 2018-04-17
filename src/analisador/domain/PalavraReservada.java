package analisador.domain;

import java.util.HashMap;
import java.util.Map;

public class PalavraReservada {

	private String palavra;
	private int idLms;
	private int idGalls;

	public static final Map<String, Integer> PALAVRAS_RESERVADAS = new HashMap<String, Integer>();

	static {
		PALAVRAS_RESERVADAS.put("PROGRAM", 22);
		PALAVRAS_RESERVADAS.put("CONST", 23);
		PALAVRAS_RESERVADAS.put("VAR", 24);
		PALAVRAS_RESERVADAS.put("PROCEDURE", 25);
		PALAVRAS_RESERVADAS.put("BEGIN", 26);
		PALAVRAS_RESERVADAS.put("END", 27);
		PALAVRAS_RESERVADAS.put("INTEGER", 28);
		PALAVRAS_RESERVADAS.put("OF", 29);
		PALAVRAS_RESERVADAS.put("CALL", 30);
		PALAVRAS_RESERVADAS.put("IF", 31);
		PALAVRAS_RESERVADAS.put("THEN", 32);
		PALAVRAS_RESERVADAS.put("ELSE", 33);
		PALAVRAS_RESERVADAS.put("WHILE", 34);
		PALAVRAS_RESERVADAS.put("DO", 35);
		PALAVRAS_RESERVADAS.put("REPEAT", 36);
		PALAVRAS_RESERVADAS.put("UNTIL", 37);
		PALAVRAS_RESERVADAS.put("READLN", 38);
		PALAVRAS_RESERVADAS.put("WRITELN", 39);
		PALAVRAS_RESERVADAS.put("OR", 40);
		PALAVRAS_RESERVADAS.put("AND", 41);
		PALAVRAS_RESERVADAS.put("NOT", 42);
		PALAVRAS_RESERVADAS.put("FOR", 43);
		PALAVRAS_RESERVADAS.put("TO", 44);
		PALAVRAS_RESERVADAS.put("CASE", 45);
	}

	public PalavraReservada(String palavra, int idLms, int idGalls) {
		this.palavra = palavra;
		this.idLms = idLms;
		this.idGalls = idGalls;
	}

	public PalavraReservada(String palavra) {
		this.palavra = palavra;
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(final String palavra) {
		this.palavra = palavra;
	}

	public int getIdLms() {
		return idLms;
	}

	public void setIdLms(final int idLms) {
		this.idLms = idLms;
	}

	public int getIdGalls() {
		return idGalls;
	}

	public void setIdGalls(final int idGalls) {
		this.idGalls = idGalls;
	}

}
