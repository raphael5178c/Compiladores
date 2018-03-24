package analisador.util;

public class StringUtil {

	public static String acumulate(String fullString, String acumulateString) {
		if(ValueUtil.isEmpty(fullString)) return acumulateString;
		return fullString += acumulateString;
	}
	
}
