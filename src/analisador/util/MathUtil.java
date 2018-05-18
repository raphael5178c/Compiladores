package analisador.util;

import java.util.HashMap;

public class MathUtil {

	public final static int FIRST_TABLE_SIZE = 25151; // first prime after 25143

	public static int getHashValue(String value, HashMap<Integer, String> fullHashTable) {
		return hash(value, FIRST_TABLE_SIZE);
	}

	public static int hash(String value, int tableSize) {
		int hashVal = 0;
		for (int i = 1; i < value.length(); ++i) {
			hashVal = 37 * hashVal + value.charAt(i);
		}
		hashVal %= tableSize;
		if (hashVal < 0) {
			hashVal += tableSize; // needed if hashVal is negative
		}
		return hashVal;
	}

}
