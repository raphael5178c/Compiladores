package analisador.app;

import java.awt.Frame;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import analisador.domain.Token;
import analisador.program.Lexico;

public class App extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private void analisar() throws IOException {
		Lexico analisador = new Lexico();

		JFileChooser chooser = new JFileChooser("resource\\arquivo");
		chooser.showOpenDialog(null);
		String file = chooser.getSelectedFile().getPath();
		Path path = new File(file).toPath();

		List<Token> tokenList = analisador.analisar(new String(Files.readAllBytes(path)) + "\n$");

		if ((tokenList != null) && (tokenList.size() >= 0)) {
			String [] colunas = {"Código do token", "Token", "Descrição do token"};
			String[][] dados = new String[tokenList.size()][3];
			for (int i = 0 ; i < tokenList.size(); i++) {
				Token token = tokenList.get(i);
				dados[i][0] = String.valueOf(token.getCodigo());
				dados[i][1] = token.getNome();
				dados[i][2] = token.getTipo();
			}
			JTable table = new JTable(dados, colunas);
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(String.class, centerRenderer);
			JPanel painel = new JPanel();
			painel.setLayout(new GridLayout(1, 1));
			JScrollPane barraRolagem = new JScrollPane(table);
			painel.add(barraRolagem);
			
			getContentPane().add(painel);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setExtendedState(Frame.MAXIMIZED_BOTH);
	        setVisible(true);
		}
	}

	public static void main(String[] args) throws IOException {
		App a = new App();
		a.analisar();

	}

}
