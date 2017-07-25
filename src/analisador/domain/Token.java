package analisador.domain;

public class Token {

	private int codigo;
	private String nome;
	private String tipo;

	public Token(int codigo, String nome, String tipo) {
		this.codigo = codigo;
		this.nome = nome;
		this.tipo = tipo;
	}

	public Token() {
		this(0, null, null);
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

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Token other = (Token) obj;
		if (this.codigo != other.codigo) {
			return false;
		}
		if (this.nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!this.nome.equals(other.nome)) {
			return false;
		}
		if (this.tipo == null) {
			if (other.tipo != null) {
				return false;
			}
		} else if (!this.tipo.equals(other.tipo)) {
			return false;
		}
		return true;
	}

	public String toString() {
		return

		"Código " + this.codigo + "\t" + "Nome: " + this.nome + "\t" + "Tipo: " + this.tipo;
	}

}
