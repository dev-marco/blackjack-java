package Main;

import java.util.Scanner;
import Game.Color;
import Game.Game;

public class BlackJack {

	public static void main(String args[]){
		Scanner scan = new Scanner(System.in);
		Game game;
		for(String s : args)
			if(s.equals("-c")){
				// Ativar manipulacao de cores
				// Do contrario, enum retornaria somente strings vazias
				Color.activate();
			}

		game = Game.getInstance();

		System.out.println("\nBem vindo ao BlackJack");

		do {
			System.out.println();
			game.play();
			System.out.print("Jogar novamente? <Y/N>\n> ");
		} while(scan.next().toUpperCase().equals("Y"));

		System.out.println("\nObrigado!");
	}

}
