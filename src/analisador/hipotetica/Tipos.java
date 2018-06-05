package analisador.hipotetica;

/**
 * Classe utilizada pela classe "Hipotetica" para armazenar as informações 
 * de uma instrução.
 * Esta classe, bem como as classes "AreaInstrucoes", "AreaLiterais"
 * e "Hipotetica" foi criada por Maicon, Reinaldo e Fabio e adaptada
 * para este aplicativo.
 */
public class Tipos {
	public int codigo; 
	public int op1;
	public int op2;
	
  /**
   * Construtor sem parâmetros.
   * Todos os atributos são inicializados com valores padrões.
   */
	public Tipos(){
	     this.codigo = 0;
	     this.op1 = 0;
	     this.op2 = 0;
   	 }

}
