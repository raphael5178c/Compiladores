package analisador.util;

import analisador.domain.Token;

public class ExceptionUtil {
	
	public static String getSyntaticErrorException(Token token) {
		return "Syntax Error, Unexpected Token: "+token.getNome()+", On Line: "+(token.getCurrentlineNumber());
	}

}
