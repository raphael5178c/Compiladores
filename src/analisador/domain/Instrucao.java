package analisador.domain;

public class Instrucao {
	
	public int searchCode;
	public int instrucaoHip;
	public String nmInstrucao;
	public int geralA;
	public int geralB;
	
	public Instrucao(String nmInstrucao, int instrucaoHipotetica) {
		this.instrucaoHip = instrucaoHipotetica;
		this.nmInstrucao = nmInstrucao;
		this.geralA = 0;
		this.geralB = 0;
	}
	
	public Instrucao(String nmInstrucao, int instrucaoHipotetica, int geralA, int geralB) {
		this.instrucaoHip = instrucaoHipotetica;
		this.nmInstrucao = nmInstrucao;
		this.geralA = geralA;
		this.geralB = geralB;
	}

}
