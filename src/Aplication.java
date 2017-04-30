import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class Aplication {

	private JFrame frame;
	private JTextField edtPesquisa;
	JTextPane txtResultado;
	
	JButton btnLimpar;
	JLabel txtEncontrados;
	JLabel txtIndexados;
	JProgressBar progressBar;
	
	String indexDir = "C:\\lucene\\Index"; 
	String dataDir = "C:\\lucene\\Data";
	
	Indexer indexer;
	Searcher searcher;
	
	List<String> lista;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Aplication window = new Aplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Aplication() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 523, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		edtPesquisa = new JTextField();
		edtPesquisa.setBounds(25, 83, 246, 23);
		frame.getContentPane().add(edtPesquisa);
		edtPesquisa.setColumns(10);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		
		btnPesquisar.setBounds(281, 82, 102, 23);
		frame.getContentPane().add(btnPesquisar);
		
		txtResultado = new JTextPane();
		txtResultado.setFont(new Font("Verdana", Font.BOLD, 12));
		txtResultado.setEditable(false);
		txtResultado.setBounds(25, 130, 454, 164);
		frame.getContentPane().add(txtResultado);
		
		txtEncontrados = new JLabel("");
		txtEncontrados.setBounds(343, 296, 154, 14);
		frame.getContentPane().add(txtEncontrados);
		
		txtIndexados = new JLabel("");
		txtIndexados.setBounds(25, 261, 118, 14);
		frame.getContentPane().add(txtIndexados);
		
		btnLimpar = new JButton("limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edtPesquisa.setText("");
			}
		});
		btnLimpar.setBounds(393, 82, 89, 23);
		frame.getContentPane().add(btnLimpar);
		
		JLabel lblResultadoDaPesquisa = new JLabel("Resultado da pesquisa:");
		lblResultadoDaPesquisa.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblResultadoDaPesquisa.setBounds(25, 114, 159, 14);
		frame.getContentPane().add(lblResultadoDaPesquisa);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Desenvolvimento\\workspace\\eclipse\\Implementação Lucene\\assets\\img\\l1.png"));
		lblNewLabel.setBounds(25, 11, 454, 61);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblVer = new JLabel("ver. 1.0");
		lblVer.setBounds(25, 296, 46, 14);
		frame.getContentPane().add(lblVer);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(81, 296, 146, 14);
		frame.getContentPane().add(progressBar);
		
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(edtPesquisa.getText().isEmpty()){
					JOptionPane.showMessageDialog(null,"Preencha o campo de pesquisa.");
				}else{
					Aplication teste;
					progressBar.setValue(100);
					
					try{
						teste = new Aplication();
						teste.createIndex();
						lista = teste.search(edtPesquisa.getText().toString());
						String console = "";
						int valorEncontrado = 0;
						
						for(int i = 0; i < lista.size(); i++){
							
							console = console + lista.get(i) + "\n";
							valorEncontrado++;
						}
						
						txtResultado.setText(console);
						txtEncontrados.setText("Encontrado: " + valorEncontrado + " arquivos.");
						
					}catch (IOException ex){
						ex.printStackTrace();
					}catch (ParseException ex1){
						ex1.printStackTrace();
					}
				}
				
				progressBar.setValue(0);
			}
		});
	}
	
	private void createIndex() throws IOException{
		
		indexer = new Indexer(indexDir);
		int numIndexed;
		long startTime = System.currentTimeMillis();
		numIndexed = indexer.createIdex(dataDir, new TextFileFilter());
		long endTime = System.currentTimeMillis();
		
		indexer.close();
		
		System.out.println(numIndexed + " Arquivo(s) indexados, tempo: " + (endTime - startTime) + " ms");
	}
	
	private List<String> search(String searchQuery) throws IOException, ParseException{
		
		String resultado = null;
		searcher = new Searcher(indexDir);
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.search(searchQuery);
		long endTime = System.currentTimeMillis();
		
		System.out.println(hits.totalHits + " arquivos encontrados. Tempo: " + (endTime - startTime) + " ms");
		JOptionPane.showMessageDialog(null,"Tempo de consulta: " + (endTime - startTime) + " ms\nPressione 'Ok' para exibir a lista de arquivos.");
		progressBar.setValue(0);
		lista = new ArrayList<String>();
		
		for(ScoreDoc scoreDoc : hits.scoreDocs){
			
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("Arquivo: " + doc.get(Constants.FILE_PATH));
			resultado = doc.get(Constants.FILE_PATH);
			lista.add(resultado);
		}
		
		searcher.close();
		
		return lista;
	}

}
