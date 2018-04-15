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
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TableModelListener;

import analisador.domain.Token;
import analisador.program.Lexico;
import analisador.util.consoleUtil;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Main extends JFrame{
	
	public static File fileSelected;
	
	public static JTextArea txtSaidaConsole = new JTextArea();
	public static JTextArea txtFonte = new JTextArea();
	
	public Main() {
		JMenuBar menuBar = new JMenuBar();
		txtFonte.setTabSize(2);
		setJMenuBar(menuBar);
		txtSaidaConsole.setEnabled(false);	
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
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JButton btnAnaliseLexica = new JButton("Analise L\u00E9xica");
		btnAnaliseLexica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
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
				analiseSintatica();
			}
		});
		btnAnaliseSinttica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton btnAnaliseSemantica = new JButton("Analise Semantica");
		
		JLabel lblConsole = new JLabel("Console:");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblConsole, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE))
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
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
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
						.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
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
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo do Token", "Token", "Descri\u00E7\u00E3o"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane_2.setViewportView(table);
		
		
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
			consoleUtil.getInstance().setTxtInfoConsole("Arquivo Selecionado: "+fileSelected.getName());
		} catch (IOException ex) {
			consoleUtil.getInstance().setTxtErrorConsole(ex);
		}
	}
	
	protected void analiseSintatica() {
		consoleUtil.getInstance().setTxtInfoConsole("Iniciando a analise sintatica do código de fonte...");
	}
	
	protected void analiseLexica() {
		String [] colunas = {"Código do token", "Token", "Descrição do token"};
		try {
			consoleUtil.getInstance().setTxtInfoConsole("Iniciando a analise léxica do código de fonte...");
			List<Token> tokenList = Lexico.getInstance().analisar(txtFonte.getText());
			if ((tokenList == null) || (tokenList.size() < 0)) 	{
				consoleUtil.getInstance().setTxtErrorConsole("Código não analisado, pois não possui Tokens");
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
			consoleUtil.getInstance().setTxtInfoConsole("Finalizada a analise léxica do código de fonte...");
		} catch (Exception ex) {
			table.setModel(new DefaultTableModel(null, colunas));
			consoleUtil.getInstance().setTxtErrorConsole(ex);
		}
	}
	

	protected static boolean isFromFile() {
		return fileSelected != null;
	}
	
	protected static Path fileToPath(File file) {
		if(file == null) return null;
		return file.toPath();
	}

	private static final long serialVersionUID = 1L;
	private JTable table;

	public static void main(String[] args) {
		Main main = new Main();
		main.setVisible(true);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        main.setExtendedState(Frame.MAXIMIZED_BOTH);
	}
}
