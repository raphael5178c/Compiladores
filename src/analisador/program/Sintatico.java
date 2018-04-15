package analisador.program;

import java.util.List;

import analisador.domain.Token;
import analisador.util.ListUtil;
import analisador.util.ValueUtil;

public class Sintatico {
	
	public static Sintatico instance;
	
	public static Sintatico getInstance() {
		return (instance == null) ? new Sintatico() : instance;
	}
	
	public boolean analiseSintatica(List<Token> listToken) {
		if(ValueUtil.isEmpty(listToken) || ListUtil.isEmpty(listToken)) return false;
		for (Token token : listToken) {
			token.getCodigo();
		}
		return true;
	}

}
