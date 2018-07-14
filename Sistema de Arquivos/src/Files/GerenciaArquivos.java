package Files;

public class GerenciaArquivos {

	private Particao principal;
	private String caminho;
	private Diretorio dirAtual;
	private static GerenciaArquivos instance;
	
	private GerenciaArquivos() {
		this.caminho = "";
		this.dirAtual = null;
	}
	
	public static GerenciaArquivos getInstance() {
		if (instance == null)
			instance = new GerenciaArquivos();
		return instance;
	}

	public Particao getPrincipal() {
		return principal;
	}

	public void setPrincipal(Particao principal) {
		this.principal = principal;
		this.caminho = principal.getNome() + ":\\";
	}
	
	public String getCaminho() {
		return caminho;
	}

	private int buscaDiretorio(String caminho) {
		Diretorio auxiliar = dirAtual;
		dirAtual = null;
		int profundidade = 0;
		boolean valido = false;
		for (int i = 0; i < caminho.length(); i++) {
			if (caminho.charAt(i) == '\\') {
				profundidade++;
				valido = true;
			} else {
				valido = false;
			}
		}
		if (!valido) {
			dirAtual = auxiliar;
			return -1;
		}
		if (profundidade == 1)
			return profundidade;
		String [] busca = new String[profundidade];
		String aux = "";
		int j = 0;
		for (int i = 0; i < caminho.length(); i++) {
			if (caminho.charAt(i) != '\\') {
				aux += caminho.charAt(i);
			} else {
				busca[j++] = aux;
				aux = "";
			}
		}
		for (j = 0; j < principal.getDiretorios().size(); j++) {
			Diretorio dir = principal.getDiretorios().get(j);
			if (dir.getNome().equals(busca[1])) {
				dirAtual = dir;
				break;
			}
		}
		if (dirAtual == null) {
			dirAtual = auxiliar;
			return -1;
		}
		if (profundidade == 2) {
			this.caminho = dirAtual.getCaminho();
			return profundidade;
		}
		boolean found = false;
		for (int i = 2; i < busca.length; i++) {
			for (j = 0; j < dirAtual.getDiretorios().size(); j++) {
				if (dirAtual.getDiretorios().get(j).getNome().equals(busca[i])) {
					dirAtual = dirAtual.getDiretorios().get(j);
					found = true;
					break;
				}
			}
			if (!found) {
				dirAtual = auxiliar;
				return -1;
			} else {
				found = false;
			}
		}
		this.caminho = dirAtual.getCaminho();
		return profundidade;
	}
	
	public void criaDiretorio(String caminho, String nome) {
		if (principal.getTamanhoUtilizado() + 2 > principal.getTamanhoTotal()) {
			System.err.println("N�o h� espa�o suficiente para criar um diret�rio.");
			return;
		}
		if (buscaDiretorio(caminho) != -1) {
			String path = caminho;
			path += nome + "\\";
			Diretorio dir = new Diretorio(nome, path, dirAtual);
			if (dirAtual == null) {
				for (int i = 0; i < principal.getDiretorios().size(); i++) {
					if (principal.getDiretorios().get(i).getCaminho().equals(dir.getCaminho())) {
						System.err.println("N�o � poss�vel criar diret�rios com mesmo nome.");
						return;
					}
				}
				principal.adicionaDiretorio(dir);
			} else {
				for (int i = 0; i < dirAtual.getDiretorios().size(); i++) {
					if (dirAtual.getDiretorios().get(i).getCaminho().equals(dir.getCaminho())) {
						System.err.println("N�o � poss�vel criar diret�rios com mesmo nome.");
						return;
					}
				}
				dirAtual.adicionaDiretorio(dir);
				principal.atualizaTamanho();
			}
			this.caminho = path;
			this.dirAtual = dir;
			System.out.println("Diret�rio adicionado com sucesso.");
		} else {
			System.err.println("O sistema n�o pode encontrar o caminho especificado.");
		}
	}
	
