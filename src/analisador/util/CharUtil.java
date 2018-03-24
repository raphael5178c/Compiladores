package analisador.util;

public class CharUtil {
	
	public static char getNextChar(char[] charArray, int currentPos) {
		try {
			if(ValueUtil.isEmpty(charArray) || ValueUtil.isEmpty(currentPos) || (currentPos > charArray.length) || ((currentPos+1 > charArray.length)) || currentPos < 0) return 0;
			return charArray[currentPos+1];
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static String charToString(char character) {
		if(ValueUtil.isEmpty(character)) return null;
		return character+"";
	}
	
	public static char[] stringToCharArray(String text) {
		if(ValueUtil.isEmpty(text)) return null;
		return text.toCharArray();
	}
	
	public static String getNexCharToString(char[] charArray, int currentPos) {
		return charToString(getNextChar(charArray, currentPos));
	}
	
	public static boolean isDigit(char c) {
		return Character.isDigit(c);
	}

	public static boolean isLetter(char c) {
		return Character.isLetter(c);
	}

}
