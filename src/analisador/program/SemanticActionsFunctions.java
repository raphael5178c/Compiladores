package analisador.program;

import java.util.ArrayList;
import java.util.List;
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

		Semantico.hipotetica = new Hipotetica();
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
		Semantico.parametros = new ArrayList<Simbolo>();
		
		Semantico.ifs = new Stack<Integer>();
		Semantico.whiles = new Stack<Integer>();
		Semantico.repeats = new Stack<Integer>();
		Semantico.procedures = new Stack<Integer>();
		Semantico.cases = new Stack<Integer>();
		Semantico.fors = new Stack<Integer>();

		Semantico.tabelaSimbolos = new TableSymbols();
		Semantico.nomeProcedure = null;
		Semantico.tipo_identificador = null;
		Semantico.temParametro = false;
		Semantico.numeroParametros = 0;
		Semantico.numeroParametrosEfetivos = 0;
		Semantico.nomeContexto = null;
		Semantico.endIdentificador = 0;
		Semantico.lcProcedure = 0;
		Semantico.nome_atribuicao_esquerda = null;
		Semantico.nome_identificador = null;
		Semantico.numeroVariaveis = 0;
		Semantico.forEnd = null;
		
		Semantico.atribuicaoTemp = null;
		Semantico.constanteTemp = null;
		Semantico.procedureTemp = null;
		Semantico.forEnd = null;
		
		Semantico.hipotetica.InicializaAI(Semantico.areaInstrucoes);
		Semantico.hipotetica.InicializaAL(Semantico.areaLiterais);
	}

	public static void reconheceFinalPrograma(Token token) throws Exception {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 26, 0, 0);
		Simbolo simboloRotulo = new Simbolo(token, 0, TipoIdentificador.ROTULO);
		Simbolo rotuloEncontrado = Semantico.tabelaSimbolos.getValue(simboloRotulo);
		if(rotuloEncontrado != null) {
			throw new Exception(ExceptionUtil.getSemanticGeneralError("Impossivel finalizar programa, Ainda há rótulos declarados"));
		}
	}

	public static void afterDeclareVariable() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 24, 0, Semantico.acaoAcumulada);
		Semantico.acaoAcumulada = 3;
	}

	public static void afterLabelDeclareRotulo() {
		Semantico.tipo_identificador = TipoIdentificador.ROTULO;
	}

	public static void econtradoRotuloVarParametro(Token token) throws Exception {
        ++Semantico.acaoAcumulada;
        if (Semantico.tipo_identificador.equals(TipoIdentificador.ROTULO)) {
        	Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(new Simbolo(token, TipoIdentificador.ROTULO, Semantico.nivel_atual, token.getNome(), 0, 0));
            if (simboloSearched != null && simboloSearched.getNivelDeclaracao() == Semantico.nivel_atual) {
                throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Rótulo %s declarado no mesmo nível, na linha %s", token.getNome(), token.getCurrentlineNumber())));
            }
            Semantico.tabelaSimbolos.insertValue(new Simbolo(token, TipoIdentificador.ROTULO, Semantico.nivel_atual, token.getNome(), 0, 0));
        }
        if (Semantico.tipo_identificador.equals(TipoIdentificador.VARIAVEL)) {
        	Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(new Simbolo(token, TipoIdentificador.VARIAVEL, Semantico.nivel_atual, token.getNome(), 0, 0));
            if (simboloSearched != null) {
                throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Variável %s já foi declarada, na linha %s", token.getNome(), token.getCurrentlineNumber())));
            }
            Semantico.tabelaSimbolos.insertValue(new Simbolo(token, TipoIdentificador.VARIAVEL, Semantico.nivel_atual, token.getNome(), Semantico.deslocamento_conforme_base, 0));
            ++Semantico.deslocamento_conforme_base;
            ++Semantico.numeroVariaveis;
        }
        if (!Semantico.tipo_identificador.equals(TipoIdentificador.PARAMETRO)) return;
        Simbolo parametro = new Simbolo(token, TipoIdentificador.PARAMETRO, Semantico.nivel_atual, token.getNome(), 0, 0);
        Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(parametro);
        if (simboloSearched != null) {
            throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Parâmetro %s já declarado, na linha %s", token.getNome(), token.getCurrentlineNumber())));
        }
        Semantico.tabelaSimbolos.insertValue(parametro);
        Semantico.parametros.add(parametro);
        ++Semantico.numeroParametros;
	}

	public static void reconhecidoNomeConstanteDeclaracao(Token token) throws Exception {
        Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(new Simbolo(token, 0, token.getNome()));
        if (simboloSearched == null) {
        	Semantico.constanteTemp =  new Simbolo(token, TipoIdentificador.CONSTANTE, Semantico.nivel_atual, token.getNome(), 0, 0);
            Semantico.tabelaSimbolos.insertValue(Semantico.constanteTemp);
            Semantico.endIdentificador = Semantico.tabelaSimbolos.getValue(Semantico.constanteTemp).getQtValuesPilha();
            return;
        }
        throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Constante %s já está declarada, na linha %s", token.getNome(), token.getCurrentlineNumber())));
	}

	public static void reconhecidoValorConstanteDeclaracao(Token token) {
		Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(Semantico.constanteTemp);
		Semantico.tabelaSimbolos.deleteByValue(simboloSearched);
		simboloSearched.setGeralA(Integer.parseInt(token.getNome()));
		Semantico.tabelaSimbolos.insertValue(simboloSearched);
	}

	public static void beforeListaIdentificadoresDeclaracaoVariaveis() {
		Semantico.tipo_identificador = TipoIdentificador.VARIAVEL;
		Semantico.numeroVariaveis = 0;
	}

	public static void afterNomeProcedureDeclaracao(Token token) {
        Semantico.deslocamento_conforme_base = 3;
        Semantico.nomeProcedure = token.getNome();
        Semantico.lcProcedure = Semantico.areaInstrucoes.LC + 1;
        Semantico.procedureTemp = new Simbolo(token, TipoIdentificador.PROCEDURE, Semantico.nivel_atual, token.getNome(), Semantico.lcProcedure, 0);
        Semantico.tabelaSimbolos.insertValue(Semantico.procedureTemp);
        Semantico.temParametro = false;
        ++Semantico.nivel_atual;
        Semantico.numeroParametros = 0;
        Semantico.parametros = new ArrayList<Simbolo>();
	}

	public static void afterDeclaracaoProcedure(Token token) {
        if (Semantico.numeroParametros > 0) {
            List<Simbolo> listTemp = Semantico.parametros;
            for (int i=0; i<Semantico.numeroParametros; i++) {
            	listTemp.get(i).setGeralA((i==0) ? -Semantico.numeroParametros : -Semantico.numeroParametros-1);
            }
            Semantico.tabelaSimbolos.deleteByValue(Semantico.procedureTemp);
            Semantico.procedureTemp.setGeralB(Semantico.numeroParametros);
            Semantico.tabelaSimbolos.insertValue(Semantico.procedureTemp);
        }
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 19, 0, 0);
        Semantico.procedures.push(Semantico.areaInstrucoes.LC-1);
	}

	public static void fimProcedure() throws Exception {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 1, 0, Semantico.numeroParametros+1);
        int i = 0;
        while (i < Semantico.tabelaSimbolos.size()) {
        	Simbolo simbolLoopSearched = Semantico.tabelaSimbolos.getByQtValueInsertedNumber(i);
            if (simbolLoopSearched != null && simbolLoopSearched.getNomeSimbolo() != null && simbolLoopSearched.getCategoria().equals(TipoIdentificador.ROTULO)) {
                if (simbolLoopSearched.getGeralB() == 0) break;
                throw new Exception(ExceptionUtil.getSemanticGeneralError("Ainda Existem rótulos declarados..."));
            }
            ++i;
        }
        int valueProcedure = Semantico.procedures.pop();
        Semantico.hipotetica.AlterarAI(Semantico.areaInstrucoes, valueProcedure, 0, Semantico.areaInstrucoes.LC);
        --Semantico.nivel_atual;
	}

	public static void beforeParametrosFormaisProcedure(Token token) {
        Semantico.tipo_identificador = TipoIdentificador.PARAMETRO;
        Semantico.temParametro = true;
	}

	public static void identificadorInstrucaoRotuladaOrAtribuicao(Token token) {
		Semantico.nome_identificador = token.getNome();
	}

	public static void instrucaoRotulada() {
		return;
	}

	public static void atribuicaoParteEsquerda(Token token) throws Exception {
		Simbolo search = new Simbolo(token, TipoIdentificador.VARIAVEL, 0, token.getNome());
		search.setUseNivelSymbolCompare(true);
		Semantico.atribuicaoTemp = Semantico.tabelaSimbolos.getValue(search);
        if (Semantico.atribuicaoTemp == null) {
            throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Identificador %s não encontrado na linha %s", token.getNome(), token.getCurrentlineNumber())));    	
        }
        if (!Semantico.atribuicaoTemp.getCategoria().equals(TipoIdentificador.VARIAVEL)) {
        	throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Tentativa de atribuição da parte esquerda em simbiolo que não é variavel, Simbolo esquerdo %s, Linha %s", token.getNome(), token.getCurrentlineNumber())));
        }
        Semantico.nome_atribuicao_esquerda = token.getNome();
	}

	public static void afterExpressaoAtribuicao(Token token) throws Exception {
        if (ValueUtil.isEmpty(Semantico.nome_atribuicao_esquerda) || Semantico.atribuicaoTemp == null) {
            throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Erro na atribuição da variavel %s na linha %s", token.getNome(), token.getCurrentlineNumber())));
        }
        int d_nivel = Semantico.nivel_atual - Semantico.atribuicaoTemp.getNivelDeclaracao();
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 4, Semantico.atribuicaoTemp.getGeralA(), d_nivel);
        Semantico.atribuicaoTemp = null;
	}

	public static void chamadaProcedure(Token token) throws Exception {
		Simbolo search = new Simbolo(token, TipoIdentificador.PROCEDURE, Semantico.nivel_atual, token.getNome(), Semantico.lcProcedure, 0);
		search.setUseNivelSymbolCompare(true);
		Simbolo procedure = Semantico.tabelaSimbolos.getValue(search);
        if (procedure != null && procedure.getCategoria().equals(TipoIdentificador.PROCEDURE)) {
            Semantico.nomeProcedure = token.getNome();
            Semantico.procedureTemp = search;
            return;
        }
        throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Procedure %s não foi declarada", token.getNome())));
	}

	public static void afterCall(Token token) throws Exception {
		Simbolo procedure = Semantico.tabelaSimbolos.getValue(Semantico.procedureTemp);
		if (ValueUtil.isEmpty(procedure)) {
			throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Procedure %s não foi declarada", Semantico.nomeProcedure)));
		} else if(procedure.getGeralB() != Semantico.numeroParametros) {
			throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Número de parametros da procedure %s não conferem com o número de parâmetros recebidos", Semantico.nomeProcedure)));
		}
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 25, 0, procedure.getGeralA());
	}

	public static void afterExpressaoComandoCall() {
		Semantico.numeroParametrosEfetivos++;
	}

	public static void comandoGoto() {
		return;
	}

	public static void afterExpressaoNoIf() throws Exception {
		Semantico.ifs.push(Semantico.areaInstrucoes.LC - 1);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 20, 0, 0);
	}

	public static void afterInstrucaoIf() throws Exception {
        Semantico.hipotetica.AlterarAI(Semantico.areaInstrucoes, Semantico.ifs.pop(), 0, Semantico.areaInstrucoes.LC);
	}

	public static void afterDominionThenBeforeElse() throws Exception {
        Semantico.hipotetica.AlterarAI(Semantico.areaInstrucoes, Semantico.ifs.pop()+1, 0, Semantico.areaInstrucoes.LC + 1);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 19, 0, 0);
		Semantico.ifs.push(Semantico.areaInstrucoes.LC - 1);
	}

	public static void comandoWhileBeforeExpressao() throws Exception {
		Semantico.whiles.push(Semantico.areaInstrucoes.LC);
	}

	public static void comandoWhileAfterExpressao() throws Exception {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 20, 0, 0);
		Semantico.whiles.push(Semantico.areaInstrucoes.LC - 1);
	}

	public static void afterWhile() throws Exception {
		int posWhileInst = Semantico.whiles.pop();
        Semantico.hipotetica.AlterarAI(Semantico.areaInstrucoes, posWhileInst, 0, Semantico.areaInstrucoes.LC + 1);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 19, 0, posWhileInst);
	}

	public static void repeatInicio() {
		Semantico.repeats.push(Semantico.areaInstrucoes.LC);
	}

	public static void repeatFim() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 20, 0, Semantico.repeats.pop());
	}

	public static void readlnInicio() {
		Semantico.nomeContexto = Contexto.READLN;
	}

	public static void identificadorVariavel(Token token) throws Exception {
        if (Semantico.nomeContexto.equals(Contexto.READLN)) {
        	Simbolo search = new Simbolo(token, TipoIdentificador.VARIAVEL, (Semantico.nivel_atual-1 < 0) ? 0 : Semantico.nivel_atual-1, token.getNome(), 0, 0);
        	search.setIgnoreCategoriaEquals(true);
        	search.setUseNivelSymbolCompare(true);
        	Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(search);
            if (simboloSearched == null) {
            	throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Identificador %s não declarado na linha %s", token.getNome(), token.getCurrentlineNumber())));
            }
            int d_nivel = Semantico.nivel_atual - simboloSearched.getNivelDeclaracao();
            if (simboloSearched.getCategoria().equals(TipoIdentificador.VARIAVEL)) {
                Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 21, 0, 0);
                Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 4, simboloSearched.getGeralA(), d_nivel);
            } else {
                throw new Exception(ExceptionUtil.getSemanticGeneralError("Identificador não é uma variável"));
            }
        }
        if (!Semantico.nomeContexto.equals(Contexto.EXPRESSAO)) return;
        Simbolo search = new Simbolo(token, TipoIdentificador.CONSTANTE, Semantico.nivel_atual, token.getNome(), 0, 0);
        search.setIgnoreCategoriaEquals(true);
        search.setUseNivelSymbolCompare(true);
    	Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(search);
        if (simboloSearched == null) {
            throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Identificador %s não declarado na linha %s", token.getNome(), token.getCurrentlineNumber())));
        }
        int d_nivel = Semantico.nivel_atual - simboloSearched.getNivelDeclaracao();
        if (simboloSearched.getCategoria().equals(TipoIdentificador.PROCEDURE) || simboloSearched.getCategoria().equals(TipoIdentificador.ROTULO)) {
        	throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Identificador %s não é uma constante na linha %s", token.getNome(), token.getCurrentlineNumber())));
        }
        if (simboloSearched.getCategoria().equals(TipoIdentificador.CONSTANTE)) {
            Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 3, 0, simboloSearched.getGeralA());
            return;
        }
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 2, d_nivel, simboloSearched.getGeralA());
	}

	public static void afterLiteralNaInstrucaoWriteln(Token tokenLiteral) {
		Semantico.hipotetica.IncluirAL(Semantico.areaLiterais, tokenLiteral.getNome());
		Semantico.ponteiro_area_literais++;
		Semantico.instrucoesHipotetica.insertLiteral(tokenLiteral.getNome(), Semantico.areaLiterais.LIT - 1);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 23, 0, Semantico.areaLiterais.LIT - 1);
	}

	public static void writelnAfterExpressao() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 22, 0, 0);
	}

	public static void afterReservadaCase() {
		return;
	}

	public static void afterComandoCase() {
//        this.instrucoes.alteraInstrucao(this.cases.veTopo(), 0, this.AI.LC + 1);
//        Semantico.hipotetica.AlterarAI(this.AI, this.cases.veTopo(), 0, this.AI.LC);
//        this.cases.tiraElemento();
//        this.instrucoes.insereInstrucao(24, 0, -1);
//        this.maquinaHipotetica.IncluirAI(this.AI, 24, 0, -1);
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

	public static void afterVariavelControleFor(Token token) throws Exception {
        Simbolo search = new Simbolo(token, TipoIdentificador.VARIAVEL, Semantico.nivel_atual, token.getNome(), 0, 0);
        search.setUseNivelSymbolCompare(true);
        Semantico.forEnd = Semantico.tabelaSimbolos.getValue(search);
    	if(Semantico.forEnd == null) {
    		throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Identificador %s não declarado na linha %s, ou não é uma variável ", token.getNome(), token.getCurrentlineNumber())));
    	}
	}
	
	public static void afterExpressaoValorInicial() {
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 4, Semantico.forEnd.getGeralB(), Semantico.forEnd.getGeralA());
	}

	public static void afterExpressaoValorFinal(Token token) throws Exception {
        Semantico.fors.push(Semantico.areaInstrucoes.LC);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 28, 0 ,0);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 2, Semantico.forEnd.getGeralB(), Semantico.forEnd.getGeralA());
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 18, 0 ,0);
        Semantico.fors.push(Semantico.areaInstrucoes.LC);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 20, 0 ,0);
	}

	public static void afterComandoEmFor() throws Exception {
		int difNivel = Semantico.nivel_atual - Semantico.forEnd.getNivelDeclaracao();
		int desNivel = Semantico.forEnd.getGeralA();
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 2, difNivel, Semantico.forEnd.getGeralA());
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 3, 0, 1);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 5, 0, 0);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 4, difNivel, desNivel);
        int forPos = Semantico.fors.pop();
        int forAfterPos = Semantico.fors.pop();
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 19, 0, forAfterPos);
        Semantico.hipotetica.AlterarAI(Semantico.areaInstrucoes, forPos, -1, Semantico.areaInstrucoes.LC);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 24, 0, -1);
	}

	public static void comparacao01() { // =
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 15, 0, 0);
	}

	public static void comparacao02() { // <
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 13, 0, 0);
	}

	public static void comparacao03() { // >
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 14, 0, 0);
	}

	public static void comparacao04() { // >=
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 18, 0, 0);
	}

	public static void comparacao05() { // <=
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 17, 0, 0);
	}

	public static void comparacao06() { // <>
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 16, 0, 0);
	}

	public static void expressaoOperandoSinalUnario() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 9, 0, 0);
	}

	public static void expressaoSoma() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 5, 0, 0);
	}

	public static void expressaoSubtracao() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 6, 0, 0);
	}

	public static void expressaoOr() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 12, 0, 0);
	}

	public static void expressaoMultiplica() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 7, 0, 0);
	}

	public static void expressaoDivisao() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 8, 0, 0);
	}

	public static void expressaoAnd() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 11, 0, 0);
	}

	public static void expressaoInt(Token token) {
        int pen = Integer.parseInt(token.getNome());
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 3, 0, pen);
	}

	public static void expressaoNot() {
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 10, 0, 0);
	}

	public static void expressaoVariavel() {
		Semantico.nomeContexto = Contexto.EXPRESSAO;
	}

}
