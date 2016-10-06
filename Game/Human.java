package Game;

import java.util.Scanner;

public class Human extends Player {

	public Human(String nome){
		super(nome);
	}

	@Override
	public void play(){
		boolean stop = false;
		Scanner scan = new Scanner(System.in);
		// Recebe baralho oficial do jogo
		Deck deck = Game.getInstance().getDeck();
		while(this.getTotal() < 21 && !stop){
			System.out.print(this.getName() +
				", você deseja outra carta? <Y/N>\n> ");
			switch(scan.nextLine().toUpperCase()){
				// Caracteres vazios representam sim
				case "": case "Y":
					System.out.println();
					// Seleciona uma carta para o jogador
					this.addCard(deck.getCard());
					System.out.println(this);
				break;
				case "N": stop = true; System.out.println(); break;
				default:
					System.out.println("\nComando inválido. Entre com Y ou N.");
				break;
			}
		}
	}

}