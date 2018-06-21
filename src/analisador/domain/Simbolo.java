package analisador.domain;

public class Simbolo {
	
	private Token token;
	private int nivelDeclaracao;
	private String nomeSimbolo;
	private String categoria;
	private int geralA;
	private int geralB;
	private int qtValuesInserted;
	private int numeroParametro;
	private boolean ignoreCategoriaEquals;
	private boolean usaNivelCompare;
	
	public Simbolo(Token token, int nivel_declaracao, String nomeSimbolo) {
		this.token = token;
		this.nomeSimbolo = nomeSimbolo;
		this.nivelDeclaracao = nivel_declaracao;
	}
	
	public Simbolo(Token token, String categoria, int nivel_declaracao, String nomeSimbolo) {
		this.categoria = categoria;
		this.token = token;
		this.nomeSimbolo = nomeSimbolo;
		this.nivelDeclaracao = nivel_declaracao;
	}
	
	public Simbolo(Token token, String categoria, int nivel_declaracao, String nomeSimbolo, int geralA, int geralB) {
		this.categoria = categoria;
		this.token = token;
		this.nomeSimbolo = nomeSimbolo;
		this.nivelDeclaracao = nivel_declaracao;
		this.geralA = geralA;
		this.geralB = geralB;
	}
	
	public Simbolo(Token token, String categoria, int nivel_declaracao, String nomeSimbolo, int geralA, int geralB, int numeroParametro) {
		this.categoria = categoria;
		this.token = token;
		this.nomeSimbolo = nomeSimbolo;
		this.nivelDeclaracao = nivel_declaracao;
		this.geralA = geralA;
		this.geralB = geralB;
		this.numeroParametro = numeroParametro;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setQtValuesPilha(int qtValuesInserted) {
		this.qtValuesInserted = qtValuesInserted;
	}
	
	public int getQtValuesPilha() {
		return this.qtValuesInserted;
	}

	public int getNumeroParametro() {
		return numeroParametro;
	}

	public void setNumeroParametro(int numeroParametro) {
		this.numeroParametro = numeroParametro;
	}
	
	@Override
	public boolean equals(Object obj) {
		super.equals(obj);
		boolean valid = false;
		Simbolo simbolo = (Simbolo) obj;
			valid = this.token.equals(simbolo.token);
			if(isUseNivelSymbolCompare()) {
				valid = valid && this.nivelDeclaracao >= simbolo.nivelDeclaracao;
			} else {
				valid = valid && this.nivelDeclaracao == simbolo.nivelDeclaracao;
			}
			valid = valid && this.nomeSimbolo.equals(simbolo.nomeSimbolo);
			valid = valid && this.numeroParametro == simbolo.numeroParametro;
			if(!isIgnoreCategoriaEquals()) {
				valid = valid && this.categoria.equals(simbolo.categoria);
			}
		return valid;
	}

	public boolean isIgnoreCategoriaEquals() {
		return ignoreCategoriaEquals;
	}

	public void setIgnoreCategoriaEquals(boolean ignoreCategoriaEquals) {
		this.ignoreCategoriaEquals = ignoreCategoriaEquals;
	}

	public void setUseNivelSymbolCompare(boolean usa) {
		this.usaNivelCompare = true;
	}
	
	public boolean isUseNivelSymbolCompare() {
		return this.usaNivelCompare;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(nomeSimbolo).append("|").append(categoria).append("|").append(nivelDeclaracao).append("|").append(geralA).append("|").append(geralB).toString();
	}
	
	
}
