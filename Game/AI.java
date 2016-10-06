package Game;

import java.util.Random;

public class AI extends Player {

	// Primeiros nomes
	static private String[] fst = {"Um ", "Outro ", "Qualquer ", "O ", "Lo "};
	// Segundos nomes
	static private String[] snd = {"Doido ", "Maluco ", "Jogador ",
		"Medroso ", "Confiante ", "Covarde ", "Mal-vestido ", "Zumbi ",
			"Cabeludo ", "Aleatório ", "Nebuloso ", "Dorminhoco ", "Ogro ",
				"Humano ", "Abacaxi ", "Guepardo ", "Computeiro ",
					"Cogumelo ", "Dinossauro ", "Pula-Pulha "};
	// Terceiros nomes
	static private String[] trd = {"No Controle", "No Pedaço", "Escolhendo",
		"Contra o Tempo", "Chorando", "Correndo", "Contra-Ataca",
			"Malandro", "Pneumático", "Gripado", "Curioso", "Na Maionese",
				"Aloprado", "Doidão", "Apressado", "De Cueca"};

	// Atributo indicando coragem do jogador
	// Coragem varia de 40.0 a 80.0 uniformemente e representa
	// o valor maximo da chance de perder pra ele jogar
	private float corage;

	// Gera nome aleatorio pro jogador
	static private String randName(){
		Random gen = new Random();
		return fst[gen.nextInt(fst.length)] +
		       snd[gen.nextInt(snd.length)] +
		       trd[gen.nextInt(trd.length)];
	}

	public AI(){
		super(AI.randName());
		// Inicializacao da coragem e' aleatoria
		this.corage = 40f + ((new Random()).nextFloat() * 40f);
	}

	@Override
	public void play(){
		Game game = Game.getInstance();
		Deck deck = game.getDeck();
		boolean stop = false;
		byte total = this.getTotal();
		// Condicao de pegar carta em ordem de prioridade:
		// 1: Se tiver alguem com uma pontuacao valida maior que a dele
		// 2: Se ele nao for o ultimo jogador (causaria vitoria automatica) e
		//    a probabilidade de perder for menor que a coragem
		while(total < 21 && (game.getMax() > total || (!game.isLast() &&
			  deck.getChances(this.cards)[2] < this.corage))){
			this.addCard(deck.getCard());
			total = this.getTotal();
			System.out.println(this);
		}
	}

}