	public void criaDiretorio(String nome) {
		if (principal.getTamanhoUtilizado() + 2 > principal.getTamanhoTotal()) {
			System.err.println("N�o h� espa�o suficiente para criar um diret�rio.");
			return;
		}
		String path = this.caminho;
		path += nome + "\\";
		Diretorio dir = new Diretorio(nome, path, dirAtual);
		if (dirAtual == null) {
			for (int i = 0; i < principal.getDiretorios().size(); i++) {
				if (principal.getDiretorios().get(i).getCaminho().equals(dir.getCaminho())) {
					System.err.println("N�o � poss�vel criar diret�rios com mesmo nome.");
					return;
				}
			}
			principal.adicionaDiretorio(dir);
		} else {
			for (int i = 0; i < dirAtual.getDiretorios().size(); i++) {
				if (dirAtual.getDiretorios().get(i).getCaminho().equals(dir.getCaminho())) {
					System.err.println("N�o � poss�vel criar diret�rios com mesmo nome.");
					return;
				}
			}
			dirAtual.adicionaDiretorio(dir);
			principal.atualizaTamanho();
		}
		this.caminho = path;
		this.dirAtual = dir;
		System.out.println("Diret�rio adicionado com sucesso.");
	}
	
	public void navega(String caminho) {
		int navega = buscaDiretorio(caminho);
		if (navega != -1) {
			if (navega == 1) {
				this.caminho = caminho;
				this.dirAtual = null;
			}
		} else {
			System.err.println("O sistema n�o pode encontrar o caminho especificado.");
		}
	}
	
	public void enter(String nome) {
		if (dirAtual == null) {
			if (principal.getDiretorios().size() == 0) {
				System.err.println("Essa parti��o n�o possui diret�rios.");
				return;
			}
			Diretorio dir = null;
			for (int i = 0; i < principal.getDiretorios().size(); i++) {
				if (principal.getDiretorios().get(i).getNome().equals(nome)) {
					dir = principal.getDiretorios().get(i);
					break;
				}
			}
			if (dir != null) {
				this.dirAtual = dir;
				this.caminho = dir.getCaminho();
			} else {
				System.err.println("N�o existe o diret�rio informado.");
			}
		} else {
			if (dirAtual.getDiretorios().size() == 0) {
				System.err.println("Esse diret�rio n�o possui outros diret�rios.");
				return;
			}
			Diretorio dir = null;
			for (int i = 0; i < dirAtual.getDiretorios().size(); i++) {
				if (dirAtual.getDiretorios().get(i).getNome().equals(nome)) {
					dir = dirAtual.getDiretorios().get(i);
					break;
				}
			}
			if (dir != null) {
				this.dirAtual = dir;
				this.caminho = dir.getCaminho();
			} else {
				System.err.println("N�o existe o diret�rio informado.");
			}
		}
	}
	
	public void out() {
		if (dirAtual == null) {
			System.err.println("N�o � poss�vel sair de uma parti��o.");
		} else {
			if (dirAtual.getPai() == null) {
				this.dirAtual = null;
				this.caminho = this.principal.getNome() + ":\\";
			} else {
				this.dirAtual = this.dirAtual.getPai();
				this.caminho = this.dirAtual.getCaminho();
			}
		}
	}
	
	public void criaArquivoDados(String nome, String dados) {
		if (principal.getTamanhoUtilizado() + dados.length() + 1 > principal.getTamanhoTotal()) {
			System.err.println("N�o h� espa�o suficiente para criar este arquivo.");
			return;
		}
		if (dirAtual == null) {
			System.err.println("N�o � poss�vel criar arquivos em uma parti��o.");
			return;
		}
		for (int i = 0; i < dirAtual.getArquivos().size(); i++) {
			if (dirAtual.getArquivos().get(i).getNome().equals(nome)) {
				System.err.println("N�o � poss�vel criar arquivos com mesmo nome.");
				return;
			}
		}
		nome += ".txt";
		Arquivo arq = new Arquivo(nome, dados, this.caminho, dirAtual);
		dirAtual.adicionaArquivo(arq);
		principal.atualizaTamanho();
		System.out.println("Arquivo adicionado com sucesso.");
	}
	
