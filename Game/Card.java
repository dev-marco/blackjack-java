package Game;

public class Card {

	private byte value;
	private String display;

	public Card(byte card, byte suit){
		String blk = Color.BLACKBG.toString(),
		       red = Color.REDBG.toString(),
		       rst = Color.RESET.toString(), symbol;
		// Constroi string de definicao da carta
		// Definicao da cor e naipe da carta
		switch(suit){
			case 0:  this.display = red; symbol = "♥ " + rst; break;
			case 1:  this.display = blk; symbol = "♠ " + rst; break;
			case 2:  this.display = red; symbol = "♦ " + rst; break;
			case 3:  this.display = blk; symbol = "♣ " + rst; break;
			default: this.display = "";  symbol = "";
		}
		// Definicao da figura e valor da carta
		switch(card){
			case 9:  this.value = 10; this.display += " J" + symbol; break;
			case 10: this.value = 10; this.display += " Q" + symbol; break;
			case 11: this.value = 10; this.display += " K" + symbol; break;
			case 12: this.value = 1;  this.display += " A" + symbol; break;
			default:
				this.value = (byte) (card + 2);
				this.display += String.format("%2d", this.value) + symbol;
			break;
		}
	}

	// Retorna valor da carta (pontos)
	public byte getValue() {
		return this.value;
	}

	// Retorna representacao da carta
	@Override
	public String toString(){
		return this.display;
	}

}