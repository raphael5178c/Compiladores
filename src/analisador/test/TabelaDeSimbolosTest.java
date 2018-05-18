package analisador.test;

import java.util.Map.Entry;

import analisador.util.TableSymbols;
import analisador.util.ValueUtil;

public class TabelaDeSimbolosTest {
	
	public static void main(String[] args) {
		TableSymbols table = new TableSymbols();
		System.out.println("============ INICIO INSERT ============");
		insertValues(table);
		printValues(table);
		System.out.println("============ FIM INSERT ============");

		System.out.println();
		System.out.println("============ INICIO ALTER ============");
		alterValues(table);
		printValues(table);
		System.out.println("============ FIM ALTER ============");
		
		System.out.println();
		System.out.println("============ INICIO DELETE ============");
		deleteValues(table);
		printValues(table);
		System.out.println("============ FIM DELETE ============");
		
		System.out.println();
		System.out.println("============ INICIO BUSCA INEXISTENTE ============");
		getElementInexistente(table);
		System.out.println("============ FIM BUSCA INEXISTENTE ============");
		
		System.out.println();
		System.out.println("============ INICIO BUSCA 3 ELEMENTOS ============");
		getElementsExistentes(table);
		System.out.println("============ FIM BUSCA 3 ELEMENTOS ============");
	}

	private static void getElementsExistentes(TableSymbols table) {
		System.out.println("Valor 1 Encontrado: "+table.getValue("AXLC AS ALTERED"));
		System.out.println("Valor 2 Encontrado: "+table.getValue("0001XE2 AS ALTERED"));
		System.out.println("Valor 3 Encontrado: "+table.getValue("DDDS AS ALTERED"));
	}

	private static void getElementInexistente(TableSymbols table) {
		String element = table.getValue("DLC");
		boolean hasElement = ValueUtil.isNotEmpty(element);
		if(!hasElement) {
			System.out.println("ERROR, ELEMENTO NÃO ENCONTRADO POR QUE O MÉTODO RETORNOU FALSE");
		} else {
			System.out.println("Elemento Encontrado: "+element);
		}
	}

	private static void deleteValues(TableSymbols table) {
		table.deleteByKey(table.getKeyByValue("DLC"));
		table.deleteByKey(table.getKeyByValue("ASPD"));
		table.deleteByKey(table.getKeyByValue("FGHH"));
	}

	private static void alterValues(TableSymbols table) {
		table.alterByKey(table.getKeyByValue("AXLC"), "AXLC AS ALTERED");
		table.alterByKey(table.getKeyByValue("0001XE2"), "0001XE2 AS ALTERED");
		table.alterByKey(table.getKeyByValue("TESTINSERT"), "TESTINSERT AS ALTERED");
		table.alterByKey(table.getKeyByValue("ABLABLa"), "ABLABLa AS ALTERED");
		table.alterByKey(table.getKeyByValue("DDDS"), "DDDS AS ALTERED");
	}

	private static void printValues(TableSymbols table) {
		System.out.println("########## IMPRIMINDO MODULO ##########");
		for (Entry<Integer, String> entry : TableSymbols.getHashTable().entrySet()) {
			System.out.println("KEY: "+ entry.getKey() + " | VALUE: "+ entry.getValue());
		}
		System.out.println("########## FIM IMPRESSÃO ##########");
	}

	private static void insertValues(TableSymbols table) {
		System.out.println("1- "+table.insertValue("ALC"));
		System.out.println("2- "+table.insertValue("DLC"));
		System.out.println("3- "+table.insertValue("AXLC"));
		System.out.println("4- "+table.insertValue("ASPD"));
		System.out.println("5- "+table.insertValue("DDDS"));
		System.out.println("6- "+table.insertValue("FGHH"));
		System.out.println("7- "+table.insertValue("0001XE2"));
		System.out.println("8- "+table.insertValue("TEST"));
		System.out.println("9- "+table.insertValue("TESTINSERT"));
		System.out.println("10- "+table.insertValue("ABLABLa"));
	}
	
}
