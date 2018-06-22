package analisador.hipotetica;

import javax.swing.JOptionPane;

//Maquina_virtual para a linguagem LMS
//Equipe:Maicon, reinaldo e Fabio - 2003A
//Adaptado e corrigido por Rogério Cortina e Charbel Szymanski - 2003B
//Atualizada por Charbel Szymanski em 2016A
//Corrigido Bugs por Raphael Santos e Guilherme Juncklaus

/**
 * Classe que implementa a máquina hipotética. Esta classe, bem como as classes
 * "Tipos", "AreaInstrucoes" e "AreaLiterais" foi criada por Maicon, Reinaldo e
 * Fabio e adaptada para este aplicativo.
 */
public class Hipotetica {
	public int MaxInst = 1000;
	public int MaxList = 100;
	public int baseSegmento; // base do segmento
	public int topoPilhaBaseDados; // topo da pilha da base de dados
	public int apontarInstrucoes; // apontador de instruções
	public int primeiroOperando; // primeiro operando
	public int segundoOperando; // segundo operando
	public int numeroVariaveis; // número de variáveis;
	public int numeroParametros; // número de parâmetros;
	public int codigoOperadorInstrucao; // codigo da instrução
	public int num_impr;
	public int[] S = new int[1000];

	/**
	 * Construtor sem parâmetros. Os atributos "nv", "np" e "num_impr" são
	 * inicializados com valores padrões.
	 */
	public Hipotetica() {
		MaxInst = 1000;
		MaxList = 100;
		baseSegmento = 0; // base do segmento
		topoPilhaBaseDados = 0; // topo da pilha da base de dados
		apontarInstrucoes = 0; // apontador de instruções
		primeiroOperando = 0; // primeiro operando
		segundoOperando = 0; // segundo operando
		numeroVariaveis = 0; // número de variáveis;
		numeroParametros = 0; // número de parâmetros;
		codigoOperadorInstrucao = 0; // codigo da instrução
		num_impr = 0;
		S = new int[1000];
	}

	/**
	 * Inicializa a área de instruções.
	 */
	public void InicializaAI(AreaInstrucoes AI) {
		for (int i = 0; i < MaxInst; i++) { // começava de 1
			AI.AI[i].codigo = -1;
			AI.AI[i].op1 = -1;
			AI.AI[i].op2 = -1;
		}
		AI.LC = 0;
	}

	/**
	 * Inicializa a área de literais
	 */
	public void InicializaAL(AreaLiterais AL) {
		for (int i = 0; i < MaxList; i++) {
			AL.AL[i] = "";
			AL.LIT = 0;
		}
	}

	/**
	 * Inclui uma instrução na área de instruções utilizada pela máquina hipotética.
	 */
	public boolean IncluirAI(AreaInstrucoes AI, int c, int o1, int o2) {
		boolean aux;
		if (AI.LC >= MaxInst) {
			aux = false;
		} else {
			aux = true;
			AI.AI[AI.LC].codigo = c;
			if (o1 != -1) {
				AI.AI[AI.LC].op1 = o1;
			}
			if (c == 24) {
				AI.AI[AI.LC].op2 = o2;
			}
			if (o2 != -1) {
				AI.AI[AI.LC].op2 = o2;
			}
			AI.LC = AI.LC + 1;
		}
		return aux;
	}

	/**
	 * Altera uma instrução da área de instruções utilizada pela máquina hipotética.
	 */
	public boolean AlterarAI(AreaInstrucoes AI, int s, int o1, int o2) {
		boolean altered = false;
		if (o1 != -1) {
			AI.AI[s].op1 = o1;
			altered = true;
		}
		if (o2 != -1) {
			AI.AI[s].op2 = o2;
			altered = true;
		}
		return altered;
	}

	/**
	 * Inclui um literal na área de literais utilizada pela máquina hipotética.
	 */
	public boolean IncluirAL(AreaLiterais AL, String literal) {
		boolean aux;
		if (AL.LIT >= MaxList) {
			aux = false;
		} else {
			aux = true;
			AL.AL[AL.LIT] = literal;
			AL.LIT = AL.LIT + 1;
		}
		return aux;
	}

