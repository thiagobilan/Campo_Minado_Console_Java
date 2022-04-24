package br.com.bilangieri.cm;

import br.com.bilangieri.cm.model.Tabuleiro;
import br.com.bilangieri.cm.view.TabuleiroConsole;

public class Application {
	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		new TabuleiroConsole(tabuleiro);
		
	}
}