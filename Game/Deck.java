package Game;

import java.util.Random;
import java.util.LinkedList;

public class Deck {
	
	private Card[] cards = new Card[52];
	private byte next;
	
	public Deck(){
		byte type = 0;
		for(byte i = 0; i < 52; type++){
			this.cards[i++] = new Card(type, (byte) 0);
			this.cards[i++] = new Card(type, (byte) 1);
			this.cards[i++] = new Card(type, (byte) 2);
			this.cards[i++] = new Card(type, (byte) 3);
			type %= 13;
		}
	}

	// Volta apontador das cartas para inicio e embaralha
	public void reset(){
		this.next = 0;
		this.shuffle();
	}

	// Embaralha aleatoriamente
	private void shuffle(){
		Random gen = new Random();
		int pos;
		Card tmp;
		for(int i = 0; i < 52; i++){
			pos = gen.nextInt(52);
			tmp = this.cards[pos];
			this.cards[pos] = this.cards[i];
			this.cards[i] = tmp;
		}
	}
	
	// Retorna proxima carta
	// Arruma apontador das cartas caso atinja o fim do baralho
	public Card getCard(){
		Card send = this.cards[this.next++];
		if(this.next == 52)
			this.reset();
		return send;
	}

	// Calcula chance de vitoria, continuacao e derrota baseado no
	// baralho atual e nas cartas passadas via argumento
	public float[] getChances(LinkedList<Card> cards){
		boolean a = false;
		byte i, value, spec, total = 0, times[] = new byte[] {0, 0, 0};
		float size, chances[] = new float[] {100f, 100f, 100f};
		// Calcula total e testa condicao do As
		for(Card card : cards){
			value = card.getValue();
			if(value == 1 && !a)
				a = true;
			else
				total += value;
		}
		// Inclui cada carta ao fim da sequencia e computa
		// seu resultado (V = 21, C < 21, D > 21)
		for(i = this.next; i < 52; i++){
			spec = total;
			value = this.cards[i].getValue();
			// Se a carta nao for um a ou ja tivermos um A na mao
			if(value != 1 || a)
				spec += value;
			// Se a carta for um a ou ja tivermos um A na mao
			if(a || value == 1){
				// A deve valer 11 ou 1 caso 11 ultrapasse 21
				value = (byte) (spec + 11);
				if(value <= 21)
					spec = value;
				else
					spec++;
			}
			if(spec == 21)
				times[0]++;
			else if(spec < 21)
				times[1]++;
			else
				times[2]++;
		}
		size = 52 - this.next;
		chances[0] *= times[0] / size;
		chances[1] *= times[1] / size;
		chances[2] *= times[2] / size;
		return chances;
	}
	
}