	public void criaArquivo(String nome, int tamanho) {
		if (principal.getTamanhoUtilizado() + tamanho > principal.getTamanhoTotal()) {
			System.err.println("N�o h� espa�o suficiente para criar um diret�rio.");
			return;
		}
		if (dirAtual == null) {
			System.err.println("N�o � poss�vel criar arquivos em uma parti��o.");
			return;
		}
		for (int i = 0; i < dirAtual.getArquivos().size(); i++) {
			if (dirAtual.getArquivos().get(i).getNome().equals(nome)) {
				System.err.println("N�o � poss�vel criar arquivos com mesmo nome.");
				return;
			}
		}
		Arquivo arq = new Arquivo(nome, tamanho, this.caminho, dirAtual);
		dirAtual.adicionaArquivo(arq);
		principal.atualizaTamanho();
		System.out.println("Arquivo adicionado com sucesso.");
	}
	
	public void removeArquivo(String nome) {
		Arquivo arq = null;
		for (int i = 0; i < dirAtual.getArquivos().size(); i++) {
			if (dirAtual.getArquivos().get(i).getNome().equals(nome)) {
				arq = dirAtual.getArquivos().get(i);
				break;
			}
		}
		if (arq == null) {
			System.err.println("O sistema n�o pode encontrar o arquivo especificado.");
		} else {
			dirAtual.removeArquivo(arq);
			principal.atualizaTamanho();
			System.out.println("Arquivo removido com sucesso.");
		}
	}
	
	public void removeDiretorio(String nome) {
		Diretorio dir = null;
		if (dirAtual == null) {
			for (int i = 0; i < principal.getDiretorios().size(); i++) {
				if (principal.getDiretorios().get(i).getNome().equals(nome)) {
					dir = principal.getDiretorios().get(i);
					break;
				}
			}
			if (dir == null) {
				System.err.println("O sistema n�o pode encontrar o diret�rio especificado.");
				return;
			} else {
				principal.getDiretorios().remove(dir);
				principal.atualizaTamanho();
				System.out.println("Diret�rio removido com sucesso.");
			}
		} else {
			for (int i = 0; i < dirAtual.getDiretorios().size(); i++) {
				if (dirAtual.getDiretorios().get(i).getNome().equals(nome)) {
					dir = dirAtual.getDiretorios().get(i);
					break;
				}
			}
			if (dir == null) {
				System.err.println("O sistema n�o pode encontrar o diret�rio especificado.");
				return;
			} else {
				principal.getDiretorios().remove(dir);
				principal.atualizaTamanho();
				System.out.println("Diret�rio removido com sucesso.");
			}
		}
	}
	
	public void infoDir() {
		if (dirAtual == null) {
			System.err.println("O sistema n�o encontra-se em um diret�rio.");
		} else {
			System.out.println(dirAtual.toString());
		}
	}
	
	public void infoPart() {
		System.out.print(principal.toString());
	}
	
	public void infoArq(String nome) {
		if (dirAtual == null) {
			System.err.println("O sistema n�o encontra-se em um diret�rio.");			
		} else {
			Arquivo arq = null;
			for (int i = 0; i < dirAtual.getArquivos().size(); i++) {
				if (dirAtual.getArquivos().get(i).getNome().equals(nome)) {
					arq = dirAtual.getArquivos().get(i);
					break;
				}
			}
			if (arq == null) {
				System.err.println("O sistema n�o pode encontrar o arquivo especificado.");
			} else {
				System.out.println(arq.toString());
			}
		}
	}
	
}
