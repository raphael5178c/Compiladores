package analisador.util;

import java.awt.Color;

import javax.swing.JTextArea;

import analisador.app.Main;

public class consoleUtil extends Console {
	
	public static final JTextArea TXT_SAIDA_CONSOLE = Main.txtSaidaConsole;
	
	public static consoleUtil instance;
	
	public static consoleUtil getInstance() {
		return (instance == null) ? new consoleUtil() : instance;
	}
	
	public JTextArea getConsoleSaida() {
		return TXT_SAIDA_CONSOLE;
	}
	
	void setInfoConsoleColor() {
		getConsoleSaida().setForeground(Color.BLUE);
		getConsoleSaida().setDisabledTextColor(Color.BLUE);
	}
	
	void setWarnConsoleColor() {
		getConsoleSaida().setForeground(Color.YELLOW);
		getConsoleSaida().setDisabledTextColor(Color.YELLOW);
	}
	
	void setErrorConsoleColor() {
		getConsoleSaida().setForeground(Color.RED);
		getConsoleSaida().setDisabledTextColor(Color.RED);
	}
	
	void acumulateConsole(String text) {
		if(ValueUtil.isEmpty(getConsoleSaida().getText())) {
			getConsoleSaida().setText(text);
			return;
		}
		getConsoleSaida().setText(getConsoleSaida().getText()+"\n"+text);
	}
	
	public void clearConsole() {
		getConsoleSaida().setText("");
		setInfoConsoleColor();
	}
	
	public void setTxtInfoConsole(String text) {
		acumulateConsole(text);
		setInfoConsoleColor();
	}
	
	public void setTxtErrorConsole(String text) {
		acumulateConsole(text);
		setErrorConsoleColor();
	}
	
	public void setTxtWarnConsole(String text) {
		acumulateConsole(text);
		setWarnConsoleColor();
	}
	
	public void setTxtInfoConsole(Exception ex) {
		acumulateConsole(ex.getMessage());
		setInfoConsoleColor();
	}
	
	public void setTxtErrorConsole(Exception ex) {
		clearConsole();
		acumulateConsole(ex.getMessage());
		setErrorConsoleColor();
	}
	
	public void setTxtWarnConsole(Exception ex) {
		acumulateConsole(ex.getMessage());
		setWarnConsoleColor();
	}

}
