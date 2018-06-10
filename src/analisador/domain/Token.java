package analisador.domain;

public class Token implements Cloneable {

	private int codigo;
	private String nome;
	private String tipo;
	private int codigoParser;
	private int acaoSemantica;
	private int currentlineNumber;

	public Token(int codigo, String nome, String tipo, int lineNumber) {
		this.codigo = codigo;
		this.nome = nome;
		this.tipo = tipo;
		this.currentlineNumber = lineNumber;
	}
	
	public Token(int codigo, String nome, String tipo, int codigoParser, int acaoSemantica, int lineNumber) {
		this.codigo = codigo;
		this.nome = nome;
		this.tipo = tipo;
		this.currentlineNumber = lineNumber;
		this.setCodigoParser(codigoParser);
		this.setAcaoSemantica(acaoSemantica);
	}
	
	public Token(int codigo, String nome, String tipo, int codigoParser, int lineNumber) {
		this.codigo = codigo;
		this.nome = nome;
		this.tipo = tipo;
		this.currentlineNumber = lineNumber;
		this.setCodigoParser(codigoParser);
	}

	public Token() {
		this(0, null, null, 0);
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String toString() {
		return "Código " + this.codigo + "\t" + "Nome: " + this.nome + "\t" + "Tipo: " + this.tipo;
	}

	public int getCodigoParser() {
		return codigoParser;
	}

	public void setCodigoParser(final int codigoParser) {
		this.codigoParser = codigoParser;
	}

	public int getAcaoSemantica() {
		return acaoSemantica;
	}

	public void setAcaoSemantica(final int acaoSemantica) {
		this.acaoSemantica = acaoSemantica;
	}

	public int getCurrentlineNumber() {
		return currentlineNumber;
	}

	public void setCurrentlineNumber(int currentlineNumber) {
		this.currentlineNumber = currentlineNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
		super.equals(obj);
		Token token = (Token) obj;
		return (this.acaoSemantica == token.acaoSemantica &&
				this.codigo == token.codigo &&
				this.codigoParser == token.codigoParser &&
				this.nome.equals(token.nome) &&
				this.tipo.equals(token.tipo));
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public Token clone2() throws CloneNotSupportedException {
		return (Token) this.clone();
	}

}
