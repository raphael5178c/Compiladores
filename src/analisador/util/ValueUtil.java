package analisador.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueUtil {
	
	public static final String VALOR_NI = "";
	
	public static boolean isEmpty(Object obj) {
		try {
			if (obj == null)
				return true;
			if ("".equalsIgnoreCase(obj + ""))
				return true;
		} catch (Exception ex) {
			return false;
		}
		return false;
	}
	
	public static boolean isEmpty(int value) {
		return value == 0;
	}
	
	public static boolean isNotEmpty(int value) {
		return !isEmpty(value);
	}
	
	public static boolean isEmpty(Entry<?, ?> entry) {
		return entry != null;
	}
	
	public static boolean isNotEmpty(Entry<?, ?> entry) {
		return !isEmpty(entry);
	}
	
	public static boolean isEmpty(HashMap<?, ?> hash) {
		return hash == null;
	}
	
	public static boolean isNotEmpty(HashMap<?, ?> hash) {
		return !isEmpty(hash);
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	public static boolean isEmpty(String string) {
		return string == null || string.isEmpty() || string.equalsIgnoreCase("");
	}

	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean validaInteiros(Object number) {
		if (isEmpty(number))
			return false;
		if (number instanceof Integer || number instanceof Double)
			return true;
		if (isDouble(number + ""))
			return true;
		if (isInteger(number + ""))
			return true;

		return false;
	}

	public static boolean validaIdentificadores(Object identificador) {
		if (isEmpty(identificador))
			return false;
		String variavel = identificador + "";
		char[] c = variavel.toCharArray();
		if (Character.isDigit(c[0])) {
			return false;
		}

		// * \s -- whitespace (espaço em branco)
		// * \S -- non-whitespace (não seja espaço em branco)
		// * \w -- word character [a-zA-Z0-9] (caractere de palavra)
		// * \W -- non-word character (não caractere de palavra)
		// * \p{Punct} -- punctuation (pontuação)
		// * \p{Lower} -- lowercase [a-z] (minúsculas)
		// * \p{Upper} -- uppercase [A-Z] (maiúsculas)
		/* aqui você defini qual o tipo de avaliação */
		Pattern pattern = null;
		Matcher matcher = null;
		pattern = Pattern.compile("\\s");
		matcher = pattern.matcher(variavel);
		while (matcher.find()) {
			return false;
		}
		pattern = Pattern.compile("\\p{Punct}");
		matcher = pattern.matcher(variavel);
		while (matcher.find()) {
			return false;
		}
		return true;
	}

}