	/**
	 * Utilizada para determinar a base.
	 */
	public int Base() {// determina base
		int b1;
		b1 = baseSegmento;
		while (primeiroOperando > 0) {
			b1 = S[b1];
			primeiroOperando = primeiroOperando - 1;
		}
		return b1;
	}

	/**
	 * Responsável por interpretar as instruções.
	 * @throws Exception 
	 */
	public void Interpreta(AreaInstrucoes AI, AreaLiterais AL) throws Exception {
		topoPilhaBaseDados = 0;
		baseSegmento = 0; // registrador base
		apontarInstrucoes = 0; // aponta proxima instrução
		S[1] = 0; // SL
		S[2] = 0; // DL
		S[3] = 0; // RA
		codigoOperadorInstrucao = 0;
		String leitura;
		while (codigoOperadorInstrucao != 26) {// Enquanto instrução diferente de Pare
			codigoOperadorInstrucao = AI.AI[apontarInstrucoes].codigo;
			primeiroOperando = AI.AI[apontarInstrucoes].op1;
			segundoOperando = AI.AI[apontarInstrucoes].op2;
			apontarInstrucoes = apontarInstrucoes + 1;

			switch (codigoOperadorInstrucao) {
			case 1:// RETU
				apontarInstrucoes = S[baseSegmento + 2];
				topoPilhaBaseDados = baseSegmento - segundoOperando;
				baseSegmento = S[baseSegmento + 1];
				break;
			case 2:// CRVL
				topoPilhaBaseDados = topoPilhaBaseDados + 1;
				S[topoPilhaBaseDados] = S[Base() + segundoOperando];
				break;
			case 3: // CRCT
				topoPilhaBaseDados = topoPilhaBaseDados + 1;
				S[topoPilhaBaseDados] = segundoOperando;
				break;
			case 4:// ARMZ
				S[Base() + segundoOperando] = S[topoPilhaBaseDados];
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 5:// SOMA
				S[topoPilhaBaseDados - 1] = S[topoPilhaBaseDados - 1] + S[topoPilhaBaseDados];
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 6:// SUBT
				S[topoPilhaBaseDados - 1] = S[topoPilhaBaseDados - 1] - S[topoPilhaBaseDados];
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 7:// MULT
				S[topoPilhaBaseDados - 1] = S[topoPilhaBaseDados - 1] * S[topoPilhaBaseDados];
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 8: // DIVI
                if (S[topoPilhaBaseDados] == 0) {
                	throw new Exception("Impossivel dividir por zero.");
                }
                S[topoPilhaBaseDados - 1] = S[topoPilhaBaseDados - 1] / S[topoPilhaBaseDados];
                --topoPilhaBaseDados;
				break;
			case 9:// INVR
				S[topoPilhaBaseDados] = -S[topoPilhaBaseDados];
				break;
			case 10: // NEGA
				S[topoPilhaBaseDados] = 1 - S[topoPilhaBaseDados];
				break;
			case 11:// CONJ
				if ((S[topoPilhaBaseDados - 1] == 1) && (S[topoPilhaBaseDados] == 1)) {
					S[topoPilhaBaseDados - 1] = 1; // A no material impresso está como "1" e aqui estava como "-1"
				} else {
					S[topoPilhaBaseDados - 1] = 0;
				}
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 12:// DISJ
				if ((S[topoPilhaBaseDados - 1] == 1 || S[topoPilhaBaseDados] == 1)) {
					S[topoPilhaBaseDados - 1] = 1;
				} else {
					S[topoPilhaBaseDados - 1] = 0;
				}
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 13:// CMME
				if (S[topoPilhaBaseDados - 1] < S[topoPilhaBaseDados]) {
					S[topoPilhaBaseDados - 1] = 1;
				} else {
					S[topoPilhaBaseDados - 1] = 0;
				}
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 14:// CMMA
				if (S[topoPilhaBaseDados - 1] > S[topoPilhaBaseDados]) {
					S[topoPilhaBaseDados - 1] = 1;
				} else {
					S[topoPilhaBaseDados - 1] = 0;
				}
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 15:// CMIG
				if (S[topoPilhaBaseDados - 1] == S[topoPilhaBaseDados]) {
					S[topoPilhaBaseDados - 1] = 1;
				} else {
					S[topoPilhaBaseDados - 1] = 0;
				}
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 16:// CMDF
				if (S[topoPilhaBaseDados - 1] != S[topoPilhaBaseDados]) {
					S[topoPilhaBaseDados - 1] = 1;
				} else {
					S[topoPilhaBaseDados - 1] = 0;
				}
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 17:// CMEI
				if (S[topoPilhaBaseDados - 1] <= S[topoPilhaBaseDados]) {
					S[topoPilhaBaseDados - 1] = 1;
				} else {
					S[topoPilhaBaseDados - 1] = 0;
				}
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 18:// CMAI
				if (S[topoPilhaBaseDados - 1] >= S[topoPilhaBaseDados]) {
					S[topoPilhaBaseDados - 1] = 1;
				} else {
					S[topoPilhaBaseDados - 1] = 0;
				}
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 19:// DSVS
				apontarInstrucoes = segundoOperando;
				break;
			case 20:// DSVF
				if (S[topoPilhaBaseDados] == 0) {
					apontarInstrucoes = segundoOperando;
					// topo=topo-1; //A no material impresso esta linha está fora do "if"!
				}
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 21:// LEIT
				topoPilhaBaseDados = topoPilhaBaseDados + 1;
				leitura = JOptionPane.showInputDialog(null, "Informe o valor:", "Leitura", JOptionPane.QUESTION_MESSAGE);
				// System.out.print("Leia: "); A
				if(leitura == null || leitura.isEmpty()) {
					throw new Exception("Leitura de variável vazia");
				}
				(S[topoPilhaBaseDados]) = Integer.parseInt(leitura); // problema aqui A
				break;
			case 22:// IMPR
				JOptionPane.showMessageDialog(null, "" + S[topoPilhaBaseDados], "Informação", JOptionPane.INFORMATION_MESSAGE);
				// System.out.println(S[topo]); A
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
				break;
			case 23:// IMPRLIT
				if (segundoOperando >= AL.LIT) {
					throw new Exception("Literal não encontrado na área dos literais.");
					// System.out.println("ERRO >> Literal nao encontrada na area"); A
				} else {
					JOptionPane.showMessageDialog(null, "" + AL.AL[segundoOperando], "Informação", JOptionPane.INFORMATION_MESSAGE);
					// System.out.println(AL.AL[a]); A
					// AL.LIT++;
				}
				break;
			case 24:// AMEM
				topoPilhaBaseDados = topoPilhaBaseDados + segundoOperando;
				break;
			case 25:// CALL
				S[topoPilhaBaseDados + 1] = Base();
				S[topoPilhaBaseDados + 2] = baseSegmento;
				S[topoPilhaBaseDados + 3] = apontarInstrucoes;
				baseSegmento = topoPilhaBaseDados + 1;
				apontarInstrucoes = segundoOperando;
				break;
			case 26:
				// System.exit(0);
				// PARA
				break;
			case 27:
				// NADA
				break;
			case 28:// COPI
				topoPilhaBaseDados = topoPilhaBaseDados + 1;
				S[topoPilhaBaseDados] = S[topoPilhaBaseDados - 1];
				break;
			case 29:// DSVT
				if (S[topoPilhaBaseDados] == 1) {
					apontarInstrucoes = segundoOperando;
					// topo=topo-1; //A no material impresso esta linha está fora do "if"!
				}
				topoPilhaBaseDados = topoPilhaBaseDados - 1;
			}
		}
	}
}