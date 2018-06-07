package analisador.program;

import java.util.Stack;

import analisador.domain.Contexto;
import analisador.domain.Instrucao;
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
		Semantico.instrucoesHipotetica.insert(26, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 26, 0, 0);
		
		Simbolo simboloRotulo = new Simbolo(token, 0, TipoIdentificador.ROTULO);
		Simbolo rotuloEncontrado = Semantico.tabelaSimbolos.getValue(simboloRotulo);
		if(rotuloEncontrado != null) {
			throw new Exception(ExceptionUtil.getSemanticGeneralError("Impossivel finalizar programa, Ainda há rótulos declarados"));
		}
	}

	public static void afterDeclareVariable() {
		Semantico.instrucoesHipotetica.insert(24, 0, Semantico.acaoAcumulada);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 24, 0, Semantico.acaoAcumulada);
		Semantico.acaoAcumulada = 3;
	}

	public static void afterLabelDeclareRotulo() {
		Semantico.tipo_identificador = TipoIdentificador.ROTULO;
	}

	public static void econtradoRotuloVarParametro(Token token) throws Exception {
        ++Semantico.acaoAcumulada;
        Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(new Simbolo(token, TipoIdentificador.PARAMETRO, Semantico.nivel_atual, token.getNome(), 0, 0));
        if (Semantico.tipo_identificador.equals(TipoIdentificador.ROTULO)) {
            if (simboloSearched != null && simboloSearched.getNivelDeclaracao() == Semantico.nivel_atual) {
                throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Rótulo %s declarado no mesmo nível, na linha %s", token.getNome(), token.getCurrentlineNumber())));
            }
            Semantico.tabelaSimbolos.insertValue(new Simbolo(token, TipoIdentificador.ROTULO, Semantico.nivel_atual, token.getNome(), 0, 0));
        }
        if (Semantico.tipo_identificador.equals(TipoIdentificador.VARIAVEL)) {
            if (simboloSearched != null) {
                throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Variável %s já foi declarada, na linha %s", token.getNome(), token.getCurrentlineNumber())));
            }
            Semantico.tabelaSimbolos.insertValue(new Simbolo(token, TipoIdentificador.VARIAVEL, Semantico.nivel_atual, token.getNome(), Semantico.deslocamento_conforme_base, 0));
            ++Semantico.deslocamento_conforme_base;
            ++Semantico.nivel_atual;
        }
        if (!Semantico.tipo_identificador.equals(TipoIdentificador.PARAMETRO)) return;
        if (simboloSearched != null) {
            if (simboloSearched.getNivelDeclaracao() == Semantico.nivel_atual) {
                throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Parâmetro %s declarado no mesmo nível, na linha %s", token.getNome(), token.getCurrentlineNumber())));
            }
            Semantico.tabelaSimbolos.insertValue(new Simbolo(token, TipoIdentificador.PARAMETRO, Semantico.nivel_atual, token.getNome(), 0, 0));
            Semantico.parametros.push(simboloSearched.getQtValuesPilha());
            ++Semantico.numeroParametros;
            return;
        }
        Semantico.tabelaSimbolos.insertValue(new Simbolo(token, TipoIdentificador.PARAMETRO, Semantico.nivel_atual, token.getNome(), 0, 0));
        Semantico.parametros.push((simboloSearched != null) ? simboloSearched.getQtValuesPilha() : -1);
        ++Semantico.numeroParametros;
	}

	public static void reconhecidoNomeConstanteDeclaracao(Token token) throws Exception {
        Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(new Simbolo(token, 0, token.getNome()));
        if (simboloSearched == null) {
        	Simbolo simboloInsertAndSearch = new Simbolo(token, TipoIdentificador.CONSTANTE, Semantico.nivel_atual, token.getNome(), 0, 0);
            Semantico.tabelaSimbolos.insertValue(simboloInsertAndSearch);
            Semantico.endIdentificador = Semantico.tabelaSimbolos.getValue(simboloInsertAndSearch).getQtValuesPilha();
            return;
        }
        throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Constante %s já está declarada, na linha %s", token.getNome(), token.getCurrentlineNumber())));
	}

	public static void reconhecidoValorConstanteDeclaracao(Token token) {
		Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(new Simbolo(token, 0, token.getNome()));
		Semantico.tabelaSimbolos.deleteByValue(simboloSearched);
		simboloSearched.setGeralA(Semantico.endIdentificador);
		Semantico.tabelaSimbolos.insertValue(simboloSearched);
	}

	public static void beforeListaIdentificadoresDeclaracaoVariaveis() {
		Semantico.tipo_identificador = TipoIdentificador.VARIAVEL;
		Semantico.nivel_atual = 0;
	}

	public static void afterNomeProcedureDeclaracao(Token token) {
		Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(new Simbolo(token, 0, token.getNome()));
        Semantico.deslocamento_conforme_base = 3;
        Semantico.nomeProcedure = token.getNome();
        Semantico.lcProcedure = Semantico.areaInstrucoes.LC + 1;
        Semantico.tabelaSimbolos.insertValue(new Simbolo(token, TipoIdentificador.PROCEDURE, Semantico.nivel_atual, token.getNome(), Semantico.lcProcedure, 0));
        Semantico.temParametro = false;
        Semantico.parametros.push((simboloSearched != null) ? simboloSearched.getQtValuesPilha() : -1);
        ++Semantico.nivel_atual;
        Semantico.numeroParametros = 0;
	}

	public static void afterDeclaracaoProcedure(Token token) {
        if (Semantico.numeroParametros > 0) {
        	Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(new Simbolo(token, TipoIdentificador.PROCEDURE, Semantico.nivel_atual-1, Semantico.nomeProcedure, Semantico.lcProcedure, 0));
        	simboloSearched.setNumeroParametro(Semantico.numeroParametros);
            int i = 0;
            while (i < Semantico.numeroParametros) {
            	Simbolo simboloByQtParameter = Semantico.tabelaSimbolos.getByQtValueInsertedNumber(Semantico.parametros.pop());
            	if(simboloByQtParameter != null) {
            		simboloByQtParameter.setGeralA(-Semantico.numeroParametros - i);
            	}
                ++i;
            }
        }
        Semantico.instrucoesHipotetica.insert(19, 0, 0);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 19, 0, 0);
        Semantico.procedures.push(Semantico.areaInstrucoes.LC-1);
        Semantico.parametros.push(Semantico.numeroParametros);
	}

	public static void fimProcedure() throws Exception {
		Semantico.parametros.pop();
		Instrucao instrucaoToAlterAfter = new Instrucao(InstrucoesHipotetica.instrucaoHipotetica[1], 1, 0, Semantico.numeroParametros+1);
		Semantico.instrucoesHipotetica.insert(instrucaoToAlterAfter);
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
        Instrucao instrucaoNew = new Instrucao(InstrucoesHipotetica.instrucaoHipotetica[1], instrucaoToAlterAfter.instrucaoHip, instrucaoToAlterAfter.geralA, instrucaoToAlterAfter.geralB);
        Semantico.instrucoesHipotetica.alterInstrucao(instrucaoToAlterAfter, instrucaoNew);
        Hipotetica.AlterarAI(Semantico.areaInstrucoes, valueProcedure, 0, Semantico.areaInstrucoes.LC);
        Semantico.tabelaSimbolos = new TableSymbols();
        --Semantico.nivel_atual;
	}

	public static void beforeParametrosFormaisProcedure() {
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
		Semantico.atribuicaoTemp = Semantico.tabelaSimbolos.getValue(new Simbolo(token, TipoIdentificador.VARIAVEL, 0, token.getNome()));
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
        Semantico.instrucoesHipotetica.insert(4, d_nivel, Semantico.atribuicaoTemp.getGeralA());
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 4, d_nivel, Semantico.atribuicaoTemp.getGeralA());
        Semantico.atribuicaoTemp = null;
	}

	public static void chamadaProcedure(Token token) throws Exception {
		Simbolo procedure = Semantico.tabelaSimbolos.getValue(new Simbolo(token, 0, Semantico.nomeProcedure));
        if (procedure != null && procedure.getCategoria().equals(TipoIdentificador.PROCEDURE)) {
            Semantico.nomeProcedure = token.getNome();
        }
        throw new Exception(ExceptionUtil.getSemanticGeneralError(String.format("Procedure %s não foi declarada", Semantico.nomeProcedure)));
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
		Semantico.instrucaoIfTemp = new Instrucao(InstrucoesHipotetica.instrucaoHipotetica[20], 20, 0, 0);
		Semantico.instrucoesHipotetica.insert(Semantico.instrucaoIfTemp);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 20, 0, 0);
		Semantico.ifs.add(Semantico.areaInstrucoes.LC - 1);
	}

	public static void afterInstrucaoIf() {
		int ifsTop = Semantico.ifs.pop();
		Instrucao instrucaoNew = new Instrucao(InstrucoesHipotetica.instrucaoHipotetica[ifsTop], ifsTop, 0, Semantico.areaInstrucoes.LC + 1);
        Semantico.instrucoesHipotetica.alterInstrucao(Semantico.instrucaoIfTemp, instrucaoNew);
        Hipotetica.AlterarAI(Semantico.areaInstrucoes, ifsTop, 0, Semantico.areaInstrucoes.LC);
	}

	public static void afterDominionThenBeforeElse() {
		int ifsTop = Semantico.ifs.pop();
		Instrucao instrucaoNew = new Instrucao(InstrucoesHipotetica.instrucaoHipotetica[ifsTop], ifsTop, 0, Semantico.areaInstrucoes.LC + 2);
        Semantico.instrucoesHipotetica.alterInstrucao(Semantico.instrucaoIfTemp, instrucaoNew);
        Hipotetica.AlterarAI(Semantico.areaInstrucoes, ifsTop, 0, Semantico.areaInstrucoes.LC + 1);
        Semantico.instrucoesHipotetica.insert(19, 0, 0);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 19, 0, 0);
        Semantico.ifs.push(Semantico.areaInstrucoes.LC - 1);
	}

	public static void comandoWhileBeforeExpressao() {
		Semantico.whiles.push(Semantico.areaInstrucoes.LC);
	}

	public static void comandoWhileAfterExpressao() {
		Semantico.instrucaoWhileTemp = new Instrucao(InstrucoesHipotetica.instrucaoHipotetica[20], 20, 0, 0);
		Semantico.instrucoesHipotetica.insert(Semantico.instrucaoWhileTemp);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 20, 0, 0);
		Semantico.whiles.push(Semantico.areaInstrucoes.LC - 1);
	}

	public static void afterWhile() {
		Instrucao instrucaoNew = new Instrucao(InstrucoesHipotetica.instrucaoHipotetica[20], 20, 0, Semantico.areaInstrucoes.LC + 2);
        Semantico.instrucoesHipotetica.alterInstrucao(Semantico.instrucaoWhileTemp, instrucaoNew);
        Semantico.instrucaoWhileTemp = null;
        Hipotetica.AlterarAI(Semantico.areaInstrucoes, Semantico.whiles.pop(), 0, Semantico.areaInstrucoes.LC + 1);
        int posWhileInst = Semantico.whiles.pop();
        Semantico.instrucoesHipotetica.insert(19, 0, posWhileInst);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 19, 0, posWhileInst);
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

	public static void identificadorVariavel(Token token) throws Exception {
    	Simbolo simboloSearched = Semantico.tabelaSimbolos.getValue(new Simbolo(token, TipoIdentificador.VARIAVEL, Semantico.nivel_atual-1, token.getNome(), 0, 0));
        if (simboloSearched == null) {
            throw new Exception(ExceptionUtil.getSemanticGeneralError("Identificador não declarado"));
        }
        int d_nivel = Semantico.nivel_atual - simboloSearched.getNivelDeclaracao();
        if (Semantico.nomeContexto.equals(Contexto.READLN)) {
            if (simboloSearched.getCategoria().equals(TipoIdentificador.VARIAVEL)) {
                Semantico.instrucoesHipotetica.insert(21, 0, 0);
                Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 21, 0, 0);
                Semantico.instrucoesHipotetica.insert(4, d_nivel, simboloSearched.getGeralA());
                Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 4, d_nivel, simboloSearched.getGeralA());
            } else {
                throw new Exception(ExceptionUtil.getSemanticGeneralError("Identificador não é uma variável"));
            }
        }
        if (!Semantico.nomeContexto.equals(Contexto.EXPRESSAO)) return;
        if (simboloSearched.getCategoria().equals(TipoIdentificador.PROCEDURE) || simboloSearched.getCategoria().equals(TipoIdentificador.ROTULO)) {
        	throw new Exception(ExceptionUtil.getSemanticGeneralError("Identificador não é uma constante"));
        }
        if (simboloSearched.getCategoria().equals(TipoIdentificador.CONSTANTE)) {
            Semantico.instrucoesHipotetica.insert(3, 0, simboloSearched.getGeralA());
            Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 3, 0, simboloSearched.getGeralA());
            return;
        }
        Semantico.instrucoesHipotetica.insert(2, d_nivel, simboloSearched.getGeralA());
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 2, d_nivel, simboloSearched.getGeralA());
	}

	public static void afterLiteralNaInstrucaoWriteln(Token tokenLiteral) {
		Hipotetica.IncluirAL(Semantico.areaLiterais, tokenLiteral.getNome());
		Semantico.ponteiro_area_literais++;
		Semantico.instrucoesHipotetica.insert(23, 0, Semantico.areaLiterais.LIT - 1);
		Semantico.instrucoesHipotetica.insertLiteral(tokenLiteral.getNome(), Semantico.areaLiterais.LIT - 1);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 23, 0, Semantico.areaLiterais.LIT - 1);
	}

	public static void writelnAfterExpressao() {
		Semantico.instrucoesHipotetica.insert(22, 0, 0);
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
		Semantico.instrucoesHipotetica.insert(13, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 13, 0, 0);
	}

	public static void comparacao03() { // >
		Semantico.instrucoesHipotetica.insert(14, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 14, 0, 0);
	}

	public static void comparacao04() { // >=
		Semantico.instrucoesHipotetica.insert(18, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 18, 0, 0);
	}

	public static void comparacao05() { // <=
		Semantico.instrucoesHipotetica.insert(17, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 17, 0, 0);
	}

	public static void comparacao06() { // <>
		Semantico.instrucoesHipotetica.insert(16, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 16, 0, 0);
	}

	public static void expressaoOperandoSinalUnario() {
		Semantico.instrucoesHipotetica.insert(9, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 9, 0, 0);
	}

	public static void expressaoSoma() {
		Semantico.instrucoesHipotetica.insert(5, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 5, 0, 0);
	}

	public static void expressaoSubtracao() {
		Semantico.instrucoesHipotetica.insert(6, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 6, 0, 0);
	}

	public static void expressaoOr() {
		Semantico.instrucoesHipotetica.insert(12, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 12, 0, 0);
	}

	public static void expressaoMultiplica() {
		Semantico.instrucoesHipotetica.insert(7, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 7, 0, 0);
	}

	public static void expressaoDivisao() {
		Semantico.instrucoesHipotetica.insert(8, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 8, 0, 0);
	}

	public static void expressaoAnd() {
		Semantico.instrucoesHipotetica.insert(11, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 11, 0, 0);
	}

	public static void expressaoInt(Token token) {
        int pen = Integer.parseInt(token.getNome());
        Semantico.instrucoesHipotetica.insert(3, 0, pen);
        Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 3, 0, pen);
	}

	public static void expressaoNot() {
		Semantico.instrucoesHipotetica.insert(10, 0, 0);
		Semantico.hipotetica.IncluirAI(Semantico.areaInstrucoes, 10, 0, 0);
	}

	public static void expressaoVariavel() {
		Semantico.nomeContexto = Contexto.EXPRESSAO;
	}

}
