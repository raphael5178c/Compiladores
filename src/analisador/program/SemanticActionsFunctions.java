package analisador.program;

import java.util.Stack;

import analisador.domain.Contexto;
import analisador.domain.Simbolo;
import analisador.domain.TipoIdentificador;
import analisador.domain.Token;
import analisador.hipotetica.AreaInstrucoes;
import analisador.hipotetica.AreaLiterais;
import analisador.hipotetica.Hipotetica;
import analisador.hipotetica.InstrucoesHipotetica;
import analisador.util.ExceptionUtil;
import analisador.util.TableSymbols;
import analisador.util.ValueUtil;

public class SemanticActionsFunctions {

	public static void reconheceNomePrograma() {
		Semantico.instrucoesHipotetica = new InstrucoesHipotetica();
		
		Semantico.areaInstrucoes = new AreaInstrucoes();
		Semantico.areaLiterais = new AreaLiterais();
		
		Semantico.nivel_atual = 0;
		Semantico.deslocamento_conforme_base = 3;
		Semantico.proxima_Instrucao = 1;
		Semantico.ponteiro_area_literais = 1;
		Semantico.acaoAcumulada = 3;
		
		Semantico.temParametro = false;
		Semantico.numeroParametros = 0;
		Semantico.numeroParametrosEfetivos = 0;
		
		Semantico.ifs = new Stack<Integer>();
		Semantico.whiles = new Stack<Integer>();
		Semantico.repeats = new Stack<Integer>();
		Semantico.procedures = new Stack<Integer>();
		Semantico.parametros = new Stack<Integer>();
		Semantico.cases = new Stack<Integer>();
		Semantico.fors = new Stack<Integer>();
		Semantico.tabelaSimbolos = new TableSymbols();
		
		Semantico.hipotetica = new Hipotetica();
		Hipotetica.InicializaAI(Semantico.areaInstrucoes);
		Hipotetica.InicializaAL(Semantico.areaLiterais);
	}

	public static void reconheceFinalPrograma(Token token) throws Exception {
		Semantico.instrucoesHipotetica.insert(26);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 26, 0, 0);
		
