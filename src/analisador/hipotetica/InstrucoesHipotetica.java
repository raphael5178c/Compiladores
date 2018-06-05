package analisador.hipotetica;

import java.util.List;

import analisador.domain.Instrucao;

public class InstrucoesHipotetica {
	
	private String[] instrucaoHipotetica = new String[30];
	public static List<Instrucao> listIntrucao;
	
	public InstrucoesHipotetica() {
        this.instrucaoHipotetica[1] = "RETU";
        this.instrucaoHipotetica[2] = "CRVL";
        this.instrucaoHipotetica[3] = "CRCT";
        this.instrucaoHipotetica[4] = "ARMZ";
        this.instrucaoHipotetica[5] = "SOMA";
        this.instrucaoHipotetica[6] = "SUBT";
        this.instrucaoHipotetica[7] = "MULT";
        this.instrucaoHipotetica[8] = "DIVI";
        this.instrucaoHipotetica[9] = "INVR";
        this.instrucaoHipotetica[10] = "NEGA";
        this.instrucaoHipotetica[11] = "CONJ";
        this.instrucaoHipotetica[12] = "DISJ";
        this.instrucaoHipotetica[13] = "CMME";
        this.instrucaoHipotetica[14] = "CMMA";
        this.instrucaoHipotetica[15] = "CMIG";
        this.instrucaoHipotetica[16] = "CMDF";
        this.instrucaoHipotetica[17] = "CMEI";
        this.instrucaoHipotetica[18] = "CMAI";
        this.instrucaoHipotetica[19] = "DSVS";
        this.instrucaoHipotetica[20] = "DSVF";
        this.instrucaoHipotetica[21] = "LEIT";
        this.instrucaoHipotetica[22] = "IMPR";
        this.instrucaoHipotetica[23] = "IMPRL";
        this.instrucaoHipotetica[24] = "AMEM";
        this.instrucaoHipotetica[25] = "CALL";
        this.instrucaoHipotetica[26] = "PARA";
        this.instrucaoHipotetica[27] = "NADA";
        this.instrucaoHipotetica[28] = "COPI";
        this.instrucaoHipotetica[29] = "DSVT";
	}
	
	public void insert(int numeroInstMaquinaVirutal) {
		if(InstrucoesHipotetica.listIntrucao != null) {
			InstrucoesHipotetica.listIntrucao.add(new Instrucao(this.instrucaoHipotetica[numeroInstMaquinaVirutal], numeroInstMaquinaVirutal));
		}
	}
	
	public void insert(int numeroInstMaquinaVirutal, int geralA, int geralB) {
		if(InstrucoesHipotetica.listIntrucao != null) {
			InstrucoesHipotetica.listIntrucao.add(new Instrucao(this.instrucaoHipotetica[numeroInstMaquinaVirutal], numeroInstMaquinaVirutal, geralA, geralB));
		}
	}

}
