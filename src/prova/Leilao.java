package prova;

import java.awt.FlowLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Leilao implements Serializable {
	private String nome;
	private double valorMinimo;
	private boolean leilaoAberto;
	private List<Lance> lancesRecebidos = new ArrayList<>();
	
	public Leilao(String nome, double valorMinimo) {
		this.nome = nome;
		this.valorMinimo = valorMinimo;
		this.leilaoAberto = true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public boolean isLeilaoAberto() {
		return leilaoAberto;
	}

	public void setLeilaoAberto(boolean leilaoAberto) {
		this.leilaoAberto = leilaoAberto;
	}
	
	public void addLance(Lance lance) {
		lancesRecebidos.add(lance);
		this.setValorMinimo(lance.getValor());
	}
	
	
	
	
	public void finalizar() {
		
		final JFrame frame = new JFrame("Lista de lances");
        JTextArea textArea = new JTextArea(20, 20);
 
        JScrollPane sp = new JScrollPane(textArea);
 
        frame.setLayout(new FlowLayout());
        
        frame.setSize(300, 600);
        frame.getContentPane().add(sp);
 
        frame.setVisible(true);
		
		this.leilaoAberto = false;
		
		textArea.append("Bem Leiloado: " + this.nome + "\n \n");
		for(Lance lance : lancesRecebidos) {
			textArea.append("\n Nome do Arrematante: "+ lance.getNome() + "\n" + "Valor do arremate: " + lance.getValor());
			textArea.append("\n --------------------\n");
		}
		
		textArea.append("Lance vencedor: " +getValorMinimo());
	
	}
	
	
	
}
