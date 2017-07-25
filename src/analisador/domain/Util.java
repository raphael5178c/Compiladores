package analisador.domain;

import java.util.HashMap;
import java.util.Map;

public class Util {

	public static final Map<String, Integer> PALAVRAS_RESERVADAS = new HashMap<String, Integer>();

	static {
		PALAVRAS_RESERVADAS.put("PROGRAM", Integer.valueOf(22));
		PALAVRAS_RESERVADAS.put("CONST", Integer.valueOf(23));
		PALAVRAS_RESERVADAS.put("VAR", Integer.valueOf(24));
		PALAVRAS_RESERVADAS.put("PROCEDURE", Integer.valueOf(25));
		PALAVRAS_RESERVADAS.put("BEGIN", Integer.valueOf(26));
		PALAVRAS_RESERVADAS.put("END", Integer.valueOf(27));
		PALAVRAS_RESERVADAS.put("INTEGER", Integer.valueOf(28));
		PALAVRAS_RESERVADAS.put("OF", Integer.valueOf(29));
		PALAVRAS_RESERVADAS.put("CALL", Integer.valueOf(30));
		PALAVRAS_RESERVADAS.put("IF", Integer.valueOf(31));
		PALAVRAS_RESERVADAS.put("THEN", Integer.valueOf(32));
		PALAVRAS_RESERVADAS.put("ELSE", Integer.valueOf(33));
		PALAVRAS_RESERVADAS.put("WHILE", Integer.valueOf(34));
		PALAVRAS_RESERVADAS.put("DO", Integer.valueOf(35));
		PALAVRAS_RESERVADAS.put("REPEAT", Integer.valueOf(36));
		PALAVRAS_RESERVADAS.put("UNTIL", Integer.valueOf(37));
		PALAVRAS_RESERVADAS.put("READLN", Integer.valueOf(38));
		PALAVRAS_RESERVADAS.put("WRITELN", Integer.valueOf(39));
		PALAVRAS_RESERVADAS.put("OR", Integer.valueOf(40));
		PALAVRAS_RESERVADAS.put("AND", Integer.valueOf(41));
		PALAVRAS_RESERVADAS.put("NOT", Integer.valueOf(42));
		PALAVRAS_RESERVADAS.put("FOR", Integer.valueOf(43));
		PALAVRAS_RESERVADAS.put("TO", Integer.valueOf(44));
		PALAVRAS_RESERVADAS.put("CASE", Integer.valueOf(45));
	}
}
