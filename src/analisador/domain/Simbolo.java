package analisador.domain;

public class Simbolo {
	
	private Token token;
	private int nivelDeclaracao;
	private String nomeSimbolo;
	private int geralA;
	private int geralB;
	
	public Simbolo(Token token, int nivel_declaracao, String nomeSimbolo) {
		this.token = token;
		this.nomeSimbolo = nomeSimbolo;
		this.nivelDeclaracao = nivel_declaracao;
	}
	
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public int getNivelDeclaracao() {
		return nivelDeclaracao;
	}
	public void setNivelDeclaracao(int nivelDeclaracao) {
		this.nivelDeclaracao = nivelDeclaracao;
	}

	public String getNomeSimbolo() {
		return this.nomeSimbolo;
	}

	public void setNomeSimbolo(String nome) {
		this.nomeSimbolo = nome;
	}

	public int getGeralA() {
		return geralA;
	}

	public void setGeralA(int geralA) {
		this.geralA = geralA;
	}

	public int getGeralB() {
		return geralB;
	}

	public void setGeralB(int geralB) {
		this.geralB = geralB;
	}
	
}
