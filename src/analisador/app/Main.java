package analisador.app;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import analisador.constants.LMSConstantTokens;
import analisador.domain.Instrucao;
import analisador.domain.Literal;
import analisador.domain.PalavraReservada;
import analisador.domain.Token;
import analisador.program.Lexico;
import analisador.program.Semantico;
import analisador.program.Sintatico;
import analisador.util.ConsoleUtil;
import javax.swing.JTabbedPane;

public class Main extends JFrame{
	
	public static ArrayList<PalavraReservada> palavraReservadaList = new ArrayList<PalavraReservada>();
	
	public static File fileSelected;
	
	public static JTextArea txtSaidaConsole = new JTextArea();
	public static JTextArea txtFonte = new JTextArea();
	public static final long serialVersionUID = 1L;
	List<Token> tokenList;
	private JTable table;
	private JTable tableInstrucoes;
	private JTable tableLiterais;
	
	public static void main(String[] args) {
		setAllTokens();
		
		Main main = new Main();
		main.setVisible(true);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        main.setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	
	public static void setAllTokens() {
		palavraReservadaList.add(new PalavraReservada("PROGRAM", 22, LMSConstantTokens.TOKEN_PROGRAM));
		palavraReservadaList.add(new PalavraReservada("CONST", 23, LMSConstantTokens.TOKEN_CONST));
		palavraReservadaList.add(new PalavraReservada("VAR", 24, LMSConstantTokens.TOKEN_VAR));
		palavraReservadaList.add(new PalavraReservada("PROCEDURE", 25, LMSConstantTokens.TOKEN_PROCEDURE));
		palavraReservadaList.add(new PalavraReservada("BEGIN", 26, LMSConstantTokens.TOKEN_BEGIN));
		palavraReservadaList.add(new PalavraReservada("END", 27, LMSConstantTokens.TOKEN_END));
		palavraReservadaList.add(new PalavraReservada("INTEGER", 28, LMSConstantTokens.TOKEN_INTEGER));
		palavraReservadaList.add(new PalavraReservada("OF", 29, LMSConstantTokens.TOKEN_OF));
		palavraReservadaList.add(new PalavraReservada("CALL", 30, LMSConstantTokens.TOKEN_CALL));
		palavraReservadaList.add(new PalavraReservada("IF", 31, LMSConstantTokens.TOKEN_IF));
		palavraReservadaList.add(new PalavraReservada("THEN", 32, LMSConstantTokens.TOKEN_THEN));
		palavraReservadaList.add(new PalavraReservada("ELSE", 33, LMSConstantTokens.TOKEN_ELSE));
		palavraReservadaList.add(new PalavraReservada("WHILE", 34, LMSConstantTokens.TOKEN_WHILE));
		palavraReservadaList.add(new PalavraReservada("DO", 35, LMSConstantTokens.TOKEN_DO));
		palavraReservadaList.add(new PalavraReservada("REPEAT", 36, LMSConstantTokens.TOKEN_REPEAT));
		palavraReservadaList.add(new PalavraReservada("UNTIL", 37, LMSConstantTokens.TOKEN_UNTIL));
		palavraReservadaList.add(new PalavraReservada("READLN", 38, LMSConstantTokens.TOKEN_READLN));
		palavraReservadaList.add(new PalavraReservada("WRITELN", 39, LMSConstantTokens.TOKEN_WRITELN));
		palavraReservadaList.add(new PalavraReservada("OR", 40, LMSConstantTokens.TOKEN_OR));
		palavraReservadaList.add(new PalavraReservada("AND", 41, LMSConstantTokens.TOKEN_AND));
		palavraReservadaList.add(new PalavraReservada("NOT", 42, LMSConstantTokens.TOKEN_NOT));
		palavraReservadaList.add(new PalavraReservada("FOR", 43, LMSConstantTokens.TOKEN_FOR));
		palavraReservadaList.add(new PalavraReservada("TO", 44, LMSConstantTokens.TOKEN_TO));
		palavraReservadaList.add(new PalavraReservada("CASE", 45, LMSConstantTokens.TOKEN_CASE));
	}
	
	public Main() {
		txtFonte.setTabSize(2);
		txtSaidaConsole.setEnabled(false);	
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Escolher Arquivo");
		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				choseFileCode();
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(Main.class.getResource("/javax/swing/plaf/metal/icons/ocean/upFolder.gif")));
		menuBar.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("");
		menuBar.add(mntmNewMenuItem);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JButton btnAnaliseLexica = new JButton("Analise L\u00E9xica");
		btnAnaliseLexica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ConsoleUtil.getInstance().clearConsole();
				table.setModel(new DefaultTableModel(null, new String[] {}));
				tableInstrucoes.setModel(new DefaultTableModel(null, new String[] {}));
				tableLiterais.setModel(new DefaultTableModel(null, new String[] {}));
				analiseLexica();
			}
		});
		btnAnaliseLexica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton btnAnaliseSinttica = new JButton("Analise Sint\u00E1tica");
		btnAnaliseSinttica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ConsoleUtil.getInstance().clearConsole();
				table.setModel(new DefaultTableModel(null, new String[] {}));
				tableInstrucoes.setModel(new DefaultTableModel(null, new String[] {}));
				tableLiterais.setModel(new DefaultTableModel(null, new String[] {}));
				analiseSintatica(false);
			}
		});
		btnAnaliseSinttica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton btnAnaliseSemantica = new JButton("Analise Semantica");
		btnAnaliseSemantica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ConsoleUtil.getInstance().clearConsole();
				table.setModel(new DefaultTableModel(null, new String[] {}));
				tableInstrucoes.setModel(new DefaultTableModel(null, new String[] {}));
				tableLiterais.setModel(new DefaultTableModel(null, new String[] {}));
				analiseSintatica(true);
			}
		});
		
		JLabel lblConsole = new JLabel("Console:");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblConsole, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
							.addGap(1))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnAnaliseLexica, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAnaliseSinttica, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAnaliseSemantica, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblConsole, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAnaliseLexica)
						.addComponent(btnAnaliseSinttica)
						.addComponent(btnAnaliseSemantica))
					.addGap(7))
		);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane.addTab("Léxico", null, scrollPane_2, null);
		table = new JTable();
		table.setEnabled(true);
		scrollPane_2.setViewportView(table);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		tabbedPane.addTab("Instruções", null, scrollPane_3, null);
		tableInstrucoes = new JTable();
		scrollPane_3.setViewportView(tableInstrucoes);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		tabbedPane.addTab("Literais", null, scrollPane_4, null);
		tableLiterais = new JTable();
		scrollPane_4.setViewportView(tableLiterais);
		
		scrollPane_1.setViewportView(txtFonte);
		scrollPane.setViewportView(txtSaidaConsole);
		getContentPane().setLayout(groupLayout);
	}
	
	protected void choseFileCode() {
		JFileChooser chooser = new JFileChooser("resource\\arquivo");
		chooser.showOpenDialog(null);
		fileSelected = new File(chooser.getSelectedFile().getPath());
		try {
			txtFonte.setText(new String(Files.readAllBytes(fileToPath(fileSelected))));
			ConsoleUtil.getInstance().setTxtInfoConsole("Arquivo Selecionado: "+fileSelected.getName());
		} catch (IOException ex) {
			ConsoleUtil.getInstance().setTxtErrorConsole(ex);
		}
	}
	
	protected void analiseSintatica(boolean goSemantica) {
		String [] colunasInstrucoes = {"Instrução", "Arg 1", "Arg 2"};
		String [] colunasLiterais = {"End.", "Literal"};
		try {
			analiseLexica();
			ConsoleUtil.getInstance().setTxtInfoConsole("Iniciando a analise sintatica do código de fonte...");
			Sintatico.getInstance().analiseSintatica(tokenList, goSemantica);
			ConsoleUtil.getInstance().setTxtInfoConsole("Finalizada a analise Sintática do código de fonte...");
			if(goSemantica && Semantico.instrucoesHipotetica != null) {
				if(Semantico.instrucoesHipotetica.listIntrucao != null) {
					String[][] dados = new String[Semantico.instrucoesHipotetica.listIntrucao.size()][3];
					for (int i = 0 ; i < Semantico.instrucoesHipotetica.listIntrucao.size(); i++) {
						Instrucao instrucao = Semantico.instrucoesHipotetica.listIntrucao.get(i);
						dados[i][0] = String.valueOf(instrucao.nmInstrucao);
						dados[i][1] = String.valueOf(instrucao.geralA);
						dados[i][2] = String.valueOf(instrucao.geralB);
					}
					DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
					centerRenderer.setHorizontalAlignment(JLabel.CENTER);
					tableInstrucoes.setModel(new DefaultTableModel(dados, colunasInstrucoes));
					tableInstrucoes.setDefaultRenderer(String.class, centerRenderer);		
				}
				if(Semantico.instrucoesHipotetica.listLiteral != null) {
					String[][] dados = new String[Semantico.instrucoesHipotetica.listLiteral.size()][2];
					for (int i = 0 ; i < Semantico.instrucoesHipotetica.listLiteral.size(); i++) {
						Literal literal = Semantico.instrucoesHipotetica.listLiteral.get(i);
						dados[i][0] = String.valueOf(literal.endMemoria);
						dados[i][1] = String.valueOf(literal.nmLiteral);
					}
					DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
					centerRenderer.setHorizontalAlignment(JLabel.CENTER);
					tableLiterais.setModel(new DefaultTableModel(dados, colunasLiterais));
					tableLiterais.setDefaultRenderer(String.class, centerRenderer);		
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			tableInstrucoes.setModel(new DefaultTableModel(null, colunasInstrucoes));
			tableLiterais.setModel(new DefaultTableModel(null, colunasLiterais));
			ConsoleUtil.getInstance().setTxtErrorConsole(ex);
		}
	}
	
	protected void analiseLexica() {
		String [] colunas = {"Código do token", "Token", "Descrição do token"};
		try {
			ConsoleUtil.getInstance().setTxtInfoConsole("Iniciando a analise léxica do código de fonte...");
			tokenList = Lexico.getInstance().analisar(txtFonte.getText());
			if ((tokenList == null) || (tokenList.size() < 0)) 	{
				ConsoleUtil.getInstance().setTxtErrorConsole("Código não analisado, pois não possui Tokens");
			}
			String[][] dados = new String[tokenList.size()][3];
			for (int i = 0 ; i < tokenList.size(); i++) {
				Token token = tokenList.get(i);
				dados[i][0] = String.valueOf(token.getCodigo());
				dados[i][1] = token.getNome();
				dados[i][2] = token.getTipo();
			}
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.setModel(new DefaultTableModel(dados, colunas));
			table.setDefaultRenderer(String.class, centerRenderer);
			ConsoleUtil.getInstance().setTxtInfoConsole("Finalizada a analise léxica do código de fonte...");
		} catch (Exception ex) {
			table.setModel(new DefaultTableModel(null, colunas));
			ConsoleUtil.getInstance().setTxtErrorConsole(ex);
		}
	}
	

	protected static boolean isFromFile() {
		return fileSelected != null;
	}
	
	protected static Path fileToPath(File file) {
		if(file == null) return null;
		return file.toPath();
	}
}
