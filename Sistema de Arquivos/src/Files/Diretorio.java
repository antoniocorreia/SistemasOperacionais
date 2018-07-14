package Files;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Diretorio {

	private String nome;
	private LocalDateTime dataDeCriacao;
	private LocalDateTime dataDeModificacao;
	private int tamanho;
	private ArrayList<Arquivo> arquivos;
	private ArrayList<Diretorio> diretorios;
	private String caminho;
	private Diretorio pai;
	
	public Diretorio(String nome, String caminho, Diretorio pai) {
		this.nome = nome;
		this.dataDeCriacao = LocalDateTime.now();
		this.dataDeModificacao = LocalDateTime.now();
		this.tamanho = 2;
		this.arquivos = new ArrayList<Arquivo>();
		this.diretorios = new ArrayList<Diretorio>();
		this.caminho = caminho;
		this.pai = pai;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDateTime getDataDeCriacao() {
		return dataDeCriacao;
	}

	public LocalDateTime getDataDeModificacao() {
		return dataDeModificacao;
	}

	public int getTamanho() {
		return tamanho;
	}

	public ArrayList<Arquivo> getArquivos() {
		return arquivos;
	}

	public ArrayList<Diretorio> getDiretorios() {
		return diretorios;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
		this.caminho += this.nome + "\\";
		this.dataDeModificacao = LocalDateTime.now();
	}

	public Diretorio getPai() {
		return pai;
	}

	public void setPai(Diretorio pai) {
		this.pai = pai;
		this.dataDeModificacao = LocalDateTime.now();
	}

	public void adicionaArquivo(Arquivo arq) {
		this.arquivos.add(arq);
		this.tamanho += arq.getTamanho();
		this.dataDeModificacao = LocalDateTime.now();
	}
	
	public void removeArquivo(Arquivo arq) {
		this.arquivos.remove(arq);
		this.tamanho -= arq.getTamanho();
		this.dataDeModificacao = LocalDateTime.now();
	}
	
	public void adicionaDiretorio(Diretorio dir) {
		this.diretorios.add(dir);
		this.tamanho += dir.getTamanho();
		this.dataDeModificacao = LocalDateTime.now();
	}
	
	public void removeDiretorio(Diretorio dir) {
		this.diretorios.remove(dir);
		this.tamanho -= dir.getTamanho();
		this.dataDeModificacao = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		String retorno = "----------------------------------------------------------------\n";
		retorno += "Diret�rio " + this.nome + ":\n";
		retorno += "\tTamanho: " + this.tamanho + "\n";
		retorno += "\tData de cria��o: " + this.dataDeCriacao + "\n";
		retorno += "\tData de modifica��o: " + this.dataDeModificacao + "\n";
		if (arquivos.size() != 0) {
			retorno += "\tArquivos do diret�rio:\n\n";
			for (int i = 0; i < arquivos.size(); i++)
				retorno += arquivos.get(i).toString();
		}
		if (diretorios.size() != 0) {
			retorno += "\tDiret�rios dentro desse diret�rio:\n";
			Diretorio dir;
			for (int i = 0; i < diretorios.size(); i++) {
				dir = diretorios.get(i);
				retorno += "\t*****************************************\n";
				retorno += "\t\tDiret�rio " + dir.getNome() + "\n";
				retorno += "\t\t\tTamanho: " + dir.getTamanho() + "\n";
				retorno += "\t\t\tData de cria��o: " + dir.getDataDeCriacao() + "\n";
				retorno += "\t\t\tData de modifica��o: " + dir.getDataDeModificacao() + "\n";
				retorno += "\t*****************************************\n";
			}
		}
		retorno += "----------------------------------------------------------------\n";
		
		return retorno;
	}
	
}
