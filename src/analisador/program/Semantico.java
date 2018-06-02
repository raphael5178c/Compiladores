package analisador.program;

import analisador.constants.SemanticActionsConstant;
import analisador.domain.Token;
import analisador.util.ExceptionUtil;

public class Semantico {
	
	public static int nivel_atual;
	public static int proxima_Instrucao;
	public static int ponteiro_area_literais;
	public static int deslocamento_conforme_base;
	
	public static void gerenciaAcoesSemanticas(int acaoSemantica, Token token) throws Exception {
		switch (acaoSemantica) {
			case SemanticActionsConstant.RECONHECE_NOME_PROGRAMA:
				SemanticActionsFunctions.reconheceNomePrograma();
			break;
			case SemanticActionsConstant.RECONHECE_FINAL_PROGRAMA:
				SemanticActionsFunctions.reconheceFinalPrograma();
			break;
			case SemanticActionsConstant.APOS_DECLARACAO_VARIAVEL:
				SemanticActionsFunctions.afterDeclareVariable();
			break;
			case SemanticActionsConstant.APOS_LABEL_DECLARACAO_ROTULO:
				SemanticActionsFunctions.afterLabelDeclareRotulo();
			break;
			case SemanticActionsConstant.ENCONTRADO_ROTULO_VAR_PARAMETRO:
				SemanticActionsFunctions.econtradoRotuloVarParametro();
			break;
			case SemanticActionsConstant.RECONHECIDO_NOME_CONSTANTE_DECLARACAO:
				SemanticActionsFunctions.reconhecidoNomeConstanteDeclaracao();
			break;
			case SemanticActionsConstant.RECONHECIDO_VALOR_CONSTANTE_DECLARACAO:
				SemanticActionsFunctions.reconhecidoValorConstanteDeclaracao();
			break;
			case SemanticActionsConstant.BEFORE_LISTA_IDENTIFICADORES_DECLARACAO_VARIAVEIS:
				SemanticActionsFunctions.beforeListaIdentificadoresDeclaracaoVariaveis();
			break;
			case SemanticActionsConstant.AFTER_NOME_PROCEDURE_DECLARACAO:
				SemanticActionsFunctions.afterNomeProcedureDeclaracao();
			break;
			case SemanticActionsConstant.AFTER_DECLARACAO_PROCEDURE:
				SemanticActionsFunctions.afterDeclaracaoProcedure();
			break;
			case SemanticActionsConstant.FIM_PROCEDURE:
				SemanticActionsFunctions.fimProcedure();
			break;
			case SemanticActionsConstant.BEFORE_PARAMETROS_FORMAIS_PROCEDURE:
				SemanticActionsFunctions.beforeParametrosFormaisProcedure();
			break;
			case SemanticActionsConstant.IDENTIFICADOR_INSTRUCAO_ROTULADA_OU_ATRIBUICAO:
				SemanticActionsFunctions.identificadorInstrucaoRotuladaOrAtribuicao();
			break;
			case SemanticActionsConstant.INSTRUCAO_ROTULADA:
				SemanticActionsFunctions.instrucaoRotulada();
			break;
			case SemanticActionsConstant.ATRIBUICAO_PARTE_ESQUERDA:
				SemanticActionsFunctions.atribuicaoParteEsquerda();
			break;
			case SemanticActionsConstant.AFTER_EXPRESSAO_ATRIBUICAO:
				SemanticActionsFunctions.afterExpressaoAtribuicao();
			break;
			case SemanticActionsConstant.CHAMADA_PROCEDURE:
				SemanticActionsFunctions.chamadaProcedure();
			break;
			case SemanticActionsConstant.AFTER_CALL:
				SemanticActionsFunctions.afterCall();
			break;
			case SemanticActionsConstant.AFTER_EXPRESSAO_COMANDO_CALL:
				SemanticActionsFunctions.afterExpressaoComandoCall();
			break;
			case SemanticActionsConstant.COMANDO_GOTO:
				SemanticActionsFunctions.comandoGoto();
			break;
			case SemanticActionsConstant.AFTER_EXPRESSAO_NUM_IF:
				SemanticActionsFunctions.afterExpressaoNoIf();
			break;
			case SemanticActionsConstant.AFTER_INSTRUCAO_IF:
				SemanticActionsFunctions.afterInstrucaoIf();
			break;
			case SemanticActionsConstant.AFTER_DOMINIO_THEN_BEFORE_ELSE:
				SemanticActionsFunctions.afterDominionThenBeforeElse();
			break;
			case SemanticActionsConstant.COMANDO_WHILE_BEFORE_EXPRESSAO:
				SemanticActionsFunctions.comandoWhileBeforeExpressao();
			break;
			case SemanticActionsConstant.COMANDO_WHILE_AFTER_EXPRESSAO:
				SemanticActionsFunctions.comandoWhileAfterExpressao();
			break;
			case SemanticActionsConstant.AFTER_WHILE:
				SemanticActionsFunctions.afterWhile();
			break;
			case SemanticActionsConstant.REPEAT_INICIO:
				SemanticActionsFunctions.repeatInicio();
			break;
			case SemanticActionsConstant.REPEAT_FIM:
				SemanticActionsFunctions.repeatFim();
			break;
			case SemanticActionsConstant.READLN_INICIO:
				SemanticActionsFunctions.readlnInicio();
			break;
			case SemanticActionsConstant.IDENTIFICADOR_VARIAVEL:
				SemanticActionsFunctions.identificadorVariavel();
			break;
			case SemanticActionsConstant.AFTER_LITERAL_NA_INSTRUCAO_WRITELN:
				SemanticActionsFunctions.afterLiteralNaInstrucaoWriteln();
			break;
			case SemanticActionsConstant.WRITELN_AFTER_EXPRESSAO:
				SemanticActionsFunctions.writelnAfterExpressao();
			break;
			case SemanticActionsConstant.AFTER_RESERVADA_CASE:
				SemanticActionsFunctions.afterReservadaCase();
			break;
			case SemanticActionsConstant.AFTER_COMANDO_CASE:
				SemanticActionsFunctions.afterComandoCase();
			break;
			case SemanticActionsConstant.RAMO_CASE_AFTER_INTEIRO_ULTIMO_LISTA:
				SemanticActionsFunctions.ramoCaseAfterInteiroUltimoLista();
			break;
			case SemanticActionsConstant.AFTER_COMANDO_EM_CASE:
				SemanticActionsFunctions.afterComandoEmCase();
			break;
			case SemanticActionsConstant.RAMO_CASE_AFTER_INTEIRO:
				SemanticActionsFunctions.ramoCaseAfterInteiro();
			break;
			case SemanticActionsConstant.AFTER_VARIAVEL_CONTROLE_FOR:
				SemanticActionsFunctions.afterVariavelControleFor();
			break;
			case SemanticActionsConstant.AFTER_EXPRESSAO_VALOR_INICIAL:
				SemanticActionsFunctions.afterExpressaoValorInicial();
			break;
			case SemanticActionsConstant.AFTER_EXPRESSAO_VALOR_FINAL:
				SemanticActionsFunctions.afterExpressaoValorFinal();
			break;
			case SemanticActionsConstant.AFTER_COMANDO_EM_FOR:
				SemanticActionsFunctions.afterComandoEmFor();
			break;
			case SemanticActionsConstant.COMPARACAO_01:
				SemanticActionsFunctions.comparacao01();
			break;
			case SemanticActionsConstant.COMPARACAO_02:
				SemanticActionsFunctions.comparacao02();
			break;
			case SemanticActionsConstant.COMPARACAO_03:
				SemanticActionsFunctions.comparacao03();
			break;
			case SemanticActionsConstant.COMPARACAO_04:
				SemanticActionsFunctions.comparacao04();
			break;
			case SemanticActionsConstant.COMPARACAO_05:
				SemanticActionsFunctions.comparacao05();
			break;
			case SemanticActionsConstant.COMPARACAO_06:
				SemanticActionsFunctions.comparacao06();
			break;
			case SemanticActionsConstant.EXPRESSAO_OPERANDO_SINAL_UNARIO:
				SemanticActionsFunctions.expressaoOperandoSinalUnario();
			break;
			case SemanticActionsConstant.EXPRESSAO_SOMA:
				SemanticActionsFunctions.expressaoSoma();
			break;
			case SemanticActionsConstant.EXPRESSAO_SUBTRACAO:
				SemanticActionsFunctions.expressaoSubtracao();
			break;
			case SemanticActionsConstant.EXPRESSAO_OR:
				SemanticActionsFunctions.expressaoOr();
			break;
			case SemanticActionsConstant.EXPRESSAO_MULTIPLICA:
				SemanticActionsFunctions.expressaoMultiplica();
			break;
			case SemanticActionsConstant.EXPRESSAO_DIVISAO:
				SemanticActionsFunctions.expressaoDivisao();
			break;
			case SemanticActionsConstant.EXPRESSAO_AND:
				SemanticActionsFunctions.expressaoAnd();
			break;
			case SemanticActionsConstant.EXPRESSAO_INT:
				SemanticActionsFunctions.expressaoInt();
			break;
			case SemanticActionsConstant.EXPRESSAO_NOT:
				SemanticActionsFunctions.expressaoNot();
			break;
			case SemanticActionsConstant.EXPRESSAO_VARIAVEL:
				SemanticActionsFunctions.expressaoVariavel();
			break;

			default:
				throw new Exception(ExceptionUtil.getSemanticErrorInvalidException(acaoSemantica, token));
		}
	}

}