package Game;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Collections;
import java.util.PriorityQueue;

public final class Game {

	private static Game game;

	private boolean last;
	private Deck deck;
	private byte max;
	private PriorityQueue<Byte> spec;
	private Player players[];

	// Proibe instanciacao de Game fora da classe
	private Game(){
		// Inicializa seu baralho
		this.deck = new Deck();
	}

	// Retorna singleton de game
	public static Game getInstance(){
		if(game == null)
			game = new Game();
		return game;
	}

	// Retorna baralho do jogo atual
	public Deck getDeck(){
		return this.deck;
	}

	// Retorna maximo do jogo atual
	public byte getMax(){
		return this.spec.size() > 0 ? this.spec.peek() : 0;
	}

	// Retorna se jogador atual e' o ultimo
	public boolean isLast(){
		return this.last;
	}

	// Funcao onde ocorre o jogo
	public void play(){
		int i, numhum, numbot, total, minbot;
		byte score;
		Player winner, pmax = null;
		LinkedList<Player> win = new LinkedList<Player>();
		Scanner scan = new Scanner(System.in);

		// Maximo principal para ser utilizado pelo script
		// para calcular melhor jogador
		this.max = 0;

		// Indica se o jogador atual e' o ultimo
		this.last = false;

		// Recebe total de jogadores humanos
		System.out.print("Quantos jogadores humanos participarão? <0+>\n> ");
		numhum = scan.nextInt();
		while(numhum < 0){
			System.out.print("\nNúmero inválido de jogadores\n> ");
			numhum = scan.nextInt();
		}

		// Calcula minimo de bots baseado no total de jogadores humanos
		// Calculo obriga usuario a respeitar minimo de 2 jogadores
		minbot = numhum == 0 ? 2 : (numhum == 1 ? 1 : 0);

		// Recebe total de jogadores da IA
		System.out.print("\nQuantos bots participarão? <" + minbot + "+>\n> ");
		numbot = scan.nextInt();
		while(numbot < minbot){
			System.out.print("\nNúmero inválido de jogadores\n> ");
			numbot = scan.nextInt();
		}
		scan.nextLine();

		// Vetor de jogadores
		total = numhum + numbot;
		players = new Player[total];

		// spec e' um heap que indica um maximo especulativo, ou seja,
		// o maximo ate o momento que de alguem que pode nao ter jogado
		// Ele serve para ser passado seu maior valor para a IA, que ira'
		// utiliza-lo como threshold para trocar para o modo probabilistico
		spec = new PriorityQueue<Byte>(total, Collections.reverseOrder());

		// Embaralha baralho e inicia contagem das cartas
		this.deck.reset();

		System.out.println();

		// Recebe nomes de jogadores humanos
		for(i = 0; i < numhum; i++){
			System.out.print("Nome do jogador " + (i + 1) + "\n> ");
			players[i] = new Human(scan.nextLine());
			System.out.println();
		}

		// Cria jogadores AI
		for(i = numhum; i < total; i++)
			players[i] = new AI();

		// Distribui primeiras cartas e testa maximo especulativo
		for(Player player : players){
			player.addCard(deck.getCard());
			player.addCard(deck.getCard());
			System.out.println(player);
			score = player.getTotal();
			// Testa BlackJack instantaneo
			if(score == 21){
				System.out.println(Color.GREEN.toString() +
					player.getName() + " obteve um BlackJack!\n" +
						Color.RESET.toString());
				win.add(player);
				this.max = 21;
			}
			spec.add(score);
		}

		// Evita que eles joguem caso alguem ja tenha vencido
		// apenas com as cartas iniciais
		if(win.size() == 0){
			for(i = 0; i < players.length; i++){
				System.out.println("--------------------------");
				System.out.println("Vez de " + players[i].getName());
				System.out.println("--------------------------\n");
				System.out.println(players[i]);

				// Testa se e' ultimo jogador
				this.last = (i + 1) == players.length;

				// Remove ele do heap de especulativos
				spec.remove(players[i].getTotal());

				// Evita que o ultimo jogador jogue quando
				// todos estiverem estourados
				if(!this.last || spec.size() > 0)
					players[i].play();

				// Teste do total
				score = players[i].getTotal();
				if(score > 21){
					// Estourou
					System.out.println(Color.RED.toString() +
						players[i].getName() + " estourou :(\n" +
							Color.RESET.toString());
				}
				else{
					// Reinsere ele no heap de especulativos
					spec.add(score);

					if(score == 21){
						// Caso onde ocorre BlackJack
						System.out.println(Color.GREEN.toString() +
							players[i].getName() + " obteve um BlackJack!\n" +
								Color.RESET.toString());
					}
					else{
						// Caso onde jogador para
						System.out.println(Color.YELLOW.toString() +
							players[i].getName() + " parou com " +
								players[i].getTotal() + '\n' + 
									Color.RESET.toString());
					}

					if(score > this.max){
						// Novo maximo
						// Limpa vencedores
						win.clear();
						// Jogador e' o vencedor
						win.add(players[i]);
						// Melhor pontuacao
						this.max = score;
					}
					else if(score == this.max){
						// Igualou anterior
						// Adiciona aos vencedores
						win.add(players[i]);
					}
				}
			}
		}

		if(win.size() == 1){
			// Apenas um jogador ganhou
			winner = win.removeFirst();
			System.out.println("O grande vencedor foi " +
				winner.getName() + " com " + this.max + " pontos.\nParabéns " +
					winner.getName() + ", você brilhou!\n");
		}
		else{
			// Varios jogadores empataram em primeiro lugar
			System.out.println("Os seguintes jogadores empataram com " +
				this.max + ':');
			for(Player player : win)
				System.out.println(player.getName());
			System.out.println();
		}
		// E' impossivel nenhum jogador vencer ja' que o programa
		// nao deixa o ultimo receber as cartas caso todos tenham estourado
		// e ja o define como vencedor da partida
	}

}