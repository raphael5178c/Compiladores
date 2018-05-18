package analisador.util;

public class MathUtil {

	public final static int FIRST_TABLE_SIZE = 25151;

	public static int getHashValue(String value) {
		int hash =  hash(value, TableSymbols.qtValuesInserted, FIRST_TABLE_SIZE);
		TableSymbols.qtValuesInserted++;
		return hash;
	}

	/**
	 * Metodo do Professor Peter Allen para calcular o hash.
	 */
	public static int hash(String value, int qtValuesInsert, int tableSize) {
		int hashVal = 0;
		for (int i = 1; i < value.length(); ++i) {
			hashVal = (37+qtValuesInsert) * hashVal + value.charAt(i);
		}
		hashVal %= tableSize;
		if (hashVal < 0) {
			hashVal += tableSize;
		}
		return hashVal;
	}

}
