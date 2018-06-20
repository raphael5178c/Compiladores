package analisador.hipotetica;

import java.util.ArrayList;
import java.util.List;

import analisador.domain.Instrucao;
import analisador.domain.Literal;
import analisador.program.Semantico;

public class InstrucoesHipotetica {
	
	public static String[] instrucaoHipotetica = new String[100];
	public List<Instrucao> listIntrucao;
	public List<Literal> listLiteral;
	
	public InstrucoesHipotetica() {
        InstrucoesHipotetica.instrucaoHipotetica[1] = "RETU";
        InstrucoesHipotetica.instrucaoHipotetica[2] = "CRVL";
        InstrucoesHipotetica.instrucaoHipotetica[3] = "CRCT";
        InstrucoesHipotetica.instrucaoHipotetica[4] = "ARMZ";
        InstrucoesHipotetica.instrucaoHipotetica[5] = "SOMA";
        InstrucoesHipotetica.instrucaoHipotetica[6] = "SUBT";
        InstrucoesHipotetica.instrucaoHipotetica[7] = "MULT";
        InstrucoesHipotetica.instrucaoHipotetica[8] = "DIVI";
        InstrucoesHipotetica.instrucaoHipotetica[9] = "INVR";
        InstrucoesHipotetica.instrucaoHipotetica[10] = "NEGA";
        InstrucoesHipotetica.instrucaoHipotetica[11] = "CONJ";
        InstrucoesHipotetica.instrucaoHipotetica[12] = "DISJ";
        InstrucoesHipotetica.instrucaoHipotetica[13] = "CMME";
        InstrucoesHipotetica.instrucaoHipotetica[14] = "CMMA";
        InstrucoesHipotetica.instrucaoHipotetica[15] = "CMIG";
        InstrucoesHipotetica.instrucaoHipotetica[16] = "CMDF";
        InstrucoesHipotetica.instrucaoHipotetica[17] = "CMEI";
        InstrucoesHipotetica.instrucaoHipotetica[18] = "CMAI";
        InstrucoesHipotetica.instrucaoHipotetica[19] = "DSVS";
        InstrucoesHipotetica.instrucaoHipotetica[20] = "DSVF";
        InstrucoesHipotetica.instrucaoHipotetica[21] = "LEIT";
        InstrucoesHipotetica.instrucaoHipotetica[22] = "IMPR";
        InstrucoesHipotetica.instrucaoHipotetica[23] = "IMPRL";
        InstrucoesHipotetica.instrucaoHipotetica[24] = "AMEM";
        InstrucoesHipotetica.instrucaoHipotetica[25] = "CALL";
        InstrucoesHipotetica.instrucaoHipotetica[26] = "PARA";
        InstrucoesHipotetica.instrucaoHipotetica[27] = "NADA";
        InstrucoesHipotetica.instrucaoHipotetica[28] = "COPI";
        InstrucoesHipotetica.instrucaoHipotetica[29] = "DSVT";
        InstrucoesHipotetica.instrucaoHipotetica[99] = "##DEBUG##";
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
