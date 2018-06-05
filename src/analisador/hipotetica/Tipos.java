package analisador.hipotetica;

/**
 * Classe utilizada pela classe "Hipotetica" para armazenar as informa��es 
 * de uma instru��o.
 * Esta classe, bem como as classes "AreaInstrucoes", "AreaLiterais"
 * e "Hipotetica" foi criada por Maicon, Reinaldo e Fabio e adaptada
 * para este aplicativo.
 */
public class Tipos {
	public int codigo; 
	public int op1;
	public int op2;
	
  /**
   * Construtor sem par�metros.
   * Todos os atributos s�o inicializados com valores padr�es.
   */
	public Tipos(){
	     this.codigo = 0;
	     this.op1 = 0;
	     this.op2 = 0;
   	 }

}
