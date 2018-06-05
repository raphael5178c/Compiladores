package analisador.util;

import analisador.domain.Token;

public class ExceptionUtil {
	
	public static String getSyntaticErrorException(Token token) {
		return "Syntax Error, Unexpected Value: "+token.getNome()+", On Line: "+(token.getCurrentlineNumber()+1);
	}
	
	public static String getSemanticErrorInvalidException(int acaoRecebida, Token token) {
		return "Syntax Error, Unexpected Semantic Action : "+acaoRecebida+" Near At "+token.getNome()+", On Line: "+(token.getCurrentlineNumber()+1);
	}
	
	public static String getSemanticGeneralError(String message) {
		return "Semantic Error, "+message;
	}


}
