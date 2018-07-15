package Files;

import java.util.Scanner;

public class PromptComando {
	
	private static String help() {
		String help = "\nComandos dispon�veis:\n";
		help += "\texit - fecha o programa\n";
		help += "\tinfoArq - informa��es de um arquivo\n";
		help += "\tinfoDir - informa��es do diret�rio atual\n";
		help += "\tinfoPart - informa��es da parti��o\n";
		help += "\tremoveDir - remove um diret�rio dentro do diret�rio atual\n";
		help += "\tremoveArq - remove um arquivo dentro do diret�rio atual\n";
		help += "\tcriaArq - cria um arquivo do tamanho que o usu�rio deseja\n";
		help += "\tcriaArqTexto - cria um arquivo de texto com os dados de entrada do usu�rio\n";
		help += "\tcriaDir - cria um diret�rio dentro do caminho atual\n";
		help += "\tcriaDirPath - cria um diret�rio dentro do caminho informado\n";
		help += "\tnavega - navega para um caminho informado\n";
		help += "\tenter - entra em um diret�rio dentro do diret�rio/parti��o atual\n";
		help += "\tout - sai do diret�rio atual para o anterior\n";
		help += "\tinfoBlocoArq - informa��es a respeito dos blocos do disco referente aos "
				+ "arquivos do diret�rio atual\n";
		help += "\tinfoBlocoDir - informa��es a respeito dos blocos do disco referente ao "
				+ "diret�rio atual\n";
		help += "\tinfoBloco - informa��es a respeito dos blocos do disco referente a todos "
				+ "os blocos existentes\n";
		help += "\tcompacta - compacta a mem�ria\n";
		help += "\thelp - comandos suportados pelo programa\n";
		return help;
	}
	
	public static void main(String[] args) {
		
		GerenciaArquivos manager = GerenciaArquivos.getInstance();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String nome;
		String key;
		String text;
		String caminho;
		int tamanho;
		System.out.println("\t\t\tBem-vindo!");
		System.out.print("D� um nome a sua parti��o: ");
		nome = sc.nextLine();
		System.out.print("Informe o tamanho da sua parti��o: ");
		tamanho = sc.nextInt();
		sc.nextLine();
		Particao principal = new Particao(nome, tamanho);
		manager.setPrincipal(principal);
		System.out.println("Parti��o criada com sucesso!");
		while(true) {
			System.out.print(manager.getCaminho() + ">");
			key = sc.nextLine();
			switch (key) {
			case "exit":
				return;
			case "infoArq":
				System.out.print("Nome do arquivo: ");
				nome = sc.nextLine();
				manager.infoArq(nome);
				break;
			case "infoDir":
				manager.infoDir();
				break;
			case "infoPart":
				manager.infoPart();
				break;
			case "removeDir":
				System.out.print("Nome do diret�rio: ");
				nome = sc.nextLine();
				manager.removeDiretorio(nome);
				break;
			case "removeArq":
				System.out.print("Nome do arquivo: ");
				nome = sc.nextLine();
				manager.removeArquivo(nome);
				break;
			case "criaArq":
				System.out.print("Nome do arquivo: ");
				nome = sc.nextLine();
				System.out.print("Tamanho do arquivo: ");
				tamanho = sc.nextInt();
				sc.nextLine();
				manager.criaArquivo(nome, tamanho);
				break;
			case "criaArqTexto":
				System.out.print("Nome do arquivo: ");
				nome = sc.nextLine();
				System.out.println("Texto do arquivo: ");
				text = sc.nextLine();
				manager.criaArquivoDados(nome, text);
				break;
			case "criaDir":
				System.out.print("Nome do diret�rio: ");
				nome = sc.nextLine();
				manager.criaDiretorio(nome);
				break;
			case "criaDirPath":
				System.out.print("Nome do diret�rio: ");
				nome = sc.nextLine();
				System.out.print("Caminho do diret�rio: ");
				caminho = sc.nextLine();
				manager.criaDiretorio(caminho, nome);
				break;
			case "navega":
				System.out.print("Caminho: ");
				caminho = sc.nextLine();
				manager.navega(caminho);
				break;
			case "help":
				System.out.println(help());
				break;
			case "enter":
				System.out.print("Diret�rio: ");
				nome = sc.nextLine();
				manager.enter(nome);
				break;
			case "out":
				manager.out();
				break;
			case "infoBlocoArq":
				manager.infoBlocoArq();
				break;
			case "infoBlocoDir":
				manager.infoBlocoDir(manager.getDirAtual());
				break;
			case "infoBloco":
				manager.infoBloco();
				break;
			case "compacta":
				manager.compacta();
				break;
				
			default:
				System.err.println("\nComando n�o reconhecido pelo sistema.");
				break;
			}
		}
		
	}
	
}
