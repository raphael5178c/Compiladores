package analisador.hipotetica;

import java.util.ArrayList;
import java.util.List;

import analisador.domain.Instrucao;
import analisador.domain.Literal;
import analisador.program.Semantico;

public class InstrucoesHipotetica {
	
	public static final int RETU = 1;
	public static final int CRVL = 2;
	public static final int CRCT = 3;
	public static final int ARMZ = 4;
	public static final int SOMA = 5;
	public static final int SUBT = 6;
	public static final int MULT = 7;
	public static final int DIVI = 8;
	public static final int INVR = 9;
	public static final int NEGA = 10;
	public static final int CONJ = 11;
	public static final int DISJ = 12;
	public static final int CMMW = 13;
	public static final int CMMA = 14;
	public static final int CMIG = 15;
	public static final int CMDF = 16;
	public static final int CMEI = 17;
	public static final int CMAI = 18;
	public static final int DSVS = 19;
	public static final int DSVF = 20;
	public static final int LEIT = 21;
	public static final int IMPR = 22;
	public static final int IMPRL = 23;
	public static final int AMEM = 24;
	public static final int CALL = 25;
	public static final int PARA = 26;
	public static final int NADA = 27;
	public static final int COPI = 28;
	public static final int DSVT= 29;
	
	public static String[] instrucaoHipotetica = new String[30];
	public List<Instrucao> listIntrucao;
	public List<Literal> listLiteral;
	
	public InstrucoesHipotetica() {
        InstrucoesHipotetica.instrucaoHipotetica[RETU] = "RETU";
        InstrucoesHipotetica.instrucaoHipotetica[CRVL] = "CRVL";
        InstrucoesHipotetica.instrucaoHipotetica[CRCT] = "CRCT";
        InstrucoesHipotetica.instrucaoHipotetica[ARMZ] = "ARMZ";
        InstrucoesHipotetica.instrucaoHipotetica[SOMA] = "SOMA";
        InstrucoesHipotetica.instrucaoHipotetica[SUBT] = "SUBT";
        InstrucoesHipotetica.instrucaoHipotetica[MULT] = "MULT";
        InstrucoesHipotetica.instrucaoHipotetica[DIVI] = "DIVI";
        InstrucoesHipotetica.instrucaoHipotetica[INVR] = "INVR";
        InstrucoesHipotetica.instrucaoHipotetica[NEGA] = "NEGA";
        InstrucoesHipotetica.instrucaoHipotetica[CONJ] = "CONJ";
        InstrucoesHipotetica.instrucaoHipotetica[DISJ] = "DISJ";
        InstrucoesHipotetica.instrucaoHipotetica[CMMW] = "CMME";
        InstrucoesHipotetica.instrucaoHipotetica[CMMA] = "CMMA";
        InstrucoesHipotetica.instrucaoHipotetica[CMIG] = "CMIG";
        InstrucoesHipotetica.instrucaoHipotetica[CMDF] = "CMDF";
        InstrucoesHipotetica.instrucaoHipotetica[CMEI] = "CMEI";
        InstrucoesHipotetica.instrucaoHipotetica[CMAI] = "CMAI";
        InstrucoesHipotetica.instrucaoHipotetica[DSVS] = "DSVS";
        InstrucoesHipotetica.instrucaoHipotetica[DSVF] = "DSVF";
        InstrucoesHipotetica.instrucaoHipotetica[LEIT] = "LEIT";
        InstrucoesHipotetica.instrucaoHipotetica[IMPR] = "IMPR";
        InstrucoesHipotetica.instrucaoHipotetica[IMPRL] = "IMPRL";
        InstrucoesHipotetica.instrucaoHipotetica[AMEM] = "AMEM";
        InstrucoesHipotetica.instrucaoHipotetica[CALL] = "CALL";
        InstrucoesHipotetica.instrucaoHipotetica[PARA] = "PARA";
        InstrucoesHipotetica.instrucaoHipotetica[NADA] = "NADA";
        InstrucoesHipotetica.instrucaoHipotetica[COPI] = "COPI";
        InstrucoesHipotetica.instrucaoHipotetica[DSVT] = "DSVT";
	}
	
	public static String getNameInstrucao(int idInstrucao) throws Exception {
		if(idInstrucao < 1 || idInstrucao > 29) throw new Exception("Instrução não cadastrada na lista de instruções");
		return instrucaoHipotetica[idInstrucao];
	}
	
	public void insertLiteral(String nmLiteral, int endMemoria) {
		if(listLiteral == null) {
			listLiteral = new ArrayList<Literal>();
		}
		listLiteral.add(new Literal(nmLiteral, endMemoria));
	}
	
	public boolean isValidInstrucoes() {
		try {
			Tipos tipo = Semantico.areaInstrucoes.AI[0];
			return tipo != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	public int qtValidInstrucoes() {
		int qtValid = 0;
		for (Tipos tipo : Semantico.areaInstrucoes.AI) {
			if(tipo.codigo == -1 && tipo.op1 == -1 && tipo.op2 == -1) continue;
			qtValid++;
		}
		return qtValid;
	}

}
