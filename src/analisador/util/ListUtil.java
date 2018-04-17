package analisador.util;

import java.util.ArrayList;
import java.util.List;

import analisador.domain.Token;

public class ListUtil {

	public static ArrayList<String> getSubListStartsWith(ArrayList<String> listString, String compareStarter) {
		if (ValueUtil.isEmpty(listString) || ValueUtil.isEmpty(compareStarter))
			return null;
		ArrayList<String> subList = new ArrayList<String>();
		for (String palavra : listString) {
			if (!palavra.toLowerCase().startsWith(compareStarter.toLowerCase()))
				continue;
			subList.add(palavra);
		}
		return subList;
	}

	public static boolean isEmpty(ArrayList<String> listString) {
		return (ValueUtil.isEmpty(listString) || listString.size() < 1);
	}

	public static boolean isNotEmpty(ArrayList<String> listString) {
		return !isEmpty(listString);
	}

	public static boolean isEmpty(List<Token> listString) {
		return (ValueUtil.isEmpty(listString) || listString.size() < 1);
	}

	public static boolean isNotEmpty(List<Token> listString) {
		return !isEmpty(listString);
	}

	public static String getObject(ArrayList<String> listString, String object) {
		if (ValueUtil.isEmpty(listString) || ValueUtil.isEmpty(object))
			return null;
		for (String palavra : listString) {
			if (object.equalsIgnoreCase(palavra))
				return palavra;
		}
		return null;
	}

	public static boolean contaisObject(ArrayList<String> listString, String object) {
		if (ValueUtil.isEmpty(listString) || ValueUtil.isEmpty(object))
			return false;
		return listString.indexOf(object) >= 0;
	}

	public static boolean noMoreObjects(ArrayList<String> listString) {
		return listString.size() == 1;
	}

	public static boolean moreObjects(ArrayList<String> listString) {
		return listString.size() > 1;
	}

}
