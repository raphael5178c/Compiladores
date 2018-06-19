package analisador.hipotetica;

import java.util.ArrayList;
import java.util.List;

import analisador.domain.Instrucao;
import analisador.domain.Literal;

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
	
	public void insert(int numeroInstMaquinaVirutal, int geralA, int geralB) {
		if(listIntrucao == null) {
			listIntrucao = new ArrayList<Instrucao>();
		}
		listIntrucao.add(new Instrucao(InstrucoesHipotetica.instrucaoHipotetica[numeroInstMaquinaVirutal], numeroInstMaquinaVirutal, geralA, geralB));
	}
	
	public void insert(Instrucao instrucao) {
		if(listIntrucao == null) {
			listIntrucao = new ArrayList<Instrucao>();
		}
		listIntrucao.add(instrucao);
	}
	
	public void insertLiteral(String nmLiteral, int endMemoria) {
		if(listLiteral == null) {
			listLiteral = new ArrayList<Literal>();
		}
		listLiteral.add(new Literal(nmLiteral, endMemoria));
	}
	
	public void alterInstrucao(Instrucao instrucaoOld, Instrucao instrucaoNew) throws Exception {
		int index = 0;
		int indexOfRemove = 0;
		Instrucao instrucaoSearched = null;
		for (Instrucao instrucao : this.listIntrucao) {
			if(instrucao.equals(instrucaoOld) && instrucaoNew.instrucaoHip == instrucao.instrucaoHip) {
				instrucaoSearched = instrucaoNew;
				indexOfRemove = index;
			}
			index++;
		}
		if(instrucaoSearched == null) {
			throw new Exception(String.format("Instrução Atual %s não foi alterada para nova instrução %s", instrucaoOld, instrucaoNew));
		} else {
			this.listIntrucao.remove(indexOfRemove);
			this.listIntrucao.add(indexOfRemove, instrucaoSearched);
		}
	}

}
