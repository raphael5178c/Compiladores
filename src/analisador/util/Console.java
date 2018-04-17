package analisador.util;

import javax.swing.JTextArea;

public abstract class Console {

	public abstract JTextArea getConsoleSaida();

	abstract void setInfoConsoleColor();

	abstract void setWarnConsoleColor();

	abstract void setErrorConsoleColor();

	abstract void acumulateConsole(String text);

	public abstract void clearConsole();

	public abstract void setTxtInfoConsole(String text);

	public abstract void setTxtErrorConsole(String text);

	public abstract void setTxtWarnConsole(String text);

	public abstract void setTxtInfoConsole(Exception ex);

	public abstract void setTxtErrorConsole(Exception ex);

	public abstract void setTxtWarnConsole(Exception ex);

}
