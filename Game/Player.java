package Game;

import java.util.Random;
import java.util.LinkedList;

public abstract class Player {

	protected LinkedList<Card> cards;
	private boolean a;
	private String name;
	private byte total, cache;
	
	// Contrato que todos os tipos de
	// jogadores devem saber jogar
	abstract public void play();

	public Player(String name){
		this.name = name;
		this.cards = new LinkedList<Card>();
	}
	
	// Adiciona uma carta a mao do jogador
	public void addCard(Card card){
		byte val = card.getValue();
		this.cards.add(card);
		if(val == 1 && !this.a)
			this.a = true;
		else
			this.total += val;
		// Zera o cache pra obrigar ter o valor calculado novamente
		cache = 0;
	}
	
	// Calcula total em maos do jogador
	public byte getTotal(){
		byte higher;
		// Testa se o cache foi zerado
		if(cache == 0){
			// Se o cache foi zerado, recalcula valor de acordo com As
			if(this.a){
				higher = (byte) (this.total + 11);
				if(higher <= 21) cache = higher;
				else cache = (byte) (this.total + 1);
			}
			else cache = this.total;
			// Ao fim, cache sera' atualizado com um numero diferente de 0
			// visto que 0 nao e' um valor valido
		}
		// Retorna o novo (ou antigo) cache
		return cache;
	}

	public String getName(){
		return this.name;
	}

	@Override
	public String toString(){
		// Recebe chances
		float[] chances = Game.getInstance().getDeck().getChances(this.cards);
		// Sequencias de cores em ANSI
		String gre = Color.GREEN.toString(),
			   yel = Color.YELLOW.toString(),
			   red = Color.RED.toString(),
			   rst = Color.RESET.toString();
		// Constroi string
		// Formato:
		//
		// ${NOME}: <V ${VENCER}%> <C ${CONTINUAR}%> <P ${PERDER}%>
		// ${CARTA1} ${CARTA2} ... <${TOTAL}>
		//
		String out = this.getName() + ": " +
			gre + "<V " + String.format("%.2f", chances[0]) + "%> "  + rst +
			yel + "<C " + String.format("%.2f", chances[1]) + "%> "  + rst +
			red + "<P " + String.format("%.2f", chances[2]) + "%>\n" + rst;
		for(Card card : this.cards)
			out += card.toString() + ' ';
		out += '<' + String.format("%02d", this.getTotal()) + ">\n";
		return out;
	}

}