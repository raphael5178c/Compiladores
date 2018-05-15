package analisador.util;

import java.util.HashMap;

public class MathUtil {
	
	public final static int tableSize = 25147; //first prime after 25143
	
	public static int getHashValue(String value, HashMap<Integer, String> fullHashTable) {
		// SÓ DEVE DESCOMENTAR QUANDO ARRUMAR O ERRO DO METODO DO CALCULO DO HASH
		//return hash(value, (fullHashTable.size() > 0) ? fullHashTable.size() : tableSize);
		return fullHashTable.size();
	}
	
	public static int hash(String value, int tableSize){
		// ESTAMOS COM ERRO NESSE METODO QUE ESTÁ RETORNANDO VALORES IGUAIS POR ALGUM MOTIVO E PRECISAMOS ARRUMAR
		int hashVal = 0; //uses Horner’s method to evaluate a polynomial
		for( int i = 0; i < value.length( ); i++ ) {
			hashVal = 37 * hashVal + value.charAt( i );
		}
		hashVal %= tableSize;
		if( hashVal < 0 ) {
			hashVal += tableSize; //needed if hashVal is negative
		}
		return hashVal;
	}


}