		Simbolo simboloRotulo = new Simbolo(token, 0, TipoIdentificador.ROTULO);
		Simbolo rotuloEncontrado = Semantico.tabelaSimbolos.getValue(simboloRotulo);
		if(rotuloEncontrado != null) {
			throw new Exception(ExceptionUtil.getSemanticGeneralError("Impossivel finalizar programa, Ainda há rótulos declarados"));
		}
	}

	public static void afterDeclareVariable() {
		Semantico.instrucoesHipotetica.insert(24);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 24, 0, Semantico.acaoAcumulada);
		Semantico.acaoAcumulada = 3;
	}

	public static void afterLabelDeclareRotulo() {
		Semantico.tipo_identificador = TipoIdentificador.ROTULO;
	}

	public static void econtradoRotuloVarParametro() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void reconhecidoNomeConstanteDeclaracao() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void reconhecidoValorConstanteDeclaracao() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void beforeListaIdentificadoresDeclaracaoVariaveis() {
		Semantico.tipo_identificador = TipoIdentificador.VARIAVEL;
		Semantico.nivel_atual = 0;
	}

	public static void afterNomeProcedureDeclaracao() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void afterDeclaracaoProcedure() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void fimProcedure() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void beforeParametrosFormaisProcedure() {
        Semantico.tipo_identificador = TipoIdentificador.PARAMETRO;
        Semantico.temParametro = true;
	}

	public static void identificadorInstrucaoRotuladaOrAtribuicao() {
		return;
	}

	public static void instrucaoRotulada() {
		return;
	}

	public static void atribuicaoParteEsquerda() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void afterExpressaoAtribuicao() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void chamadaProcedure() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void afterCall(Token token) throws Exception {
		Simbolo procedure = Semantico.tabelaSimbolos.getValue(new Simbolo(token, 0, Semantico.nomeProcedure));
		if (ValueUtil.isEmpty(procedure)) {
			throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Procedure %s não foi declarada", Semantico.nomeProcedure)));
		} else if(procedure.getGeralB() != Semantico.numeroParametros) {
			throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Número de parametros da procedure %s não conferem com o número de parâmetros recebidos", Semantico.nomeProcedure)));
		}
		Semantico.instrucoesHipotetica.insert(25, 0, procedure.getGeralA() + 1);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 25, 0, procedure.getGeralA());
	}

	public static void afterExpressaoComandoCall() {
		Semantico.numeroParametrosEfetivos++;
	}

	public static void comandoGoto() {
		return;
	}

	public static void afterExpressaoNoIf() {
		Semantico.instrucoesHipotetica.insert(20);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 20, 0, 0);
		Semantico.ifs.add(Semantico.areaInstrucoes.LC - 1);
	}

	public static void afterInstrucaoIf() {
		int ifsTop = Semantico.ifs.pop();
		
		// TODO Arrumar forma de altera a instrução
		//Semantico.instrucoesHipotetica.alteraInstrucao(ifsTop, 0, Semantico.areaInstrucoes.LC + 1);
		Hipotetica.AlterarAI(Semantico.areaInstrucoes, ifsTop, 0, Semantico.areaInstrucoes.LC);
	}

	public static void afterDominionThenBeforeElse() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void comandoWhileBeforeExpressao() {
		Semantico.whiles.push(Semantico.areaInstrucoes.LC);
	}

	public static void comandoWhileAfterExpressao() {
		Semantico.instrucoesHipotetica.insert(20, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 20, 0, 0);
		Semantico.whiles.push(Semantico.areaInstrucoes.LC - 1);
	}

	public static void afterWhile() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void repeatInicio() {
		Semantico.repeats.push(Semantico.areaInstrucoes.LC);
	}

	public static void repeatFim() {
		int repeatsTop = Semantico.repeats.pop();
		Semantico.instrucoesHipotetica.insert(20, 0, repeatsTop + 1);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 20, 0, repeatsTop);
	}

	public static void readlnInicio() {
		Semantico.nomeContexto = Contexto.READLN;
	}

	public static void identificadorVariavel() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void afterLiteralNaInstrucaoWriteln(Token tokenLiteral) {
		Hipotetica.IncluirAL(Semantico.areaLiterais, tokenLiteral.getNome());
		Semantico.ponteiro_area_literais++;
		Semantico.instrucoesHipotetica.insert(23, 0, Semantico.areaLiterais.LIT - 1);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 23, 0, Semantico.areaLiterais.LIT - 1);
	}

	public static void writelnAfterExpressao() {
		Semantico.instrucoesHipotetica.insert(22);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 22, 0, 0);
	}

	public static void afterReservadaCase() {
		return;
	}

	public static void afterComandoCase() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void ramoCaseAfterInteiroUltimoLista() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void afterComandoEmCase() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void ramoCaseAfterInteiro() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void afterVariavelControleFor() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void afterExpressaoValorInicial() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void afterExpressaoValorFinal() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void afterComandoEmFor() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void comparacao01() { // =
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 15, 0, 0);
	}

	public static void comparacao02() { // <
		Semantico.instrucoesHipotetica.insert(13);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 13, 0, 0);
	}

	public static void comparacao03() { // >
		Semantico.instrucoesHipotetica.insert(14);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 14, 0, 0);
	}

	public static void comparacao04() { // >=
		Semantico.instrucoesHipotetica.insert(18);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 18, 0, 0);
	}

	public static void comparacao05() { // <=
		Semantico.instrucoesHipotetica.insert(17);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 17, 0, 0);
	}

	public static void comparacao06() { // <>
		Semantico.instrucoesHipotetica.insert(16);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 16, 0, 0);
	}

	public static void expressaoOperandoSinalUnario() {
		Semantico.instrucoesHipotetica.insert(9);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 9, 0, 0);
	}

	public static void expressaoSoma() {
		Semantico.instrucoesHipotetica.insert(5);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 5, 0, 0);
	}

	public static void expressaoSubtracao() {
		Semantico.instrucoesHipotetica.insert(6);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 6, 0, 0);
	}

	public static void expressaoOr() {
		Semantico.instrucoesHipotetica.insert(12);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 12, 0, 0);
	}

	public static void expressaoMultiplica() {
		Semantico.instrucoesHipotetica.insert(7);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 7, 0, 0);
	}

	public static void expressaoDivisao() {
		Semantico.instrucoesHipotetica.insert(8);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 8, 0, 0);
	}

	public static void expressaoAnd() {
		Semantico.instrucoesHipotetica.insert(11);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 11, 0, 0);
	}

	public static void expressaoInt() {
		// TODO Falta Implementar essa ação Semântica
	}

	public static void expressaoNot() {
		Semantico.instrucoesHipotetica.insert(10);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 10, 0, 0);
	}

	public static void expressaoVariavel() {
		Semantico.nomeContexto = Contexto.EXPRESSAO;
	}

}
