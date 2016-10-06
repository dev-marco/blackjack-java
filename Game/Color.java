package Game;

// enum que representa as cores do ANSI
// seu retorno pode ser vazio se o modo cor estiver desativado
public enum Color {

	RESET  ("\033[0m"),
	BOLD   ("\033[1m"),
	RED    ("\033[1;31m"),
	GREEN  ("\033[1;32m"),
	YELLOW ("\033[1;33m"),
	BLUE   ("\033[1;34m"),
	MAGENTA("\033[1;35m"),
	CYAN   ("\033[1;36m"),
	WHITE  ("\033[1;34m"),
	BLACKBG("\033[1;47;30m"),
	REDBG  ("\033[1;47;31m");
	private String str;
	private static boolean active;

	// Ativa cores
	public static void activate(){
		active = true;
	}

	private Color(String str){
		this.str = str;
	}

	// Retorna codigo ANSI da cor, fundo ou reset
	@Override
	public String toString(){
		return Color.active ? this.str : "";
	}

}